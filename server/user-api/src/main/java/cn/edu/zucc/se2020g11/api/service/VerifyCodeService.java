package cn.edu.zucc.se2020g11.api.service;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author nonlinearthink
 */
@Service
@EnableAsync
public class VerifyCodeService {
    /**
     * 日志记录器
     */
    private final Logger logger;
    /**
     * redis 键前缀
     */
    private final String prefix = "verifyCode_";
    /**
     * redis 字符串
     */
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public VerifyCodeService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.logger = LogManager.getLogger(LogCategory.BUSINESS);
    }

    /**
     * 节流函数，防止一直请求验证码
     *
     * @param email 请求邮箱
     * @throws BaseException 异常
     */
    private void throttle(String email) throws BaseException {
        if (stringRedisTemplate.opsForValue().get(prefix + email) != null) {
            throw new BaseException(ErrorDictionary.REQUEST_TOO_FREQUENTLY, LogCategory.BUSINESS);
        }
        logger.info("允许请求验证码");
    }

    /**
     * 获取验证码
     *
     * @param email 请求邮箱
     * @return 验证码
     */
    public String getVerifyCode(String email) {
        throttle(email);
        String verifyCode = RandomStringUtils.random(6, true, true);
        logger.info("生成验证码 " + verifyCode);
        return verifyCode;
    }

    /**
     * 缓存验证码到 Redis
     *
     * @param email      请求邮箱
     * @param verifyCode 验证码
     */
    @Async
    public void cacheVerifyCode(String email, String verifyCode) {
        logger.info("设置验证码缓存 " + prefix + email);
        stringRedisTemplate.opsForValue().set(prefix + email, verifyCode, 60, TimeUnit.SECONDS);
    }

    /**
     * 验证验证码是否正确
     *
     * @param verifyCode 验证码
     * @param email      邮箱
     * @throws BaseException 异常
     */
    public void validateCode(String email, String verifyCode) throws BaseException {
        System.out.println(prefix + email);
        System.out.println(stringRedisTemplate.opsForValue().get(prefix + email));
        if (!verifyCode.equals(stringRedisTemplate.opsForValue().get(prefix + email))) {
            throw new BaseException(ErrorDictionary.INCORRECT_VERIFY_CODE, LogCategory.BUSINESS);
        }
        logger.info("验证码通过验证");
    }

}
