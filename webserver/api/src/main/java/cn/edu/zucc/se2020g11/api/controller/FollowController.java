package cn.edu.zucc.se2020g11.api.controller;


import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.FollowEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.FollowModel;
import cn.edu.zucc.se2020g11.api.service.FollowService;
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
@RequestMapping("/api/follow")
@Api(value = "FollowController")
public class FollowController
{
    private final FollowService followService;

    @Autowired(required = false)
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/{username}")
    @ApiOperation(value = "获取当前用户关注的所有人")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllFollows(@PathVariable("username") String username, FollowEntity followEntity) {
        followEntity.setUsername(username);
        List<FollowEntity> followEntityList = followService.selectAllFollows(followEntity);
        List<UserEntity> userEntityList = followService.findFollows(followEntityList);
        List<FollowModel> followModelList = followService.getFollowInfo(userEntityList);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("followList", followModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{username}/follower")
    @ApiOperation(value = "获取关注当前用户的所有人")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllFollowers(@PathVariable("username") String username, FollowEntity followEntity) {
        followEntity.setTargetUsername(username);
        List<FollowEntity> followEntityList = followService.selectAllFollowers(followEntity);
        List<UserEntity> userEntityList = followService.findFollowers(followEntityList);
        List<FollowModel> followModelList = followService.getFollowInfo(userEntityList);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("followList", followModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/{username}")
    @ApiOperation(value = "用户添加关注")
    public ResponseEntity<ApiResult<Map<String, Object>>> addFollow(@PathVariable("username") String username, HttpServletRequest request) {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername((String)request.getAttribute("username"));
        followEntity.setTargetUsername(username);
        int id = followService.addFollow(followEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("followId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @DeleteMapping("/{username}")
    @ApiOperation(value = "用户取消关注")
    public ResponseEntity<ApiResult<Boolean>> deleteComposition(@PathVariable("username") String username, HttpServletRequest request) {
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUsername((String)request.getAttribute("username"));
        followEntity.setTargetUsername(username);
        followService.deleteFollow(followEntity);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("删除成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
