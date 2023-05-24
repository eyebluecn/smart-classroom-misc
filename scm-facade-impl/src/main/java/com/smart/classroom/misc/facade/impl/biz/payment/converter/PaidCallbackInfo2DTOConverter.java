package com.smart.classroom.misc.facade.impl.biz.payment.converter;

import com.smart.classroom.misc.domain.biz.payment.info.PaidCallbackInfo;
import com.smart.classroom.misc.domain.biz.payment.info.PreparePaymentInfo;
import com.smart.classroom.misc.facade.biz.payment.response.PaidCallbackDTO;
import com.smart.classroom.misc.facade.biz.payment.response.PreparePaymentDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class PaidCallbackInfo2DTOConverter {

    /**
     * 将DO转换成模型
     */
    public static PaidCallbackDTO convert(PaidCallbackInfo paidCallbackInfo) {
        if (paidCallbackInfo == null) {
            return null;
        }

        PaidCallbackDTO paidCallbackDTO = PaidCallbackDTO.builder()
                .code(paidCallbackInfo.getCode())
                .msg(paidCallbackInfo.getMsg())
                .build();

        return paidCallbackDTO;
    }


}
