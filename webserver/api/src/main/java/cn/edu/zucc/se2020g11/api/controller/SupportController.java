package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.SupportEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.SupportViewModel;
import cn.edu.zucc.se2020g11.api.service.SupportService;
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
 * 点赞控制器
 *
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/support")
@Api(value = "SupportController")
public class SupportController
{
    private final SupportService supportService;

    @Autowired(required = false)
    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @LoginRequired(type = UserType.USER)
    @GetMapping("")
    @ApiOperation(value = "获取当前用户点赞的所有文章")
    @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
            "String")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllSupports(HttpServletRequest request) {
        List<SupportEntity> supportEntityList = supportService.selectAllSupports((String)request.getAttribute("username"));
        List<CompositionEntity> compositionEntityList = supportService.findSupportedComposition(supportEntityList);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("compositionList", compositionEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @GetMapping("/all")
    @ApiOperation(value = "获取收到的赞")
    @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
            "String")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectSupportView(HttpServletRequest request) {
        List<SupportViewModel> supportViewModelList = supportService.selectSupportView((String)request.getAttribute("username"));
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("supportViewModelList", supportViewModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/{compositionId}")
    @ApiOperation(value = "用户点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "文章ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
                    "String")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "文章ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
                    "String")
    })
    public ResponseEntity<ApiResult<Boolean>> deleteSupport(@PathVariable("compositionId") Integer compositionId, HttpServletRequest request) {
        SupportEntity supportEntity = new SupportEntity();
        supportEntity.setUsername((String)request.getAttribute("username"));
        supportEntity.setCompositionId(compositionId);
        supportService.deleteSupport(supportEntity);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("删除成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
