package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.SuccessModel;
import cn.edu.zucc.se2020g11.api.service.DynamicService;
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
@RequestMapping("/api/dynamic")
@Api(value = "DynamicController")
public class DynamicController {

    private DynamicService dynamicService;

    @Autowired(required = false)
    public DynamicController(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }

    @GetMapping("/")
    @ApiOperation(value="获取信息", notes="获取所有动态信息")
    public ResponseEntity<SuccessModel> getAll() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/details")
    @ApiOperation(value="获取信息", notes="获取用户所有动态信息")
    @ApiImplicitParam(paramType="path", name="username", value="用户名", required=true, dataType="String")
    public ResponseEntity<UserEntity> getDetail(@PathVariable("username") String username) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @PostMapping("/{username}/details")
    @ApiOperation(value="添加信息", notes="添加用户动态信息")
    @ApiImplicitParam(paramType="path", name="username", value="用户名", required=true, dataType="String")
    public ResponseEntity<UserEntity> insertDynamicDetail(@PathVariable("username") String username) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/details/{dynamicId}")
    @ApiOperation(value="获取信息", notes="获取用户动态信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name="username",value="用户名", required=true, dataType="String"),
            @ApiImplicitParam(paramType="path", name="dynamicId", value="动态编号", required=true, dataType="Integer")
    })
    public ResponseEntity<UserEntity> getDynamicDetail(@PathVariable("username") String username, @PathVariable("dynamicId") Integer dynamicId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @DeleteMapping("/{username}/details/{dynamicId}")
    @ApiOperation(value="删除信息", notes="删除用户动态信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name="username",value="用户名", required=true, dataType="String"),
            @ApiImplicitParam(paramType="path", name="dynamicId", value="动态编号", required=true, dataType="Integer")
    })
    public ResponseEntity<UserEntity> deleteDynamicDetail(@PathVariable("username") String username, @PathVariable("dynamicId") Integer dynamicId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @PutMapping("/{username}/details/{dynamicId}")
    @ApiOperation(value="修改信息", notes="修改用户动态信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="path", name="username",value="用户名", required=true, dataType="String"),
            @ApiImplicitParam(paramType="path", name="dynamicId", value="动态编号", required=true, dataType="Integer")
    })
    public ResponseEntity<UserEntity> updateDynamicDetail(@PathVariable("username") String username, @PathVariable("dynamicId") Integer dynamicId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
