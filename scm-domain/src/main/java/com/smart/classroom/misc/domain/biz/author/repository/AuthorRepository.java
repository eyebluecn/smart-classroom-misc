package com.smart.classroom.misc.domain.biz.author.repository;

import com.smart.classroom.misc.domain.biz.author.model.AuthorModel;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface AuthorRepository {

    AuthorModel queryById(Long id);

    AuthorModel queryOneByUsername(String username);

    AuthorModel insert(AuthorModel authorModel);

}
