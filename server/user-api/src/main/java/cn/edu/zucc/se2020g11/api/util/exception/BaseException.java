package cn.edu.zucc.se2020g11.api.util.exception;

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

    public BaseException(ExceptionDictionary exceptionDictionary) {
        this.status = exceptionDictionary.getStatus();
        this.code = exceptionDictionary.getCode();
        this.message = exceptionDictionary.getMessage();
    }
}
