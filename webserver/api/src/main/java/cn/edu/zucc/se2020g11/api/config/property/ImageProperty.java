package cn.edu.zucc.se2020g11.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author nonlinearthink
 */
@Component
@ConfigurationProperties(prefix = "res")
public class ImageProperty {
    private String storePath;
    private String imagePath;
}
