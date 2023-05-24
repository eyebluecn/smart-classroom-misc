package com.smart.classroom.misc.controller.auth;

import com.smart.classroom.misc.facade.biz.editor.response.EditorDTO;
import com.smart.classroom.misc.facade.biz.reader.response.ReaderDTO;
import com.smart.classroom.misc.utility.exception.ExceptionCode;
import com.smart.classroom.misc.utility.exception.UtilException;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lishuang
 * @date 2023-05-18
 */
@Service
public class LoginStoreService {

    //简单将登录的读者存在这里。 TODO: 这里的做法仅为了演示，实际要将登录信息分布式持久化。
    Map<String, ReaderDTO> loginReaders = new HashMap<>();
    Map<String, EditorDTO> loginEditors = new HashMap<>();


    public static String READER_COOKIE_KEY = "rxy";
    public static String AUTHOR_COOKIE_KEY = "axy";
    public static String EDITOR_COOKIE_KEY = "exy";


    public void storeLoginReader(String key, ReaderDTO readerDTO) {
        this.loginReaders.put(key, readerDTO);
    }


    public void storeLoginEditor(String key, EditorDTO editorDTO) {
        this.loginEditors.put(key, editorDTO);
    }


    public void clearLoginReader(String key) {
        this.loginReaders.remove(key);
    }


    public void clearLoginEditor(String key) {
        this.loginEditors.remove(key);
    }


    //从request中获取用户登录的token
    public String getAuthentication(HttpServletRequest request, String key) {

        if (request == null) {
            return null;
        }
        //尝试去验证token。
        Cookie[] cookies = request.getCookies();
        String authentication = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    authentication = cookie.getValue();
                }
            }
        }

        //尝试从request中读取
        if (authentication == null) {
            authentication = request.getParameter(key);
        }

        return authentication;
    }


    public ReaderDTO findLoginReader(HttpServletRequest request) {
        String authentication = this.getAuthentication(request, READER_COOKIE_KEY);
        ReaderDTO readerDTO = this.loginReaders.get(authentication);

        return readerDTO;
    }


    public ReaderDTO checkLoginReader(HttpServletRequest request) {
        ReaderDTO readerDTO = this.findLoginReader(request);
        if (readerDTO == null) {
            throw new UtilException(ExceptionCode.LOGIN, "未登录，禁止访问");
        }
        return readerDTO;
    }

    public EditorDTO findLoginEditor(HttpServletRequest request) {
        String authentication = this.getAuthentication(request, EDITOR_COOKIE_KEY);
        EditorDTO editorDTO = this.loginEditors.get(authentication);

        return editorDTO;
    }


    public EditorDTO checkLoginEditor(HttpServletRequest request) {
        EditorDTO editorDTO = this.findLoginEditor(request);
        if (editorDTO == null) {
            throw new UtilException(ExceptionCode.LOGIN, "未登录，禁止访问");
        }
        return editorDTO;
    }


    //获取到当前这个链接的的request
    public HttpServletRequest getRequest() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            return attributes.getRequest();
        } catch (Throwable throwable) {
            return null;
        }
    }


    public ReaderDTO findLoginReader() {
        HttpServletRequest request = getRequest();
        return this.findLoginReader(request);
    }


    public ReaderDTO checkLoginReader() {
        HttpServletRequest request = getRequest();
        return this.checkLoginReader(request);
    }


    public EditorDTO findLoginEditor() {
        HttpServletRequest request = getRequest();
        return this.findLoginEditor(request);
    }


    public EditorDTO checkLoginEditor() {
        HttpServletRequest request = getRequest();
        return this.checkLoginEditor(request);
    }


    /**
     * 做权限拦截的事情。主要给AuthInterceptor使用
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return 是否能够顺理通过鉴权
     */
    @Transactional
    public boolean doAuthIntercept(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {

        HandlerMethod handlerMethod = null;
        if (handler instanceof ResourceHttpRequestHandler) {
            //资源文件处理器的拦截
            return true;
        } else {
            try {
                handlerMethod = (HandlerMethod) handler;

                if (handlerMethod.getBeanType() == BasicErrorController.class) {
                    //系统内部的基础错误处理器，直接放行
                    return true;
                }
            } catch (Exception e) {
                throw new UtilException("转换HandlerMethod出错，请及时排查。");
            }
        }


        Feature feature = handlerMethod.getMethodAnnotation(Feature.class);

        if (feature != null) {
            FeatureType[] types = feature.value();

            //公共接口直接允许访问。
            if (types[0] == FeatureType.PUBLIC) {
                return true;
            } else {


                List<FeatureType> featureTypes = Arrays.asList(types);
                if (featureTypes.contains(FeatureType.READER_LOGIN)) {
                    ReaderDTO loginReader = this.findLoginReader(request);
                    if (loginReader != null) {
                        return true;
                    }

                }

                if (featureTypes.contains(FeatureType.EDITOR_LOGIN)) {
                    EditorDTO loginEditor = this.findLoginEditor(request);
                    if (loginEditor != null) {
                        return true;
                    }
                }

                if (featureTypes.contains(FeatureType.AUTHOR_LOGIN)) {
                    throw new UtilException(ExceptionCode.LOGIN, "暂不支持作者登录");
                }

                throw new UtilException(ExceptionCode.LOGIN, "没有登录，禁止访问");
            }

        } else {
            throw new UtilException("您访问的接口{}不存在或者未配置Feature！", request.getRequestURI());
        }

    }


}
