#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
os.environ["CUDA_VISIBLE_DEVICES"] = "7"

from keras.optimizers import Adam
import keras
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import cohen_kappa_score
from keras.callbacks import EarlyStopping, ModelCheckpoint
from scipy import stats
import numpy

from model import create_model
from data_process import convert_to_vec, convertword2vec, load_training_data, vectorize_data
from kit.plot import plot_analyse
from kit.code_trains_utf import handleEncoding
from load_and_prediction import model_pred

print("\n>>> essay auto scoring <<<")

# prepare data
print("prepare data...")
emb_path = './data/glove.6B.300d.txt'
word_idx, word2vec = convertword2vec(emb_path)
print("file transfer begin...")
# original_file = './data/training_set_rel3.tsv'
# target_file = "./data/training_set_rel3_utf8.tsv"
# try:
#     handleEncoding(original_file, target_file)
# except:
#     print("the format of your file can't be recognized. Set original file by default!")
#     target_file = original_file
print("file transfer end!")
target_file = "./data/training_set_rel3_utf8.tsv"

# here, we use prompt 1 from training, the main topic is persuasive / narrative  / expository
# perhaps we need to optimize it to avoid over-adapt
essay_set_id = 1
train_path = target_file
essay_list, resolved_scores, essay_id = load_training_data(train_path, essay_set_id)
vocab_size = len(word_idx) + 1
max_sent_size = 1000
vectorized_data = vectorize_data(essay_list, word_idx, max_sent_size)
X = convert_to_vec(vectorized_data, word2vec)
k_chose = 6
skip_width = 50

# build and compile model
print("bulding model...\n")
model = create_model(k=k_chose, coherence_width=skip_width, model_type='tensor')

model.compile(optimizer=Adam(lr=0.001, decay=1e-6), loss='mse')

model.summary()

# unfortunately, the validation and test set don't tagged,so we split the train set
X_train, X_test, y_train, y_test = train_test_split(X, resolved_scores, test_size=0.3, random_state=42)
X_train = numpy.array(X_train)
X_test = numpy.array(X_test)
y_train = numpy.array(y_train)
y_test = numpy.array(y_test)

out_dir = "./checkpoints"
if not os.path.exists(out_dir):
    os.mkdir(out_dir)

print("training begin...")
# use early stop to save time
earlystopping = EarlyStopping(monitor="val_loss", patience=10)
# use model_checkpoint to save best model
save_best_name = 'k={}_essaytype={}_best.h5'.format(k_chose, essay_set_id)
model_checkpoint = ModelCheckpoint(filepath=os.path.join(out_dir, save_best_name), monitor="val_loss",
                                   save_best_only=True)
call_backs = [earlystopping, model_checkpoint]
model.fit(X_train, y_train, batch_size=64, epochs=50, callbacks=call_backs,validation_data=(X_test, y_test), verbose=2)

# save last step model
save_last_name = 'k={}_essaytype={}_last.h5'.format(k_chose, essay_set_id)
model.save(os.path.join(out_dir, save_last_name))
# save_last_name = 'k={}_essaytype={}_last.json'.format(k_chose, essay_set_id)
# save_last_name = os.path.join(out_dir, save_last_name)
# save_weight = 'k={}_essaytype={}_last.json.h5'.format(k_chose, essay_set_id)
# save_weight = os.path.join(out_dir, save_weight)
# model_json = model.to_json()
# with open(save_last_name,"w") as f:
#     f.write(model_json)
# model.save_weights(save_weight)
print("model has been saved into {}".format(os.path.join(out_dir, save_last_name)))

# prediction
predictions = model.predict_on_batch(X_test)
y_val = [int(np.round(a)) for a in y_test]
y_pred = [int(np.round(a)) for a in predictions]
print("\nmodel prediction score(quadratic):")
print(cohen_kappa_score(y_val, y_pred, weights='quadratic'))

# plot and analyse
print("plot...")
# plot_analyse(y_pred, y_val, save_path=os.path.join(out_dir, "run_result.jpg"))

print("\nload best model and begin prediction")

inputs = "Compare and contrast knowledge is gained experience with knowledge gained from books."
score = model_pred(model_path=os.path.join(out_dir, save_best_name),emb_path=emb_path, inputs=inputs, k_chose=k_chose,
                   skip_width=skip_width)
print("===> Your input's score is %.1f (2 - 12) " % score)
