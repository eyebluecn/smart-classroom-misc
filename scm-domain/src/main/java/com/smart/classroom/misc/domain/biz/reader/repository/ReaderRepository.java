package com.smart.classroom.misc.domain.biz.reader.repository;

import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface ReaderRepository {

    ReaderModel queryById(long id);

    ReaderModel queryByUsername(String username);

    ReaderModel insert(ReaderModel readerModel);

}
