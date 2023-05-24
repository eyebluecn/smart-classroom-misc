package com.smart.classroom.misc.facade.biz.editor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorLoginRequest implements Serializable {

    /**
     * 用户名
     */
    private String username = null;

    /**
     * 密码
     */
    private String password = null;

}
