package cn.edu.zucc.se2020g11.api.controller;

import cn.edu.zucc.se2020g11.api.model.ApiResult;
import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author nonlinearthink
 */
@ControllerAdvice
@RestController
public class GlobalExceptionController {
    private final Logger dbLogger = LogManager.getLogger(LogCategory.DB);
    private final Logger systemLogger = LogManager.getLogger(LogCategory.SYSTEM);
    private final Logger businessLogger = LogManager.getLogger(LogCategory.BUSINESS);

    /**
     * 根据 log 的位置获取对应的 Logger
     *
     * @param logCategory Log 的位置
     * @return Logger 特定位置的 logger
     */
    private Logger getLogger(LogCategory logCategory) {
        if (logCategory.getPosition().equals(LogCategory.SYSTEM.getPosition())) {
            return systemLogger;
        } else if (logCategory.getPosition().equals(LogCategory.DB.getPosition())) {
            return dbLogger;
        } else if (logCategory.getPosition().equals(LogCategory.BUSINESS.getPosition())) {
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
    public ResponseEntity<ApiResult<String>> baseExceptionHandler(BaseException exception) {
        ApiResult<String> result = new ApiResult<>();
        result.setCode(exception.getCode());
        result.setMsg(exception.getMessage());
        getLogger(exception.getLogCategory()).error(exception.getMessage(), exception);
        return ResponseEntity.status(exception.getStatus()).body(result);
    }

    /**
     * SQL异常处理器
     *
     * @param exception SQL异常
     * @return 统一错误信息返回格式
     */
    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public ResponseEntity<ApiResult<String>> sqlExceptionHandler(SQLException exception) {
        ErrorDictionary errorMsg = ErrorDictionary.SQL_ERROR;
        ApiResult<String> result = new ApiResult<>();
        result.setCode(errorMsg.getCode());
        result.setMsg(errorMsg.getMessage());
        dbLogger.error(exception.getMessage(), exception);
        return ResponseEntity.status(errorMsg.getStatus()).body(result);
    }

    /**
     * 参数检验错误
     *
     * @param exception 参数检验异常
     * @return 统一错误信息返回格式
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<Object>> methodArgumentNotValidExceptionHandler(HttpServletRequest request,
                                                                                    MethodArgumentNotValidException exception) {
        ErrorDictionary errorMsg = ErrorDictionary.INVALID_PARAMETER;
        ApiResult<Object> result = new ApiResult<>();
        result.setCode(errorMsg.getCode());
        result.setMsg(errorMsg.getMessage());
        Map<String, Object> data = new HashMap<>(1);
        // 收集所有错误信息
        data.put("details", exception.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage));
        result.setData(data);
        businessLogger.error(exception.getMessage(), exception);
        return ResponseEntity.status(errorMsg.getStatus()).body(result);
    }
}
