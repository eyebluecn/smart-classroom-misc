package com.smart.classroom.misc.domain.middleware.mq.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lishuang
 * @date 2023-05-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MqMessagePayloadInfo {

    String messageId;

    String content;

    String tag;

    String keys;

}
