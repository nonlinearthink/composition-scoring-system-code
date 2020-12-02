package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
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
    @ApiOperation(value="获取所有作文信息")
    public ResponseEntity<SuccessModel> getAll() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/details")
    @ApiOperation(value="获取用户所有作文信息")
    @ApiImplicitParam(paramType="path", name="username", value="用户名", required=true, dataType="String")
    public ResponseEntity<UserEntity> getDetail(@PathVariable("username") String username) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @PostMapping("/{username}/details")
    @ApiOperation(value="添加用户作文信息")
    @ApiImplicitParam(paramType="path", name="username", value="用户名", required=true, dataType="String")
    public ResponseEntity<UserEntity> insertCompositionDetail(@PathVariable("username") String username) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/details/{compositionId}")
    @ApiOperation(value="获取用户作文信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name="username",value="用户名", required=true, dataType="String"),
            @ApiImplicitParam(paramType="path", name="compositionId", value="作文编号", required=true, dataType="Integer")
    })
    public ResponseEntity<UserEntity> getCompositionDetail(@PathVariable("username") String username, @PathVariable("compositionId") Integer compositionId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @DeleteMapping("/{username}/details/{compositionId}")
    @ApiOperation(value="删除用户作文信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name="username",value="用户名", required=true, dataType="String"),
            @ApiImplicitParam(paramType="path", name="compositionId", value="作文编号", required=true, dataType="Integer")
    })
    public ResponseEntity<UserEntity> deleteCompositionDetail(@PathVariable("username") String username, @PathVariable("compositionId") Integer compositionId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @PutMapping("/{username}/details/{compositionId}")
    @ApiOperation(value="修改用户作文信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name="username",value="用户名", required=true, dataType="String"),
            @ApiImplicitParam(paramType="path", name="compositionId", value="作文编号", required=true, dataType="Integer")
    })
    public ResponseEntity<UserEntity> updateCompositionDetail(@PathVariable("username") String username, @PathVariable("compositionId") Integer compositionId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
