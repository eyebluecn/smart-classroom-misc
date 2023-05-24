package com.smart.classroom.misc.facade.biz.column;

import com.smart.classroom.misc.facade.biz.column.request.OmnibusRequest;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface ColumnWriteFacade {

    /**
     * ！！！此接口仅作为演示用途！！！
     * 一口气创建 作者，作者合同，专栏，课程文章，专栏报价，编辑
     * 为了保证数据库不出现脏数据
     */
    void omnibus(OmnibusRequest request);

}
