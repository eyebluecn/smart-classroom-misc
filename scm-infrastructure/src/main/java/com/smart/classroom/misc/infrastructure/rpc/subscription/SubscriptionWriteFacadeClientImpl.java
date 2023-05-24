package com.smart.classroom.misc.infrastructure.rpc.subscription;

import com.smart.classroom.misc.domain.rpc.subscription.SubscriptionWriteFacadeClient;
import com.smart.classroom.misc.domain.rpc.subscription.vo.SubscriptionVO;
import com.smart.classroom.misc.infrastructure.rpc.subscription.converter.SubscriptionDTO2VOConverter;
import com.smart.classroom.subscription.facade.biz.subscription.SubscriptionWriteFacade;
import com.smart.classroom.subscription.facade.biz.subscription.response.SubscriptionDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-22
 */
@Service
public class SubscriptionWriteFacadeClientImpl implements SubscriptionWriteFacadeClient {

    @DubboReference
    SubscriptionWriteFacade subscriptionWriteFacade;

    @Override
    public SubscriptionVO compensatePaymentPaid(long paymentId) {
        SubscriptionDTO subscriptionDTO = subscriptionWriteFacade.compensatePaymentPaid(paymentId);

        return SubscriptionDTO2VOConverter.convert(subscriptionDTO);
    }
}
