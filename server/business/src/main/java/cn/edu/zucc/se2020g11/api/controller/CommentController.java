package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.CommentEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.CommentService;
import cn.edu.zucc.se2020g11.api.service.PermissionService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import io.swagger.annotations.Api;
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

    @GetMapping("/{compositionId}")
    @ApiOperation(value = "获取当前作文的所有评论")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllComments(@PathVariable("compositionId") Integer compositionId) {
        List<CommentEntity> commentEntityList = commentService.selectAllComments(compositionId);
//        List<CompositionEntity> compositionEntityList = supportService.findSupportedComposition(supportEntityList);
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
}
