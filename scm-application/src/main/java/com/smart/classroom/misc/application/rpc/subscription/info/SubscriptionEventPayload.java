package com.smart.classroom.misc.application.rpc.subscription.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lishuang
 * @date 2023-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEventPayload {

    /**
     * 读者id
     */
    private Long readerId = null;

    /**
     * 专栏id
     */
    private Long columnId = null;

    /**
     * 订单id
     */
    private Long orderId = null;

    /**
     * 状态 CREATED/OK/DISABLED
     */
    private String status = null;

    /**
     * 时间
     */
    private Date occurTime = null;

}
