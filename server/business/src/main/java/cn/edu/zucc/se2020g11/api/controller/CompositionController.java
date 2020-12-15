package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.*;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.CompositionService;
import cn.edu.zucc.se2020g11.api.service.PermissionService;
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
@RequestMapping("/api/composition")
@Api(value = "CompositionController")
public class CompositionController {

    private CompositionService compositionService;
    private PermissionService permissionService;

    @Autowired(required = false)
    public CompositionController(CompositionService compositionService, PermissionService permissionService) {
        this.compositionService = compositionService;
        this.permissionService = permissionService;
    }

    @LoginRequired(type = UserType.ADMIN)
    @GetMapping("/all")
    @ApiOperation(value = "获取所有作文信息")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllCompositions(UserEntity userEntity) {
        userEntity.setUsername(null);
        List<CompositionEntity> compositionList = compositionService.selectAllCompositions(userEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("compositionList", compositionList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @GetMapping("")
    @ApiOperation(value = "获取单个用户所有作文信息")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllCompositionsByUser(UserEntity userEntity, HttpServletRequest request) {
        userEntity.setUsername((String)request.getAttribute("username"));
        List<CompositionEntity> compositionList = compositionService.selectAllCompositions(userEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("compositionList", compositionList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("")
    @ApiOperation(value = "用户添加作文")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "body", name = "compositionEntity", value = "作文", required = true, dataType =
                    "CompositionEntity")
    })
    public ResponseEntity<ApiResult<Map<String, Object>>> addComposition(@RequestBody CompositionEntity compositionEntity, HttpServletRequest request) {
        compositionEntity.setUsername((String)request.getAttribute("username"));
        int id = compositionService.addComposition(compositionEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("compositionId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @ApiOperation(value = "用户删除作文")
    @DeleteMapping("/{compositionId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer")
    })
    public ResponseEntity<ApiResult<Boolean>> deleteComposition(@PathVariable("compositionId") Integer compositionId, HttpServletRequest request) {
        permissionService.validateComposition((String)request.getAttribute("username"), compositionId);
        compositionService.deleteComposition(compositionId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("删除成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @ApiOperation(value = "用户修改作文")
    @PutMapping("/{compositionId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "compositionEntity", value = "作文", required = true, dataType =
                    "CompositionEntity")
    })
    public ResponseEntity<ApiResult<Boolean>> updateComposition(@PathVariable("compositionId") Integer compositionId,
                                                          @RequestBody CompositionEntity compositionEntity, HttpServletRequest request) {
        permissionService.validateComposition((String)request.getAttribute("username"), compositionId);
        compositionService.updateComposition(compositionEntity, compositionId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @ApiOperation(value = "他人为作文添加评论")
    @PostMapping("/{compositionId}/comments")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "commentEntity", value = "评论", required = true, dataType =
                    "CommentEntity")
    })
    public ResponseEntity<String> addComment(@PathVariable("compositionId") Integer compositionId,
                                                   @RequestBody CommentEntity commentEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired(type = UserType.USER)
    @ApiOperation(value = "他人为作文添加点赞")
    @PostMapping("/{compositionId}/support")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "commentEntity", value = "点赞", required = true, dataType =
                    "SupportEntity")
    })
    public ResponseEntity<String> addSupport(@PathVariable("compositionId") Integer compositionId,
                                                   @RequestBody SupportEntity supportEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired(type = UserType.USER)
    @ApiOperation(value = "他人为作文添加收藏")
    @PostMapping("/{compositionId}/favorite")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "favoriteEntity", value = "收藏", required = true, dataType =
                    "FavoriteEntity")
    })
    public ResponseEntity<String> addFavorite(@PathVariable("compositionId") Integer compositionId,
                                                    @RequestBody FavoriteEntity favoriteEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired(type = UserType.USER)
    @ApiOperation(value = "他人为作文添加举报")
    @PostMapping("/{compositionId}/report")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "reportEntity", value = "举报", required = true, dataType =
                    "ReportEntity")
    })
    public ResponseEntity<String> addReport(@PathVariable("compositionId") Integer compositionId,
                                                  @RequestBody ReportEntity reportEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
