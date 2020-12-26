package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class CommentReportEntity {
    private Integer commentReportId;

    private Integer commentId;

    private String username;

    private String commentReportBody;

    private Date time;

    private Integer status;
}