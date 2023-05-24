package com.smart.classroom.misc.facade.impl.biz.quote;

import com.smart.classroom.misc.domain.biz.quote.model.ColumnQuoteModel;
import com.smart.classroom.misc.domain.biz.quote.repository.ColumnQuoteRepository;
import com.smart.classroom.misc.facade.biz.quote.ColumnQuoteReadFacade;
import com.smart.classroom.misc.facade.biz.quote.response.ColumnQuoteDTO;
import com.smart.classroom.misc.facade.impl.biz.quote.converter.ColumnQuoteModel2DTOConverter;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-16
 */
@DubboService
public class ColumnQuoteReadFacadeImpl implements ColumnQuoteReadFacade {

    @Resource
    ColumnQuoteRepository columnQuoteRepository;


    @Override
    public ColumnQuoteDTO queryById(long columnQuoteId) {

        ColumnQuoteModel columnQuoteModel = columnQuoteRepository.queryById(columnQuoteId);

        return ColumnQuoteModel2DTOConverter.convert(columnQuoteModel);
    }

    @Override
    public ColumnQuoteDTO queryByColumnId(long columnId) {

        //找到一个有效的。
        ColumnQuoteModel columnQuoteModel = columnQuoteRepository.queryByColumnIdAndStatusOk(columnId);


        return ColumnQuoteModel2DTOConverter.convert(columnQuoteModel);
    }
}
