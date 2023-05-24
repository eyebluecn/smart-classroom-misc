package com.smart.classroom.misc.application.biz.column;

import com.smart.classroom.misc.domain.biz.author.repository.AuthorRepository;
import com.smart.classroom.misc.domain.biz.author.service.AuthorRegisterDomainService;
import com.smart.classroom.misc.domain.biz.column.model.ColumnModel;
import com.smart.classroom.misc.domain.biz.column.repository.ColumnRepository;
import com.smart.classroom.misc.domain.biz.column.repository.query.ColumnPageQuery;
import com.smart.classroom.misc.domain.biz.column.service.ColumnCreateDomainService;
import com.smart.classroom.misc.domain.biz.contract.service.ContractCreateDomainService;
import com.smart.classroom.misc.domain.biz.editor.repository.EditorRepository;
import com.smart.classroom.misc.domain.biz.editor.service.EditorRegisterDomainService;
import com.smart.classroom.misc.domain.biz.quote.service.ColumnQuoteCreateDomainService;
import com.smart.classroom.misc.repository.transaction.TransactionService;
import com.smart.classroom.misc.utility.result.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class ColumnReadAppService {


    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    EditorRepository editorRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    AuthorRegisterDomainService authorRegisterDomainService;

    @Autowired
    EditorRegisterDomainService editorRegisterDomainService;

    @Autowired
    ColumnCreateDomainService columnCreateDomainService;

    @Autowired
    ContractCreateDomainService contractCreateDomainService;

    @Autowired
    ColumnQuoteCreateDomainService columnQuoteCreateDomainService;

    @Autowired
    TransactionService transactionService;

    //专栏分页查询
    public Pager<ColumnModel> page(ColumnPageQuery columnPageQuery) {

        return columnRepository.page(columnPageQuery);
    }

    //专栏详情查询
    public ColumnModel queryById(Long columnId) {

        return columnRepository.queryById(columnId);
    }


}
