package com.smart.classroom.misc.domain.middleware.mq;

/**
 * @author lishuang
 * @date 2023-05-17
 * MQ消息订阅
 */
public interface MqConsumer {

    /**
     * 监听某个"领域"下的某个"事件"消息。
     *
     * @param eventName 事件由 领域名前缀+事件构成. 需要上下游约定产出。
     */
    void listen(String eventName, MqConsumerListener mqConsumerListener);

}
