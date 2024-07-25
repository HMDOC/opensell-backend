package com.opensell.service.time;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailCleanup {
    private final JdbcTemplate queryExecutor;

    @Scheduled(fixedRate = 600000, initialDelay = 600000)
    protected void emailCleanUp() {
        String deleteCode = "DELETE vc FROM verification_code vc WHERE vc.customer_id IN (SELECT c.id FROM customer c WHERE c.is_activated = 0)";
        String deleteEmail = "DELETE c FROM customer c WHERE c.is_activated = 0";
        queryExecutor.execute(deleteCode);
        queryExecutor.execute(deleteEmail);
        System.out.println("Email cleanup executed");
    }
}
