package cn.edu.zucc.se2020g11.apiserver.controller;

import cn.edu.zucc.se2020g11.apiserver.bean.User;
import cn.edu.zucc.se2020g11.apiserver.service.UserService;
import cn.edu.zucc.se2020g11.apiserver.util.ResponseMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/user")
public class UserController
{

    private UserService userService;

    @Autowired(required = false)
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public ResponseMsg hello() {
        User user = new User();
        user.setUserName("nonlinearthink");
        user.setPassword("123");
//        userService.insert(user);
        Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");
        return new ResponseMsg(1, "登录成功").setPayload(user);
    }
}
