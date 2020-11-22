package cn.edu.zucc.se2020g11.api.util.log;

import lombok.Getter;
import lombok.Setter;

/**
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
