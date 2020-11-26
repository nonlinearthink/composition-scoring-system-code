package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.ErrorModel;
import cn.edu.zucc.se2020g11.api.model.LoginModel;
import cn.edu.zucc.se2020g11.api.model.PasswordChangeModel;
import cn.edu.zucc.se2020g11.api.model.SuccessModel;
import cn.edu.zucc.se2020g11.api.service.UserService;
import cn.edu.zucc.se2020g11.api.util.annotation.AdminRequired;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.util.exception.ExceptionDictionary;
import cn.edu.zucc.se2020g11.api.util.log.LogPosition;
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
    @ApiOperation(value = "获取所有用户及其附带信息的信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 401, message = "认证失败")})
    public ResponseEntity<SuccessModel> getAllUserData() {
        // TODO 组合Service完成处理
        SuccessModel successModel = new SuccessModel();
        successModel.setSuccess(true);
        // TODO 调用successModel.setData
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation(value = "用户注册")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "用户登录成功")})
    @ApiImplicitParam(paramType = "body", name = "userEntity", value = "注册信息", required = true, dataType = "UserEntity")
    public ResponseEntity<SuccessModel> signup(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/session")
    @ApiOperation(value = "用户登录")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "用户注册成功")})
    @ApiImplicitParam(paramType = "body", name = "LoginModel", value = "登录表单", required = true, dataType = "LoginModel")
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
    @ApiOperation(value = "获取信息", notes = "获取用户信息")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<UserEntity> getDetail(@PathVariable("username") String username) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
