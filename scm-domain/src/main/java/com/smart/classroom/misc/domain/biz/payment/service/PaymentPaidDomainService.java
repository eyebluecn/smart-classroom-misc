package com.smart.classroom.misc.domain.biz.payment.service;

import com.smart.classroom.misc.domain.biz.payment.enums.PayStatus;
import com.smart.classroom.misc.domain.biz.payment.event.PaymentDomainEvent;
import com.smart.classroom.misc.domain.biz.payment.info.PaidCallbackInfo;
import com.smart.classroom.misc.domain.biz.payment.info.PaymentEventPayload;
import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;
import com.smart.classroom.misc.domain.biz.payment.repository.PaymentRepository;
import com.smart.classroom.misc.domain.middleware.mq.MqProducer;
import com.smart.classroom.misc.domain.middleware.mq.info.MqSendResultInfo;
import com.smart.classroom.misc.domain.middleware.thread.AsyncHandlerService;
import com.smart.classroom.misc.domain.rpc.subscription.SubscriptionWriteFacadeClient;
import com.smart.classroom.misc.domain.thirdparty.pay.ThirdpartyPayClient;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.util.JsonUtil;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 支付单完成支付 的领域服务
 *
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class PaymentPaidDomainService {

    @Autowired
    ThirdpartyPayClient thirdpartyPayClient;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    MqProducer mqProducer;


    @Autowired
    SubscriptionWriteFacadeClient subscriptionWriteFacadeClient;


    @Autowired
    AsyncHandlerService asyncHandlerService;


    /**
     * 支付成功。
     * 由三方支付平台回调方法触发到此方法。
     */
    public PaidCallbackInfo paidCallback(@NonNull String orderNo) {


        PaymentModel paymentModel = paymentRepository.queryByOrderNo(orderNo);
        if (paymentModel == null) {
            throw new UtilException("订单号 {} 对应的支付单不存在", orderNo);
        }

        //修改支付单状态
        paymentModel.setStatus(PayStatus.PAID);
        paymentModel = paymentRepository.updateStatus(paymentModel);

        //发布领域事件。
        String traceId = StringUtil.uuid();
        Long paymentId = paymentModel.getId();
        PaymentEventPayload paymentEventPayload = new PaymentEventPayload(
                paymentId,
                paymentModel.getOrderNo(),
                paymentModel.getMethod(),
                paymentModel.getAmount(),
                paymentModel.getStatus(),
                new Date()
        );
        String content = JsonUtil.toJson(paymentEventPayload);
        MqSendResultInfo mqSendResultInfo = mqProducer.send(PaymentDomainEvent.PAYMENT_DOMAIN_EVENT_PAID.name(), traceId, content);
        if (mqSendResultInfo.isSuccess()) {
            log.info("领域事件{}发送成功", PaymentDomainEvent.PAYMENT_DOMAIN_EVENT_PAID.name());
        } else {
            log.error("领域事件{}发送失败", PaymentDomainEvent.PAYMENT_DOMAIN_EVENT_PAID.name());
        }

        log.info("支付单完成支付：paymentId = {} orderNo = {}", paymentId, orderNo);


        //FIXME: 演示环境可能消息收不到，所以这里直接调用消息补偿接口。 正确做法应该是靠消息解耦。
        asyncHandlerService.submit(() -> {
            log.info("演示环境可能消息收不到，所以这里直接调用消息补偿接口。 正确做法应该是靠消息解耦。");
            subscriptionWriteFacadeClient.compensatePaymentPaid(paymentId);
        });


        return new PaidCallbackInfo("OK", "支付单已确认支付成功");

    }

}
