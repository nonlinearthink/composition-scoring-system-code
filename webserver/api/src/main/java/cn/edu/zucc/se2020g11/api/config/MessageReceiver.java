package cn.edu.zucc.se2020g11.api.config;

import cn.edu.zucc.se2020g11.api.entity.ErrorEntity;
import cn.edu.zucc.se2020g11.api.service.ErrorService;
import cn.edu.zucc.se2020g11.api.service.RedisMessageMqService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author nonlinearthink
 */
@Component
public class MessageReceiver {
    @Value("${subscribe.result-receiver}")
    private String resultReceiver;

    RedisMessageMqService messageMqService;
    ErrorService errorService;

    @Autowired
    public MessageReceiver(RedisMessageMqService messageMqService, ErrorService errorService) {
        this.messageMqService = messageMqService;
        this.errorService = errorService;
    }

    /**接收消息的方法*/
    public void receiveMessage(String message){
        System.out.println("收到一条消息："+message);
        LinkedHashMap linkedHashMap = (LinkedHashMap)messageMqService.pop(resultReceiver);
        String msg = JSON.toJSONString(linkedHashMap);
        System.out.println(msg);
        JSONObject jsonObject = JSONObject.parseObject(msg);
        ErrorEntity errorEntity = new ErrorEntity();
        errorEntity.setCompositionId(jsonObject.getInteger("composition_id"));
        errorEntity.setText(jsonObject.getString("compare_result"));
        errorService.addError(errorEntity);
        String context = jsonObject.getString("processed_context");
        System.out.println(context);
//        String context = jsonObject.getString("processed_context");
//        String regex = ",|，|\\s+";
//        String[] stringArray = context.split(regex);
//        for (int i = 0; i < stringArray.length; i++) {
//            System.out.println("i="+i+" Val="+stringArray[i]);
//        }
    }

}