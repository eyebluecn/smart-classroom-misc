package com.smart.classroom.misc.facade.impl.biz.column;

import com.smart.classroom.misc.application.biz.column.ColumnWriteAppService;
import com.smart.classroom.misc.facade.biz.column.ColumnWriteFacade;
import com.smart.classroom.misc.facade.biz.column.request.OmnibusRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@DubboService
public class ColumnWriteServiceImpl implements ColumnWriteFacade {

    @Resource
    ColumnWriteAppService columnAppCommandService;


    /**
     * ！！！此接口仅作为演示用途！！！
     * 一口气创建 作者，作者合同，专栏，课程文章，专栏报价，编辑
     * 为了保证数据库不出现脏数据
     */
    @Override
    public void omnibus(OmnibusRequest request) {
        log.info("请求参数：{}", request);

        columnAppCommandService.omnibus(
                request.getAuthorName(),
                request.getColumnName(),
                request.getEditorId(),
                request.getColumnPrice()
        );

    }


}
