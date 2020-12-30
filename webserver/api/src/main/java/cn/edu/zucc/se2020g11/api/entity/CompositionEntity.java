package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class CompositionEntity {
    private Integer compositionId;

    private String username;

    private Date releaseTime;

    private String compositionBody;

    private Integer status;

    private String title;

    private String description;

    private Integer visibility;

    private Integer score;

    private Integer valid;

    private Double wordScore;

    private Double grammarScore;

    private Double sentenceFluencyScore;

    private Double lengthScore;

    private Double richnessScore;
}