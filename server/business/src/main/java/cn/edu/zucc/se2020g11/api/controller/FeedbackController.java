package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.constant.UserType;
import cn.edu.zucc.se2020g11.api.entity.FeedbackEntity;
import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.service.FeedbackService;
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
@RequestMapping("/api/feedback")
@Api(value = "FeedbackController")
public class FeedbackController
{
    private FeedbackService feedbackService;

    @Autowired(required = false)
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @LoginRequired(type = UserType.USER)
    @PostMapping("")
    @ApiOperation(value = "添加用户反馈")
    @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
            "PushArticle")
    public ResponseEntity<ApiResult<Map<String, Object>>> addFeedback(@RequestBody FeedbackEntity feedbackEntity, HttpServletRequest request) {
        feedbackEntity.setUsername((String)request.getAttribute("username"));
        int id = feedbackService.addFeedback(feedbackEntity);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("添加成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("feedbackId", id);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "更新用户反馈")
    @PutMapping("/{feedbackId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "articleId", value = "文章ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
                    "PushArticle")
    })
    public  ResponseEntity<ApiResult<Boolean>> updateFeedback(@PathVariable("feedbackId") Integer feedbackId,
                                                                   @RequestBody FeedbackEntity feedbackEntity, HttpServletRequest request) {
        feedbackService.updateFeedback(feedbackEntity, feedbackId);
        ApiResult<Boolean> result = new ApiResult<>();
        result.setMsg("修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @LoginRequired(type = UserType.ADMIN)
    @ApiOperation(value = "获取用户反馈")
    @GetMapping("")
    public ResponseEntity<ApiResult<Map<String, Object>>> selectAllFeedbacks() {
        List<FeedbackEntity> feedbackEntityList = feedbackService.selectAllFeedbacks();
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setMsg("获取成功");
        Map<String, Object> data = new HashMap<>(1);
        data.put("feedbackEntityList", feedbackEntityList);
        result.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
