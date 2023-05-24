package com.smart.classroom.misc.controller.biz.reader;


import com.smart.classroom.misc.controller.auth.Feature;
import com.smart.classroom.misc.controller.auth.FeatureType;
import com.smart.classroom.misc.controller.auth.LoginStoreService;
import com.smart.classroom.misc.controller.base.BaseController;
import com.smart.classroom.misc.controller.base.result.WebResult;
import com.smart.classroom.misc.facade.biz.reader.ReaderReadFacade;
import com.smart.classroom.misc.facade.biz.reader.ReaderWriteFacade;
import com.smart.classroom.misc.facade.biz.reader.request.ReaderLoginRequest;
import com.smart.classroom.misc.facade.biz.reader.request.ReaderRegisterRequest;
import com.smart.classroom.misc.facade.biz.reader.response.ReaderDTO;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller.
 *
 * @author lishuang
 * @date 2023-05-13
 */
@Slf4j
@RestController
@RequestMapping("/api/reader")
public class ReaderController extends BaseController {

    @DubboReference
    ReaderWriteFacade readerWriteFacade;

    @DubboReference
    ReaderReadFacade readerReadFacade;

    @Autowired
    LoginStoreService loginStoreService;

    /**
     * 读者
     */
    @Feature({FeatureType.READER_LOGIN})
    @RequestMapping("/tiny/login")
    public WebResult<?> tinyLogin() {

        ReaderDTO readerDTO = this.checkLoginReader();

        return success(readerDTO);
    }

    /**
     * 注册一个读者
     */
    @Feature({FeatureType.PUBLIC})
    @RequestMapping("/register")
    public WebResult<?> register(
            @RequestParam String username,
            @RequestParam String password
    ) {

        ReaderRegisterRequest request = new ReaderRegisterRequest(
                username,
                password
        );

        ReaderDTO readerDTO = readerWriteFacade.register(request);

        return success(readerDTO);
    }

    /**
     * 登录
     */
    @Feature({FeatureType.PUBLIC})
    @RequestMapping("/login")
    public WebResult<?> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        //如果是测试账号，保证测试账号已注册。
        if ("demo_reader".equals(username) && "123456".equals(password)) {
            ReaderDTO readerDTO = readerReadFacade.queryByUsername(username);
            if (readerDTO == null) {
                ReaderDTO registerDTO = readerWriteFacade.register(new ReaderRegisterRequest(username, password));
                log.info("自动注册演示账号成功{}", registerDTO);
            }
        }


        ReaderDTO readerDTO = readerWriteFacade.login(new ReaderLoginRequest(
                username,
                password
        ));

        //设置cookie.
        String key = StringUtil.uuid();
        Cookie cookie = new Cookie(LoginStoreService.READER_COOKIE_KEY, key);
        //存储一年
        cookie.setMaxAge(60 * 60 * 24 * 365);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        loginStoreService.storeLoginReader(key, readerDTO);

        return success(readerDTO);
    }

    /**
     * 退出登录
     */
    @Feature({FeatureType.PUBLIC})
    @RequestMapping("/logout")
    public WebResult<?> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        ReaderDTO reader = this.findLoginReader();
        if (reader == null) {
            return success("已经退出了登录");
        }

        //清楚本地缓存
        String authentication = loginStoreService.getAuthentication(loginStoreService.getRequest(), LoginStoreService.READER_COOKIE_KEY);
        loginStoreService.clearLoginReader(authentication);

        //清除cookie
        Cookie cookie = new Cookie(LoginStoreService.READER_COOKIE_KEY, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return success("退出成功！");
    }


}
