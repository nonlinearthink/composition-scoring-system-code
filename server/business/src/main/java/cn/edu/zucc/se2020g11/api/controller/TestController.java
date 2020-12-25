package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.service.RedisMessageMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    public TestController(RedisMessageMqService messageMqService) {
        this.messageMqService = messageMqService;
    }

    RedisMessageMqService messageMqService;

    @PostMapping("/send")
    public void send(@RequestBody Map<String, Object> body){
        messageMqService.push("test", (Serializable) body);
    }
}
