import pandas
import re

def load_training_data(train_path,essay_set = 1):
    train_path = train_path
    training_data = pandas.read_csv(train_path,encoding="utf-8",delimiter='\t')
    resolved_score = training_data[training_data['essay_set'] == essay_set]['domain1_score']
    essay_ids = training_data[training_data['essay_set'] == essay_set]['essay_id']
    essays = training_data[training_data['essay_set'] == essay_set]['essay']
    essay_list = []
    for idx, essay in essays.iteritems():
        essay_list.append(clean_tokenize(essay))
    return essay_list, resolved_score.tolist(), essay_ids.tolist()

def clean_tokenize(data):
    data = re.sub(r"[^A-Za-z0-9(),!?\'\`]", " ", data)
    data = re.sub(r"\'s", " \'s", data)
    data = re.sub(r"\'ve", " \'ve", data)
    data = re.sub(r"n\'t", " n\'t", data)
    data = re.sub(r"\'re", " \'re", data)
    data = re.sub(r"\'d", " \'d", data)
    data = re.sub(r"\'ll", " \'ll", data)
    data = re.sub(r",", " , ", data)
    data = re.sub(r"!", " ! ", data)
    data = re.sub(r"\(", " ( ", data)
    data = re.sub(r"\)", " ) ", data)
    data = re.sub(r"\?", " ? ", data)
    data = re.sub(r"\s{2,}", " ", data)
    data = data.lower()
    return [x.strip() for x in re.split('(\W+)?', data) if x.strip()]


def score_range(self):
    return {"1": (2, 12),"2": (1, 6),"3": (0, 3),"4": (0, 3),"5": (0, 4),"6": (0, 4),"7": (0, 30),"8": (0, 60)}

def normalize_score(self, essay_set_id, score):
    lo, hi = self.score_range[str(essay_set_id)]
    score = float(score)
    return (score - lo) / (hi - lo)


def vectorize_data(data, word_idx, sentence_size):
    E = []
    for essay in data:
        ls = max(0, sentence_size - len(essay))
        wl = []
        for w in essay:
            if w in word_idx:
                wl.append(word_idx[w])
            else:
                wl.append(0)
        wl += [0]*ls
        E.append(wl)
    return E

def convert_to_vec(vectorized_data, word2vec):
    total_vector = []
    for essay in vectorized_data:
        essay_vector = []
        for word in essay:
            essay_vector.append((word2vec[word]))
        total_vector.append(essay_vector)
    return total_vector

def convertword2vec(emb_path,dim=300):
    word2vec = []
    word_idx = {}
    word2vec.append([0]*dim)
    count = 1
    with open(emb_path,"r") as f:
        for line in f:
            l = line.split()
            word = l[0]
            vector = list(map(float, l[1:]))
            word_idx[word] = count
            word2vec.append(vector)
            count += 1
    return word_idx, word2vec
