package com.smart.classroom.misc.repository.impl.column;

import com.github.pagehelper.Page;
import com.smart.classroom.misc.domain.biz.column.model.ColumnModel;
import com.smart.classroom.misc.domain.biz.column.repository.ColumnRepository;
import com.smart.classroom.misc.domain.biz.column.repository.query.ColumnPageQuery;
import com.smart.classroom.misc.repository.impl.column.converter.ColumnDO2ModelConverter;
import com.smart.classroom.misc.repository.impl.column.converter.ColumnModel2DOConverter;
import com.smart.classroom.misc.repository.mapper.column.ColumnMapper;
import com.smart.classroom.misc.repository.orm.column.ColumnDO;
import com.smart.classroom.misc.utility.result.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class ColumnRepositoryImpl implements ColumnRepository {

    @Autowired
    ColumnMapper columnMapper;

    @Override
    public ColumnModel queryById(Long id) {

        ColumnDO columnDO = columnMapper.queryById(id);

        return ColumnDO2ModelConverter.convert(columnDO);
    }

    @Override
    public ColumnModel queryByName(String name) {

        ColumnDO columnDO = columnMapper.queryTopByName(name);

        return ColumnDO2ModelConverter.convert(columnDO);
    }

    @Override
    public Pager<ColumnModel> page(ColumnPageQuery columnPageQuery) {
        Page<ColumnDO> page = columnMapper.page(
                columnPageQuery.getPageNum(),
                columnPageQuery.getPageSize(),
                columnPageQuery.getOrderCreateTime(),
                columnPageQuery.getOrderUpdateTime(),
                columnPageQuery.getName(),
                columnPageQuery.getAuthorId(),
                columnPageQuery.getStatus()
        );

        List<ColumnModel> columnModelList = page.stream().map(ColumnDO2ModelConverter::convert).collect(Collectors.toList());

        return new Pager<>(page.getPageNum(), page.getPageSize(), page.getTotal(), columnModelList);
    }

    @Override
    public ColumnModel insert(ColumnModel columnModel) {

        ColumnDO columnDO = ColumnModel2DOConverter.convert(columnModel);

        columnMapper.insert(columnDO);

        columnDO = columnMapper.queryById(columnDO.getId());

        return ColumnDO2ModelConverter.convert(columnDO);
    }
}
