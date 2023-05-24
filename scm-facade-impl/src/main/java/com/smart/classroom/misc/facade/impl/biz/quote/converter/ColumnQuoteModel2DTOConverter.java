package com.smart.classroom.misc.facade.impl.biz.quote.converter;


import com.smart.classroom.misc.domain.biz.quote.enums.ColumnQuoteStatus;
import com.smart.classroom.misc.domain.biz.quote.model.ColumnQuoteModel;
import com.smart.classroom.misc.facade.biz.quote.response.ColumnQuoteDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class ColumnQuoteModel2DTOConverter {

    /**
     * 将DO转换成模型
     */
    public static ColumnQuoteDTO convert(ColumnQuoteModel columnQuoteModel) {
        if (columnQuoteModel == null) {
            return null;
        }

        ColumnQuoteDTO columnQuoteDTO = ColumnQuoteDTO.builder()
                .id(columnQuoteModel.getId())
                .createTime(columnQuoteModel.getCreateTime())
                .updateTime(columnQuoteModel.getUpdateTime())

                .columnId(columnQuoteModel.getColumnId())
                .editorId(columnQuoteModel.getEditorId())
                .price(columnQuoteModel.getPrice())
                .status(ColumnQuoteStatus.toString(columnQuoteModel.getStatus()))

                .build();

        return columnQuoteDTO;
    }


}
