package cn.edu.zucc.se2020g11.api.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 基本异常类 异常字典
 *
 * @author nonlinearthink
 */
public enum ErrorDictionary {
    /**
     * 数据库错误
     */
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1, "数据库错误"),
    /**
     * 过于频繁的请求
     */
    REQUEST_TOO_FREQUENTLY(HttpStatus.FORBIDDEN, 2, "过于频繁地请求"),
    /**
     * 非法请求
     */
    INVALID_REQUEST(HttpStatus.FORBIDDEN, 3, "非法请求"),
    /**
     * 参数校验失败
     */
    INVALID_PARAMETER(HttpStatus.NOT_ACCEPTABLE, 4, "参数校验失败"),
    /**
     * 没有携带token
     */
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, 10, "没有携带token"),
    /**
     * token过期
     */
    TOKEN_EXPIRATION(HttpStatus.UNAUTHORIZED, 11, "token过期"),
    /**
     * 错误构造的token
     */
    TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED, 12, "错误构造的token"),
    /**
     * 构造正确，但是不被支持的token
     */
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, 13, "构造正确，但是不被支持的token"),
    /**
     * token认证失败
     */
    TOKEN_AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, 14, "token认证失败"),
    /**
     * 密码错误
     */
    INCORRECT_PASSWORD(HttpStatus.FORBIDDEN, 20, "密码错误"),
    /**
     * 验证码错误
     */
    INCORRECT_VERIFY_CODE(HttpStatus.NOT_ACCEPTABLE, 21, "验证码错误"),
    /**
     * 用户名冲突
     */
    USERNAME_CONFLICTS(HttpStatus.FORBIDDEN, 22, "用户名冲突"),
    /**
     * 用户名不存在
     */
    USERNAME_NOT_EXIST(HttpStatus.NOT_ACCEPTABLE, 23, "用户名不存在"),
    /**
     * 账号冻结中
     */
    ACCOUNT_BEING_FROZEN(HttpStatus.FORBIDDEN, 24, "账号冻结中");

    @Getter
    @Setter
    private HttpStatus status;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;

    ErrorDictionary(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
