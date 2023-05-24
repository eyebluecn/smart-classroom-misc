package com.smart.classroom.misc.repository.impl.editor.converter;

import com.smart.classroom.misc.domain.biz.editor.model.EditorModel;
import com.smart.classroom.misc.repository.orm.editor.EditorDO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class EditorModel2DOConverter {

    /**
     * 将模型转换成DO
     */
    public static EditorDO convert(EditorModel editorModel) {
        if (editorModel == null) {
            return null;
        }

        EditorDO editorDO = EditorDO.builder()
                .username(editorModel.getUsername())
                .password(editorModel.getPassword())
                .workNo(editorModel.getWorkNo())
                .build();

        editorDO.setId(editorModel.getId());
        editorDO.setCreateTime(editorModel.getCreateTime());
        editorDO.setUpdateTime(editorModel.getUpdateTime());

        return editorDO;

    }


}
