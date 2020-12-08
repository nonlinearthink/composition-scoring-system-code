package cn.edu.zucc.se2020g11.api.util.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置类
 *
 * @author nonlinearthink
 */
@Component
public class JwtConfig {
    /**
     * 在HTTP header中的标签
     */
    public static final String HTTP_HEADER = "Authorization";
    /**
     * 过期间隔
     */
    public static final int EXPIRE_INTERVAL = 60 * 60 * 24;
    /**
     * 密钥长度
     */
    public static final int PRIVATE_KEY_LENGTH = 16;
    /**
     * 签发人
     */
    public static final String ISSUER = "cn.edu.zucc.se2020g11";
}
