package cn.edu.zucc.se2020g11.api.config;

import cn.edu.zucc.se2020g11.api.plugin.JwtInterceptor;
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
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册JWT拦截器
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**");
    }
}
