package com.smart.classroom.misc.repository.impl.column.converter;

import com.smart.classroom.misc.domain.biz.column.model.ColumnModel;
import com.smart.classroom.misc.repository.orm.column.ColumnDO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class ColumnModel2DOConverter {

    /**
     * 将DO转换成模型
     */
    public static ColumnDO convert(ColumnModel columnModel) {
        if (columnModel == null) {
            return null;
        }

        ColumnDO columnDO = ColumnDO.builder()

                .name(columnModel.getName())
                .authorId(columnModel.getAuthorId())
                .status(columnModel.getStatus())
                .build();

        columnDO.setId(columnModel.getId());
        columnDO.setCreateTime(columnModel.getCreateTime());
        columnDO.setUpdateTime(columnModel.getUpdateTime());
        return columnDO;
    }


}
