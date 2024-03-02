package com.opensell.service.timeService;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.opensell.service.QuartzInitializer;

@Component
public class CodeCleanup implements Job {  // création d'un job pour effacer les codes de vérification expirés

    @Autowired
    private JdbcTemplate queryExecutor;

    @Autowired
    private QuartzInitializer scheduler;
    
    public JobDetail jobSetup() { // set le job
        return JobBuilder.newJob(CodeCleanup.class)
        .withIdentity("deleteExpiredVerificationCodes", "databaseCleanup")
        .build(); 
    }

    public Trigger triggerSetup() { // set le trigger, donc le moment où le job va être exécuté
        return TriggerBuilder.newTrigger()
        .withIdentity("deleteExpiredVerificationCodes", "databaseCleanup")
        .startNow()
        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInMinutes(10) // exécuter à chaque minute
            .repeatForever())
        .build();
    }

    public void execute(JobExecutionContext var1) throws JobExecutionException { // exécute le job
        String deleteQuery = "DELETE FROM verification_code WHERE TIMESTAMPDIFF(MINUTE, created_at, CURRENT_TIMESTAMP) > 10";
        queryExecutor.execute(deleteQuery);
        System.out.println("Code cleanup executed");
    }

    public void scheduleJob() throws Exception { // met le job et le trigger ensemble
        scheduler.schedule(jobSetup(), triggerSetup());
    }
    
}
