package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.HelpEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.HelpService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帮助控制器
 *
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/help")
@Api(value = "HelpController")
public class HelpController
{
    private final HelpService helpService;

    @Autowired(required = false)
    public HelpController(HelpService helpService) {
        this.helpService = helpService;
    }

    @LoginRequired(type = UserType.ADMIN)
    @PostMapping("")
    @ApiOperation(value = "添加帮助手册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "helpEntity", value = "帮助", required = true, dataType =
                    "HelpEntity"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
                    "String")
    })
    public ResponseEntity<ApiResult<Map<String, Object>>> addHelp(@RequestBody HelpEntity helpEntity, HttpServletRequest request) {
        helpEntity.setAdminName((String)request.getAttribute("username"));
        int id = helpService.addHelp(helpEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("helpId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "删除帮助手册")
    @DeleteMapping("/{helpId}")
    @ApiImplicitParam(paramType = "path", name = "helpId", value = "帮助ID", required = true, dataType =
                    "Integer")
    public ResponseEntity<ApiResult<Boolean>> deleteHelp(@PathVariable("helpId") Integer helpId) {
        helpService.deleteHelp(helpId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("删除成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "更新帮助手册")
    @PutMapping("/{helpId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "helpId", value = "帮助ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "helpEntity", value = "帮助", required = true, dataType =
                    "HelpEntity")
    })
    public  ResponseEntity<ApiResult<Boolean>> updateHelp(@PathVariable("helpId") Integer helpId, @RequestBody HelpEntity helpEntity) {
        helpService.updateHelp(helpEntity, helpId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("")
    @ApiOperation(value = "获取帮助手册")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllHelps() {
        List<HelpEntity> helpEntityList = helpService.selectAllHelps();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("helpEntityList", helpEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
