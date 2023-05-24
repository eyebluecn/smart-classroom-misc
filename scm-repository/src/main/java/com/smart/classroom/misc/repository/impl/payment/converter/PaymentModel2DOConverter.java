package com.smart.classroom.misc.repository.impl.payment.converter;

import com.smart.classroom.misc.domain.biz.payment.enums.PayStatus;
import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;
import com.smart.classroom.misc.repository.orm.payment.PaymentDO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class PaymentModel2DOConverter {

    /**
     * 将DO转换成模型
     */
    public static PaymentDO convert(PaymentModel paymentModel) {
        if (paymentModel == null) {
            return null;
        }

        PaymentDO paymentDO = PaymentDO.builder()

                .orderNo(paymentModel.getOrderNo())
                .method(paymentModel.getMethod())
                .thirdTransactionNo(paymentModel.getThirdTransactionNo())
                .amount(paymentModel.getAmount())
                .status(paymentModel.getStatus())
                .build();

        paymentDO.setId(paymentModel.getId());
        paymentDO.setCreateTime(paymentModel.getCreateTime());
        paymentDO.setUpdateTime(paymentModel.getUpdateTime());
        return paymentDO;
    }


}
