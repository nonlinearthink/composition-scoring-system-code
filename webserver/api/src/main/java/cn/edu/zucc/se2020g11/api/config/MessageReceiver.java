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
 * Redis消息接收类
 *
 * @author nonlinearthink
 */
@Component
public class  MessageReceiver {
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

    /**
     * 接收消息
     *
     * @param message 消息
     */
    public void receiveMessage(String message){
        System.out.println("收到一条消息："+message);
        LinkedHashMap linkedHashMap = (LinkedHashMap)messageMqService.pop(resultReceiver);
        String msg = JSON.toJSONString(linkedHashMap);
        System.out.println(msg);

        //取出返回数据
        JSONObject jsonObject = JSONObject.parseObject(msg);
        int compositionId = jsonObject.getInteger("compositionId");
        String compareResultWord = jsonObject.getString("compareResultWord");
        String compareResultGrammar = jsonObject.getString("compareResultGrammar");
        Double wordScore = jsonObject.getDouble("wordScore")*100;
        Double grammarScore = jsonObject.getDouble("grammarScore")*100;
        Double sentenceFluencyScore = jsonObject.getDouble("sentenceFluencyScore")*100;
        Double lengthScore = jsonObject.getDouble("lengthScore")*100;
        Double richnessScore = jsonObject.getDouble("richnessScore")*100;

        //获取作文评分
        CompositionEntity compositionEntity = new CompositionEntity();
        compositionEntity.setCompositionId(compositionId);
        compositionEntity.setWordScore(wordScore);
        compositionEntity.setGrammarScore(grammarScore);
        compositionEntity.setSentenceFluencyScore(sentenceFluencyScore);
        compositionEntity.setLengthScore(lengthScore);
        compositionEntity.setRichnessScore(richnessScore);
        compositionEntity.setScore((int)(wordScore + grammarScore + sentenceFluencyScore + lengthScore + richnessScore) / 5);
        compositionService.updateComposition(compositionEntity, compositionId);

        //单词错误
        ErrorEntity errorEntity = new ErrorEntity();
        errorEntity.setCompositionId(compositionId);
        errorEntity.setText(compareResultWord);
        errorEntity.setErrorType("0");
        errorService.addError(errorEntity);

        //语法错误
        errorEntity.setText(compareResultGrammar);
        errorEntity.setErrorType("1");
        errorService.addError(errorEntity);

        //更新作文状态
        compositionEntity = compositionService.selectCompositionById(compositionId);
        compositionEntity.setStatus(3);
        compositionService.updateComposition(compositionEntity, compositionId);
    }

}