package cn.edu.zucc.se2020g11.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nonlinearthink
 */
@Getter
@Setter
public class PasswordModel {

    private String oldPassword;

    private String newPassword;
}
