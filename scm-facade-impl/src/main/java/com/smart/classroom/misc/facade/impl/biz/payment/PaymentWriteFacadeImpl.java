package com.smart.classroom.misc.facade.impl.biz.payment;

import com.smart.classroom.misc.application.biz.payment.PaymentWriteAppService;
import com.smart.classroom.misc.domain.biz.payment.enums.PayMethod;
import com.smart.classroom.misc.domain.biz.payment.info.PaidCallbackInfo;
import com.smart.classroom.misc.domain.biz.payment.info.PreparePaymentInfo;
import com.smart.classroom.misc.facade.biz.payment.PaymentWriteFacade;
import com.smart.classroom.misc.facade.biz.payment.request.PaidCallbackRequest;
import com.smart.classroom.misc.facade.biz.payment.request.PaymentCreateRequest;
import com.smart.classroom.misc.facade.biz.payment.response.PaidCallbackDTO;
import com.smart.classroom.misc.facade.biz.payment.response.PreparePaymentDTO;
import com.smart.classroom.misc.facade.impl.biz.payment.converter.PaidCallbackInfo2DTOConverter;
import com.smart.classroom.misc.facade.impl.biz.payment.converter.PreparePaymentInfo2DTOConverter;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@DubboService
public class PaymentWriteFacadeImpl implements PaymentWriteFacade {

    @Resource
    PaymentWriteAppService paymentWriteAppService;

    @Override
    public PreparePaymentDTO create(PaymentCreateRequest paymentCreateRequest) {

        PayMethod payMethod = PayMethod.toEnum(paymentCreateRequest.getMethod());

        PreparePaymentInfo preparePaymentInfo = paymentWriteAppService.create(paymentCreateRequest.getOrderNo(), payMethod, paymentCreateRequest.getAmount());

        return PreparePaymentInfo2DTOConverter.convert(preparePaymentInfo);
    }

    @Override
    public PaidCallbackDTO paidCallback(PaidCallbackRequest paidCallbackRequest) {

        PaidCallbackInfo paidCallbackInfo = paymentWriteAppService.paidCallback(paidCallbackRequest.getOrderNo());

        return PaidCallbackInfo2DTOConverter.convert(paidCallbackInfo);
    }
}
