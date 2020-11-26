package cn.edu.zucc.se2020g11.api.plugin;

import cn.edu.zucc.se2020g11.api.util.exception.BaseException;
import cn.edu.zucc.se2020g11.api.util.security.JwtApplication;
import cn.edu.zucc.se2020g11.api.util.security.JwtRegistry;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 应用初始化运行器，在应用启动之后被加载
 *
 * @author nonlinearthink
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {
    /**
     * 需要运行的代码
     *
     * @param args 应用程序参数
     * @throws BaseException 基础异常
     */
    @Override
    public void run(ApplicationArguments args) throws BaseException {
        JwtRegistry.register("user", new JwtApplication());
        JwtRegistry.register("admin", new JwtApplication());
    }
}
