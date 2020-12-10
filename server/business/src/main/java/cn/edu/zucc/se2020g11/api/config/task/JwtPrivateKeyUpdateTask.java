package cn.edu.zucc.se2020g11.api.config.task;

import cn.edu.zucc.se2020g11.api.service.JwtService;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时JWT私钥更改任务
 *
 * @author nonlinearthink
 */
@Configuration
@EnableScheduling
public class JwtPrivateKeyUpdateTask {
    /**
     * 日志记录器
     */
    private final Logger logger = LogManager.getLogger(LogCategory.SYSTEM);
    /**
     * 运行间隔
     */
    private final int interval = 86400000;
    /**
     * 私钥长度，从 Spring Boot 配置项获取
     */
    @Value("${jwt.private-key-length}")
    private int privateKeyLength;

    /**
     * 具体任务细节
     */
    @Scheduled(fixedRate = interval)
    public void doUpdate() {
        String privateKey = RandomStringUtils.random(privateKeyLength, true, true);
        JwtService.setAdminPrivateKey(privateKey);
        logger.info("更新管理员的JWT私钥: " + privateKey);
        privateKey = RandomStringUtils.random(privateKeyLength, true, true);
        JwtService.setUserPrivateKey(privateKey);
        logger.info("更新用户的JWT私钥: " + privateKey);
    }
}
