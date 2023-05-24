package com.smart.classroom.misc.facade.impl.biz.author.converter;

import com.smart.classroom.misc.domain.biz.author.model.AuthorModel;
import com.smart.classroom.misc.facade.biz.author.response.AuthorDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class AuthorModel2DTOConverter {

    /**
     * 将DO转换成模型
     */
    public static AuthorDTO convert(AuthorModel authorModel) {
        if (authorModel == null) {
            return null;
        }

        AuthorDTO authorDTO = AuthorDTO.builder()
                .id(authorModel.getId())
                .createTime(authorModel.getCreateTime())
                .updateTime(authorModel.getUpdateTime())

                .username(authorModel.getUsername())
                .password(authorModel.getPassword())
                .realname(authorModel.getRealname())
                .build();

        return authorDTO;
    }


}
