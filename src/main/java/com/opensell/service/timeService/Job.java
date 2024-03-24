package com.opensell.service.timeService;

/**
 * To do a task forever or for a number of repetition.
 * 
 * @author Achraf
*/
public final class Job implements Runnable {
    // Time between each task
    private long delay;

    // Repetition or Forever 
    private JobType jobType;

    // The task you want to do
    private JobAction jobAction;

    // The number of time the task is made
    private int repetition;

    public Job(JobAction jobAction, long delay) {
        this.jobAction = jobAction;
        this.delay = delay;
        jobType = JobType.FOREVER;
    }

    public Job(JobAction jobAction, long delay, int repetition) {
        this.jobAction = jobAction;
        this.delay = delay;
        this.repetition = repetition;
        jobType = JobType.REPETITION;
    }

    public enum JobType {
        FOREVER,
        REPETITION
    }

    @Override
    public void run() {
        try {
            switch (jobType) {
                case FOREVER -> {
                    while (true) {
                        jobAction.task();
                        Thread.sleep(delay);
                    }
                }

                case REPETITION -> {
                    for(int i = 0; i < repetition; i++) {
                        jobAction.task();
                        Thread.sleep(delay);
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
