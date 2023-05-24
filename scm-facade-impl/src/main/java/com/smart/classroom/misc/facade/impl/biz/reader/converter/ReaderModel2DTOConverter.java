package com.smart.classroom.misc.facade.impl.biz.reader.converter;

import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;
import com.smart.classroom.misc.facade.biz.reader.response.ReaderDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class ReaderModel2DTOConverter {

    /**
     * 将DO转换成模型
     */
    public static ReaderDTO convert(ReaderModel readerModel) {
        if (readerModel == null) {
            return null;
        }

        ReaderDTO readerDTO = ReaderDTO.builder()
                .id(readerModel.getId())
                .createTime(readerModel.getCreateTime())
                .updateTime(readerModel.getUpdateTime())
                .username(readerModel.getUsername())

                .build();

        return readerDTO;
    }


}
