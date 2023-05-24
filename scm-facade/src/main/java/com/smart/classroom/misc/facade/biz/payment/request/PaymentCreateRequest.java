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
public class PaymentCreateRequest implements Serializable {

    /**
     * 订单编号
     */
    private String orderNo = null;

    /**
     * 支付方式 ALIPAY/WEIXIN 支付宝/微信
     */
    private String method = null;

    /**
     * 金额(单位：分)
     */
    private Long amount = null;

}
