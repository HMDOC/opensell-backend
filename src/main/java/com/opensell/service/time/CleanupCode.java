package com.opensell.service.time;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CleanupCode {
    private final JdbcTemplate queryExecutor;

    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void codeCleanup() {
        String deleteCode = "DELETE FROM verification_code WHERE TIMESTAMPDIFF(MINUTE, created_at, CURRENT_TIMESTAMP) > 10";
        queryExecutor.execute(deleteCode);
        System.out.println("Code cleanup executed");
    }
}
