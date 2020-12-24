import yaml
import runtime
import util.redis as ru
import time


class Myhandler(ru.RedisMessageQueueHandler):
    """实现抽象类"""
    def do_handle(self, message):
        print(message)
        # 模拟模型预测
        time.sleep(10)
        # 创建发送返回结果的消息队列
        publish_queue = ru.RedisMessageQueue()
        publish_queue.subscribe("message2")
        # 返回结果
        publish_queue.publish(message+" 已处理")


def main():
    """主函数"""
    # 加载配置文件
    with open("application.yml", encoding='utf-8') as config_file:
        # 解析配置文件
        config = yaml.load(config_file, Loader=yaml.FullLoader)
        # 设置程序运行时配置
        runtime.RuntimeConfigurator.set("redis", config['redis'])
    # 创建模型预测任务消息队列
    task_mq = ru.RedisMessageQueue()
    task_mq.subscribe("message1")
    # 监听消息队列
    task_mq.listen(Myhandler())


if __name__ == "__main__":
    main()
