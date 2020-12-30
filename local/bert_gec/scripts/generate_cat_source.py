#!/usr/bin/python
# -*- coding: UTF-8 -*-

import argparse
import os
import sys
import json
import subprocess

import torch
from multiprocessing import Pool
import multiprocessing as multi
import re
from nltk.tokenize.treebank import TreebankWordDetokenizer as Detok
# from subword import apply_bpe_text
import apply_bpe_beta

# sys.path.append(os.path.dirname(__file__) + os.sep + '../')
sys.path.append("./")
sys.path.append("../")
sys.path.append("../../")


def detok(input):
    tokens = input.split()
    detokenizer = Detok()
    text = detokenizer.detokenize(tokens)
    text = re.sub('\s*,\s*', ', ', text)
    text = re.sub('\s*\.\s*', '. ', text)
    text = re.sub('\s*\?\s*', '? ', text)
    text = text.strip()

    return text


def process_context(context):
    '''
    param: context,
    type: str

    return: result,  a long string which has been split and tokenized, each sentence split with a '\n' ,
                    it used as model input, each line means a instance, the whole article is taken as a batch
            sen_lis, a list which contain all sentence split by [.?!], note each sentence don't has end token
                    it used as original sentence when doing word correction and grammar correction
            processed_context, a string which include all processed sentences in input text ,split by '\n'
    '''
    # split sentence
    not_empty = lambda x: x.strip() and x

    # use [. ? !] as sentence split token, and filter those empty sentence (e.g. "i like !!!",has multiple split token)
    # the sentence in the sen_lis are all raw sentence!
    # TODO : the len(sen_lis) should be equal to front end
    sen_lis = list(filter(not_empty, re.split("[\.\?\!]", context.replace("\n", " "))))
    data = [l.strip() + " ." for l in sen_lis]
    # tokenize
    p = Pool(multi.cpu_count())
    data = p.map(detok, data)
    p.close()
    # refactored to a long string
    result = "\n".join(data)

    return result, sen_lis


def main(id: str, context: str):
    '''
    used in exterior python file, input the id and context, return the final tokenized context,
    which can be used for word correction and grammar correction
    '''
    id = id
    context = context
    processed_context, sen_lis = process_context(context)

    # generate test.bpe.src
    test_bpe_src = apply_bpe_beta.bpe_process(processed_context)

    temp_src_context = processed_context.split('\n')
    temp_src_bpe = test_bpe_src.split('\n')
    test_cat_src = ""
    for c, b in zip(temp_src_bpe, temp_src_context):
        test_cat_src += c + "\n" + b + '\n'

    # save file to cache
    out_path = "../cache_file/test_cat"  # TODO: change this path to fit your own need
    if not os.path.exists(out_path):
        os.mkdir(out_path)
    with open(os.path.join(out_path, id + ".src"), "w") as f:
        f.write(test_cat_src)

    # return the processed context ,and will be used for word correction at down stream
    return sen_lis,processed_context


if __name__ == "__main__":
    # run this code, gain the article id and context, process it and then write it into database
    # parser = argparse.ArgumentParser()
    # parser.add_argument('--id', type=str, default=None, required=True, help="The essay id in database.")
    # parser.add_argument('--context', type=str, default=None, required=True,
    #                     help="The essay context load from database (redis)")
    # args = parser.parse_args()
    # id = args.id
    # context = args.context
    id = "003"
    context = "He had little to eat and a large house to live in.\nHe had no sooner arrived when he fell ill.\nIf you go this way, and you will soon see the hospital.\nWhat a beautiful weather we are having today!\nPlease give my best regard to your parents.\nI have got good marks in all my subject."

    # id = sys.argv[1]
    # context = sys.argv[2]
    # context = "it is happy tosee you.\ngd\nmama ilike you.\n"
    # id = "1"
    # context = "I've readthenovel the     third time.\n"
    # context += "He ran after the bus, and could    catch it.\n"
    # context += "I was about to going to bed when the telephone rang.\n"
    processed_context = process_context(context)
    # print(processed_context)
    # ==========================================================================================
    # if not os.path.exists("../process/vertification"):
    #     os.mkdir("../process/vertification")
    # with open("../process/vertification/processed.txt", "w") as f:
    #     f.write(processed_context)
    # =========================================================================================

    # generate test.bpe.src
    test_bpe_src = apply_bpe_beta.bpe_process(context)

    temp_src_context = processed_context.split('\n')
    temp_src_bpe = test_bpe_src.split('\n')
    test_cat_src = ""
    for c, b in zip(temp_src_bpe, temp_src_context):
        test_cat_src += c + "\n" + b + '\n'
    # print(test_cat_src)

    # use BERT to correct the context
    # activate = "conda activate torch_lrz"
    # child = subprocess.Popen(activate, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
    # t = child.communicate()
    # cmd = "python ../bert-nmt/interactive_func.py ../bert-fuse -s src -t trg --input {}".format(test_cat_src)
    # child = subprocess.Popen(cmd, stdout=subprocess.PIPE,stderr=subprocess.PIPE,shell=True)
    # result = child.communicate()
    # wait = True

    # save file to cache
    out_path = "../cache_file/test_cat"
    if not os.path.exists(out_path):
        os.mkdir(out_path)
    with open(os.path.join(out_path, id + ".src"), "w") as f:
        f.write(test_cat_src)
