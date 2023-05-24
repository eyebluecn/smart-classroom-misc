package com.smart.classroom.misc.infrastructure.middleware.rocketmq;

import com.smart.classroom.misc.domain.middleware.mq.MqConsumer;
import com.smart.classroom.misc.domain.middleware.mq.MqConsumerListener;
import com.smart.classroom.misc.domain.middleware.mq.info.MqMessagePayloadInfo;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lishuang
 * @date 2023-05-17
 *
 * 实现代码参考：https://rocketmq.apache.org/zh/docs/quickStart/01quickstart
 */
@Slf4j
@Component
public class RocketMqConsumerImpl implements MqConsumer {
    // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8081;xxx:8081。
    public static String ENDPOINT = "localhost:8081";

    // 消息发送的目标Topic名称，需要提前在RocketMQ中创建。
    public static String TOPIC = "SmartClassroomTopic";

    // 为消费者指定所属的消费者分组，Group需要提前创建。
    public static String CONSUMER_GROUP = "SmartClassroomConsumerGroup";

    //标识是否已完成启动，防止多次启动
    private boolean initialized = false;

    //所有的消费者。
    private final Map<String, PushConsumer> pushConsumers = new HashMap<>();

    /**
     * 这个方法只在系统启动的时候调用，系统可能调多次。
     */
    @EventListener(ContextRefreshedEvent.class)
    public void applicationRefreshed() {

        log.info("准备启动RocketMqConsumer");

        if (initialized) {
            return;
        } else {
            initialized = true;
        }

    }

    /**
     * 这个方法只在系统停止的时候调用
     */
    @EventListener(ContextStoppedEvent.class)
    public void applicationStop() {
        log.info("RocketMqConsumer 系统停止钩子函数");

        try {

            for (Map.Entry<String, PushConsumer> entry : pushConsumers.entrySet()) {

                PushConsumer pushConsumer = entry.getValue();

                pushConsumer.close();
            }

        } catch (Throwable e) {
            log.error("在关闭RocketMqConsumer的过程中遭遇错误", e);
        }
    }


    @Override
    public synchronized void listen(String eventName, MqConsumerListener mqConsumerListener) {


        if (StringUtil.isBlank(eventName)) {
            throw new UtilException("事件名不能为空");
        }

        if (this.pushConsumers.containsKey(eventName)) {
            throw new UtilException("{}已经完成了注册，请勿重复注册", eventName);
        }

        final ClientServiceProvider provider = ClientServiceProvider.loadService();

        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(ENDPOINT)
                .build();
        // 订阅消息的过滤规则，表示订阅所有Tag的消息。 我们使用eventName来充当tag.
        FilterExpression filterExpression = new FilterExpression(eventName, FilterExpressionType.TAG);

        try {

            // 初始化PushConsumer，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系。
            PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                    .setClientConfiguration(clientConfiguration)
                    // 设置消费者分组。
                    .setConsumerGroup(CONSUMER_GROUP)
                    // 设置预绑定的订阅关系。
                    .setSubscriptionExpressions(Collections.singletonMap(TOPIC, filterExpression))
                    // 设置消费监听器。
                    .setMessageListener(messageView -> {


                        String messageId = messageView.getMessageId().toString();
                        String content = StandardCharsets.UTF_8.decode(messageView.getBody()).toString();
                        Collection<String> keys = messageView.getKeys();
                        String keysStr = String.join(",", keys);

                        log.info("收到消息 messageId={},keys={},content={}", messageId, keysStr, content);

                        MqMessagePayloadInfo mqMessagePayloadInfo = new MqMessagePayloadInfo();
                        mqMessagePayloadInfo.setMessageId(messageId);
                        mqMessagePayloadInfo.setContent(content);
                        mqMessagePayloadInfo.setKeys(keysStr);

                        boolean success = mqConsumerListener.consume(mqMessagePayloadInfo);

                        log.info("消息消费成功 messageId={},keys={}", messageId, keysStr);

                        // 处理消息并返回消费结果。
                        return success ? ConsumeResult.SUCCESS : ConsumeResult.FAILURE;
                    }).build();

            this.pushConsumers.put(eventName, pushConsumer);

            log.info("注册Mq消息监听成功 eventName = {}", eventName);

        } catch (Throwable throwable) {

            log.error("在注册Mq消息监听时出错了 eventName = {}", eventName, throwable);
        }

    }
}
