package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
    private String userName;

    private String password;

    private String nickName;

    private String signature;

    private String avatarUrl;

    private String phone;

    private String email;

    private Boolean isMale;

    private Integer vipDays;
}