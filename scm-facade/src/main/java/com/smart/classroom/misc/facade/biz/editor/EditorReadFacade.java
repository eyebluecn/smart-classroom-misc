package com.smart.classroom.misc.facade.biz.editor;

import com.smart.classroom.misc.facade.biz.editor.response.EditorDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface EditorReadFacade {

    EditorDTO queryById(long editorId);

    EditorDTO queryByUsername(String username);

}
