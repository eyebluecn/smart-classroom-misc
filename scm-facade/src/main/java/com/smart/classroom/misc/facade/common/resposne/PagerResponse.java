package com.smart.classroom.misc.facade.common.resposne;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
public class PagerResponse<T> implements Serializable {

    //当前页码。 1基
    private long pageNum;

    //每页的大小。
    private long pageSize;

    //总条目数
    private long totalItems;

    //总页数
    private long totalPages;

    private List<T> data;


    public PagerResponse(long pageNum, long pageSize, long totalItems, List<T> data) {

        if (pageSize <= 0) {
            pageSize = 10;
        }

        this.pageNum = pageNum;

        this.pageSize = pageSize;

        this.totalItems = totalItems;

        this.totalPages = (long) Math.ceil(this.totalItems * 1.0 / this.pageSize);

        this.data = data;

    }


}
