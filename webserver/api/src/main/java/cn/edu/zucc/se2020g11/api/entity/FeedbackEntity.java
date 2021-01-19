package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 反馈实体
 *
 * @author Tuenity
 */
@Getter
@Setter
public class FeedbackEntity {
    private Integer feedbackId;

    private String username;

    private String feedbackBody;

    private String feedbackType;

    private Date time;

    private Integer status;
}