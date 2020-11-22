package cn.edu.zucc.se2020g11.api.util.log;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nonlinearthink
 */
public enum LogLevel {
    /**
     * log4j2 info等级的信息
     */
    INFO("info"),
    /**
     * log4j2 warn等级的信息
     */
    WARN("warn"),
    /**
     * log4j2 error等级的信息
     */
    ERROR("error"),
    /**
     * log4j2 fatal等级的信息
     */
    FATAL("fatal");

    @Getter
    @Setter
    private String level;

    LogLevel(String level) {
        this.level = level;
    }
}
