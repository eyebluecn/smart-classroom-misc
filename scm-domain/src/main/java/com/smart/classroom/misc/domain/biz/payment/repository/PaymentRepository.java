package com.smart.classroom.misc.domain.biz.payment.repository;

import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface PaymentRepository {

    PaymentModel queryByOrderNo(String orderNo);

    PaymentModel queryById(long id);

    PaymentModel insert(PaymentModel paymentModel);

    PaymentModel updateStatus(PaymentModel paymentModel);
}
