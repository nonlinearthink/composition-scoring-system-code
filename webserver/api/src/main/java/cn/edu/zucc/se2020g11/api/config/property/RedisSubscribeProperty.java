package cn.edu.zucc.se2020g11.api.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis订阅属性
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "subscribe")
public class RedisSubscribeProperty {
    @Value("${subscribe.task-publisher}")
    private String taskPublisher;
    @Value("${subscribe.result-receiver}")
    private String resultReceiver;
    @Value("${subscribe.result-notify}")
    private String resultNotify;
}
