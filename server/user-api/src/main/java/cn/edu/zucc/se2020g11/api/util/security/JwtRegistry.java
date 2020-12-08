package cn.edu.zucc.se2020g11.api.util.security;

import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.util.exception.ExceptionDictionary;
import cn.edu.zucc.se2020g11.api.config.LogPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * JWT 注册表类
 *
 * @author nonlinearthink
 */
public class JwtRegistry {

    private static final Map<String, JwtApplication> JWT_APPLICATIONS = new TreeMap<>();

    private static final Logger logger = LogManager.getLogger(LogPosition.SYSTEM);

    /**
     * 更新全部私钥
     */
    public static void updatePrivateKey() {
        JWT_APPLICATIONS.forEach((String registerName, JwtApplication jwtApplication) -> {
            logger.info("更新JWT Application " + registerName + " 的密钥");
            jwtApplication.updatePrivateKey();
        });
    }

    /**
     * 注册一个JWT应用
     *
     * @param registerName   注册名
     * @param jwtApplication JWT 应用实例
     */
    public static void register(String registerName, JwtApplication jwtApplication) throws BaseException {
        if (JWT_APPLICATIONS.containsKey(registerName)) {
            throw new BaseException(ExceptionDictionary.ALREADY_REGISTERED_JWT, LogPosition.SYSTEM);
        }
        JWT_APPLICATIONS.put(registerName, jwtApplication);
        logger.info("注册一个新的应用，JWT Application " + registerName);
    }

    /**
     * 获取一个JWT应用
     *
     * @param registerName 注册名
     * @return JWT 应用实例
     */
    public static JwtApplication getApplication(String registerName) {
        return JWT_APPLICATIONS.get(registerName);
    }

}
