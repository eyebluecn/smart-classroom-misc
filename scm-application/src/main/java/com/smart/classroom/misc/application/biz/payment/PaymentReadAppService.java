package com.smart.classroom.misc.application.biz.payment;

import com.smart.classroom.misc.domain.biz.payment.info.PreparePaymentInfo;
import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;
import com.smart.classroom.misc.domain.biz.payment.repository.PaymentRepository;
import com.smart.classroom.misc.domain.thirdparty.pay.ThirdpartyPayClient;
import com.smart.classroom.misc.domain.thirdparty.pay.info.ThirdpartyPayInfo;
import com.smart.classroom.misc.utility.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class PaymentReadAppService {

    @Autowired
    ThirdpartyPayClient thirdpartyPayClient;

    @Autowired
    PaymentRepository paymentRepository;

    //专栏详情查询
    public PaymentModel queryById(Long paymentId) {

        return paymentRepository.queryById(paymentId);
    }

    //准备支付。
    public PreparePaymentInfo prepare(Long paymentId) {
        PaymentModel paymentModel = paymentRepository.queryById(paymentId);
        if (paymentModel == null) {
            throw new UtilException("id={}对应的支付单不存在", paymentId);
        }

        ThirdpartyPayInfo thirdpartyPayInfo = thirdpartyPayClient.queryOrder(paymentModel);
        //说明还没有创建。
        if (thirdpartyPayInfo == null) {
            thirdpartyPayInfo = thirdpartyPayClient.createOrder(paymentModel);
        }

        PreparePaymentInfo preparePaymentInfo=new PreparePaymentInfo(
                paymentModel,
                thirdpartyPayInfo.getThirdTransactionNo(),
                thirdpartyPayInfo.getNonceStr()
        );

        return preparePaymentInfo;

    }


}
