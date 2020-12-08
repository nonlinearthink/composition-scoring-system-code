package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.LoginModel;
import cn.edu.zucc.se2020g11.api.model.PasswordChangeModel;
import cn.edu.zucc.se2020g11.api.model.SuccessModel;
import cn.edu.zucc.se2020g11.api.service.UserService;
import cn.edu.zucc.se2020g11.api.util.annotation.AdminRequired;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.util.exception.ExceptionDictionary;
import cn.edu.zucc.se2020g11.api.config.LogPosition;
import cn.edu.zucc.se2020g11.api.util.security.JwtRegistry;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @AdminRequired
    @GetMapping("/")
    @ApiOperation(value = "获取所有用户及其属性")
    public ResponseEntity<SuccessModel> getAllUserData() {
        SuccessModel successModel = new SuccessModel();
        successModel.setSuccess(true);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(paramType = "body", name = "userEntity", value = "注册表单", required = true, dataType = "UserEntity")
    public ResponseEntity<SuccessModel> signup(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/session")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParam(paramType = "body", name = "loginModel", value = "登录表单", required = true, dataType = "LoginModel")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginModel loginModel) {
        if (loginModel.getUserName() == null || loginModel.getPassword() == null) {
            throw new BaseException(ExceptionDictionary.NONSTANDARD_PARAMETERS, LogPosition.SYSTEM);
        }
        Map<String, Object> response = new TreeMap<>();
        response.put("token", JwtRegistry.getApplication("user").generateToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @LoginRequired
    @DeleteMapping("/session/{username}")
    @ApiOperation(value = "用户登出")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<UserEntity> logout(@PathVariable("username") String username) {
        System.out.println(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @PutMapping("/{username}/password")
    @ApiOperation(value = "修改用户密码信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "body", name = "passwordModel", value = "密码信息", required = true, dataType =
                    "PasswordModel")
    })
    public ResponseEntity<UserEntity> changeUserPassword(@PathVariable("username") String username,
                                                         @RequestBody PasswordChangeModel passwordChangeModel) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @GetMapping("/{username}/details")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<UserEntity> getDetail(@PathVariable("username") String username) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @PostMapping("/{username}/follow")
    @ApiOperation(value = "他人关注用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "follower", value = "跟随者", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String")
    })
    public ResponseEntity<UserEntity> addFollow(@PathVariable("username") String username,
                                                @RequestParam("follower") String follower) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
