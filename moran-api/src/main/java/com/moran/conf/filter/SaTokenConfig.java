package com.moran.conf.filter;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 路由拦截鉴权
 * @author :moran
 **/
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Resource
    private FilterConfig filterConfig;

    /**
     * 注册Sa-Token全局过滤器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
                    //白名单只需要登录即可
                    SaRouter.match(filterConfig.getLogins())
                            .check(r -> StpUtil.checkLogin()).stop();

                    String url = SaHolder.getRequest().getRequestPath();
                    SaRouter.match("/**")
                            .check(r -> StpUtil.checkPermission(url));
                })).addPathPatterns("/**")
                .excludePathPatterns("/auth/**");
    }
}
