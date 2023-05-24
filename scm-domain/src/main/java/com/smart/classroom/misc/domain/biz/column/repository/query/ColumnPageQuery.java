package com.smart.classroom.misc.domain.biz.column.repository.query;

import com.smart.classroom.misc.domain.biz.column.enums.ColumnStatus;
import com.smart.classroom.misc.utility.enums.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnPageQuery {

    int pageNum = 1;
    int pageSize = 20;
    SortDirection orderCreateTime;
    SortDirection orderUpdateTime;
    String name;
    Long authorId;
    ColumnStatus status;

}
