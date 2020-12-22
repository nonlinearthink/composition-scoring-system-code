package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.SystemMessageEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.SystemMessageService;
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
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/system-message")
@Api(value = "SystemMessageController")
public class SystemMessageController
{
    private SystemMessageService systemMessageService;

    @Autowired(required = false)
    public SystemMessageController(SystemMessageService systemMessageService) {
        this.systemMessageService = systemMessageService;
    }

    @LoginRequired(type = UserType.ADMIN)
    @PostMapping("")
    @ApiOperation(value = "添加系统消息")
    @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
            "PushArticle")
    public ResponseEntity<ApiResult<Map<String, Object>>> addSystemMessage(@RequestBody SystemMessageEntity systemMessageEntity, HttpServletRequest request) {
        systemMessageEntity.setAdminName((String)request.getAttribute("username"));
        int id = systemMessageService.addSystemMessage(systemMessageEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("sMessageId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "删除系统消息")
    @DeleteMapping("/{sMessageId}")
    @ApiImplicitParam(paramType = "path", name = "articleId", value = "文章ID", required = true, dataType =
            "Integer")
    public ResponseEntity<ApiResult<Boolean>> deleteSystemMessage(@PathVariable("sMessageId") Integer sMessageId, HttpServletRequest request) {
        systemMessageService.deleteSystemMessage(sMessageId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("删除成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "更新系统消息")
    @PutMapping("/{sMessageId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "articleId", value = "文章ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
                    "PushArticle")
    })
    public  ResponseEntity<ApiResult<Boolean>> updateSystemMessage(@PathVariable("sMessageId") Integer sMessageId,
                                                             @RequestBody SystemMessageEntity systemMessageEntity, HttpServletRequest request) {
        systemMessageEntity.setAdminName((String)request.getAttribute("username"));
        systemMessageService.updateSystemMessage(systemMessageEntity, sMessageId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "获取系统消息")
    @GetMapping("")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllSystemMessages() {
        List<SystemMessageEntity> systemMessageEntityList = systemMessageService.selectAllSystemMessages();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("systemMessageEntityList", systemMessageEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
