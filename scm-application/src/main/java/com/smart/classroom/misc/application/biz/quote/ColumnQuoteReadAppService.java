package com.smart.classroom.misc.application.biz.quote;

import com.smart.classroom.misc.domain.biz.payment.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class ColumnQuoteReadAppService {

    @Autowired
    PaymentRepository paymentRepository;


}
