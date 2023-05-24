package com.smart.classroom.misc.controller.biz.payment;


import com.smart.classroom.misc.controller.auth.Feature;
import com.smart.classroom.misc.controller.auth.FeatureType;
import com.smart.classroom.misc.controller.base.BaseController;
import com.smart.classroom.misc.controller.base.result.WebResult;
import com.smart.classroom.misc.facade.biz.payment.PaymentWriteFacade;
import com.smart.classroom.misc.facade.biz.payment.request.PaidCallbackRequest;
import com.smart.classroom.misc.facade.biz.payment.response.PaidCallbackDTO;
import com.smart.classroom.subscription.facade.biz.subscription.SubscriptionWriteFacade;
import com.smart.classroom.subscription.facade.biz.subscription.request.PrepareSubscribeRequest;
import com.smart.classroom.subscription.facade.biz.subscription.response.PrepareSubscribeDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller.
 *
 * @author lishuang
 * @date 2023-05-17
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController extends BaseController {

    @DubboReference
    SubscriptionWriteFacade subscriptionWriteFacade;

    @DubboReference
    PaymentWriteFacade paymentWriteFacade;

    /**
     * 第三方支付平台，支付成功后的回调接口。
     * 一般是传输的密文过来，需要用证书解密。
     */
    @Feature({FeatureType.READER_LOGIN})
    @RequestMapping("/paid/callback")
    public WebResult<?> paidCallback(
            @RequestParam String orderNo
    ) {

        PaidCallbackRequest paidCallbackRequest = new PaidCallbackRequest(orderNo);
        PaidCallbackDTO paidCallbackDTO = paymentWriteFacade.paidCallback(paidCallbackRequest);

        return success(paidCallbackDTO);
    }


}
