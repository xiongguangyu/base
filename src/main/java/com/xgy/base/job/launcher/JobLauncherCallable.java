package com.xgy.base.job.launcher;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class JobLauncherCallable implements Callable<JobExecution> {

    private final Job job;

    private final JobParameters jobParameters;

    private final JobLauncher jobLauncher;

    @Override
    public JobExecution call() throws Exception {
        JobExecution jobExecution = null;
        try {
            jobExecution = jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobExecution;
    }
}
