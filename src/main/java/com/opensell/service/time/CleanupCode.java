package com.opensell.service.time;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CleanupCode implements JobAction {
    private final JdbcTemplate queryExecutor;

    @Override
    public void execute() {
        String deleteCode = "DELETE FROM verification_code WHERE TIMESTAMPDIFF(MINUTE, created_at, CURRENT_TIMESTAMP) > 10";
        queryExecutor.execute(deleteCode);
        System.out.println("Code cleanup executed");
    }
}
