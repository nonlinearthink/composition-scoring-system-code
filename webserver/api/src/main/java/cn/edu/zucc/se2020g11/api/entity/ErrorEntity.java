package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class ErrorEntity {
    private Integer errorId;

    private Integer compositionId;

    private String text;

    private String errorType;
}