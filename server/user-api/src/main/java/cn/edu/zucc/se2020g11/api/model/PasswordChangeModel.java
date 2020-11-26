package cn.edu.zucc.se2020g11.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 密码修改模型
 *
 * @author nonlinearthink
 */
@Getter
@Setter
public class PasswordChangeModel {

    private String oldPassword;

    private String newPassword;
}
