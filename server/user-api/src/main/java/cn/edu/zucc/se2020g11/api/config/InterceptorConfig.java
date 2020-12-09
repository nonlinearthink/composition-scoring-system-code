package cn.edu.zucc.se2020g11.api.config;

import cn.edu.zucc.se2020g11.api.config.intercepter.JwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 *
 * @author nonlinearthink
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("${jwt.http-header}")
    private String httpHeader;

    /**
     * 添加拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册JWT拦截器
        registry.addInterceptor(new JwtInterceptor(httpHeader)).addPathPatterns("/**");
    }
}
