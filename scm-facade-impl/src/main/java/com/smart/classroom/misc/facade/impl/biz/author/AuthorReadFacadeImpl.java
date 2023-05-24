package com.smart.classroom.misc.facade.impl.biz.author;

import com.smart.classroom.misc.domain.biz.author.model.AuthorModel;
import com.smart.classroom.misc.domain.biz.author.repository.AuthorRepository;
import com.smart.classroom.misc.facade.biz.author.AuthorReadFacade;
import com.smart.classroom.misc.facade.biz.author.response.AuthorDTO;
import com.smart.classroom.misc.facade.impl.biz.author.converter.AuthorModel2DTOConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-19
 */
@Slf4j
@DubboService
public class AuthorReadFacadeImpl implements AuthorReadFacade {


    @Resource
    AuthorRepository authorRepository;

    @Override
    public AuthorDTO queryById(long authorId) {

        AuthorModel authorModel = authorRepository.queryById(authorId);

        return AuthorModel2DTOConverter.convert(authorModel);
    }
}
