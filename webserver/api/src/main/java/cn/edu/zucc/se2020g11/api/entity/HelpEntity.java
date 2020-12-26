package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Tuenity
 */
@Getter
@Setter
public class HelpEntity {
    private Integer helpId;

    private String adminName;

    private String helpTitle;

    private String helpBody;

    private Date time;
}