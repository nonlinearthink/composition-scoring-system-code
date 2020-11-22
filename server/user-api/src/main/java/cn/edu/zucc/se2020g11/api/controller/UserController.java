package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import cn.edu.zucc.se2020g11.api.model.LoginModel;
import cn.edu.zucc.se2020g11.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
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

    @GetMapping("/test")
    public ResponseEntity<UserEntity> test() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("wty");
        String s = "123";
        userEntity.setPassword(DigestUtils.md5DigestAsHex(s.getBytes()));
        System.out.println(userEntity.getPassword());
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginModel loginModel) {
        System.out.println(loginModel.getUserName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> signup(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
