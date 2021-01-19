package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 登录表单
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class LoginForm {
    @ApiModelProperty(value = "用户名", required = true, example = "test")
    @NotNull(message = "username不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotNull(message = "password不能为空")
    private String password;
}
