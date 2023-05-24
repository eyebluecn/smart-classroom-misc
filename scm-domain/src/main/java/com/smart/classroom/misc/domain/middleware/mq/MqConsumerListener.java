package com.smart.classroom.misc.domain.middleware.mq;

import com.smart.classroom.misc.domain.middleware.mq.info.MqMessagePayloadInfo;

/**
 * @author lishuang
 * @date 2023-05-17
 * MQ消息订阅
 */
public interface MqConsumerListener {

    /**
     * 订阅这。
     *
     * @param mqMessagePayloadInfo Mq投递过来的内容
     * @return true:表示消费成功，消息不再重新投递。 false:表示消费失败，消息会继续投递。
     */
    boolean consume(MqMessagePayloadInfo mqMessagePayloadInfo);

}
