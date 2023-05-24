package com.smart.classroom.misc.facade.biz.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lishuang
 * @date 2023-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreparePaymentDTO implements Serializable {

    /**
     * 支付单
     */
    PaymentDTO paymentDTO;

    /**
     * 支付的一些token及信息
     */
    String thirdTransactionNo;

    /**
     * 支付时候的验证信息等。
     */
    String nonceStr;

}
