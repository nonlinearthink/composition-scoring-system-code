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
     * 用户名不能为空
     */
    USERNAME_IS_NULL(HttpStatus.NOT_ACCEPTABLE, 20, "用户名不能为空"),
    /**
     * 密码不能为空
     */
    PASSWORD_IS_NULL(HttpStatus.NOT_ACCEPTABLE, 21, "密码不能为空"),
    /**
     * 邮箱不能为空
     */
    EMAIL_IS_NULL(HttpStatus.NOT_ACCEPTABLE, 22, "邮箱不能为空"),
    /**
     * 密码错误
     */
    INCORRECT_PASSWORD(HttpStatus.FORBIDDEN, 23, "密码错误"),
    /**
     * 验证码错误
     */
    INCORRECT_VERIFY_CODE(HttpStatus.NOT_ACCEPTABLE, 24, "验证码错误"),
    /**
     * 用户名冲突
     */
    USERNAME_CONFLICTS(HttpStatus.FORBIDDEN, 25, "用户名冲突"),
    /**
     * 用户名不存在
     */
    USERNAME_NOT_EXIST(HttpStatus.NOT_ACCEPTABLE, 26, "用户名不存在"),
    /**
     * 账号冻结中
     */
    ACCOUNT_BEING_FROZEN(HttpStatus.FORBIDDEN, 27, "账号冻结中");

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
