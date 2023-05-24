package com.smart.classroom.misc.facade.impl.biz.column;

import com.smart.classroom.misc.application.biz.column.ColumnReadAppService;
import com.smart.classroom.misc.domain.biz.column.enums.ColumnStatus;
import com.smart.classroom.misc.domain.biz.column.model.ColumnModel;
import com.smart.classroom.misc.domain.biz.column.repository.query.ColumnPageQuery;
import com.smart.classroom.misc.facade.biz.column.ColumnReadFacade;
import com.smart.classroom.misc.facade.biz.column.request.ColumnPageRequest;
import com.smart.classroom.misc.facade.biz.column.response.ColumnDTO;
import com.smart.classroom.misc.facade.common.resposne.PagerResponse;
import com.smart.classroom.misc.facade.impl.biz.column.converter.ColumnModel2DTOConverter;
import com.smart.classroom.misc.utility.enums.SortDirection;
import com.smart.classroom.misc.utility.result.Pager;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@DubboService
public class ColumnReadFacadeImpl implements ColumnReadFacade {

    @Resource
    ColumnReadAppService columnAppQueryService;


    @Override
    public PagerResponse<ColumnDTO> list(ColumnPageRequest columnPageRequest) {

        ColumnPageQuery columnPageQuery = new ColumnPageQuery(
                columnPageRequest.getPageNum(),
                columnPageRequest.getPageSize(),
                SortDirection.toEnum(columnPageRequest.getOrderCreateTime()),
                SortDirection.toEnum(columnPageRequest.getOrderUpdateTime()),
                columnPageRequest.getName(),
                columnPageRequest.getAuthorId(),
                ColumnStatus.toEnum(columnPageRequest.getStatus())
        );

        Pager<ColumnModel> columnPager = columnAppQueryService.page(
                columnPageQuery
        );

        List<ColumnDTO> columnDTOList = columnPager.getData().stream().map(ColumnModel2DTOConverter::convert).collect(Collectors.toList());

        return new PagerResponse<>(
                columnPager.getPageNum(),
                columnPager.getPageSize(),
                columnPager.getTotalItems(),
                columnDTOList
        );
    }

    @Override
    public ColumnDTO queryById(long columnId) {

        ColumnModel columnModel = columnAppQueryService.queryById(columnId);

        return ColumnModel2DTOConverter.convert(columnModel);
    }
}
