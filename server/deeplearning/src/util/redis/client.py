import abc
from time import sleep
from util.redis.connect import RedisUtil


class RedisMessageQueueHandler:
    """Redis消息队列处理器

    用于操作Redis消息队列的返回值，需要实现抽象函数
    """

    @abc.abstractmethod
    def do_handle(self, message: str):
        """处理消息函数

        抽象函数，对消息队列的单条返回值进行操作

        Args:
            message (str): 消息，Redis消息队列的返回值
        """
        pass


class RedisMessageQueue:
    """Redis消息队列

    使用Redis实现的消息队列
    """

    @abc.abstractmethod
    def listen(self, handler: RedisMessageQueueHandler):
        """监听消息队列

        根据订阅监听对应的消息队列

        Args:
            handler (RedisMessageQueueHandler): 消息处理器
        """
        pass



class RedisListClient(RedisMessageQueue):
    def __init__(self, key):
        self.conn = RedisUtil.get_connection()
        self.key = key

    def listen(self, handler: RedisMessageQueueHandler):
        """监听消息队列

        根据订阅监听对应的消息队列

        Args:
            handler (RedisMessageQueueHandler): 消息处理器
        """
        while True:
            # 堵塞监听消息队列头
            (key, value) = self.conn.blpop(self.title)
            # 把获取的值传给消息处理器
            handler.do_handle(bytes.decode(value, encoding='utf-8'))

    def push(self, value: str):
        self.conn.rpush(self.key, value)


class RedisSubClient(RedisMessageQueue):
    """Redis消息队列

    使用Redis实现的消息队列
    """

    def __init__(self, title):
        """含参数的初始化函数

        Args:
            title (str): 订阅主题
        """
        self.conn = RedisUtil.get_connection()
        self.client = self.conn.pubsub()
        self.client.subscribe(title)

    def listen(self, handler: RedisMessageQueueHandler):
        """监听消息队列

        根据订阅监听对应的消息队列

        Args:
            handler (RedisMessageQueueHandler): 消息处理器
        """
        for message in self.client.listen():
            handler.do_handle(message)


class RedisPubClient:
    def __init__(self, title):
        """含参数的初始化函数

        Args:
            title (str): 订阅主题
        """
        self.conn = RedisUtil.get_connection()
        self.title = title

    def publish(self, message: str):
        """发布消息

        Args:
            message (str): 消息
        """
        self.conn.publish(self.title, message)
