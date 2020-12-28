package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.CommentReportEntity;
import cn.edu.zucc.se2020g11.api.entity.CompositionReportEntity;
import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.model.ReportModel;
import cn.edu.zucc.se2020g11.api.service.CommentService;
import cn.edu.zucc.se2020g11.api.service.CompositionService;
import cn.edu.zucc.se2020g11.api.service.ReportService;
import cn.edu.zucc.se2020g11.api.service.UserService;
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
 * @author Tuenity
 */
@RestController
@RequestMapping("/api/report")
@Api(value = "ReportController")
public class ReportController
{
    private final ReportService reportService;
    private final CompositionService compositionService;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired(required = false)
    public ReportController(ReportService reportService, CompositionService compositionService, CommentService commentService, UserService userService) {
        this.reportService = reportService;
        this.compositionService = compositionService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "获取违规信息")
    @GetMapping("")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllReports() {
        List<ReportModel> reportModelList = reportService.selectAllReports();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("reportModelList", reportModelList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "管理员冻结或解冻用户账号")
    @PutMapping("/{username}")
    public ResponseEntity<ApiResult<Boolean>> freezeUser(@PathVariable("username") String username, @RequestBody UserEntity userEntity) {
        userEntity.setUsername(username);
        userEntity.setFrozen(userEntity.getFrozen());
        userEntity.setDefrostingTime(userEntity.getDefrostingTime());
        userService.updateUser(username, userEntity);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/composition/{compositionId}")
    @ApiOperation(value = "提交作文举报")
    @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
            "PushArticle")
    public ResponseEntity<ApiResult<Map<String, Object>>> addCompositionReport(@PathVariable("compositionId") Integer compositionId, @RequestBody CompositionReportEntity compositionReportEntity) {
        compositionReportEntity.setUsername(compositionService.selectCompositionById(compositionId).getUsername());
        compositionReportEntity.setCompositionId(compositionId);
        int id = reportService.addCompositionReport(compositionReportEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("compositionReportId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("/comment/{commentId}")
    @ApiOperation(value = "提交评论举报")
    @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
            "PushArticle")
    public ResponseEntity<ApiResult<Map<String, Object>>> addCommentReport(@PathVariable("commentId") Integer commentId, @RequestBody CommentReportEntity commentReportEntity) {
        commentReportEntity.setUsername(commentService.selectCommentById(commentId).getUsername());
        commentReportEntity.setCommentId(commentId);
        int id = reportService.addCommentReport(commentReportEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("commentReportId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "更新作文举报状态")
    @PutMapping("/composition/status/{compositionReportId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "articleId", value = "文章ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
                    "PushArticle")
    })
    public  ResponseEntity<ApiResult<Boolean>> updateCompositionReport(@PathVariable("compositionReportId") Integer compositionReportId,
                                                              @RequestBody CompositionReportEntity compositionReportEntity) {
        reportService.updateCompositionReport(compositionReportEntity, compositionReportId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "更新评论举报状态")
    @PutMapping("/comment/status/{commentReportId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "articleId", value = "文章ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
                    "PushArticle")
    })
    public  ResponseEntity<ApiResult<Boolean>> updateCommentReport(@PathVariable("commentReportId") Integer commentReportId,
                                                              @RequestBody CommentReportEntity commentReportEntity) {
        reportService.updateCommentReport(commentReportEntity, commentReportId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "获取作文举报")
    @GetMapping("/composition")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllCompositionReports() {
        List<CompositionReportEntity> compositionReportEntityList = reportService.selectAllCompositionReports();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("compositionReportEntityList", compositionReportEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "获取评论举报")
    @GetMapping("/comment")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllCommentReports() {
        List<CommentReportEntity> commentReportEntityList = reportService.selectAllCommentReports();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("commentReportEntityList", commentReportEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
