package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.ErrorEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/error")
@Api(value = "ErrorController")
public class ErrorController
{
    private ErrorService errorService;

    @Autowired(required = false)
    public ErrorController(ErrorService errorService)
    {
        this.errorService = errorService;
    }

    @GetMapping("/{compositionId}/{errorType}")
    @ApiOperation(value = "获取作文批改信息")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectError(@PathVariable("compositionId") Integer compositionId, @PathVariable("errorType") String errorType) {
        ErrorEntity errorEntity = errorService.selectError(compositionId, errorType);
        JSONArray jsonArray = JSONObject.parseArray(errorEntity.getText());
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("JSONArray", jsonArray);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
