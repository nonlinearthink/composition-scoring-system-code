package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class MessageEntity {
    private Integer messageId;

    private String username;

    private String targetUsername;

    private String messageBody;

    private Date sendTime;
}