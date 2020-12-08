package cn.edu.zucc.se2020g11.api.config;

import lombok.Getter;
import lombok.Setter;

/**
 * log 位置枚举类
 *
 * @author nonlinearthink
 */
public enum LogPosition {
    /**
     * 数据库层的log
     */
    DB("db"),
    /**
     * 业务层的log
     */
    BUSINESS("business"),
    /**
     * 系统层的log
     */
    SYSTEM("system");

    @Getter
    @Setter
    private String position;

    LogPosition(String position) {
        this.position = position;
    }
}
