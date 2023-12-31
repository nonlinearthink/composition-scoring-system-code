## Getting Started

启动 [UserApiApplication](src/main/java/cn/edu/zucc/se2020g11/api/UserApiApplication.java)

## Develop Tools

介绍本项目的一系列开发工具。

### Druid Stat

这是一个监控程序，当你启动 [UserApiApplication](src/main/java/cn/edu/zucc/se2020g11/api/UserApiApplication.java) 时，它就已经可以运行了。

访问 [localhost:8080/druid](https://localhost:8080/druid) ，你会看到一个登录界面，默认情况下，提供的账号是 `admin`，密码是 `123`。

即使是在部署阶段，这一功能依然能用，虽然很方便，但是这是不安全的功能，推荐使用复杂的密码。

### Swagger-UI

这是一个接口测试工具。

在开启 Spring Boot 之后，访问网址[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)。

[swagger-ui 官网网站](https://swagger.io/tools/swagger-ui/)

#### 生成 swagger 文档

获取 swagger 文档的 JSON 文件：[http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs)

下载转换器：[https://github.com/JMCuixy/swagger2word](https://github.com/JMCuixy/swagger2word)

> 原项目的端口号和本项目使用的端口号相同，提供更改过端口号的版本：[https://github.com/nonlinearthink/swagger2word](https://github.com/nonlinearthink
>/swagger2word)。

通过访问 [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) ，调用其中的 API 可实现自动生成 Word文档。

推荐使用传递 JSON 地址的方式。

### Mybatis-Generator

根据项目的实际情况配置 [mybatis.xml](src/main/resources/generator/mybatis.xml)，具体的配置规则见[MyBatis Generator](http://mybatis.org/generator/configreference
/xmlconfig.html)官方网站：

使用命令行执行：

```shell script
mvn mybatis-generator:generate
```

或者也可以用 IntelliJ IDEA 等工具内置的一些 maven 执行工具。

### Lombok

可能需要安装相应的 IDE 插件支持。

IntelliJ IDEA 需要在插件中心安装 Lombok 插件。

[Lombok 使用指南](https://projectlombok.org/features/all)

