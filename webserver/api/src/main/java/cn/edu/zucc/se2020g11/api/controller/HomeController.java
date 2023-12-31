package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.model.*;
import cn.edu.zucc.se2020g11.api.service.FollowService;
import cn.edu.zucc.se2020g11.api.service.HomeService;
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
 * 首页控制器
 *
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/home")
@Api(value = "HomeController")
public class HomeController
{
    private final HomeService homeService;
    private final FollowService followService;

    @Autowired(required = false)
    public HomeController(HomeService homeService, FollowService followService) {
        this.homeService = homeService;
        this.followService = followService;
    }


    @GetMapping("/article")
    @ApiOperation(value = "获取推送文章")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllArticles() {
        List<ArticleModel> articleModelList = homeService.selectAllArticles();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("articleModelList", articleModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @GetMapping("/follow")
    @ApiOperation(value = "获取关注文章")
    @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
            "String")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectFollowCompositions(HttpServletRequest request) {
        List<FollowCardModel> followCardModelList = homeService.selectFollowCompositions((String)request.getAttribute("username"));
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("followCardModelList", followCardModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/new")
    @ApiOperation(value = "获取新鲜文章")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectNewCompositions() {
        List<NewCardModel> newCardModelList = homeService.selectNewCompositions();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("newCardModelList", newCardModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/hot")
    @ApiOperation(value = "获取热榜文章")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectHotCompositions() {
        List<HotCardModel> hotCardModelList = homeService.selectHotCompositions();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("hotCardModelList", hotCardModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/search/detail/{nickname}")
    @ApiOperation(value = "模糊查询详细用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "nickname", value = "昵称", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType =
                    "String")
    })
    public ResponseEntity<ApiResult<Map<String, Object>>> selectUserView(@PathVariable String nickname, HttpServletRequest request) {
        List<UsernameCardModel> usernameCardModelList = homeService.selectUserView(nickname);
        for(UsernameCardModel u:usernameCardModelList ){
            Boolean isFollow = followService.findFollow((String)request.getAttribute("username"), u.getUsername());
            u.setIsFollow(isFollow);
        }
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("usernameCardModelList", usernameCardModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
