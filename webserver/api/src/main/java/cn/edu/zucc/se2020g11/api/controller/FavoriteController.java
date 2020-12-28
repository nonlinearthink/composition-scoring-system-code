package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.entity.FavoriteEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.FavoriteService;
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
@RequestMapping("/api/favorite")
@Api(value = "FavoriteController")
public class FavoriteController
{
    private final FavoriteService favoriteService;

    @Autowired(required = false)
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @LoginRequired(type = UserType.USER)
    @GetMapping("")
    @ApiOperation(value = "获取当前用户收藏的所有文章")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllFavorites(HttpServletRequest request) {
        List<FavoriteEntity> favoriteEntityList = favoriteService.selectAllFavorites((String)request.getAttribute("username"));
        List<CompositionEntity> compositionEntityList = favoriteService.findFavoriteComposition(favoriteEntityList);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("compositionList", compositionEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/{compositionId}")
    @ApiOperation(value = "用户收藏")
    public ResponseEntity<ApiResult<Map<String, Object>>> addSupport(@PathVariable("compositionId") Integer compositionId, HttpServletRequest request) {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername((String)request.getAttribute("username"));
        favoriteEntity.setCompositionId(compositionId);
        int id = favoriteService.addFavorite(favoriteEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("favoriteId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @DeleteMapping("/{compositionId}")
    @ApiOperation(value = "用户取消收藏")
    public ResponseEntity<ApiResult<Boolean>> deleteComposition(@PathVariable("compositionId") Integer compositionId, HttpServletRequest request) {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setUsername((String)request.getAttribute("username"));
        favoriteEntity.setCompositionId(compositionId);
        favoriteService.deleteFavorite(favoriteEntity);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("删除成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
