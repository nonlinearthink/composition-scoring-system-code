package cn.edu.zucc.se2020g11.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录表结构
 *
 * @author nonlinearthink
 */
@Getter
@Setter
@ApiModel
public class LoginModel {
    @ApiModelProperty(value = "用户名", required = true, example = "wty")
    private String userName;

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;
}
