package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class PushArticleEntity {
    private Integer articleId;

    private String adminName;

    private String articleTitle;

    private String articleBody;

    private Date time;
}