#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import json
from word_correction.CorrectWords import correct_text_generic

# special token
UNK = "-unk-"


def get_item(id, sen_id, pred):
    item = dict()
    item["id"] = id
    item["sen_id"] = sen_id
    item["pred"] = pred

    return item


def compare_word(id: str, sen_lis: list):
    '''
    param: sen_lis,a python list which contain all raw sentence that is similar with front
    return: a python list, each items in this list is a dict, which contain 2 attributions(write into database)
            a score, which measure the Lexical accuracy
    e.g. [{ id: "01",
            sen_id: 0,
            pred: ["it","is","an","apple","."]}]
    '''

    result = []
    lexical_acc = 0.
    for i, sen in enumerate(sen_lis):
        # perform the word correction
        sen_pred = correct_text_generic(sen)
        sen_pred_lis = sen_pred.strip().split()
        sen_ori_lis = sen.strip().split()
        param = {"id": id, "sen_id": i, "pred": sen_pred_lis}
        item = get_item(**param)
        result.append(item)

        # compute the lexical accuracy
        assert len(sen_ori_lis) == len(sen_pred_lis)
        error_cnt = 0  # error word num
        all_cnt = 0  # all num of words
        for j in range(len(sen_ori_lis)):
            ori_word = sen_ori_lis[j]
            pred_word = sen_pred_lis[j]

            if pred_word == UNK or pred_word != ori_word:
                error_cnt += 1
            all_cnt += 1
        # TODO: optimization
        accuracy = 1 - (error_cnt / all_cnt)
        assert 0 - 1e-4 < accuracy < 1 + 1e-4
        lexical_acc += accuracy
    lexical_acc /= (i + 1)

    out_dir = "../cache_file/compare_result"
    if not os.path.exists(out_dir):
        os.mkdir(out_dir)
    with open(os.path.join(out_dir, id + "_word.json"), "w") as f:
        json.dump(result, f)
    with open(os.path.join(out_dir, id + "_word_score.txt"), "w") as f:
        f.write(str(lexical_acc) + "\n")

    assert 0 - 1e-4 < lexical_acc < 1 + 1e-4

    return result, lexical_acc
