package com.smart.classroom.misc.facade.impl.biz.payment;

import com.smart.classroom.misc.application.biz.payment.PaymentReadAppService;
import com.smart.classroom.misc.domain.biz.payment.info.PreparePaymentInfo;
import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;
import com.smart.classroom.misc.facade.biz.payment.PaymentReadFacade;
import com.smart.classroom.misc.facade.biz.payment.response.PaymentDTO;
import com.smart.classroom.misc.facade.biz.payment.response.PreparePaymentDTO;
import com.smart.classroom.misc.facade.impl.biz.payment.converter.PaymentModel2DTOConverter;
import com.smart.classroom.misc.facade.impl.biz.payment.converter.PreparePaymentInfo2DTOConverter;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@DubboService
public class PaymentReadFacadeImpl implements PaymentReadFacade {


    @Resource
    PaymentReadAppService paymentReadAppService;

    @Override
    public PaymentDTO queryById(long paymentId) {
        PaymentModel paymentModel = paymentReadAppService.queryById(paymentId);
        return PaymentModel2DTOConverter.convert(paymentModel);
    }

    @Override
    public PreparePaymentDTO prepare(long paymentId) {

        PreparePaymentInfo preparePaymentInfo = paymentReadAppService.prepare(paymentId);

        return PreparePaymentInfo2DTOConverter.convert(preparePaymentInfo);
    }
}
