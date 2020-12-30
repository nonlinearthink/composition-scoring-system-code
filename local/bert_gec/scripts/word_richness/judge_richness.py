import nltk
import collections
import re
import os

# the pos tag which we care
richness_tag = {'JJ', 'RB', 'JJR', 'JJS', 'RBR', 'RBS', 'WRB', 'UH', 'MD'}


# find all lower token in corpus
def tokens(text):
    return re.findall("[a-z]+", text.lower())


def known(word: str):
    """
    judge whether the word exist in corpus
    """
    return word in word_counts


# open the file, count all words' frequency
# TODO: change the dict_path according to the call path
with open('./word_correction/dictionary.txt', 'r') as f:
    words = tokens(f.read())
word_counts = collections.Counter(words)


def get_word_rarity(word: str):
    # get the word's rarity in dictionary
    all_frequency = sorted(list(word_counts.values()), reverse=True)
    rank = all_frequency.index(word_counts.get(word)) + 1
    rarity = float(rank) / len(all_frequency)

    return rarity


def pos_tag_score(id: str, input: str):
    '''
    The function is exploited to measure the words' richness, the rule is as follows:
    - The repetition of adjectives and adverbs
    - The rarity of adjectives and adverbs
    - The use of comparative and superlative (also include modal or interjection) should be bonus items

    param: input, a string which include all processed sentences in input text ,split by '\n'
    return: score
    '''
    rare_jj = dict()
    rare_rb = dict()
    total_jj = 0
    total_rb = 0
    bonus_set = set()
    sen_lis = input.split('\n')
    for i, sen in enumerate(sen_lis):
        word_lis = list(filter(known, sen.split()))
        pos_tags = nltk.pos_tag(word_lis)
        for word, tag in pos_tags:
            if tag in richness_tag:
                if tag == 'JJ':
                    # get word rarity
                    rare_jj[word] = get_word_rarity(word)
                    total_jj += 1
                elif tag == 'RB':
                    # get word rarity
                    rare_rb[word] = get_word_rarity(word)
                    total_rb += 1
                else:
                    # bonus term
                    bonus_set.add(tag)

    # calculate JJ and RB
    n_jj = len(rare_jj)
    n_rb = len(rare_rb)
    # 1. word repetition
    repet_jj = n_jj / total_jj if total_jj != 0 else 0
    repet_rb = n_rb / total_rb if total_rb != 0 else 0
    # 2. word rarity
    rareity_jj = sum(list(rare_jj.values())) / n_jj if n_jj != 0 else 0
    rareity_rb = sum(list(rare_rb.values())) / n_rb if n_rb != 0 else 0

    # calculate total score
    # type_score = rareity_rb * 0.3 + repet_jj * 0.7
    score_jj = 0.7 * repet_jj + 0.3 * rareity_jj
    score_rb = 0.7 * repet_rb + 0.3 * rareity_rb
    type_score = (score_jj + score_rb) / 2.
    # bonus score = len(bonus_set) / 7.0
    bonus_score = len(bonus_set) / 7.0

    total_score = type_score * 0.8 + bonus_score * 0.2

    out_dir = "../cache_file/compare_result"
    if not os.path.exists(out_dir):
        os.mkdir(out_dir)
    with open(os.path.join(out_dir, id + "_richness_score.txt"), "w") as f:
        f.write(str(total_score)+'\n')

    assert 0 - 1e-4 < total_score < 1 + 1e-4


    return total_score


if __name__ == '__main__':
    print(word_counts)
