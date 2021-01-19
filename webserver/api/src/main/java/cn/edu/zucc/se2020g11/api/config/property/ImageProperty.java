package cn.edu.zucc.se2020g11.api.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 图片属性
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "res")
public class ImageProperty {
    private String storePath;
    private String imagePath;
}
