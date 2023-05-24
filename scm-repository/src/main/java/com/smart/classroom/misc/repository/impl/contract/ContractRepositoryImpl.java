package com.smart.classroom.misc.repository.impl.contract;

import com.smart.classroom.misc.domain.biz.contract.model.ContractModel;
import com.smart.classroom.misc.domain.biz.contract.repository.ContractRepository;
import com.smart.classroom.misc.repository.impl.contract.converter.ContractDO2ModelConverter;
import com.smart.classroom.misc.repository.impl.contract.converter.ContractModel2DOConverter;
import com.smart.classroom.misc.repository.mapper.contract.ContractMapper;
import com.smart.classroom.misc.repository.orm.contract.ContractDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class ContractRepositoryImpl implements ContractRepository {

    @Autowired
    ContractMapper contractMapper;


    @Override
    public ContractModel insert(ContractModel contractModel) {


        ContractDO contractDO = ContractModel2DOConverter.convert(contractModel);

        contractMapper.insert(contractDO);

        //查询一次。
        contractDO = contractMapper.queryById(contractDO.getId());

        return ContractDO2ModelConverter.convert(contractDO);

    }
}
