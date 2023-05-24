package com.smart.classroom.misc.application.biz.payment;

import com.smart.classroom.misc.domain.biz.payment.enums.PayMethod;
import com.smart.classroom.misc.domain.biz.payment.info.PaidCallbackInfo;
import com.smart.classroom.misc.domain.biz.payment.info.PreparePaymentInfo;
import com.smart.classroom.misc.domain.biz.payment.service.PaymentCreateDomainService;
import com.smart.classroom.misc.domain.biz.payment.service.PaymentPaidDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class PaymentWriteAppService {

    @Autowired
    PaymentCreateDomainService paymentCreateDomainService;

    @Autowired
    PaymentPaidDomainService paymentPaidDomainService;

    //准备支付。
    public PreparePaymentInfo create(
            String orderNo,
            PayMethod method,
            Long amount
    ) {

        return paymentCreateDomainService.create(orderNo, method, amount);

    }


    /**
     * 第三方支付平台，支付成功后的回调接口。
     * 一般是传输的密文过来，需要用证书解密。
     */
    public PaidCallbackInfo paidCallback(String orderNo) {

        return paymentPaidDomainService.paidCallback(orderNo);

    }


}
