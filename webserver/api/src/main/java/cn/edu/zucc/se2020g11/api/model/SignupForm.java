package cn.edu.zucc.se2020g11.api.model;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 注册表单
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
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
