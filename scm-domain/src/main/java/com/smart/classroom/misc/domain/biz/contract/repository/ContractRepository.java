package com.smart.classroom.misc.domain.biz.contract.repository;

import com.smart.classroom.misc.domain.biz.contract.model.ContractModel;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface ContractRepository {

    ContractModel insert(ContractModel contractModel);

}
