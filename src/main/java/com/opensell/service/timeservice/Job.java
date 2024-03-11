package com.opensell.service.timeservice;

public final class Job implements Runnable {
    private long delay;
    private JobType jobType;
    private JobAction jobAction;
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
                case JobType.FOREVER -> {
                    while (true) {
                        jobAction.task();
                        Thread.sleep(delay);
                    }
                }

                case JobType.REPETITION -> {
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
