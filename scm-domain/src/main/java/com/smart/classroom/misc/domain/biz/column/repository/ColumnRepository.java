package com.smart.classroom.misc.domain.biz.column.repository;

import com.smart.classroom.misc.domain.biz.column.model.ColumnModel;
import com.smart.classroom.misc.domain.biz.column.repository.query.ColumnPageQuery;
import com.smart.classroom.misc.utility.result.Pager;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface ColumnRepository {

    ColumnModel queryById(Long id);


    ColumnModel queryByName(String name);

    //分页查询
    Pager<ColumnModel> page(ColumnPageQuery columnPageQuery);


    ColumnModel insert(ColumnModel author);


}
