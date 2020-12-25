package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class UserEntity {
    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private String signature;

    private String avatarUrl;

    private Boolean isMale;

    private Integer vipDays;

    private Boolean frozen;

    private Date defrostingTime;
}