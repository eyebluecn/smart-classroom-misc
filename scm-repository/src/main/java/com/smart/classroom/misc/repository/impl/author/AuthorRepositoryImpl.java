package com.smart.classroom.misc.repository.impl.author;

import com.smart.classroom.misc.domain.biz.author.model.AuthorModel;
import com.smart.classroom.misc.domain.biz.author.repository.AuthorRepository;
import com.smart.classroom.misc.repository.impl.author.converter.AuthorDO2ModelConverter;
import com.smart.classroom.misc.repository.impl.author.converter.AuthorModel2DOConverter;
import com.smart.classroom.misc.repository.mapper.author.AuthorMapper;
import com.smart.classroom.misc.repository.orm.author.AuthorDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class AuthorRepositoryImpl implements AuthorRepository {


    @Autowired
    AuthorMapper authorMapper;


    @Override
    public AuthorModel queryById(Long id) {
        AuthorDO authorDO = authorMapper.queryById(id);

        return AuthorDO2ModelConverter.convert(authorDO);
    }

    @Override
    public AuthorModel queryOneByUsername(String username) {

        AuthorDO authorDO = authorMapper.queryTopByUsername(username);

        return AuthorDO2ModelConverter.convert(authorDO);
    }

    @Override
    public AuthorModel insert(AuthorModel authorModel) {

        AuthorDO authorDO = AuthorModel2DOConverter.convert(authorModel);

        authorMapper.insert(authorDO);

        //查询一次。
        authorDO = authorMapper.queryById(authorDO.getId());

        return AuthorDO2ModelConverter.convert(authorDO);
    }
}
