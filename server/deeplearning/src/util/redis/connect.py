import redis
import runtime


class RedisUtil(object):
    """Redis连接工具类"""

    pool = None  # 连接池

    @staticmethod
    def get_connection():
        """从连接池获取Redis连接

        Returns:
            Redis连接
        """
        # 如果连接池为空，创建连接池
        if RedisUtil.pool is None:
            RedisUtil.on_connection()
        # 返回Redis连接
        return redis.Redis(connection_pool=RedisUtil.pool)

    @staticmethod
    def on_connection():
        """创建Redis连接"""
        # 获取运行时Redis配置
        config = runtime.RuntimeConfigurator.load().get("redis")
        # 创建连接池
        RedisUtil.pool = redis.ConnectionPool(
            host=config["host"], port=config["port"], password=config["password"], max_connections=config["pool"]["max-active"]
        )
