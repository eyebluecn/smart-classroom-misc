package com.smart.classroom.misc.facade.impl.biz.reader;

import com.smart.classroom.misc.application.biz.reader.ReaderWriteAppService;
import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;
import com.smart.classroom.misc.facade.biz.reader.ReaderReadFacade;
import com.smart.classroom.misc.facade.biz.reader.response.ReaderDTO;
import com.smart.classroom.misc.facade.impl.biz.reader.converter.ReaderModel2DTOConverter;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@DubboService
public class ReaderReadFacadeImpl implements ReaderReadFacade {

    @Resource
    ReaderWriteAppService readerWriteAppService;


    @Override
    public ReaderDTO queryById(long readerId) {
        ReaderModel readerModel = readerWriteAppService.queryById(readerId);
        return ReaderModel2DTOConverter.convert(readerModel);
    }

    @Override
    public ReaderDTO queryByUsername(String username) {
        ReaderModel readerModel = readerWriteAppService.queryByUsername(username);
        return ReaderModel2DTOConverter.convert(readerModel);
    }
}
