package com.smart.classroom.misc.facade.impl.biz.editor;

import com.smart.classroom.misc.application.biz.editor.EditorWriteAppService;
import com.smart.classroom.misc.domain.biz.editor.model.EditorModel;
import com.smart.classroom.misc.facade.biz.editor.EditorReadFacade;
import com.smart.classroom.misc.facade.biz.editor.response.EditorDTO;
import com.smart.classroom.misc.facade.impl.biz.editor.converter.EditorModel2DTOConverter;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@DubboService
public class EditorReadFacadeImpl implements EditorReadFacade {

    @Resource
    EditorWriteAppService editorWriteAppService;


    @Override
    public EditorDTO queryById(long editorId) {
        EditorModel editorModel = editorWriteAppService.queryById(editorId);
        return EditorModel2DTOConverter.convert(editorModel);
    }

    @Override
    public EditorDTO queryByUsername(String username) {
        EditorModel editorModel = editorWriteAppService.queryByUsername(username);
        return EditorModel2DTOConverter.convert(editorModel);
    }
}
