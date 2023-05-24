package com.smart.classroom.misc.domain.rpc.subscription;

import com.smart.classroom.misc.domain.rpc.subscription.vo.SubscriptionVO;

/**
 * @author lishuang
 * @date 2023-05-22
 */
public interface SubscriptionWriteFacadeClient {

    /**
     * 支付成功消息补偿。
     */
    SubscriptionVO compensatePaymentPaid(long paymentId);

}
