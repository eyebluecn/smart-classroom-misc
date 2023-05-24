package com.smart.classroom.misc.domain.biz.editor.model;

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
public class EditorModel {

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
     * 昵称
     */
    private String username = null;

    /**
     * 密码
     */
    private String password = null;

    /**
     * 工号
     */
    private String workNo = null;


}
