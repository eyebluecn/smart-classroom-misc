package com.smart.classroom.misc.facade.biz.payment;

import com.smart.classroom.misc.facade.biz.payment.request.PaidCallbackRequest;
import com.smart.classroom.misc.facade.biz.payment.request.PaymentCreateRequest;
import com.smart.classroom.misc.facade.biz.payment.response.PaidCallbackDTO;
import com.smart.classroom.misc.facade.biz.payment.response.PreparePaymentDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface PaymentWriteFacade {

    /**
     * 创建一个支付单同时返回支付准备物料等信息。
     */
    PreparePaymentDTO create(PaymentCreateRequest paymentCreateRequest);


    /**
     * 第三方支付平台，支付成功后的回调接口。
     * 一般是传输的密文过来，需要用证书解密。
     */
    PaidCallbackDTO paidCallback(PaidCallbackRequest paidCallbackRequest);

}
