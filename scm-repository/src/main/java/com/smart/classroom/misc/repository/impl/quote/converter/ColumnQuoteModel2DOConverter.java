package com.smart.classroom.misc.repository.impl.quote.converter;


import com.smart.classroom.misc.domain.biz.quote.model.ColumnQuoteModel;
import com.smart.classroom.misc.repository.orm.column.ColumnQuoteDO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class ColumnQuoteModel2DOConverter {

    /**
     * 将模型转换成DO
     */
    public static ColumnQuoteDO convert(ColumnQuoteModel columnQuoteModel) {
        if (columnQuoteModel == null) {
            return null;
        }

        ColumnQuoteDO columnQuoteDO = ColumnQuoteDO.builder()
                .columnId(columnQuoteModel.getColumnId())
                .editorId(columnQuoteModel.getEditorId())
                .price(columnQuoteModel.getPrice())
                .status(columnQuoteModel.getStatus())
                .build();

        columnQuoteDO.setId(columnQuoteDO.getId());
        columnQuoteDO.setCreateTime(columnQuoteDO.getCreateTime());
        columnQuoteDO.setUpdateTime(columnQuoteDO.getUpdateTime());

        return columnQuoteDO;
    }


}
