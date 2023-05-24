package com.smart.classroom.misc.facade.biz.reader;

import com.smart.classroom.misc.facade.biz.reader.response.ReaderDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface ReaderReadFacade {

    ReaderDTO queryById(long readerId);

    ReaderDTO queryByUsername(String username);

}
