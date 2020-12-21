package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.ArticleModel;
import cn.edu.zucc.se2020g11.api.model.FollowCardModel;
import cn.edu.zucc.se2020g11.api.service.HomeService;
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
@RequestMapping("/api/home")
@Api(value = "HomeController")
public class HomeController
{
    private HomeService homeService;

    @Autowired(required = false)
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @ApiOperation(value = "获取推送文章")
    @GetMapping("/article")
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
    @ApiOperation(value = "获取关注文章")
    @GetMapping("/follow")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectFollowCompositions(HttpServletRequest request) {
        List<FollowCardModel> followCardModelList = homeService.selectFollowCompositions((String)request.getAttribute("username"));
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("followCardModelList", followCardModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    @ApiOperation(value = "获取新鲜文章")
//    @GetMapping("/follow")
//    public ResponseEntity<ApiResult<Map<String, Object>>> selectNewCompositions(HttpServletRequest request) {
//        List<CompositionEntity> followCompositionList = homeService.selectFollowCompositions((String)request.getAttribute("username"));
//        ApiResult<Map<String, Object>> result = new ApiResult<>();
//        result.setMsg("获取成功");
//        Map<String, Object> data = new HashMap<>(1);
//        data.put("followCompositionList", followCompositionList);
//        result.setData(data);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }
}
