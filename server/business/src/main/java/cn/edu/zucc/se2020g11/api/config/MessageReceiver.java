package cn.edu.zucc.se2020g11.api.config;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    /**接收消息的方法*/
    public void receiveMessage(String message){
        System.out.println("收到一条消息："+message);
    }

}