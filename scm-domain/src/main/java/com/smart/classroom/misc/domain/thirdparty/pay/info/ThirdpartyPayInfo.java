package com.smart.classroom.misc.domain.thirdparty.pay.info;

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
public class ThirdpartyPayInfo {

    /**
     * 订单号
     */
    String orderNo;

    /**
     * 支付的一些token及信息
     */
    String thirdTransactionNo;

    /**
     * 支付时候的验证信息等。
     */
    String nonceStr;


}
