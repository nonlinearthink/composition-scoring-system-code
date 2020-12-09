package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.LoginForm;
import cn.edu.zucc.se2020g11.api.model.PasswordChangeModel;
import cn.edu.zucc.se2020g11.api.model.SignupForm;
import cn.edu.zucc.se2020g11.api.service.JwtService;
import cn.edu.zucc.se2020g11.api.service.MailService;
import cn.edu.zucc.se2020g11.api.service.UserService;
import cn.edu.zucc.se2020g11.api.service.VerifyCodeService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "UserController")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final MailService mailService;
    private final VerifyCodeService verifyCodeService;

    private final Logger logger = LogManager.getLogger(LogCategory.SYSTEM);

    @Autowired(required = false)
    public UserController(UserService userService, JwtService jwtService, MailService mailService,
                          VerifyCodeService verifyCodeService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.mailService = mailService;
        this.verifyCodeService = verifyCodeService;
    }

    @LoginRequired(type = UserType.ADMIN)
    @GetMapping("/")
    @ApiOperation(value = "获取所有用户及其属性")
    public ResponseEntity<String> getAllUserData() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/verify-code")
    @ApiOperation(value = "获取验证码")
    @ApiImplicitParam(paramType = "query", name = "email", value = "邮箱", required = true, dataType = "String")
    public ResponseEntity<ApiResult<Boolean>> getVerifyCode(@RequestParam("email") String email) {
        // 获取验证码
        String verifyCode = verifyCodeService.getVerifyCode(email);
        // 异步操作，缓存验证码
        verifyCodeService.cacheVerifyCode(email, verifyCode);
        // 发送邮件
        mailService.sendSimpleMail(email, "批多多注册认证服务", "注册验证码:" + verifyCode + "(区分大小写)");
        ApiResult<Boolean> result = new ApiResult<>();
        result.setCode(0);
        result.setMsg("请求成功");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/")
    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(paramType = "body", name = "userEntity", value = "注册表单", required = true, dataType = "UserEntity")
    public ResponseEntity<ApiResult<Boolean>> signup(@RequestBody @Validated SignupForm signupForm) {
        // 过滤验证码错误的用户
        verifyCodeService.validateCode(signupForm.getEmail(), signupForm.getVerifyCode());
        // 注册
        userService.signup(signupForm);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setCode(0);
        result.setMsg("创建成功");
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/session")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParam(paramType = "body", name = "loginModel", value = "登录表单", required = true, dataType = "LoginModel")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Validated LoginForm loginForm) {
        // 登录检测
        userService.login(loginForm);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setCode(0);
        result.setMsg("创建成功");
        Map<String, Object> data = new HashMap<>(1);
        // 创建 token
        data.put("token", jwtService.generateToken(loginForm.getUsername(), UserType.USER));
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @LoginRequired(type = UserType.USER)
    @DeleteMapping("/session/{username}")
    @ApiOperation(value = "用户登出")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<UserEntity> logout(@PathVariable("username") String username) {
        System.out.println(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired(type = UserType.USER)
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

    @LoginRequired(type = UserType.USER)
    @GetMapping("/{username}/details")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<UserEntity> getDetail(@PathVariable("username") String username) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired(type = UserType.USER)
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
