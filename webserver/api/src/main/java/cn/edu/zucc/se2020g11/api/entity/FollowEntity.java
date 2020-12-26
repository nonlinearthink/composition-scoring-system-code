package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class FollowEntity {
    private Integer followId;

    private String username;

    private String targetUsername;

    private Date time;
}