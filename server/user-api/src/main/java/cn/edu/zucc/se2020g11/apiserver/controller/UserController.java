package cn.edu.zucc.se2020g11.apiserver.controller;

import cn.edu.zucc.se2020g11.apiserver.bean.User;
import cn.edu.zucc.se2020g11.apiserver.dao.UserMapper;
import cn.edu.zucc.se2020g11.apiserver.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/hello")
public class HelloController {

    private HelloService helloService;

    @Autowired(required = false)
    public HelloController(HelloService helloService)
    {
        this.helloService = helloService;
    }

    @GetMapping
    public String hello() {
        User user = new User();
        user.setUserName("nonlinearthink");
        user.setPassword("123");
        helloService.insert(user);
        Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");
        return "Hello World";
    }
}
