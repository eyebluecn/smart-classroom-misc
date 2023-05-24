package com.smart.classroom.misc.domain.biz.payment.info;

import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lishuang
 * @date 2023-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreparePaymentInfo {

    /**
     * 支付单
     */
    PaymentModel paymentModel;

    /**
     * 支付的一些token及信息
     */
    String thirdTransactionNo;

    /**
     * 支付时候的验证信息等。
     */
    String nonceStr;

}
