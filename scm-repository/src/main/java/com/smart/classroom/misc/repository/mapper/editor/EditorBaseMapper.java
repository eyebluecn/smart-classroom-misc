/**
 * smart-classroom.com Inc. Copyright (c) 2015-present All Rights Reserved.
 * generated by SaberGenerator
 */
package com.smart.classroom.misc.repository.mapper.editor;

import com.github.pagehelper.Page;
import com.smart.classroom.misc.repository.mapper.base.BaseMapper;
import com.smart.classroom.misc.repository.orm.editor.EditorDO;
import com.smart.classroom.misc.utility.enums.SortDirection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EditorBaseMapper extends BaseMapper<EditorDO> {

    /**
     * 当入参中含有PageNum和pageSize时，会自动分页。
     */
    Page<EditorDO> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("orderCreateTime") SortDirection orderCreateTime,
            @Param("orderUpdateTime") SortDirection orderUpdateTime,
            @Param("workNo") String workNo
    );

}