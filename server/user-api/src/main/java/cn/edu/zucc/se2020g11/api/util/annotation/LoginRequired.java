package cn.edu.zucc.se2020g11.api.util.annotation;

import cn.edu.zucc.se2020g11.api.constant.UserType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否需要用户登录，需要和JwtInterceptor一起使用
 *
 * @author nonlinearthink
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
    UserType type() default UserType.ADMIN;
}
