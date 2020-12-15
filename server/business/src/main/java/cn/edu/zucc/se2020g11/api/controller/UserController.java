package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
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
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        result.setMsg("请求成功");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("")
    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(paramType = "body", name = "signupForm", value = "注册表单", required = true, dataType = "SignupForm")
    public ResponseEntity<ApiResult<Boolean>> signup(@RequestBody @Validated SignupForm signupForm) {
        // 过滤验证码错误的用户
        verifyCodeService.validateCode(signupForm.getEmail(), signupForm.getVerifyCode());
        // 注册
        userService.signup(signupForm);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("创建成功");
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/session")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParam(paramType = "body", name = "loginForm", value = "登录表单", required = true, dataType = "LoginForm")
    public ResponseEntity<ApiResult<Map<String, Object>>> login(@RequestBody @Validated LoginForm loginForm) {
        // 登录检测
        UserEntity user = userService.login(loginForm);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("创建成功");
        Map<String, Object> data = new HashMap<>(1);
        // 创建 token
        data.put("token", jwtService.generateToken(loginForm.getUsername(), UserType.USER));
        data.put("user", user);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @DeleteMapping("/session")
    @ApiOperation(value = "用户登出")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<ApiResult<Boolean>> logout(HttpServletRequest request) {
        jwtService.clearTokenCache((String)request.getAttribute("username"));
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("删除成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PutMapping("/account/password")
    @ApiOperation(value = "修改用户密码信息")
    @ApiImplicitParam(paramType = "body", name = "passwordChangeModel", value = "密码修改模型", required = true, dataType =
                    "PasswordChangeModel")
    public ResponseEntity<ApiResult<Boolean>> updateUserPassword(@RequestBody @Validated PasswordChangeModel passwordChangeModel, HttpServletRequest request) {
        // 修改密码
        userService.updateUserPassword((String)request.getAttribute("username"), passwordChangeModel);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PutMapping("/account/details")
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParam(paramType = "body", name = "userEntity", value = "用户实体", required = true, dataType =
            "UserEntity")
    public ResponseEntity<ApiResult<Boolean>> updateUserDetail(@RequestBody @Validated UserEntity userEntity, HttpServletRequest request) {
        // 修改用户信息
        userService.updateUserDetail((String)request.getAttribute("username"), userEntity);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/password")
    @ApiOperation(value = "忘记密码")
    @ApiImplicitParam(paramType = "query", name = "follower", value = "跟随者", required = true, dataType = "String")
    public ResponseEntity<UserEntity> addFollow(@PathVariable("username") String username,
                                                @RequestParam("follower") String follower) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
