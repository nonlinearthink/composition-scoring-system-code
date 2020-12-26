package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.AdminEntity;
import cn.edu.zucc.se2020g11.api.model.AdminLoginForm;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/admin")
@Api(value = "AdminController")
public class AdminController
{
    private final AdminService adminService;
    private final JwtService jwtService;

    private final Logger logger = LogManager.getLogger(LogCategory.SYSTEM);

    @Autowired(required = false)
    public AdminController(AdminService adminService, JwtService jwtService) {
        this.adminService = adminService;
        this.jwtService = jwtService;
    }

    @PostMapping("/session")
    @ApiOperation(value = "管理员登录")
    @ApiImplicitParam(paramType = "body", name = "loginForm", value = "登录表单", required = true, dataType = "LoginForm")
    public ResponseEntity<ApiResult<Map<String, Object>>> login(@RequestBody @Validated AdminLoginForm adminLoginForm) {
        // 登录检测
        AdminEntity admin = adminService.login(adminLoginForm);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("创建成功");
        Map<String, Object> data = new HashMap<>(1);
        // 创建 token
        data.put("token", jwtService.generateToken(adminLoginForm.getAdminName(), UserType.ADMIN));
        data.put("admin", admin);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
