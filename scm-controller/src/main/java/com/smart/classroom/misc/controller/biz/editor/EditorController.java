package com.smart.classroom.misc.controller.biz.editor;


import com.smart.classroom.misc.controller.auth.Feature;
import com.smart.classroom.misc.controller.auth.FeatureType;
import com.smart.classroom.misc.controller.auth.LoginStoreService;
import com.smart.classroom.misc.controller.base.BaseController;
import com.smart.classroom.misc.controller.base.result.WebResult;
import com.smart.classroom.misc.facade.biz.editor.EditorReadFacade;
import com.smart.classroom.misc.facade.biz.editor.EditorWriteFacade;
import com.smart.classroom.misc.facade.biz.editor.request.EditorLoginRequest;
import com.smart.classroom.misc.facade.biz.editor.request.EditorRegisterRequest;
import com.smart.classroom.misc.facade.biz.editor.response.EditorDTO;
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
@RequestMapping("/api/editor")
public class EditorController extends BaseController {

    @DubboReference
    EditorWriteFacade editorWriteFacade;

    @DubboReference
    EditorReadFacade editorReadFacade;

    @Autowired
    LoginStoreService loginStoreService;

    /**
     * 小编
     */
    @Feature({FeatureType.EDITOR_LOGIN})
    @RequestMapping("/tiny/login")
    public WebResult<?> tinyLogin() {

        EditorDTO editorDTO = this.checkLoginEditor();

        return success(editorDTO);
    }

    /**
     * 注册一个小编
     */
    @Feature({FeatureType.PUBLIC})
    @RequestMapping("/register")
    public WebResult<?> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String workNo
    ) {

        EditorRegisterRequest request = new EditorRegisterRequest(
                username,
                password,
                workNo
        );

        EditorDTO editorDTO = editorWriteFacade.register(request);

        return success(editorDTO);
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
        if ("demo_editor".equals(username) && "123456".equals(password)) {
            EditorDTO editorDTO = editorReadFacade.queryByUsername(username);
            if (editorDTO == null) {
                EditorDTO registerDTO = editorWriteFacade.register(new EditorRegisterRequest(username, password, username));
                log.info("自动注册演示账号成功{}", registerDTO);
            }
        }


        EditorDTO editorDTO = editorWriteFacade.login(new EditorLoginRequest(
                username,
                password
        ));

        //设置cookie.
        String key = StringUtil.uuid();
        Cookie cookie = new Cookie(LoginStoreService.EDITOR_COOKIE_KEY, key);
        //存储一年
        cookie.setMaxAge(60 * 60 * 24 * 365);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        loginStoreService.storeLoginEditor(key, editorDTO);

        return success(editorDTO);
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

        EditorDTO editor = this.findLoginEditor();
        if (editor == null) {
            return success("已经退出了登录");
        }

        //清楚本地缓存
        String authentication = loginStoreService.getAuthentication(loginStoreService.getRequest(), LoginStoreService.EDITOR_COOKIE_KEY);
        loginStoreService.clearLoginEditor(authentication);

        //清除cookie
        Cookie cookie = new Cookie(LoginStoreService.EDITOR_COOKIE_KEY, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return success("退出成功！");
    }


}
