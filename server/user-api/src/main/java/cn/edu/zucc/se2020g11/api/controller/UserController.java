package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.LoginModel;
import cn.edu.zucc.se2020g11.api.model.PasswordChangeModel;
import cn.edu.zucc.se2020g11.api.model.SignupForm;
import cn.edu.zucc.se2020g11.api.service.JwtService;
import cn.edu.zucc.se2020g11.api.service.UserService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final Logger logger = LogManager.getLogger(LogCategory.SYSTEM);

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired(required = false)
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @LoginRequired(type = UserType.ADMIN)
    @GetMapping("/")
    @ApiOperation(value = "获取所有用户及其属性")
    public ResponseEntity<String> getAllUserData() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/")
    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(paramType = "body", name = "userEntity", value = "注册表单", required = true, dataType = "UserEntity")
    public ResponseEntity<ApiResult<Map<String, Object>>> signup(@RequestBody SignupForm signupForm) {
        userService.signup(signupForm);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setCode(0);
        result.setMsg("创建成功");
        Map<String, Object> data = new TreeMap<>();
        data.put("token", jwtService.generateToken(signupForm.getUsername(), UserType.USER));
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/session")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParam(paramType = "body", name = "loginModel", value = "登录表单", required = true, dataType = "LoginModel")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginModel loginModel) {
//        if (loginModel.getUserName() == null || loginModel.getPassword() == null) {
//            throw new BaseException(ErrorDictionary.NONSTANDARD_PARAMETERS, LogCategory.SYSTEM);
//        }
        Map<String, Object> response = new TreeMap<>();
//        response.put("token", JwtRegistry.getApplication("user").generateToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
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
