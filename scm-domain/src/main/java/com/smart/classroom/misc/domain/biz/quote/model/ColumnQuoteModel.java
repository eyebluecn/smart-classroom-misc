package com.smart.classroom.misc.domain.biz.quote.model;

import com.smart.classroom.misc.domain.biz.quote.enums.ColumnQuoteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnQuoteModel {

    /**
     * 主键
     */
    private Long id = null;

    /**
     * 创建时间
     */
    private Date createTime = null;

    /**
     * 修改时间
     */
    private Date updateTime = null;

    /**
     * 专栏id
     */
    private Long columnId = null;

    /**
     * 编辑id
     */
    private Long editorId = null;

    /**
     * 价格（单位：分）
     */
    private Long price = null;

    /**
     * 报价状态 DISABLED/OK 未生效/已生效
     */
    private ColumnQuoteStatus status = ColumnQuoteStatus.DISABLED;


}
