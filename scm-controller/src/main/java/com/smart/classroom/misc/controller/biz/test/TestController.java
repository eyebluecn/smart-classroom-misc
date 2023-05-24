package com.smart.classroom.misc.controller.biz.test;


import com.smart.classroom.misc.controller.auth.Feature;
import com.smart.classroom.misc.controller.auth.FeatureType;
import com.smart.classroom.misc.controller.base.BaseController;
import com.smart.classroom.misc.controller.base.result.WebResult;
import com.smart.classroom.misc.domain.middleware.mq.MqConsumer;
import com.smart.classroom.misc.domain.middleware.mq.MqProducer;
import com.smart.classroom.misc.domain.middleware.mq.info.MqSendResultInfo;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller.
 *
 * @author lishuang
 * @date 2023-05-17
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController extends BaseController {

    @Autowired
    MqProducer mqProducer;

    @Autowired
    MqConsumer mqConsumer;

    @Feature({FeatureType.PUBLIC})
    @RequestMapping("/mq/send")
    public WebResult<?> mqSend(
            @RequestParam String content
    ) {

        MqSendResultInfo mqSendResultInfo = mqProducer.send("HELLO_EVENT", StringUtil.uuid(), content);

        String result = content + "" + StringUtil.format("消息发送情况：{} {}", mqSendResultInfo.isSuccess(), mqSendResultInfo.getMessageId());
        log.info(result);

        return success(result);
    }

    @Feature({FeatureType.PUBLIC})
    @RequestMapping("/mq/listen")
    public WebResult<?> mqListen() {
        mqConsumer.listen("HELLO_EVENT", mqMessagePayloadInfo -> {

            log.info("我在正确的地方消费到了消息：messageId={},keys={},content={}",
                    mqMessagePayloadInfo.getMessageId(),
                    mqMessagePayloadInfo.getKeys(),
                    mqMessagePayloadInfo.getContent());

            return true;
        });
        mqConsumer.listen("PAYMENT_DOMAIN_EVENT_PAID", mqMessagePayloadInfo -> {

            log.info("我PAYMENT_DOMAIN_EVENT_PAID消费到了消息：messageId={},keys={},content={}",
                    mqMessagePayloadInfo.getMessageId(),
                    mqMessagePayloadInfo.getKeys(),
                    mqMessagePayloadInfo.getContent());

            return true;
        });

        return success();
    }


}
