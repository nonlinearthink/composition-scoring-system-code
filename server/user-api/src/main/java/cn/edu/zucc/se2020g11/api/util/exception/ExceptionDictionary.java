package cn.edu.zucc.se2020g11.api.util.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 基本异常类 异常字典
 *
 * @author nonlinearthink
 */
public enum ExceptionDictionary {
    /**
     * TOKEN过期
     */
    TOKEN_EXPIRATION(HttpStatus.UNAUTHORIZED, 1, "认证信息过期"),

    /**
     * 无效的TOKEN签发人
     */
    TOKEN_INVALID_ISSUER(HttpStatus.UNAUTHORIZED, 2, "无效的认证信息签发人"),

    /**
     * 账号和密码不匹配
     */
    PASSWORD_MISMATCH(HttpStatus.NOT_ACCEPTABLE, 3, "账号密码不匹配"),

    /**
     * 数据库错误
     */
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 4, "内部错误");

    @Getter
    @Setter
    private HttpStatus status;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;

    ExceptionDictionary(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
