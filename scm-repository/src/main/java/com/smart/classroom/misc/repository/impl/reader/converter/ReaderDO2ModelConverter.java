package com.smart.classroom.misc.repository.impl.reader.converter;

import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;
import com.smart.classroom.misc.repository.orm.reader.ReaderDO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class ReaderDO2ModelConverter {

    /**
     * 将DO转换成模型
     */
    public static ReaderModel convert(ReaderDO readerDO) {
        if (readerDO == null) {
            return null;
        }

        return ReaderModel.builder()
                .id(readerDO.getId())
                .createTime(readerDO.getCreateTime())
                .updateTime(readerDO.getUpdateTime())
                .username(readerDO.getUsername())
                .password(readerDO.getPassword())
                .build();
    }


}
