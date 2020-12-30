package cn.edu.zucc.se2020g11.api.config;

import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.ErrorEntity;
import cn.edu.zucc.se2020g11.api.service.CompositionService;
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
    CompositionService compositionService;

    @Autowired
    public MessageReceiver(RedisMessageMqService messageMqService, ErrorService errorService, CompositionService compositionService) {
        this.messageMqService = messageMqService;
        this.errorService = errorService;
        this.compositionService = compositionService;
    }

    /**接收消息的方法*/
    public void receiveMessage(String message){
        System.out.println("收到一条消息："+message);
        LinkedHashMap linkedHashMap = (LinkedHashMap)messageMqService.pop(resultReceiver);
        String msg = JSON.toJSONString(linkedHashMap);
        System.out.println(msg);

        //取出返回数据
        JSONObject jsonObject = JSONObject.parseObject(msg);
        int compositionId = jsonObject.getInteger("composition_id");
        String text = jsonObject.getString("compare_result");

        //更新作文状态
        CompositionEntity compositionEntity = compositionService.selectCompositionById(compositionId);
        compositionEntity.setStatus(3);
        compositionService.updateComposition(compositionEntity, compositionId);

        //语法错误
        ErrorEntity errorEntity = new ErrorEntity();
        errorEntity.setCompositionId(compositionId);
        errorEntity.setText(text);
        errorService.addError(errorEntity);

        String context = jsonObject.getString("processed_context");
        System.out.println(context);
    }

}