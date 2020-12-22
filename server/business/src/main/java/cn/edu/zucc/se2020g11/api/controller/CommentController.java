package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.CommentEntity;
import cn.edu.zucc.se2020g11.api.entity.CompositionEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.CommentService;
import cn.edu.zucc.se2020g11.api.service.PermissionService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/comment")
@Api(value = "CommentController")
public class CommentController
{
    private CommentService commentService;
    private PermissionService permissionService;

    @Autowired(required = false)
    public CommentController(CommentService commentService, PermissionService permissionService){
        this.commentService = commentService;
        this.permissionService = permissionService;
    }

    @LoginRequired(type = UserType.ADMIN)
    @GetMapping("")
    @ApiOperation(value = "获取所有评论")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllComments() {
        List<CommentEntity> commentEntityList = commentService.selectAllComments();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("commentEntityList", commentEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{compositionId}")
    @ApiOperation(value = "获取当前作文的所有评论")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectComment(@PathVariable("compositionId") Integer compositionId) {
        List<CommentEntity> commentEntityList = commentService.selectComment(compositionId);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("commentEntityList", commentEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/{compositionId}")
    @ApiOperation(value = "用户添加评论")
    public ResponseEntity<ApiResult<Map<String, Object>>> addComment(@PathVariable("compositionId") Integer compositionId, @RequestBody @Validated CommentEntity commentEntity, HttpServletRequest request) {
        commentEntity.setCompositionId(compositionId);
        commentEntity.setUsername((String)request.getAttribute("username"));
        int id = commentService.addComment(commentEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("commentId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @DeleteMapping("/{commentId}")
    @ApiOperation(value = "用户删除评论")
    public ResponseEntity<ApiResult<Boolean>> deleteComment(@PathVariable("commentId") Integer commentId,HttpServletRequest request) {
        permissionService.validateComment((String)request.getAttribute("username"), commentId);
        commentService.deleteComment(commentId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("删除成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "管理员修改评论状态")
    @PutMapping("/valid/{commentId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "compositionEntity", value = "作文", required = true, dataType =
                    "CompositionEntity")
    })
    public ResponseEntity<ApiResult<Boolean>> updateCommentByValid(@PathVariable("commentId") Integer commentId,
                                                                       @RequestBody CommentEntity commentEntity, HttpServletRequest request) {
        commentService.updateCommentByValid(commentEntity, commentId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
