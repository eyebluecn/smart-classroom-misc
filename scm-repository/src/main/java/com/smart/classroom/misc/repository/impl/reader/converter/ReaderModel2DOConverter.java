package com.smart.classroom.misc.repository.impl.reader.converter;

import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;
import com.smart.classroom.misc.repository.orm.reader.ReaderDO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class ReaderModel2DOConverter {

    /**
     * 将模型转换成DO
     */
    public static ReaderDO convert(ReaderModel readerModel) {
        if (readerModel == null) {
            return null;
        }

        ReaderDO readerDO = ReaderDO.builder()
                .username(readerModel.getUsername())
                .password(readerModel.getPassword())
                .build();

        readerDO.setId(readerModel.getId());
        readerDO.setCreateTime(readerModel.getCreateTime());
        readerDO.setUpdateTime(readerModel.getUpdateTime());

        return readerDO;

    }


}
