package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.SuccessModel;
import cn.edu.zucc.se2020g11.api.service.EssayService;
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
@RequestMapping("/api/essay")
@Api(value = "EssayController")
public class EssayController {

    private EssayService essayService;

    @Autowired(required = false)
    public EssayController(EssayService essayService) {
        this.essayService = essayService;
    }
    
    @GetMapping("/")
    @ApiOperation(value="获取信息", notes="获取所有随笔信息")
    public ResponseEntity<SuccessModel> getAll() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/details")
    @ApiOperation(value="获取信息", notes="获取用户所有随笔信息")
    @ApiImplicitParam(paramType="path", name="username", value="用户名", required=true, dataType="String")
    public ResponseEntity<UserEntity> getDetail(@PathVariable("username") String username) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{username}/details/{essayId}")
    @ApiOperation(value="获取信息", notes="获取用户随笔信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="path", name="username",value="用户名", required=true, dataType="String"),
        @ApiImplicitParam(paramType="path", name="essayId", value="随笔编号", required=true, dataType="Integer")
    })
    public ResponseEntity<UserEntity> getEssayDetail(@PathVariable("username") String username, @PathVariable("essayId") String essayId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @LoginRequired
    @PutMapping("/{username}/details/{essayId}")
    @ApiOperation(value="获取信息", notes="获取用户随笔信息")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="path", name="username",value="用户名", required=true, dataType="String"),
        @ApiImplicitParam(paramType="path", name="essayId", value="随笔编号", required=true, dataType="Integer")
    })
    public ResponseEntity<UserEntity> updateEssayDetail(@PathVariable("username") String username, @PathVariable("essayId") String essayId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
