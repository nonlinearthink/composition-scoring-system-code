package cn.edu.zucc.se2020g11.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author nonlinearthink
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {
    private String privateKeyLength;
}
