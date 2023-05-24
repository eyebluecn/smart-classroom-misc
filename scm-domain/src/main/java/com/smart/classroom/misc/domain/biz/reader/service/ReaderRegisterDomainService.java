package com.smart.classroom.misc.domain.biz.reader.service;

import com.smart.classroom.misc.domain.biz.reader.model.ReaderModel;
import com.smart.classroom.misc.domain.biz.reader.repository.ReaderRepository;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.util.CryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 注册用户的领域服务
 *
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class ReaderRegisterDomainService {

    @Autowired
    ReaderRepository readerRepository;

    /**
     * 读者注册
     */
    public ReaderModel register(String username, String password) {
        ReaderModel readerModel = readerRepository.queryByUsername(username);
        if (readerModel != null) {
            throw new UtilException("{}对应的用户名已存在", username);
        }

        //密码简单使用
        String encodedPassword = CryptUtil.bCryptEncode(password);

        readerModel = ReaderModel
                .builder()
                .createTime(new Date())
                .updateTime(new Date())
                .username(username)
                .password(encodedPassword)
                .build();

        readerModel = readerRepository.insert(readerModel);

        //TODO: 发送注册成功的领域事件。

        return readerModel;
    }

    public ReaderModel login(String username, String password) {
        ReaderModel readerModel = readerRepository.queryByUsername(username);
        if (readerModel == null) {
            throw new UtilException("用户名或密码错误");
        }

        boolean b = CryptUtil.bCryptMatches(password, readerModel.getPassword());
        if (!b) {
            throw new UtilException("用户名或密码错误");
        }

        //TODO: 发送登录成功的领域事件。

        return readerModel;
    }
}
