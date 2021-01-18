package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 历史记录实体
 *
 * @author Tuenity
 */
@Getter
@Setter
public class HistoryEntity {
    private Integer historyId;

    private String username;

    private Integer compositionId;

    private Date time;
}