package com.smart.classroom.misc.facade.biz.author;

import com.smart.classroom.misc.facade.biz.author.response.AuthorDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface AuthorReadFacade {

    AuthorDTO queryById(long authorId);
}
