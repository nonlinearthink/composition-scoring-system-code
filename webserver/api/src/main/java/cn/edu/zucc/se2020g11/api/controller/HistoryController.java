package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.HistoryEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.HistoryModel;
import cn.edu.zucc.se2020g11.api.service.HistoryService;
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
 * 历史记录控制器
 *
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/history")
@Api(value = "HistoryController")
public class HistoryController
{
    private final HistoryService historyService;

    @Autowired(required = false)
    public HistoryController(HistoryService historyService)
    {
        this.historyService = historyService;
    }

    @LoginRequired(type = UserType.USER)
    @GetMapping("")
    @ApiOperation(value = "获取当前用户所有历史记录")
    @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
            "String")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectHistoryByUser(HttpServletRequest request) {
        List<HistoryModel> historyModelList = historyService.selectHistoryByUser((String)request.getAttribute("username"));
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("historyModelList", historyModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/{compositionId}")
    @ApiOperation(value = "用户访问添加历史记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "文章ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
                    "String")
    })
    public ResponseEntity<ApiResult<Map<String, Object>>> addHistory(@PathVariable("compositionId") Integer compositionId, HttpServletRequest request) {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setUsername((String)request.getAttribute("username"));
        historyEntity.setCompositionId(compositionId);
        int id = historyService.addHistory(historyEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("historyId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
