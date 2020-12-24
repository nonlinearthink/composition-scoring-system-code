import abc
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

    def __init__(self):
        """初始化函数"""
        # 获取Redis连接
        self.conn = RedisUtil.get_connection()
        # 设置订阅主题
        self.title = None

    def __init__(self, title: str):
        """含参数的初始化函数

        Args:
            title (str): 订阅主题
        """
        self.conn = RedisUtil.get_connection()
        self.title = title

    def subscribe(self, title: str):
        """设置订阅

        Args:
            title (str): 订阅主题
        """
        self.title = title

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

    def publish(self, message: str):
        """发布消息

        Args:
            message (str): 消息
        """
        # 添加到消息队列尾
        self.conn.rpush(self.title, message)
