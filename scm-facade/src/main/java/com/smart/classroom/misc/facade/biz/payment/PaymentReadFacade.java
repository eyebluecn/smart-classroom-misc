package com.smart.classroom.misc.facade.biz.payment;

import com.smart.classroom.misc.facade.biz.payment.response.PaymentDTO;
import com.smart.classroom.misc.facade.biz.payment.response.PreparePaymentDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface PaymentReadFacade {


    PaymentDTO queryById(long paymentId);

    /**
     * 获取一个支付单对应的支付准备物料等信息。
     */
    PreparePaymentDTO prepare(long paymentId);


}
