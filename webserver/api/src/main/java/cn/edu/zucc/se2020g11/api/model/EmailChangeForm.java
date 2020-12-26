package cn.edu.zucc.se2020g11.api.model;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class EmailChangeForm
{
    @NotNull(message = "username不能为空")
    private String username;

    private String oldEmail;

    private String newEmail;

    private String oldVerifyCode;

    private String newVerifyCode;

    public UserEntity makeUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setEmail(newEmail);
        return userEntity;
    }
}
