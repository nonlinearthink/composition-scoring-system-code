package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.*;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.CompositionCountModel;
import cn.edu.zucc.se2020g11.api.service.*;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
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

    @Value("${subscribe.task-publisher}")
    private String taskPublisher;
    RedisMessageMqService messageMqService;
    private final CompositionService compositionService;
    private final SupportService supportService;
    private final FavoriteService favoriteService;
    private final CommentService commentService;
    private final PermissionService permissionService;
    private final FollowService followService;

    @Autowired(required = false)
    public CompositionController(RedisMessageMqService messageMqService, CompositionService compositionService, SupportService supportService, FavoriteService favoriteService, CommentService commentService, PermissionService permissionService, FollowService followService) {
        this.messageMqService = messageMqService;
        this.compositionService = compositionService;
        this.supportService = supportService;
        this.favoriteService = favoriteService;
        this.commentService = commentService;
        this.permissionService = permissionService;
        this.followService = followService;
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
    @GetMapping("/{compositionId}")
    @ApiOperation(value = "获取单篇作文信息")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectComposition(@PathVariable("compositionId") Integer compositionId, HttpServletRequest request) {
        CompositionCountModel compositionCountModel = compositionService.selectCountByCompositionId(compositionId);
        List<CommentEntity> commentEntityList = commentService.selectComment(compositionId);
        Boolean isFollow = followService.findFollow((String)request.getAttribute("username"), compositionService.selectCountByCompositionId(compositionId).getUsername());
        Boolean isSupport = supportService.findSupport((String)request.getAttribute("username"), compositionId);
        Boolean isFavorite = favoriteService.findFavorite((String)request.getAttribute("username"), compositionId);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("compositionCountModel", compositionCountModel);
        data.put("commentEntityList", commentEntityList);
        data.put("isFollow", isFollow);
        data.put("isSupport", isSupport);
        data.put("isFavorite", isFavorite);
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
        Map<String, Object> body = new HashMap<>(1);
        body.put("compositionId", compositionEntity.getCompositionId());
        body.put("compositionBody", compositionEntity.getCompositionBody());
        if(compositionEntity.getStatus()==2){
            messageMqService.push(taskPublisher, (Serializable) body);
        }
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
        supportService.deleteSupportByCompositionId(compositionId);
        favoriteService.deleteFavoriteByCompositionId(compositionId);
        commentService.deleteCommentByCompositionId(compositionId);
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
        Map<String, Object> body = new HashMap<>(1);
        body.put("compositionId", compositionEntity.getCompositionId());
        body.put("compositionBody", compositionEntity.getCompositionBody());
        if(compositionEntity.getStatus()==2){
            messageMqService.push(taskPublisher, (Serializable) body);
        }
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "管理员修改作文状态")
    @PutMapping("/valid/{compositionId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "compositionEntity", value = "作文", required = true, dataType =
                    "CompositionEntity")
    })
    public ResponseEntity<ApiResult<Boolean>> updateCompositionByValid(@PathVariable("compositionId") Integer compositionId,
                                                                @RequestBody CompositionEntity compositionEntity) {
        compositionService.updateCompositionByValid(compositionEntity, compositionId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
