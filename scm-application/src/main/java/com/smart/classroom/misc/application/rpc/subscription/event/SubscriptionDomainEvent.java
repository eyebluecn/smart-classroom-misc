package com.smart.classroom.misc.application.rpc.subscription.event;

import lombok.Getter;

/**
 * 定义领域事件类型。
 */
public enum SubscriptionDomainEvent {

    SUBSCRIPTION_DOMAIN_EVENT_CREATED("订阅成功");

    @Getter
    private final String description;

    SubscriptionDomainEvent(String description) {
        this.description = description;
    }


    public static String toString(SubscriptionDomainEvent status) {
        if (status == null) {
            return null;
        }
        return status.name();
    }

    public static SubscriptionDomainEvent toEnum(String s) {
        if (s == null) {
            return null;
        }
        return SubscriptionDomainEvent.valueOf(s);
    }


}
