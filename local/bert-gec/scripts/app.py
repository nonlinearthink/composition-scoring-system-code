import os
import subprocess
import time
import json
from util.redis import RedisMessageQueueHandler, RedisListClient, RedisPubSubClient

import compare_ori_pred
import generate_cat_source


class Application(RedisMessageQueueHandler):  # RedisMessageQueueHandler

    def __init__(self):
        super(Application, self).__init__()
        self.notify = RedisPubSubClient("scoring_finished")
        self.feedback = RedisListClient("scoring_result")

    @RedisListClient.listen("scoring_task")
    def on_listen(self, message):  # message
        """
        docstring
        """
        print(message)
        message = json.loads(message)
        print(type(message))
        # 模拟模型预测
        # id = "006"
        # context = "He had little to eat and a large house to live in.\nHe had no sooner arrived when he fell ill.\nIf you go this way, and you will soon see the hospital.\nWhat a beautiful weather we are having today!\nPlease give my best regard to your parents.\nI have got good marks in all my subject."

        # tokenize and chunking...
        id = message["compositionId"]
        content = message["compositionBody"]
        print("tokenization and chunking begin...")
        param = {"id": id,
                 "context": content}
        # print(param)
        processed_context = generate_cat_source.main(**param)
        # TODO: the processed_context used for word correction,then write the word correction information into database

        print("tokenization and chunking end!")

        # grammar correction
        print("grammar correction begin...")
        cmd = "bash generate.sh {}".format(id)
        subprocess.run(cmd, shell=True)
        # child = subprocess.Popen(cmd, shell=True)
        # child.wait()  # Block subprocess
        # child.terminate()
        # compare original sentence with target sentence which can be used for client displaying
        compare_result = compare_ori_pred.main(id)
        # TODO: write the compare_result into database
        print("grammar correction end!\n")
        result = {"composition_id": id, "processed_context": processed_context,
                  "compare_result": compare_result}
        print(result)
        # # 返回结果
        self.feedback.push(json.dumps(result))
        # # 发布完成通知
        self.notify.publish("ok")


def main():
    """主函数"""
    # 创建主程序类
    app = Application()
    # 开启监听
    app.on_listen()


if __name__ == "__main__":
    main()
