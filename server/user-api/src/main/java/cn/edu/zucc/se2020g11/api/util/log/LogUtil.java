package cn.edu.zucc.se2020g11.api.util.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author nonlinearthink
 */
public class LogUtil {
    private static final Map<String, Logger> LOGGER_MAP;

    static {
        LOGGER_MAP = new TreeMap<>();
        LOGGER_MAP.put("db", LogManager.getLogger("db"));
        LOGGER_MAP.put("business", LogManager.getLogger("business"));
        LOGGER_MAP.put("system", LogManager.getLogger("system"));
    }

    /**
     * 日志打印
     *
     * @param message  信息
     * @param position 打印位置
     * @param level    打印等级
     */
    public static void print(String message, @NotNull LogPosition position, @NotNull LogLevel level) {
        Logger currentLogger = LOGGER_MAP.get(position.getPosition());
        switch (level.getLevel()) {
            case "info":
                currentLogger.info(message);
                break;
            case "warn":
                currentLogger.warn(message);
                break;
            case "error":
                currentLogger.error(message);
                break;
            case "fatal":
                currentLogger.fatal(message);
                break;
            default:
                break;
        }
    }

    /**
     * 日志打印
     *
     * @param message   信息
     * @param position  打印位置
     * @param level     打印等级
     * @param throwable 可被抛出的异常
     */
    public static void print(String message, @NotNull LogPosition position, @NotNull LogLevel level,
                             Throwable throwable) {
        Logger currentLogger = LOGGER_MAP.get(position.getPosition());
        switch (level.getLevel()) {
            case "info":
                currentLogger.info(message, throwable);
                break;
            case "warn":
                currentLogger.warn(message, throwable);
                break;
            case "error":
                currentLogger.error(message, throwable);
                break;
            case "fatal":
                currentLogger.fatal(message, throwable);
                break;
            default:
                break;
        }
    }
}
