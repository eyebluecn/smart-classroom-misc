package com.smart.classroom.misc.facade.biz.quote;

import com.smart.classroom.misc.facade.biz.quote.response.ColumnQuoteDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface ColumnQuoteReadFacade {

    ColumnQuoteDTO queryById(long columnQuoteId);

    ColumnQuoteDTO queryByColumnId(long columnId);

}
