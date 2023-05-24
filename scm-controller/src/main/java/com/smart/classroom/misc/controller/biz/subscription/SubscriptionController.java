package com.smart.classroom.misc.controller.biz.subscription;


import com.smart.classroom.misc.controller.auth.Feature;
import com.smart.classroom.misc.controller.auth.FeatureType;
import com.smart.classroom.misc.controller.base.BaseController;
import com.smart.classroom.misc.controller.base.result.WebResult;
import com.smart.classroom.misc.controller.biz.subscription.response.RichSubscriptionDTO;
import com.smart.classroom.misc.facade.biz.column.ColumnReadFacade;
import com.smart.classroom.misc.facade.biz.column.response.ColumnDTO;
import com.smart.classroom.misc.facade.biz.reader.response.ReaderDTO;
import com.smart.classroom.subscription.facade.biz.order.OrderReadFacade;
import com.smart.classroom.subscription.facade.biz.order.response.OrderDTO;
import com.smart.classroom.subscription.facade.biz.subscription.SubscriptionReadFacade;
import com.smart.classroom.subscription.facade.biz.subscription.SubscriptionWriteFacade;
import com.smart.classroom.subscription.facade.biz.subscription.request.PrepareSubscribeRequest;
import com.smart.classroom.subscription.facade.biz.subscription.request.SubscriptionPageRequest;
import com.smart.classroom.subscription.facade.biz.subscription.response.PrepareSubscribeDTO;
import com.smart.classroom.subscription.facade.biz.subscription.response.SubscriptionDTO;
import com.smart.classroom.subscription.facade.common.resposne.PagerResponse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * controller.
 *
 * @author lishuang
 * @date 2023-05-17
 */
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController extends BaseController {

    @DubboReference
    SubscriptionReadFacade subscriptionReadFacade;

    @DubboReference
    SubscriptionWriteFacade subscriptionWriteFacade;

    @DubboReference
    ColumnReadFacade columnReadFacade;

    @DubboReference
    OrderReadFacade orderReadFacade;

    /**
     * 准备订阅
     */
    @Feature({FeatureType.READER_LOGIN})
    @RequestMapping("/prepare")
    public WebResult<?> prepare(
            @RequestParam long columnId,
            @RequestParam String payMethod
    ) {

        ReaderDTO readerDTO = this.checkLoginReader();

        PrepareSubscribeRequest request = new PrepareSubscribeRequest(
                readerDTO.getId(),
                columnId,
                payMethod
        );

        PrepareSubscribeDTO prepareSubscribeDTO = subscriptionWriteFacade.prepareSubscribe(request);

        return success(prepareSubscribeDTO);
    }


    /**
     * 订阅列表
     */
    @Feature({FeatureType.READER_LOGIN})
    @RequestMapping("/page")
    public WebResult<?> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String orderCreateTime,
            @RequestParam(required = false) String orderUpdateTime,
            @RequestParam(required = false) Long columnId,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) String status
    ) {


        ReaderDTO readerDTO = findLoginReader();

        PagerResponse<SubscriptionDTO> page = subscriptionReadFacade.page(new SubscriptionPageRequest(
                pageNum,
                pageSize,
                orderCreateTime,
                orderUpdateTime,
                readerDTO.getId(),
                columnId,
                orderId,
                status
        ));

        List<RichSubscriptionDTO> list = new ArrayList<>();
        for (SubscriptionDTO subscriptionDTO : page.getData()) {

            ColumnDTO columnDTO = columnReadFacade.queryById(subscriptionDTO.getColumnId());

            OrderDTO orderDTO = orderReadFacade.queryById(subscriptionDTO.getOrderId());


            RichSubscriptionDTO richSubscriptionDTO = new RichSubscriptionDTO(
                    subscriptionDTO,
                    columnDTO,
                    readerDTO,
                    orderDTO
            );

            list.add(richSubscriptionDTO);
        }

        PagerResponse<RichSubscriptionDTO> richSubscriptionDTOPagerResponse = new PagerResponse<>(
                page.getPageNum(),
                page.getPageSize(),
                page.getTotalItems(),
                list
        );


        return success(richSubscriptionDTOPagerResponse);
    }


    /**
     * 支付成功的消息补偿。
     */
    @Feature({FeatureType.EDITOR_LOGIN})
    @RequestMapping("/compensate")
    public WebResult<?> compensate(
            @RequestParam long paymentId
    ) {

        SubscriptionDTO subscriptionDTO = subscriptionWriteFacade.compensatePaymentPaid(paymentId);
        return success(subscriptionDTO);
    }


}
