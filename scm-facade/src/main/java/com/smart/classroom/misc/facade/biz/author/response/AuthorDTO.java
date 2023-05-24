package com.smart.classroom.misc.facade.biz.author.response;

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
public class AuthorDTO implements Serializable {

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
     * 用户名
     */
    private String username = null;

    /**
     * 密码
     */
    private String password = null;

    /**
     * 真实姓名
     */
    private String realname = null;
}
