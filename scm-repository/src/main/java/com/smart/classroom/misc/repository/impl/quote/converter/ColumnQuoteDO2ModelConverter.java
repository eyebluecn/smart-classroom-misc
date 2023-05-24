package com.smart.classroom.misc.repository.impl.quote.converter;


import com.smart.classroom.misc.domain.biz.quote.model.ColumnQuoteModel;
import com.smart.classroom.misc.repository.orm.column.ColumnQuoteDO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class ColumnQuoteDO2ModelConverter {

    /**
     * 将DO转换成模型
     */
    public static ColumnQuoteModel convert(ColumnQuoteDO columnQuoteDO) {
        if (columnQuoteDO == null) {
            return null;
        }

        return ColumnQuoteModel.builder()
                .id(columnQuoteDO.getId())
                .createTime(columnQuoteDO.getCreateTime())
                .updateTime(columnQuoteDO.getUpdateTime())
                .columnId(columnQuoteDO.getColumnId())
                .editorId(columnQuoteDO.getEditorId())
                .price(columnQuoteDO.getPrice())
                .status(columnQuoteDO.getStatus())
                .build();
    }


}
