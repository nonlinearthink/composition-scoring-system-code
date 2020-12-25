class RuntimeConfigurator(object):
    """运行时配置类

    在程序运行时，为所有模块提供统一的配置信息存储服务。
    """
    
    config = {} # 配置信息

    @staticmethod
    def load():
        """加载配置信息
        
        Returns:
            运行时配置信息
        """
        return RuntimeConfigurator.config

    @staticmethod
    def set(key, value):
        """添加或更改配置项"""
        RuntimeConfigurator.config[key] = value
