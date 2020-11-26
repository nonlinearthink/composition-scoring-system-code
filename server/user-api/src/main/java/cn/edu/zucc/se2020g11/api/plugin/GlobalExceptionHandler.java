package cn.edu.zucc.se2020g11.api.plugin;

import cn.edu.zucc.se2020g11.api.model.ErrorModel;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.util.exception.ExceptionDictionary;
import cn.edu.zucc.se2020g11.api.util.log.LogPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * 全局异常处理器
 *
 * @author nonlinearthink
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger dbLogger = LogManager.getLogger(LogPosition.DB);
    private final Logger systemLogger = LogManager.getLogger(LogPosition.SYSTEM);
    private final Logger businessLogger = LogManager.getLogger(LogPosition.BUSINESS);

    /**
     * 根据 log 的位置获取对应的 Logger
     *
     * @param logPosition Log 的位置
     * @return Logger 特定位置的 logger
     */
    private Logger getLogger(LogPosition logPosition) {
        if (logPosition.getPosition().equals(LogPosition.SYSTEM.getPosition())) {
            return systemLogger;
        } else if (logPosition.getPosition().equals(LogPosition.DB.getPosition())) {
            return dbLogger;
        } else if (logPosition.getPosition().equals(LogPosition.BUSINESS.getPosition())) {
            return businessLogger;
        } else {
            // 默认情况下返回的是根logger
            return LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
        }
    }

    /**
     * 基本异常处理器
     *
     * @param exception 基本异常
     * @return 统一错误信息返回格式
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResponseEntity<ErrorModel> baseExceptionHandler(BaseException exception) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setSuccess(false);
        errorModel.setCode(exception.getCode());
        errorModel.setMessage(exception.getMessage());
        getLogger(exception.getLogPosition()).error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorModel, exception.getStatus());
    }

    /**
     * SQL异常处理器
     *
     * @param exception SQL异常
     * @return 统一错误信息返回格式
     */
    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public ResponseEntity<ErrorModel> sqlExceptionHandler(SQLException exception) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setSuccess(false);
        errorModel.setCode(ExceptionDictionary.SQL_ERROR.getCode());
        errorModel.setMessage(ExceptionDictionary.SQL_ERROR.getMessage());
        dbLogger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorModel, ExceptionDictionary.SQL_ERROR.getStatus());
    }
}
