package com.smart.classroom.misc.domain.biz.author.service;

import com.smart.classroom.misc.domain.biz.author.model.AuthorModel;
import com.smart.classroom.misc.domain.biz.author.repository.AuthorRepository;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.util.CryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 作者注册领域服务
 *
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class AuthorRegisterDomainService {

    @Autowired
    AuthorRepository authorRepository;


    /**
     * 作者注册
     */
    public AuthorModel register(String username, String password, String realname) {
        AuthorModel authorModel = authorRepository.queryOneByUsername(username);
        if (authorModel != null) {
            throw new UtilException("{}对应的账号已存在", username);
        }

        //密码简单使用 md5加密
        String encodedPassword = CryptUtil.bCryptEncode(password);

        authorModel = AuthorModel
                .builder()
                .createTime(new Date())
                .updateTime(new Date())
                .username(username)
                .password(encodedPassword)
                .realname(realname)
                .build();

        authorModel = authorRepository.insert(authorModel);

        //TODO: 发送注册成功的领域事件。

        return authorModel;
    }

}
