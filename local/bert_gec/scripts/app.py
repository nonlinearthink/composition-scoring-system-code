import os
import subprocess
import time
import json

from word_correction.compare_ori_pred import compare_word
from word_richness.judge_richness import pos_tag_score
from essay_length import length_scoring
# from util.redis import RedisMessageQueueHandler, RedisListClient, RedisPubSubClient

import compare_ori_pred, generate_cat_source


class Application(object):  # RedisMessageQueueHandler

    def __init__(self):
        super(Application, self).__init__()
        # self.notify = RedisPubSubClient("scoring_finished")
        # self.feedback = RedisListClient("scoring_result")

    # @RedisListClient.listen("scoring_task")
    def on_listen(self):  # message
        """
        docstring
        """
        # print(message)
        # message = json.loads(message)
        # print(type(message))

        # model prediction
        id = "01"
        # context = "He had little to eat and a large house to live in.\nHe had no sooner arrived when he fell ill.\nIf you go this way, and you will soon see the hospital.\nWhat a beautiful weather we are having today!\nPlease give my best regard to your parents.\nI have got good marks in all my subject."
        context = "Last year my father lost his job. At that time my parents felt a bit sad. I encouraged my father and said I was old enough and could do something to help. In order to help my parents, I took a part time job on weekends in the KFC near my home.Luckily, it didn't take long time for my father to find a new job in a company. With the money I earned through working I bought a pair of new shoes for my father to celebrate the good news. My parents were deeply moved."
        # context = "I funck your mother! but shet didn't love it ,saafsa"

        # tokenize and chunking...
        print("\n===preparation===\ntokenization and chunking begin...")
        param = {"id": id, "context": context}
        sen_lis, processed_context = generate_cat_source.main(**param)
        # with open("../cache_file/sen_lis.json","w") as f:
        #     json.dump(sen_lis,f)
        print("tokenization and chunking end!")

        # word erroneous correction
        print("\n===PART1===\nword correction begin...")
        compare_result_word, word_score = compare_word(id, sen_lis)
        print("word score:", word_score)
        # TODO:write the word correction information into database
        print("word correction end!")

        # grammar erroneous correction
        print("\n===PART2===\ngrammar correction begin...")
        cmd = "bash generate.sh {}".format(id)
        child = subprocess.Popen(cmd, shell=True)
        child.wait()  # Block subprocess
        # compare original sentence with target sentence which can be used for client displaying
        compare_result_grammar, grammar_score = compare_ori_pred.main(id, sen_lis)
        print("grammar score:", grammar_score)
        # TODO: write the grammar correction informaton into database
        print("grammar correction end!")

        # auto essay scoring
        print("\n===PART3===\nauto essay scoring begin...")

        print("auto essay scoring end!")

        # essay length scoring
        print("\n===PART4===\nessay length scoring begin...")
        length_score = length_scoring.main(id, processed_context)
        print("length score:", length_score)
        print("essay length scoring end!")

        # judge the richness
        # use nltk POS tag and chunk to analyse the input sentences
        # the score come from the harmonic mean of 'adjective' and 'adverb'
        # the use of comparative and superlative is also a bonus item
        print("\n===PART5===\njudge the richness of words begin...")
        richness_score = pos_tag_score(id, processed_context)
        print("richness score:", richness_score)
        print("judge the richness of words end!")

        # time.sleep(10)
        # # 返回结果
        # self.feedback.push(json.dumps({"message": message, "status": "已处理"}))
        # # 发布完成通知
        # self.notify.publish("ok")


def main():
    """主函数"""
    # 创建主程序类
    app = Application()
    # 开启监听
    app.on_listen()


if __name__ == "__main__":
    begin = time.time()
    main()
    end = time.time()

    print("\ntotally running time:%.2f" % ((end - begin) / 60.0))
