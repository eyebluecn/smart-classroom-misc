package com.smart.classroom.misc.domain.thirdparty.pay;

import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;
import com.smart.classroom.misc.domain.thirdparty.pay.info.ThirdpartyPayInfo;

/**
 * @author lishuang
 * @date 2023-05-16
 * <p>
 * 第三方支付服务
 */
public interface ThirdpartyPayClient {

    /**
     * 向第三方支付机构下单
     */
    ThirdpartyPayInfo createOrder(PaymentModel paymentModel);


    /**
     * 根据平台订单信息，查询到在第三方机构的订单信息。
     * 可能返回null.
     */
    ThirdpartyPayInfo queryOrder(PaymentModel paymentModel);

}
