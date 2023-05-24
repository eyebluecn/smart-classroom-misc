package com.smart.classroom.misc.application.biz.column;

import com.smart.classroom.misc.domain.biz.author.model.AuthorModel;
import com.smart.classroom.misc.domain.biz.author.repository.AuthorRepository;
import com.smart.classroom.misc.domain.biz.author.service.AuthorRegisterDomainService;
import com.smart.classroom.misc.domain.biz.column.model.ColumnModel;
import com.smart.classroom.misc.domain.biz.column.repository.ColumnRepository;
import com.smart.classroom.misc.domain.biz.column.service.ColumnCreateDomainService;
import com.smart.classroom.misc.domain.biz.contract.model.ContractModel;
import com.smart.classroom.misc.domain.biz.contract.service.ContractCreateDomainService;
import com.smart.classroom.misc.domain.biz.editor.model.EditorModel;
import com.smart.classroom.misc.domain.biz.editor.repository.EditorRepository;
import com.smart.classroom.misc.domain.biz.editor.service.EditorRegisterDomainService;
import com.smart.classroom.misc.domain.biz.quote.model.ColumnQuoteModel;
import com.smart.classroom.misc.domain.biz.quote.service.ColumnQuoteCreateDomainService;
import com.smart.classroom.misc.repository.transaction.TransactionService;
import com.smart.classroom.misc.utility.exception.UtilException;
import com.smart.classroom.misc.utility.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class ColumnWriteAppService {


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

    /**
     * ！！！此接口仅作为演示用途！！！
     * 一口气创建 作者，作者合同，专栏，课程文章，编辑，专栏报价
     * 为了保证数据库不出现脏数据
     */
    public void omnibus(
            String authorName,
            String columnName,
            long editorId,
            int columnPrice
    ) {


        if (StringUtil.isBlank(authorName) || StringUtil.isBlank(columnName)) {
            throw new UtilException("参数不能为空");
        }


        //采用精细手动挡的事务控制
        TransactionStatus transactionStatus = transactionService.begin();
        try {

            //根据作者名，寻找作者
            AuthorModel authorModel = authorRepository.queryOneByUsername(authorName);
            if (authorModel == null) {
                //作者注册。 简单用密码123456，真名和用户名用一样的。
                authorModel = authorRegisterDomainService.register(authorName, "123456", authorName);
            }

            //根据小编工号，寻找小编
            EditorModel editorModel = editorRepository.queryById(editorId);
            if (editorModel == null) {
                throw new UtilException("小编不存在！");
            }

            //创建专栏
            ColumnModel columnModel = columnRepository.queryByName(columnName);
            if (columnModel != null) {
                throw new UtilException("专栏已存在，请勿重复创建！");
            }
            columnModel = columnCreateDomainService.create(authorModel, columnName);

            //创建作者合同。
            ContractModel contractModel = contractCreateDomainService.create(authorModel, columnModel);

            //创建专栏报价。
            ColumnQuoteModel columnQuoteModel = columnQuoteCreateDomainService.create(columnModel, authorModel, editorModel, columnPrice);


            log.info("完成了混合创建：authorId={} editorId={} columnId={} contractId={} columnQuoteId={}",
                    authorModel.getId(), editorModel.getId(), columnModel.getId(), contractModel.getId(), columnQuoteModel.getId());


            //提交事务
            transactionService.commit(transactionStatus);
        } catch (Throwable throwable) {
            //回滚事务
            transactionService.rollback(transactionStatus);
            throw new UtilException(throwable);
        }

    }

}
