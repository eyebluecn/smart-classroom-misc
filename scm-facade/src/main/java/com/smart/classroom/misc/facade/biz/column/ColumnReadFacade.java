package com.smart.classroom.misc.facade.biz.column;

import com.smart.classroom.misc.facade.biz.column.request.ColumnPageRequest;
import com.smart.classroom.misc.facade.biz.column.response.ColumnDTO;
import com.smart.classroom.misc.facade.common.resposne.PagerResponse;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface ColumnReadFacade {

    PagerResponse<ColumnDTO> list(ColumnPageRequest columnPageRequest);

    ColumnDTO queryById(long columnId);
}
