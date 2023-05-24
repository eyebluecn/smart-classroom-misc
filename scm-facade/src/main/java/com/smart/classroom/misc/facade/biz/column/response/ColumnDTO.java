package com.smart.classroom.misc.facade.biz.column.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDTO implements Serializable {

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
     * 专栏名称
     */
    private String name = null;

    /**
     * 作者id
     */
    private Long authorId = null;

    /**
     * 参考：ColumnStatus
     * 状态 NEW/OK/DISABLED 未发布/已生效/已禁用
     */
    private String status = null;
}
