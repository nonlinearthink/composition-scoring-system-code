package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 管理员登录表单
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class AdminLoginForm
{
    @ApiModelProperty(value = "管理员名", required = true, example = "test")
    @NotNull(message = "adminName不能为空")
    private String adminName;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotNull(message = "password不能为空")
    private String password;
}
