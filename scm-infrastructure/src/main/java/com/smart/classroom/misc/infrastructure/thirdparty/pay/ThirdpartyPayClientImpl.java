package com.smart.classroom.misc.infrastructure.thirdparty.pay;

import com.smart.classroom.misc.domain.biz.payment.model.PaymentModel;
import com.smart.classroom.misc.domain.thirdparty.pay.ThirdpartyPayClient;
import com.smart.classroom.misc.domain.thirdparty.pay.info.ThirdpartyPayInfo;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-16
 */
@Slf4j
@Service
public class ThirdpartyPayClientImpl implements ThirdpartyPayClient {

    @Override
    public ThirdpartyPayInfo createOrder(PaymentModel paymentModel) {

        log.info("【模拟】向第三方支付平台发起创建订单 paymentId={} orderNo={}", paymentModel.getId(), paymentModel.getOrderNo());

        ThirdpartyPayInfo thirdpartyPayInfo = new ThirdpartyPayInfo(
                paymentModel.getOrderNo(),
                StringUtil.uuid(),
                StringUtil.randomString(10)
        );

        log.info("【模拟】向第三方支付平台 订单创建成功 orderNo={} thirdTransactionNo={}", thirdpartyPayInfo.getOrderNo(), thirdpartyPayInfo.getThirdTransactionNo());

        return thirdpartyPayInfo;
    }

    @Override
    public ThirdpartyPayInfo queryOrder(PaymentModel paymentModel) {

        //随机返回null或者查到内容。
        if (System.currentTimeMillis() % 3 == 0) {
            return null;
        } else {
            log.info("【模拟】向第三方支付平台查询订单 paymentId={} orderNo={}", paymentModel.getId(), paymentModel.getOrderNo());

            ThirdpartyPayInfo thirdpartyPayInfo = new ThirdpartyPayInfo(
                    paymentModel.getOrderNo(),
                    StringUtil.uuid(),
                    StringUtil.randomString(10)
            );

            log.info("【模拟】向第三方支付平台 查询成功 orderNo={} thirdTransactionNo={}", thirdpartyPayInfo.getOrderNo(), thirdpartyPayInfo.getThirdTransactionNo());

            return thirdpartyPayInfo;
        }

    }
}
