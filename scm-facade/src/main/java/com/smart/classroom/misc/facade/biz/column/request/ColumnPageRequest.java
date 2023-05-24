package com.smart.classroom.misc.facade.biz.column.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnPageRequest implements Serializable {
    int pageNum = 1;
    int pageSize = 20;
    //参考 SortDirection枚举
    String orderCreateTime = null;
    //参考 SortDirection枚举
    String orderUpdateTime = null;
    String name;
    Long authorId;
    String status;
}
