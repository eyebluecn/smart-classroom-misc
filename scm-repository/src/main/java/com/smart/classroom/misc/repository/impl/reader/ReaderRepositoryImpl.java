package com.smart.classroom.misc.repository.impl.reader;

import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;
import com.smart.classroom.misc.domain.biz.reader.repository.ReaderRepository;
import com.smart.classroom.misc.repository.impl.reader.converter.ReaderDO2ModelConverter;
import com.smart.classroom.misc.repository.impl.reader.converter.ReaderModel2DOConverter;
import com.smart.classroom.misc.repository.mapper.reader.ReaderMapper;
import com.smart.classroom.misc.repository.orm.reader.ReaderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class ReaderRepositoryImpl implements ReaderRepository {


    @Autowired
    ReaderMapper readerMapper;


    @Override
    public ReaderModel queryById(long id) {

        ReaderDO readerDO = readerMapper.queryById(id);

        return ReaderDO2ModelConverter.convert(readerDO);
    }

    @Override
    public ReaderModel queryByUsername(String username) {

        ReaderDO readerDO = readerMapper.queryByUsername(username);

        return ReaderDO2ModelConverter.convert(readerDO);
    }

    @Override
    public ReaderModel insert(ReaderModel readerModel) {

        ReaderDO readerDO = ReaderModel2DOConverter.convert(readerModel);

        readerMapper.insert(readerDO);

        //查询一次。
        readerDO = readerMapper.queryById(readerDO.getId());

        return ReaderDO2ModelConverter.convert(readerDO);
    }
}
