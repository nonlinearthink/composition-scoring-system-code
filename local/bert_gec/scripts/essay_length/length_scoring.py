import math
import os

def length_score(length):
    '''
    this function increase monotonically, and the gradient decrease monotonically
    the range of the function is between 0 and 1
    the domain of definition is between 0 and +∞
    '''
    score = math.log(length+1) / math.log(length+1000)
    return score

def main(id: str, input: str):
    ''' The function is exploited to scoring essay according to its length

    param: input, a string which include all processed sentences in input text ,split by '\n'
    return: score
    '''
    # most essay all between 200-300 word
    # really low number of words will gain low score
    # gold number that is around 300 words will gain a high score, but it is not indefinite

    sen_lis = input.split('\n')
    words_cnt = 0
    not_empty = lambda x: x.strip() and x
    for i, sen in enumerate(sen_lis):
        words_lis = list(filter(not_empty,sen.strip().split()))
        words_cnt += len(words_lis)

    # vase_line = 250  # the normal factor which can be take as standard words number
    # math.log()

    score = length_score(words_cnt)

    out_dir = "../cache_file/compare_result"
    if not os.path.exists(out_dir):
        os.mkdir(out_dir)
    with open(os.path.join(out_dir, id + "_length_score.txt"), "w") as f:
        f.write(str(score) + '\n')

    assert 0 - 1e-4 < score < 1 + 1e-4

    return score


if __name__ == '__main__':
    print(length_score(1000))