package com.opensell.service.timeService;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailCleanup implements JobAction {
    private final JdbcTemplate queryExecutor;

    @Override
    public void execute() {
        String deleteCode = "DELETE FROM verification_code WHERE customer_id IN (SELECT c.id_customer FROM customer c WHERE c.is_activated = 0)";
        String deleteEmail = "DELETE c FROM customer c WHERE c.is_activated = 0";
        queryExecutor.execute(deleteCode);
        queryExecutor.execute(deleteEmail);
        System.out.println("Email cleanup executed");
    }

}
