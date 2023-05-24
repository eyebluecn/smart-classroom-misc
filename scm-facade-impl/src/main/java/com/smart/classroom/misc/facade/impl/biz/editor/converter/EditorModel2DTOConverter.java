package com.smart.classroom.misc.facade.impl.biz.editor.converter;

import com.smart.classroom.misc.domain.biz.editor.model.EditorModel;
import com.smart.classroom.misc.facade.biz.editor.response.EditorDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class EditorModel2DTOConverter {

    /**
     * 将DO转换成模型
     */
    public static EditorDTO convert(EditorModel editorModel) {
        if (editorModel == null) {
            return null;
        }

        EditorDTO editorDTO = EditorDTO.builder()
                .id(editorModel.getId())
                .createTime(editorModel.getCreateTime())
                .updateTime(editorModel.getUpdateTime())
                .username(editorModel.getUsername())
                .workNo(editorModel.getWorkNo())

                .build();

        return editorDTO;
    }


}
