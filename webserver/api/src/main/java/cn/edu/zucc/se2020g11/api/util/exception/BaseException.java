package cn.edu.zucc.se2020g11.api.util.exception;

import cn.edu.zucc.se2020g11.api.constant.ErrorDictionary;
import cn.edu.zucc.se2020g11.api.constant.LogCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 基本异常类
 *
 * @author nonlinearthink
 */
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {
    @Getter
    @Setter
    private HttpStatus status;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private LogCategory logCategory;

    public BaseException(ErrorDictionary errorDictionary, LogCategory logCategory) {
        this.status = errorDictionary.getStatus();
        this.code = errorDictionary.getCode();
        this.message = errorDictionary.getMessage();
        this.logCategory = logCategory;
    }
}
