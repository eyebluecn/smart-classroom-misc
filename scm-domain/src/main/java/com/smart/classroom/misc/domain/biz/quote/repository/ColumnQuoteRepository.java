package com.smart.classroom.misc.domain.biz.quote.repository;

import com.smart.classroom.misc.domain.biz.quote.model.ColumnQuoteModel;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface ColumnQuoteRepository {

    ColumnQuoteModel queryById(long columnQuoteId);

    /**
     * 查询某个专栏下有效的报价
     */
    ColumnQuoteModel queryByColumnIdAndStatusOk(long columnId);

    ColumnQuoteModel insert(ColumnQuoteModel columnQuoteModel);

}
