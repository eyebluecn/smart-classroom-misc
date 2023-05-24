package com.smart.classroom.misc.application.biz.reader;

import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;
import com.smart.classroom.misc.domain.biz.reader.repository.ReaderRepository;
import com.smart.classroom.misc.domain.biz.reader.service.ReaderRegisterDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class ReaderWriteAppService {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    ReaderRegisterDomainService readerRegisterDomainService;

    public ReaderModel register(String username, String password) {


        ReaderModel readerModel = readerRegisterDomainService.register(username, password);

        return readerModel;

    }

    public ReaderModel queryById(long readerId) {
        return readerRepository.queryById(readerId);
    }

    public ReaderModel login(String username, String password) {
        ReaderModel readerModel = readerRegisterDomainService.login(username, password);

        return readerModel;
    }

    public ReaderModel queryByUsername(String username) {
        return readerRepository.queryByUsername(username);
    }
}
