package com.smart.classroom.misc.domain.biz.editor.repository;

import com.smart.classroom.misc.domain.biz.editor.model.EditorModel;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface EditorRepository {

    EditorModel queryById(long id);

    EditorModel queryByUsername(String username);

    EditorModel queryByWorkNo(String workNo);

    EditorModel insert(EditorModel editorModel);

}
