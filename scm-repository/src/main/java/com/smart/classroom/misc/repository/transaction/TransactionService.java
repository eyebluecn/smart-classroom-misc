package com.smart.classroom.misc.repository.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 事务，手动挡。
 *
 * @author lishuang
 * @date 2023-05-12
 */
@Component
public class TransactionService {

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    //*************************** 精细手动控制 开始 ***************************//

    //开启事务, 默认使用RR隔离级别，REQUIRED传播级别
    public TransactionStatus begin() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 事物隔离级别，开启新事务
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        // 事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //将拿到的事务返回进去，才能提交。
        return transactionManager.getTransaction(def);
    }

    //提交事务
    public void commit(TransactionStatus transaction) {
        //提交事务
        transactionManager.commit(transaction);
    }

    //回滚事务
    public void rollback(TransactionStatus transaction) {
        transactionManager.rollback(transaction);
    }

    //*************************** 精细手动控制 结束 ***************************//



    //*************************** 粗粒度手动控制 开始 ***************************//
    //直接执行方法方法体。
    @Nullable
    public <T> T execute(TransactionCallback<T> action) throws TransactionException {
        return transactionTemplate.execute(action);
    }
    //*************************** 粗粒度手动控制 结束 ***************************//

}