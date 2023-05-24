/**
 * smart-classroom.com Inc. Copyright (c) 2015-present All Rights Reserved.
 * generated by SaberGenerator
 */
package com.smart.classroom.misc.repository.mapper.editor;

import com.smart.classroom.misc.repository.orm.author.AuthorDO;
import com.smart.classroom.misc.repository.orm.editor.EditorDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EditorMapper extends EditorBaseMapper {

    EditorDO queryByUsername(String username);

    EditorDO queryTopByWorkNo(String workNo);
}