package com.smart.classroom.misc.facade.impl.biz.editor;

import com.smart.classroom.misc.application.biz.editor.EditorWriteAppService;
import com.smart.classroom.misc.domain.biz.editor.model.EditorModel;
import com.smart.classroom.misc.facade.biz.editor.EditorWriteFacade;
import com.smart.classroom.misc.facade.biz.editor.request.EditorLoginRequest;
import com.smart.classroom.misc.facade.biz.editor.request.EditorRegisterRequest;
import com.smart.classroom.misc.facade.biz.editor.response.EditorDTO;
import com.smart.classroom.misc.facade.impl.biz.editor.converter.EditorModel2DTOConverter;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@DubboService
public class EditorWriteFacadeImpl implements EditorWriteFacade {

    @Resource
    EditorWriteAppService editorWriteAppService;


    /**
     * 注册一个读者
     */
    public EditorDTO register(EditorRegisterRequest request) {

        EditorModel editorModel = editorWriteAppService.register(request.getUsername(), request.getPassword(),request.getWorkNo());

        return EditorModel2DTOConverter.convert(editorModel);
    }


    @Override
    public EditorDTO login(EditorLoginRequest request) {


        EditorModel editorModel = editorWriteAppService.login(request.getUsername(), request.getPassword());

        return EditorModel2DTOConverter.convert(editorModel);
    }


}
