package org.javaboy.demo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.TimeoutException;

/**
 * @author 江南一点雨
 * @微信公众号 江南一点雨
 * @网站 http://www.itboyhub.com
 * @国际站 http://www.javaboy.org
 * @微信 a_java_boy
 * @GitHub https://github.com/lenve
 * @Gitee https://gitee.com/lenve
 */
public class UserService {
    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;
    private PlatformTransactionManager platformTransactionManager;

    public UserService(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate, PlatformTransactionManager platformTransactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
        this.platformTransactionManager = platformTransactionManager;
    }

    public void m1() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.update("update user set money=999 where username='zhangsan'");
                    int i = 1 / 0;
                } catch (DataAccessException e) {
                    status.setRollbackOnly();
                }
            }
        });
    }

    public void m2() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus status = platformTransactionManager.getTransaction(definition);
        try {
            jdbcTemplate.update("update user set money=998 where username='zhangsan'");
            int i = 1 / 0;
            platformTransactionManager.commit(status);
        } catch (DataAccessException e) {
            platformTransactionManager.rollback(status);
        }
    }

    public void m3() throws TimeoutException {
        jdbcTemplate.update("update user set money=997 where username=?", "zhangsan");
//        int i = 1 / 0;
        throw new TimeoutException();
    }
}
