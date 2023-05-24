package com.smart.classroom.misc.domain.biz.payment.service;

import com.smart.classroom.misc.domain.biz.payment.enums.PayMethod;
import com.smart.classroom.misc.domain.biz.payment.enums.PayStatus;
import com.smart.classroom.misc.domain.biz.payment.info.PreparePaymentInfo;
import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;
import com.smart.classroom.misc.domain.biz.payment.repository.PaymentRepository;
import com.smart.classroom.misc.domain.thirdparty.pay.ThirdpartyPayClient;
import com.smart.classroom.misc.domain.thirdparty.pay.info.ThirdpartyPayInfo;
import com.smart.classroom.misc.utility.exception.UtilException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 支付单创建的领域服务
 *
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class PaymentCreateDomainService {

    @Autowired
    ThirdpartyPayClient thirdpartyPayClient;

    @Autowired
    PaymentRepository paymentRepository;


    /**
     * 创建支付单，同时做支付的准备。
     */
    public PreparePaymentInfo create(
            @NonNull String orderNo,
            @NonNull PayMethod method,
            @NonNull Long amount
    ) {
        PaymentModel paymentModel = paymentRepository.queryByOrderNo(orderNo);
        if (paymentModel != null) {
            throw new UtilException("订单号 {} 对应的支付单已存在，请勿重复创建", orderNo);
        }

        paymentModel = new PaymentModel(
                null,
                new Date(),
                new Date(),
                orderNo,
                method,
                null,
                amount,
                PayStatus.UNPAID
        );

        paymentModel = paymentRepository.insert(paymentModel);


        ThirdpartyPayInfo thirdpartyPayInfo = thirdpartyPayClient.queryOrder(paymentModel);
        //说明还没有创建。
        if (thirdpartyPayInfo == null) {
            thirdpartyPayInfo = thirdpartyPayClient.createOrder(paymentModel);
        }

        PreparePaymentInfo preparePaymentInfo = new PreparePaymentInfo(
                paymentModel,
                thirdpartyPayInfo.getThirdTransactionNo(),
                thirdpartyPayInfo.getNonceStr()
        );

        log.info("创建支付单成功：{}", preparePaymentInfo);

        return preparePaymentInfo;

    }

}
