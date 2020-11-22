package cn.edu.zucc.se2020g11.api.util.exception;

import cn.edu.zucc.se2020g11.api.util.log.LogLevel;
import cn.edu.zucc.se2020g11.api.util.log.LogPosition;
import cn.edu.zucc.se2020g11.api.util.log.LogUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 全局异常处理器
 *
 * @author nonlinearthink
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 基本异常处理器
     *
     * @param exception 基本异常
     * @return 统一错误信息返回格式
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> baseExceptionHandler(BaseException exception) {
        Map<String, Object> response = new TreeMap<>();
        response.put("success", false);
        response.put("code", exception.getCode());
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, exception.getStatus());
    }

    /**
     * SQL异常处理器
     *
     * @param exception SQL异常
     * @return 统一错误信息返回格式
     */
    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sqlExceptionHandler(SQLException exception) {
        LogUtil.print(exception.getMessage(), LogPosition.DB, LogLevel.ERROR, exception);
        return baseExceptionHandler(new BaseException(ExceptionDictionary.SQL_ERROR));
    }
}
