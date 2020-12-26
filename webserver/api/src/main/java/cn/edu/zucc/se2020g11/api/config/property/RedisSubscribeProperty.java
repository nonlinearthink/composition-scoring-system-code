package cn.edu.zucc.se2020g11.api.config.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author nonlinearthink
 */
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
