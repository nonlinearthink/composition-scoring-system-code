package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.PushArticle;
import cn.edu.zucc.se2020g11.api.model.SuccessModel;
import cn.edu.zucc.se2020g11.api.util.annotation.AdminRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/article")
@Api(value = "ArticleController")
public class ArticleController {

    @AdminRequired
    @PostMapping("")
    @ApiOperation(value = "添加推送文章")
    @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
            "PushArticle")
    public ResponseEntity<SuccessModel> addArticle(@RequestBody PushArticle pushArticle) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AdminRequired
    @ApiOperation(value = "删除推送文章")
    @DeleteMapping("/{articleId}")
    @ApiImplicitParam(paramType = "path", name = "articleId", value = "文章ID", required = true, dataType =
            "Integer")
    public ResponseEntity<SuccessModel> deleteArticle(@PathVariable("articleId") Integer articleId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AdminRequired
    @ApiOperation(value = "更新推送文章")
    @PutMapping("/{articleId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "articleId", value = "文章ID", required = true, dataType =
                    "Integer"),
            @ApiImplicitParam(paramType = "body", name = "pushArticle", value = "文章", required = true, dataType =
                    "PushArticle")
    })
    public ResponseEntity<SuccessModel> updateArticle(@PathVariable("articleId") Integer articleId,
                                                      @RequestBody PushArticle pushArticle) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "请求所有推送文章")
    @GetMapping("")
    public ResponseEntity<SuccessModel> queryAllArticle() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
