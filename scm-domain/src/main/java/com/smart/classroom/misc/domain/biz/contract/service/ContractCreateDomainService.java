package com.smart.classroom.misc.domain.biz.contract.service;

import com.smart.classroom.misc.domain.biz.author.model.AuthorModel;
import com.smart.classroom.misc.domain.biz.column.model.ColumnModel;
import com.smart.classroom.misc.domain.biz.contract.enums.ContractStatus;
import com.smart.classroom.misc.domain.biz.contract.model.ContractModel;
import com.smart.classroom.misc.domain.biz.contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 创建作者合同领域服务
 *
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class ContractCreateDomainService {

    @Autowired
    ContractRepository contractRepository;

    /**
     * 创建作者合同
     */
    public ContractModel create(AuthorModel authorModel, ColumnModel columnModel) {

        //FIXME: 临时起一个名字。
        String name = "《" + columnModel.getName() + "》合同";

        //这里创建后就立即置为发布状态
        ContractModel contractModel = ContractModel.builder()
                .createTime(new Date())
                .updateTime(new Date())
                .name(name)
                .content("这里是合同内容：" + name)
                .columnId(columnModel.getId())
                .authorId(authorModel.getId())
                .status(ContractStatus.OK)
                .percentage(new BigDecimal("0.4"))
                .paymentDay("LAST_DAY_OF_MONTH")
                .build();

        contractModel = contractRepository.insert(contractModel);


        //TODO: 发送合同创建成功的领域事件。

        return contractModel;
    }

}
