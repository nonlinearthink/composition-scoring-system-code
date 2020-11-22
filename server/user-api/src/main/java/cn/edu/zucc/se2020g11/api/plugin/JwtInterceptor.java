package cn.edu.zucc.se2020g11.api.plugin;

import cn.edu.zucc.se2020g11.api.util.JwtUtil;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * JWT拦截器，需要搭配注解@LoginRequired使用
 *
 * @author nonlinearthink
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //不拦截定义在非方法上的映射
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //获取被拦截的方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断该方法是否使用了注解@LoginRequired
        LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
        //对使用了注解的方法进行登入认证
        if (loginRequired != null) {
            String token = request.getHeader(JwtUtil.HEADER);
            JwtUtil.validateToken(token);
        }
        return true;
    }
}
