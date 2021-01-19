package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.ZoneModel;
import cn.edu.zucc.se2020g11.api.service.CompositionService;
import cn.edu.zucc.se2020g11.api.service.FollowService;
import cn.edu.zucc.se2020g11.api.service.ZoneService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import java.util.List;
import java.util.Map;

/**
 * 空间控制器
 *
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/zone")
@Api(value = "ZoneController")
public class ZoneController
{
    private final ZoneService zoneService;
    private final FollowService followService;
    private final CompositionService compositionService;

    @Autowired(required = false)
    public ZoneController(ZoneService zoneService, FollowService followService, CompositionService compositionService){
        this.zoneService = zoneService;
        this.followService = followService;
        this.compositionService = compositionService;
    }

    @LoginRequired(type = UserType.USER)
    @GetMapping("/{username}")
    @ApiOperation(value = "获取空间信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", name = "username", value = "空间用户名", required = true, dataType =
                "String"),
        @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
                "String")
    })
    public ResponseEntity<ApiResult<Map<String, Object>>> selectZone(@PathVariable("username") String username, HttpServletRequest request) {
        Boolean isFollow = followService.findFollow((String)request.getAttribute("username"), username);
        ZoneModel zoneModel = zoneService.selectZone(username);
        List<CompositionEntity> compositionEntityList = compositionService.selectZoneCompositions(username);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("zoneModel", zoneModel);
        data.put("isFollow", isFollow);
        data.put("compositionEntityList", compositionEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
