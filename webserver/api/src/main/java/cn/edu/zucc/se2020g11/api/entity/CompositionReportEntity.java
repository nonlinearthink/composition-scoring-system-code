package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class CompositionReportEntity {
    private Integer compositionReportId;

    private String username;

    private Integer compositionId;

    private String compositionReportBody;

    private Date time;

    private Integer status;
}