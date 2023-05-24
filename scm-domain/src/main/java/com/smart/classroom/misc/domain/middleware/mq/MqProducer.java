package com.smart.classroom.misc.domain.middleware.mq;

import com.smart.classroom.misc.domain.middleware.mq.info.MqSendResultInfo;

/**
 * @author lishuang
 * @date 2023-05-17
 * MQ消息生产
 */
public interface MqProducer {

    /**
     * 发送MQ消息
     *
     * 消息的topic统一采用"SmartClassroomTopic"
     * @param tags  消息的tags
     * @param keys  消息的Key字段是为了唯一标识消息的，方便运维排查问题。如果不设置Key，则无法定位消息丢失原因。
     * @param body  发送的消息体，在智慧课堂中只发送文本消息。
     */
    MqSendResultInfo send(String tags, String keys, String body);


}
