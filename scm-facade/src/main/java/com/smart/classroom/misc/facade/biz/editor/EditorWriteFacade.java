package com.smart.classroom.misc.facade.biz.editor;

import com.smart.classroom.misc.facade.biz.editor.request.EditorLoginRequest;
import com.smart.classroom.misc.facade.biz.editor.request.EditorRegisterRequest;
import com.smart.classroom.misc.facade.biz.editor.response.EditorDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface EditorWriteFacade {

    /**
     * 注册一个读者
     */
    EditorDTO register(EditorRegisterRequest request);


    /**
     * 读者登录.
     * 登录失败抛异常
     */
    EditorDTO login(EditorLoginRequest request);


}
