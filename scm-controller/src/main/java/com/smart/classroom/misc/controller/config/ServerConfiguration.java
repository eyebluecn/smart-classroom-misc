package com.smart.classroom.misc.controller.config;

import com.smart.classroom.misc.controller.config.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@EnableScheduling 是启用任务调度功能。
@Configuration
@EnableWebMvc
@EnableScheduling
public class ServerConfiguration implements WebMvcConfigurer {

    //添加拦截器。
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //Trace拦截器
        //registry.addInterceptor(new TraceInterceptor());
        //添加权限拦截器
        registry.addInterceptor(new AuthInterceptor());
        //添加监控拦截器
        //registry.addInterceptor(new FootprintInterceptor(AppType.MANAGE));
    }

    //配置模板。
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


    }
}
