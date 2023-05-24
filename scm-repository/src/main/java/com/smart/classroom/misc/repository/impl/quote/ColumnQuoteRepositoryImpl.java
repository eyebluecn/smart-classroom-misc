package com.smart.classroom.misc.repository.impl.quote;

import com.smart.classroom.misc.domain.biz.quote.model.ColumnQuoteModel;
import com.smart.classroom.misc.domain.biz.quote.repository.ColumnQuoteRepository;
import com.smart.classroom.misc.repository.impl.quote.converter.ColumnQuoteDO2ModelConverter;
import com.smart.classroom.misc.repository.impl.quote.converter.ColumnQuoteModel2DOConverter;
import com.smart.classroom.misc.repository.mapper.column.ColumnQuoteMapper;
import com.smart.classroom.misc.repository.orm.column.ColumnQuoteDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class ColumnQuoteRepositoryImpl implements ColumnQuoteRepository {

    @Autowired
    ColumnQuoteMapper columnQuoteMapper;


    @Override
    public ColumnQuoteModel queryById(long columnQuoteId) {
        ColumnQuoteDO columnQuoteDO = columnQuoteMapper.queryById(columnQuoteId);

        return ColumnQuoteDO2ModelConverter.convert(columnQuoteDO);
    }

    @Override
    public ColumnQuoteModel queryByColumnIdAndStatusOk(long columnId) {
        ColumnQuoteDO columnQuoteDO = columnQuoteMapper.queryByColumnIdAndStatusOk(columnId);

        return ColumnQuoteDO2ModelConverter.convert(columnQuoteDO);
    }

    @Override
    public ColumnQuoteModel insert(ColumnQuoteModel columnQuoteModel) {


        ColumnQuoteDO columnQuoteDO = ColumnQuoteModel2DOConverter.convert(columnQuoteModel);

        columnQuoteMapper.insert(columnQuoteDO);

        //查询一次。
        columnQuoteDO = columnQuoteMapper.queryById(columnQuoteDO.getId());

        return ColumnQuoteDO2ModelConverter.convert(columnQuoteDO);

    }
}
