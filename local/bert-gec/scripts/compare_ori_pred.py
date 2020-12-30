#!/usr/bin/python
# -*- coding: UTF-8 -*-

import sys
import os
import json
import re

def get_item(id,sen_id,pred):
    item = dict()
    item["id"] = id
    item["sen_id"] = sen_id
    # item["token_id"] = token_id
    # item["error_len"] = error_len
    item["pred"] = pred

    return item

def compare(id, sen_id, src: str, trg: str):
    '''
    # compare the two sentences: src and trg ,finding the differences
    # param: id, essay id in database
    #        sen_id, the sentence id according to the essay
    #        src: original sentence
    #        trg: model predict sentence
    # return: a python list, which contain all information about the grammar error of input sentence
    '''

    src = src.strip().lower()
    trg = trg.strip().lower()

    src_lis = src.split(" ")
    trg_lis = trg.split(" ")

    # TODO: Need to optimize the matching algorithm in the future
    # use re to split sentence by punctuation, compare the span split by the punctuation
    del_space = lambda x:x.strip()
    src_spans = list(map(del_space,re.split('[\.\,\;\?\!\~]', src)))
    trg_spans = list(map(del_space,re.split('[\.\,\;\?\!\~]', trg)))

    # assumption: considering the convenience of this problem, the model will not change the punctuation in original sentence
    #             thereby, we assert the span num of source sentence is equal to target sentence
    sen_ans = []
    # if len(src_spans) == len(trg_spans):
    #     for i, src_span in enumerate(src_spans):
    #         trg_span = trg_spans[i]
    #         if src_span != trg_span:
    #             token_id = src.index(
    #                 src_span)  # the token_id means the character index of sentence(include one space) where error occur
    #             error_len = len(src_span.split(" "))
    #             pred = trg_span.split(" ")
    #             param = {"id": id, "sen_id": sen_id, "token_id": token_id, "error_len": error_len, "pred": pred}
    #             item = get_item(**param)
    #             sen_ans.append(item)
    # else:
        # if the model change the punctuation, return the whole sentence
    if src!=trg:
        pred = trg_lis
        param = {"id": id, "sen_id": sen_id, "pred": pred}
        item = get_item(**param)
        sen_ans.append(item)

    return sen_ans

def main(id: str):
    '''
    # this file used to load the original processed essay and model predicted one from cache file
    # compare the differences with them , find the grammar error position and get the model's prediction
    # param: id, the essay id in database
    # return: a python list, each items in this list is a dict, which contain 2 attributions(write into database)
    # e.g. [{ id: "01",
    #         sentence_id: 0,
    #         token_id: 6,
    #         error_len: 2
    #         pred: ["it","is","a","apple"] }]
    '''

    cache_dir = "../cache_file"
    test_cat = os.path.join(cache_dir, "test_cat")
    # nbest_tok = os.path.join(cache_dir,"nbest_tok")
    best_tok = os.path.join(cache_dir, "best_tok")
    
    if not os.path.exists(test_cat) or not os.path.exists(best_tok):
        raise IOError("cache file has not been generated yet!")

    pred_file = open(os.path.join(best_tok, "{}.tok".format(id)), "r", encoding="utf-8")
    trg = pred_file.readlines()
    comp_result = []
    with open(os.path.join(test_cat, "{}.src".format(id)), "r", encoding="utf-8") as src_file:
        # the second line is the original sentence
        for i, line in enumerate(src_file.readlines()):
            if i % 2 == 0:
                continue
            else:
                sen_id = int(i // 2)
                trg_sen = trg[sen_id]
                src_sen = line
                param = {"id": id, "sen_id": sen_id, "src": src_sen, "trg": trg_sen}
                sen_comp_result = compare(**param)
                comp_result += sen_comp_result
    pred_file.close()

    # debug ==================================
    out_dir = "../cache_file/compare_result"
    if not os.path.exists(out_dir):
        os.mkdir(out_dir)
    with open(os.path.join(out_dir, id + ".json"), "w") as f:
        json.dump(comp_result, f)
    # ========================================

    return comp_result


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
