package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.SupportEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.FollowService;
import cn.edu.zucc.se2020g11.api.service.SupportService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import io.swagger.annotations.Api;
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
@RequestMapping("/api/support")
@Api(value = "SupportController")
public class SupportController
{
    private SupportService supportService;

    @Autowired(required = false)
    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @LoginRequired(type = UserType.USER)
    @GetMapping("")
    @ApiOperation(value = "获取当前用户点赞的所有文章")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllSupports(HttpServletRequest request) {
        List<SupportEntity> supportList = supportService.selectAllSupports((String)request.getAttribute("username"));
        List<CompositionEntity> compositionEntityList = supportService.findSupportedComposition(supportList);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("compositionList", compositionEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/{compositionId}")
    @ApiOperation(value = "用户点赞")
    public ResponseEntity<ApiResult<Map<String, Object>>> addSupport(@PathVariable("compositionId") Integer compositionId, HttpServletRequest request) {
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername((String)request.getAttribute("username"));
        supportEntity.setCompositionId(compositionId);
        int id = supportService.addSupport(supportEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("supportId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @DeleteMapping("/{compositionId}")
    @ApiOperation(value = "用户取消点赞")
    public ResponseEntity<ApiResult<Boolean>> deleteComposition(@PathVariable("compositionId") Integer compositionId, HttpServletRequest request) {
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername((String)request.getAttribute("username"));
        supportEntity.setCompositionId(compositionId);
        supportService.deleteSupport(supportEntity);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("删除成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
