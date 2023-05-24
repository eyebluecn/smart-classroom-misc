package com.smart.classroom.misc.controller.biz.column.response;

import com.smart.classroom.misc.facade.biz.author.response.AuthorDTO;
import com.smart.classroom.misc.facade.biz.column.response.ColumnDTO;
import com.smart.classroom.misc.facade.biz.quote.response.ColumnQuoteDTO;
import com.smart.classroom.subscription.facade.biz.subscription.response.SubscriptionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lishuang
 * @date 2023-05-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichColumnDTO {

    /**
     * 专栏本身
     */
    private ColumnDTO column = null;


    /**
     * 关联的作者
     */
    private AuthorDTO author = null;

    /**
     * 关联的报价
     */
    private ColumnQuoteDTO columnQuote = null;


    /**
     * 关联的订阅情况
     */
    private SubscriptionDTO subscription = null;


}
