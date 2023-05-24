package com.smart.classroom.misc.facade.biz.payment.request;

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
public class PaidCallbackRequest implements Serializable {

    /**
     * 订单编号
     */
    private String orderNo = null;


}
