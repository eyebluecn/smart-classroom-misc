package com.smart.classroom.misc.application.biz.reader;

import com.smart.classroom.misc.application.rpc.subscription.event.SubscriptionDomainEvent;
import com.smart.classroom.misc.domain.biz.reader.repository.ReaderRepository;
import com.smart.classroom.misc.domain.biz.reader.service.ReaderRegisterDomainService;
import com.smart.classroom.misc.domain.middleware.mq.MqConsumer;
import com.smart.classroom.misc.domain.middleware.mq.info.MqMessagePayloadInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class ReaderNotifyAppService {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    ReaderRegisterDomainService readerRegisterDomainService;

    @Autowired
    MqConsumer mqConsumer;


    /**
     * 注册领域事件监听。
     */
    @EventListener(ContextRefreshedEvent.class)
    public void applicationRefreshed() {

        log.info("注册监听领域事件：{}", SubscriptionDomainEvent.SUBSCRIPTION_DOMAIN_EVENT_CREATED);

        mqConsumer.listen(SubscriptionDomainEvent.SUBSCRIPTION_DOMAIN_EVENT_CREATED.name(), this::onSubscriptionCreated);

    }

    public boolean onSubscriptionCreated(MqMessagePayloadInfo mqMessagePayloadInfo) {

        log.info("用户收到了订阅成功的消息:{} 准备去发送用户通知", mqMessagePayloadInfo);

        return true;
    }


}
