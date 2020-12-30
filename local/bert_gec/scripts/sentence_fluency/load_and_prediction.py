#!/usr/bin/env python
# -*- coding: utf-8 -*-
# Author: Lou

import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'  # mute the warning generate by tensorflow
os.environ["CUDA_VISIBLE_DEVICES"] = "-1"  # we don't use gpu when prediction

import numpy as np
import keras
import h5py
from h5py import File

from nltk import word_tokenize, sent_tokenize
from sentence_fluency.model import create_model, Neural_Tensor_layer, Temporal_Mean_Pooling
from sentence_fluency.data_process import vectorize_data, convert_to_vec, convertword2vec


def model_pred(model_path, emb_path, inputs, k_chose=6, skip_width=50, max_sent_size=1000):
    ''' this function used to load model from disk and utilize it to predict

    :param model_path: the best performance model save path
    :param emb_path: the pretrained vectors path
    :param inputs: a string , which has been processed and split by '\n'
    :param max_sent_size: the maximal sentence length
    :return: the average score of all sentences
    '''
    # TODO: pip install h5py==2.10.0 and you may need to download nltk.punkt, see:
    # https://blog.csdn.net/qq_41297934/article/details/111310009
    # load pretrained word
    word_idx, word2vec = convertword2vec(emb_path)
    # load model from disk
    model = keras.models.load_model(model_path, custom_objects={"Neural_Tensor_layer": Neural_Tensor_layer,
                                                                "Temporal_Mean_Pooling": Temporal_Mean_Pooling})
    # model.summary()

    essay_sen_lis = sent_tokenize(inputs)
    essay_words_lis = [word_tokenize(sen) for sen in essay_sen_lis]

    vectorized_data = vectorize_data(essay_words_lis, word_idx, max_sent_size)
    X = convert_to_vec(vectorized_data, word2vec)
    x = np.array(X)

    # take all sentences as a batch, the final score can be taken as average score
    score = model.predict(x)[0][0]

    # or, we can calculate the average score
    # score = 0.
    # for i, essay_words in enumerate(essay_words_lis):
    #     vectorized_data = vectorize_data([essay_words], word_idx, max_sent_size)
    #     X = convert_to_vec(vectorized_data, word2vec)
    #     x = np.array(X)
    #     # take all sentences as a batch, the final score can be taken as average score
    #     score += model.predict(x)[0][0]
    #
    # score /= (i + 1)

    # TODO: optimization
    # because of we put each sentence into model separately, so each sentences' score is very low due to the length
    # so, we just plus the score with a factor 7 empirically
    # we need to solve this problem in the future

    score = min(11.25,score+7)
    normal_score = score / 12.0

    return normal_score


if __name__ == '__main__':
    inputs = "So they always go to bed very late to watch TVs or play games.In addition, it takes students too much money to buy famous products.Apart of students use class time to play game or watch TV so that they couldn't focus attention on studying courses."
    model_path = "./checkpoints/k=6_essaytype=1_best.h5"
    emb_path = "./data/glove.6B.300d.txt"
    param = {"model_path": model_path, "emb_path": emb_path, "inputs": inputs}
    score = model_pred(**param)
    print("score:",score)
    wait = True
