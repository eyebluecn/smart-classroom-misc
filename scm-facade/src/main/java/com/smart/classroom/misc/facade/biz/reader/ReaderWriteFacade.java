package com.smart.classroom.misc.facade.biz.reader;

import com.smart.classroom.misc.facade.biz.reader.request.ReaderLoginRequest;
import com.smart.classroom.misc.facade.biz.reader.request.ReaderRegisterRequest;
import com.smart.classroom.misc.facade.biz.reader.response.ReaderDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface ReaderWriteFacade {

    /**
     * 注册一个读者
     */
    ReaderDTO register(ReaderRegisterRequest request);


    /**
     * 读者登录.
     * 登录失败抛异常
     */
    ReaderDTO login(ReaderLoginRequest request);


}
