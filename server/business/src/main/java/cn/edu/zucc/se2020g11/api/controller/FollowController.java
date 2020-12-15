package cn.edu.zucc.se2020g11.api.controller;


import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.FollowService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/follow")
@Api(value = "FollowController")
public class FollowController
{
    private FollowService followService;

    @Autowired(required = false)
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @LoginRequired(type = UserType.USER)
    @GetMapping("")
    @ApiOperation(value = "获取当前用户所有关注的人")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllFollows(UserEntity userEntity, HttpServletRequest request) {
        userEntity.setUsername((String)request.getAttribute("username"));
        List<FollowEntity> followList = followService.selectAllFollows(userEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("followList", followList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
