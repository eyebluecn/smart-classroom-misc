package com.smart.classroom.misc.facade.impl.biz.reader;

import com.smart.classroom.misc.application.biz.reader.ReaderWriteAppService;
import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;
import com.smart.classroom.misc.facade.biz.reader.ReaderWriteFacade;
import com.smart.classroom.misc.facade.biz.reader.request.ReaderLoginRequest;
import com.smart.classroom.misc.facade.biz.reader.request.ReaderRegisterRequest;
import com.smart.classroom.misc.facade.biz.reader.response.ReaderDTO;
import com.smart.classroom.misc.facade.impl.biz.reader.converter.ReaderModel2DTOConverter;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@DubboService
public class ReaderWriteFacadeImpl implements ReaderWriteFacade {

    @Resource
    ReaderWriteAppService readerWriteAppService;


    /**
     * 注册一个读者
     */
    public ReaderDTO register(ReaderRegisterRequest request) {

        ReaderModel readerModel = readerWriteAppService.register(request.getUsername(), request.getPassword());

        return ReaderModel2DTOConverter.convert(readerModel);
    }


    @Override
    public ReaderDTO login(ReaderLoginRequest request) {


        ReaderModel readerModel = readerWriteAppService.login(request.getUsername(), request.getPassword());

        return ReaderModel2DTOConverter.convert(readerModel);
    }


}
