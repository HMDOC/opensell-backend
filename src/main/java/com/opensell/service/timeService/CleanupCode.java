package com.opensell.service.timeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CleanupCode implements JobAction {


    @Autowired
    private JdbcTemplate queryExecutor;

    @Override
    public void execute() {
        String deleteQuery = "DELETE FROM verification_code WHERE TIMESTAMPDIFF(MINUTE, created_at, CURRENT_TIMESTAMP) > 10";
        queryExecutor.execute(deleteQuery);
        System.out.println("Code cleanup executed");
    }

}
