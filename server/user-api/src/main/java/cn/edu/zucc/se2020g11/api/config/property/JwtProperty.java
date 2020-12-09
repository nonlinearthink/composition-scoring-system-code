package cn.edu.zucc.se2020g11.api.config.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author nonlinearthink
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {
    @Value("${jwt.private-key-length}")
    private int privateKeyLength;

    @Value("${jwt.http-header}")
    private String httpHeader;
}
