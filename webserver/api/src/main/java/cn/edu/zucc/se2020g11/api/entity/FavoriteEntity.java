package cn.edu.zucc.se2020g11.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 收藏实体
 *
 * @author Tuenity
 */
@Getter
@Setter
public class FavoriteEntity {
    private Integer favoriteId;

    private Integer compositionId;

    private String username;

    private Date time;
}