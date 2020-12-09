package cn.edu.zucc.se2020g11.api.config.intercepter;

import cn.edu.zucc.se2020g11.api.service.JwtService;
import cn.edu.zucc.se2020g11.api.util.annotation.LoginRequired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * JWT拦截器，需要搭配注解@LoginRequired和@AdminRequired使用
 *
 * @author nonlinearthink
 */
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtService jwtService = new JwtService();

    private final String httpHeader;

    public JwtInterceptor(String httpHeader) {
        this.httpHeader = httpHeader;
    }

    /**
     * 在处理之前完成的动作
     *
     * @param request  HTTP请求
     * @param response HTTP返回信息
     * @param handler  处理器
     * @return 是否成功
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 不拦截定义在非方法上的映射
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 获取被拦截的方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断该方法是否使用了注解@LoginRequired
        LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
        // 对使用了注解的方法进行登录认证
        if (loginRequired != null) {
            // 获取token
            String token = request.getHeader(httpHeader);
            // 设置 username 参数
            request.setAttribute("username", jwtService.validateToken(token, loginRequired.type()));
        }
        return true;
    }

}
