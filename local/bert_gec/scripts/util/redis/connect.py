import redis
import yaml


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
        # 加载配置文件
        with open("application.yml", encoding='utf-8') as config_file:
            # 解析配置文件
            config = yaml.load(config_file, Loader=yaml.FullLoader)
            # 创建连接池
            RedisUtil.pool = redis.ConnectionPool(
                host=config["redis"]["host"], port=config["redis"]["port"], password=config[
                    "redis"]["password"], max_connections=config["redis"]["pool"]["max-active"]
            )
