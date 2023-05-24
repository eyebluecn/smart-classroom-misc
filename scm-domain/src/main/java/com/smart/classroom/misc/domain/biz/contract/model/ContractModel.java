package com.smart.classroom.misc.domain.biz.contract.model;

import com.smart.classroom.misc.domain.biz.contract.enums.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractModel {

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
     * 名称
     */
    private String name = null;

    /**
     * 内容
     */
    private String content = null;

    /**
     * 专栏id
     */
    private Long columnId = null;

    /**
     * 作者id
     */
    private Long authorId = null;

    /**
     * 状态 NEW/OK/DISABLED 未生效/已生效/已禁用
     */
    private ContractStatus status = ContractStatus.NEW;

    /**
     * 分成比例
     */
    private BigDecimal percentage = null;

    /**
     * 账期日
     */
    private String paymentDay = null;


}
