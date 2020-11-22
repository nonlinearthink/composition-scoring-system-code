package cn.edu.zucc.se2020g11.apiserver.controller;

import cn.edu.zucc.se2020g11.apiserver.entity.UserEntity;
import cn.edu.zucc.se2020g11.apiserver.model.LoginModel;
import cn.edu.zucc.se2020g11.apiserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author nonlinearthink
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired(required = false)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("test")
    public ResponseEntity<UserEntity> test() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("wty");
        userEntity.setPassword("123");
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginModel loginModel) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<UserEntity> signup(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
