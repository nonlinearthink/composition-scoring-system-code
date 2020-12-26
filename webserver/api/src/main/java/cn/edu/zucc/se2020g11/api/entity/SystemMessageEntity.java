package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class SystemMessageEntity {
    private Integer sMessageId;

    private String adminName;

    private String sMessageBody;

    private Date time;
}