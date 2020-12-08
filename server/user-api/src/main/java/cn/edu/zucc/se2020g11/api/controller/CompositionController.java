package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.*;
import cn.edu.zucc.se2020g11.api.model.SuccessModel;
import cn.edu.zucc.se2020g11.api.service.CompositionService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/composition")
@Api(value = "CompositionController")
public class CompositionController {

    private CompositionService compositionService;

    @Autowired(required = false)
    public CompositionController(CompositionService compositionService) {
        this.compositionService = compositionService;
    }

    @GetMapping("/")
    @ApiOperation(value = "获取所有作文信息")
    public ResponseEntity<SuccessModel> queryAllComposition() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @ApiOperation(value = "获取单个用户所有作文信息")
    @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType = "String")
    public ResponseEntity<SuccessModel> queryCompositionWithUser(@PathVariable("username") String username) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @PostMapping("/{username}")
    @ApiOperation(value = "用户添加作文")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "body", name = "compositionEntity", value = "作文", required = true, dataType =
                    "CompositionEntity")
    })
    public ResponseEntity<SuccessModel> addComposition(@PathVariable("username") String username,
                                                       @RequestBody CompositionEntity compositionEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @ApiOperation(value = "用户删除作文")
    @DeleteMapping("/{username}/{compositionId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer")
    })
    public ResponseEntity<SuccessModel> deleteComposition(@PathVariable("username") String username,
                                                          @PathVariable("compositionId") Integer compositionId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @ApiOperation(value = "用户修改作文")
    @PutMapping("/{username}/{compositionId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "username", value = "用户名", required = true, dataType =
                    "String"),
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "compositionEntity", value = "作文", required = true, dataType =
                    "CompositionEntity")
    })
    public ResponseEntity<SuccessModel> updateComposition(@PathVariable("username") String username,
                                                          @PathVariable("compositionId") Integer compositionId,
                                                          @RequestBody CompositionEntity compositionEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @ApiOperation(value = "他人为作文添加评论")
    @PostMapping("/{compositionId}/comments")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "commentEntity", value = "评论", required = true, dataType =
                    "CommentEntity")
    })
    public ResponseEntity<SuccessModel> addComment(@PathVariable("compositionId") Integer compositionId,
                                                   @RequestBody CommentEntity commentEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @ApiOperation(value = "他人为作文添加点赞")
    @PostMapping("/{compositionId}/support")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "commentEntity", value = "点赞", required = true, dataType =
                    "SupportEntity")
    })
    public ResponseEntity<SuccessModel> addSupport(@PathVariable("compositionId") Integer compositionId,
                                                   @RequestBody SupportEntity supportEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @ApiOperation(value = "他人为作文添加收藏")
    @PostMapping("/{compositionId}/favorite")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "favoriteEntity", value = "收藏", required = true, dataType =
                    "FavoriteEntity")
    })
    public ResponseEntity<SuccessModel> addFavorite(@PathVariable("compositionId") Integer compositionId,
                                                    @RequestBody FavoriteEntity favoriteEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @ApiOperation(value = "他人为作文添加举报")
    @PostMapping("/{compositionId}/report")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "compositionId", value = "作文ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "reportEntity", value = "举报", required = true, dataType =
                    "ReportEntity")
    })
    public ResponseEntity<SuccessModel> addReport(@PathVariable("compositionId") Integer compositionId,
                                                  @RequestBody ReportEntity reportEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
