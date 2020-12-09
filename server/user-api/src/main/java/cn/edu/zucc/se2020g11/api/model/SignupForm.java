package cn.edu.zucc.se2020g11.api.model;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nonlinearthink
 */
@Getter
@Setter
public class SignupForm {
    private String username;

    private String password;

    private String email;

    private String phone;

    private String verifyCode;

    public UserEntity makeUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setEmail(email);
        userEntity.setPhone(phone);
        return userEntity;
    }
}
