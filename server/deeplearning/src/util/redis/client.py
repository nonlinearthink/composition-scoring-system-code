import abc
import functools
from util.redis.connect import RedisUtil


class RedisMessageQueueHandler:
    """Redis消息队列处理器

    抽象类，用于操作Redis消息队列的返回值
    """

    @abc.abstractmethod
    def on_listen(self, message: str):
        """处理消息函数

        抽象函数，对消息队列的单条返回值进行操作

        Args:
            message (str): 消息，Redis消息队列的返回值
        """
        raise NotImplementedError


class RedisListClient:
    def __init__(self, key: str):
        self.conn = RedisUtil.get_connection()
        self.key = key

    def listen(topic: str):
        """监听消息队列

        根据订阅监听对应的消息队列

        Args:
            handler (RedisMessageQueueHandler): 消息处理器
        """
        # 获取连接（闭包实现）
        conn = RedisUtil.get_connection()

        def setup(handler: RedisMessageQueueHandler.on_listen):

            @functools.wraps(handler)
            def event_loop(self):
                while True:
                    # 堵塞监听消息队列头
                    (key, value) = conn.blpop(topic)
                    # 解码
                    message = bytes.decode(value, encoding='utf-8')
                    # 把获取的值传给消息处理器
                    handler(self, message)
            return event_loop
        return setup

    def push(self, value: str):
        self.conn.rpush(self.key, value)


class RedisPubSubClient:
    """Redis消息队列

    使用Redis实现的消息队列
    """

    def __init__(self, topic: str):
        """含参数的初始化函数

        Args:
            title (str): 订阅主题
        """
        self.conn = RedisUtil.get_connection()
        self.topic = topic

    @staticmethod
    def listen(topic: str):
        """监听消息队列

        根据订阅监听对应的消息队列

        Args:
            handler (RedisMessageQueueHandler): 消息处理器
        """
        conn = RedisUtil.get_connection()
        client = conn.pubsub()
        client.subscribe(topic)

        def setup(handler: RedisMessageQueueHandler.on_listen):

            @functools.wraps(handler)
            def event_loop(self):
                # 堵塞监听
                for message in client.listen():
                    # 传递给消息处理器
                    handler(self, message)
            return event_loop
        return setup

    def publish(self, message: str):
        """发布消息

        Args:
            message (str): 消息
        """
        self.conn.publish(self.topic, message)
