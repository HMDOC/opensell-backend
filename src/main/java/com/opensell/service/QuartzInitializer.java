package com.opensell.service;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is the Quartz API initializer. This class can be called to schedule a task
 */

@Service
public class QuartzInitializer {

    @Autowired
    private Scheduler scheduler;
    
    public void startService() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();
        scheduler.start();
    }

    public void schedule (JobDetail job, Trigger trigger) throws Exception {
        scheduler.scheduleJob(job, trigger);
    }
}
