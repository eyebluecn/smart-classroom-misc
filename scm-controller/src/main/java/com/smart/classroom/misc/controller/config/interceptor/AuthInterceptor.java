package com.smart.classroom.misc.controller.config.interceptor;

import com.smart.classroom.misc.controller.auth.LoginStoreService;
import com.smart.classroom.misc.utility.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//权限拦截器。
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //后台接口不缓存内容，否则IE会出现数据不更新的bug.
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //分为两套鉴权体系

        LoginStoreService loginStoreService = SpringContextUtil.getBean(LoginStoreService.class);
        return loginStoreService.doAuthIntercept(request, response, handler);


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
