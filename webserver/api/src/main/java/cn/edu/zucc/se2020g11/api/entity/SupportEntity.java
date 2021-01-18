package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 点赞实体
 *
 * @author Tuenity
 */
@Getter
@Setter
public class SupportEntity {
    private Integer supportId;

    private String username;

    private Integer compositionId;

    private Date time;
}