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
     * 没有携带token
     */
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, 1, "需要登入认证信息"),
    /**
     * token过期
     */
    TOKEN_EXPIRATION(HttpStatus.UNAUTHORIZED, 2, "登入认证信息过期"),
    /**
     * 错误构造的token
     */
    TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED, 3, "错误构造的登入认证信息"),
    /**
     * 构造正确，但是不被支持的token
     */
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, 4, "不被支持的登入认证信息"),
    /**
     * token认证失败
     */
    AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, 5, "登入认证信息认证失败"),
    /**
     * 无效的token签发人
     */
    TOKEN_INVALID_ISSUER(HttpStatus.UNAUTHORIZED, 6, "无效的登入认证信息签发人"),
    /**
     * 账号和密码不匹配
     */
    PASSWORD_MISMATCH(HttpStatus.NOT_ACCEPTABLE, 7, "账号密码不匹配"),
    /**
     * 数据库错误
     */
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 8, "内部错误");

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
