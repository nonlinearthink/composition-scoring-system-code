package cn.edu.zucc.se2020g11.api.config;

import cn.edu.zucc.se2020g11.api.service.RedisMessageMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author nonlinearthink
 */
@Component
public class MessageReceiver {
    @Value("${subscribe.result-receiver}")
    private String resultReceiver;

    RedisMessageMqService messageMqService;

    @Autowired
    public MessageReceiver(RedisMessageMqService messageMqService) {
        this.messageMqService = messageMqService;
    }

    /**接收消息的方法*/
    public void receiveMessage(String message){
        System.out.println("收到一条消息："+message);
        System.out.println(messageMqService.pop(resultReceiver));
    }

}