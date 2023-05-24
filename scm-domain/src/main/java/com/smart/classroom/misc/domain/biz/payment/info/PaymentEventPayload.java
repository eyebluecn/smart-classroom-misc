package com.smart.classroom.misc.domain.biz.payment.info;

import com.smart.classroom.misc.domain.biz.payment.enums.PayMethod;
import com.smart.classroom.misc.domain.biz.payment.enums.PayStatus;
import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lishuang
 * @date 2023-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEventPayload {

    /**
     * 支付单
     */
    private Long paymentId;

    /**
     * 订单编号
     */
    private String orderNo = null;

    /**
     * 支付方式 ALIPAY/WEIXIN 支付宝/微信
     */
    private PayMethod method = PayMethod.ALIPAY;

    /**
     * 金额(单位：分)
     */
    private Long amount = null;

    /**
     * 支付状态 UNPAID/PAID/CLOSED 未支付/已支付/已关闭
     */
    private PayStatus status = null;

    /**
     * 时间
     */
    private Date occurTime = null;

}
