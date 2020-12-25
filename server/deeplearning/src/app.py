import time
from util.redis import RedisMessageQueueHandler, RedisListClient, RedisPubSubClient


class Application(RedisMessageQueueHandler):

    def __init__(self):
        self.notify = RedisPubSubClient("scoring_finished")
        self.feedback = RedisListClient("message")

    @RedisListClient.listen("test")
    def on_listen(self, message: str):
        """
        docstring
        """
        print(message)
        # 模拟模型预测
        time.sleep(10)
        # 返回结果
        self.feedback.push(message+" 已处理")
        # 发布完成通知
        self.notify.publish("ok")


def main():
    """主函数"""
    # 创建主程序类
    app = Application()
    # 开启监听
    app.on_listen()


if __name__ == "__main__":
    main()
