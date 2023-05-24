package com.smart.classroom.misc.domain.biz.payment.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lishuang
 * @date 2023-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaidCallbackInfo implements Serializable {

    /**
     * 状态
     */
    String code;

    /**
     * 消息
     */
    String msg;

}
