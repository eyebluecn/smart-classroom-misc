package com.smart.classroom.misc.facade.biz.column.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmnibusRequest implements Serializable {
    private String authorName;
    private String columnName;
    private Long editorId;
    private int columnPrice;
}
