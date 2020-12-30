#!/usr/bin/python
# -*- coding: UTF-8 -*-

import sys
import os
import json
import re
import copy

punctuation = '!,;?"、，；'


def removePunctuation(text):
    text = re.sub(r'[{}]+'.format(punctuation), '', text)
    text = " ".join(text.split())  # avoid multiple space
    return text


def get_item(id, sen_id, pred):
    item = dict()
    item["id"] = id
    item["sen_id"] = sen_id
    item["pred"] = pred

    return item


# def compare(id, sen_id, src: str, trg: str):
#     '''
#     # compare the two sentences: src and trg ,finding the differences
#     # param: id, essay id in database
#     #        sen_id, the sentence id according to the essay
#     #        src: original sentence
#     #        trg: model predict sentence
#     # return: a python list, which contain all information about the grammar error of input sentence
#     '''
#
#     src = src.strip().lower()
#     trg = trg.strip().lower()
#
#     src_lis = src.split(" ")
#     trg_lis = trg.split(" ")
#     # use re to split sentence by punctuation, compare the span split by the punctuation
#     del_space = lambda x: x.strip()
#     src_spans = list(map(del_space, re.split('[\.\,\;\?\!\~]', src)))
#     trg_spans = list(map(del_space, re.split('[\.\,\;\?\!\~]', trg)))
#
#     # assumption: considering the convenience of this problem, the model will not change the punctuation in original sentence
#     #             thereby, we assert the span num of source sentence is equal to target sentence
#     sen_ans = []
#     if len(src_spans) == len(trg_spans):
#         for i, src_span in enumerate(src_spans):
#             trg_span = trg_spans[i]
#             if src_span != trg_span:
#                 token_id = src.index(
#                     src_span)  # the token_id means the character index of sentence(include one space) where error occur
#                 error_len = len(src_span.split(" "))
#                 pred = trg_span.split(" ")
#                 param = {"id": id, "sen_id": sen_id, "token_id": token_id, "error_len": error_len, "pred": pred}
#                 item = get_item(**param)
#                 sen_ans.append(item)
#     else:
#         # if the model change the punctuation, return the whole sentence
#         token_id = 0
#         error_len = len(src_lis)
#         pred = trg_lis
#         param = {"id": id, "sen_id": sen_id, "token_id": token_id, "error_len": error_len, "pred": pred}
#         item = get_item(**param)
#         sen_ans.append(item)
#
#     return sen_ans


def main(id: str, sen_lis: list):
    '''
    # this file used to load the original processed essay and model predicted one from cache file
    # compare the differences with them , find the grammar error position and get the model's prediction
    # param: id, the essay id in database
    #        sen_lis, a python list which contain all raw sentences split from original article
    # return: a python list, each items in this list is a dict, which contain 2 attributions(write into database)
    #         a score, which measure the grammar accuracy of input text
    # e.g. [{ id: "01",
    #         sentence_id: 0,
    #         pred: ["it","is","an","apple","."]}]
    '''

    cache_dir = "../cache_file"
    best_tok = os.path.join(cache_dir, "best_tok")

    if not os.path.exists(best_tok):
        raise IOError("the best tok has not been generated in cache file yet!")

    pred_file = open(os.path.join(best_tok, "{}.tok".format(id)), "r", encoding="utf-8")
    trg = pred_file.readlines()
    not_empty = lambda x: x.strip() and x
    comp_result = []
    error_cnt = 0
    all_cnt = 0

    # here, the assumption is the original sentence number is equal to target one
    assert len(trg) == len(sen_lis)

    for i, sen_pred in enumerate(trg):
        sen = sen_lis[i]
        temp_sen = list(filter(not_empty, re.split('\s', sen.strip())))

        cache_sen_pred = copy.copy(sen_pred)

        sen_ori = " ".join(temp_sen)
        sen_pred = sen_pred.strip()[:-2]

        # we don't care the punctuation
        sen_ori = removePunctuation(sen_ori)
        sen_pred = removePunctuation(sen_pred)

        # sen_pred_lis = sen_pred.strip().split()
        # sen_ori_lis = sen.strip().split()

        # for j in range(len(sen_ori_lis)):
        #     ori_word = sen_ori_lis[j]
        #     pred_word = sen_pred_lis[j]
        #
        #     if pred_word != ori_word:
        #         error_cnt += 1

        # grammar erroneous correction and grammar accuracy
        if sen_pred != sen_ori:
            print("pred: ", sen_pred, "\nori: ", sen_ori)
            param = {"id": id, "sen_id": i, "pred": cache_sen_pred.strip().split()}
            item = get_item(**param)
            comp_result.append(item)

            error_cnt += 1
        all_cnt += 1

    pred_file.close()

    # TODO: optimization
    grammar_accuracy = 1 - (error_cnt / all_cnt)

    out_dir = "../cache_file/compare_result"
    if not os.path.exists(out_dir):
        os.mkdir(out_dir)
    with open(os.path.join(out_dir, id + "_grammar.json"), "w") as f:
        json.dump(comp_result, f)
    with open(os.path.join(out_dir, id + "_grammar_score.txt"), "w") as f:
        f.write(str(grammar_accuracy) + "\n")

    assert 0 - 1e-4 < grammar_accuracy < 1 + 1e-4


    return comp_result, grammar_accuracy


if __name__ == '__main__':
    id = sys.argv[1]
    main(id)

    # debug the compare function
    # src_sen = 'this is an apple, ilove . it has ; well'
    # trg_sen = "this is an orange, ihate . i remmeber it ; let go on"
    # param = {"id": "001", "sen_id": "001", "src": src_sen, "trg": trg_sen}
    # ans=compare(**param)
    # print(ans)
    # print(len(ans))
    # wait=True

    # main("002")
