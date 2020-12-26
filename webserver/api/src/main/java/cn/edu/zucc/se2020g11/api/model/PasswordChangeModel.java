package cn.edu.zucc.se2020g11.api.model;

import cn.edu.zucc.se2020g11.api.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 密码修改模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class PasswordChangeModel {
    @NotNull(message = "oldPassword不能为空")
    private String oldPassword;

    @NotNull(message = "newPassword不能为空")
    private String newPassword;
}
