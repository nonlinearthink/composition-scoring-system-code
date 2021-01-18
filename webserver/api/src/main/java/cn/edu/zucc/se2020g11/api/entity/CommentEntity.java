package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 评论实体
 *
 * @author Tuenity
 */
@Getter
@Setter
public class CommentEntity {
    private Integer commentId;

    private Integer compositionId;

    private String username;

    private String commentBody;

    private Date time;

    private Integer status;
}