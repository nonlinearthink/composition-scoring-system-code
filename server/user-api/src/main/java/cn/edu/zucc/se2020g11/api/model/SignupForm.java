package cn.edu.zucc.se2020g11.api.model;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author nonlinearthink
 */
@Getter
@Setter
public class SignupForm {
    @NotNull(message = "username不能为空")
    private String username;

    @NotNull(message = "password不能为空")
    private String password;

    @NotNull(message = "email不能为空")
    private String email;

    private String phone;

    @NotNull(message = "verifyCode不能为空")
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
