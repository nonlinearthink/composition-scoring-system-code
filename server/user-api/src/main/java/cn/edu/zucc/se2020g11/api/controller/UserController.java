package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.LoginModel;
import cn.edu.zucc.se2020g11.api.model.SuccessModel;
import cn.edu.zucc.se2020g11.api.service.UserService;
import cn.edu.zucc.se2020g11.api.util.JwtUtil;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.util.exception.ExceptionDictionary;
import cn.edu.zucc.se2020g11.api.util.log.LogLevel;
import cn.edu.zucc.se2020g11.api.util.log.LogPosition;
import cn.edu.zucc.se2020g11.api.util.log.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;


/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "UserController")
public class UserController {

    private UserService userService;

    @Autowired(required = false)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/session")
    @ApiOperation(value="登录", notes="实现用户登录")
    @ApiImplicitParam(paramType="body", name = "loginModel", value = "登录信息", required = true, dataType = "LoginModel")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginModel loginModel) {
        if(loginModel.getUserName() == null || loginModel.getPassword() == null){
            throw new BaseException(ExceptionDictionary.NONSTANDARD_PARAMETERS);
        }
        Map<String, Object> response = new TreeMap<>();
        response.put("token", JwtUtil.generateToken(loginModel.getUserName(), "test"));
        LogUtil.print("test", LogPosition.SYSTEM, LogLevel.INFO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @LoginRequired
    @DeleteMapping("/session/{username}")
    @ApiOperation(value="登出", notes="实现用户登出")
    @ApiImplicitParam(paramType="body", name = "username", value = "登出", required = true, dataType = "String")
    public ResponseEntity<UserEntity> logout(@PathVariable("username") String username) {
        System.out.println(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation(value="注册", notes="实现用户注册")
    @ApiImplicitParam(paramType="body", name = "userEntity", value = "注册", required = true, dataType = "UserEntity")
    public ResponseEntity<SuccessModel> signup(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @GetMapping("/detail")
    public ResponseEntity<UserEntity> getDetail(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
