package com.smart.classroom.misc.infrastructure.middleware.rocketmq;

import com.smart.classroom.misc.domain.middleware.mq.MqProducer;
import com.smart.classroom.misc.domain.middleware.mq.info.MqSendResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author lishuang
 * @date 2023-05-17
 *
 * 实现代码参考：https://rocketmq.apache.org/zh/docs/quickStart/01quickstart
 */
@Slf4j
@Component
public class RocketMqProducerImpl implements MqProducer {
    // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8081;xxx:8081。
    public static String ENDPOINT = "localhost:8081";

    // 消息发送的目标Topic名称，需要提前在RocketMQ中创建。
    // 一般一个应用对应一个TOPIC.
    public static String TOPIC = "SmartClassroomTopic";

    //标识是否已完成启动，防止多次启动
    private boolean initialized = false;

    private ClientServiceProvider provider;

    //消费者，发送消息的物件。
    private Producer producer = null;

    /**
     * 这个方法只在系统启动的时候调用，系统可能调多次。
     */
    @EventListener(ContextRefreshedEvent.class)
    public void applicationRefreshed() {

        log.info("准备启动RocketMQProducer");

        if (initialized) {
            return;
        } else {
            initialized = true;
        }

        /*
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        try {
            this.provider = ClientServiceProvider.loadService();
            ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(ENDPOINT);
            ClientConfiguration configuration = builder.build();
            // 初始化Producer时需要设置通信配置以及预绑定的Topic。
            this.producer = provider.newProducerBuilder()
                    .setTopics(TOPIC)
                    .setClientConfiguration(configuration)
                    .build();

        } catch (Throwable throwable) {
            log.error("RocketMQ启动失败，请检查RocketMQ是否已经启动", throwable);
        }
    }


    /**
     * 这个方法只在系统停止的时候调用
     */
    @EventListener(ContextStoppedEvent.class)
    public void applicationStop() {
        log.info("RocketMQProducer 系统停止钩子函数");

        //应用退出时，要调用shutdown来清理资源，关闭网络连接，从RocketMQ服务器上注销自己
        if (producer == null) {
            log.warn("在尝试关闭RocketMQProducer时发现producer已经不存在了");
        } else {
            try {
                producer.close();
            } catch (Throwable e) {
                log.error("在关闭RocketMQProducer的过程中遭遇错误", e);
            }
        }
    }


    @Override
    public MqSendResultInfo send(String tags, String keys, String body) {

        // 普通消息发送。
        Message message = this.provider.newMessageBuilder()
                .setTopic(TOPIC)
                // 设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(keys)
                // 设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(tags)
                // 消息体。
                .setBody(body.getBytes())
                .build();

        try {
            // 发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            String messageId = sendReceipt.getMessageId().toString();
            log.info("消息发送成功 keys={} tags={} messageId={} version={}", keys, tags, messageId, sendReceipt.getMessageId().getVersion());
            return new MqSendResultInfo(true, messageId, null);
        } catch (Throwable e) {
            String errorMessage = e.getMessage();
            log.error("消息发送失败 keys={} tags={} error={}", keys, tags, errorMessage);
            return new MqSendResultInfo(false, null, errorMessage);
        }
    }
}
