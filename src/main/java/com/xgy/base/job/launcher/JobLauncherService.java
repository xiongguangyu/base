package com.xgy.base.job.launcher;

import com.xgy.base.common.JobRunRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class JobLauncherService {

    private final JobLauncher jobLauncher;

    private final ThreadPoolTaskExecutor taskExecutor;

    private final JobRepository jobRepository;

    public Future<JobExecution> runJob(Job job, JobRunRequest request){

        JobParameters jobParameters = buildJobParameters(request);

        JobLauncherCallable jobLauncherCallable = new JobLauncherCallable(job, jobParameters, jobLauncher);

        return taskExecutor.submit(jobLauncherCallable);
    }

    private JobParameters buildJobParameters(JobRunRequest request) {
        JobParametersBuilder parametersBuilder = new JobParametersBuilder();
        BeanWrapperImpl wrapper = new BeanWrapperImpl(request);
        Arrays.stream(wrapper.getPropertyDescriptors()).forEach(item ->{
            try {
                Object invoke = item.getReadMethod().invoke(request);
                if (invoke != null) {
                    parametersBuilder.addString(item.getName(), String.valueOf(invoke));
                }
            } catch (Exception e) {

            }
        });
        parametersBuilder.addString("timeStamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        return parametersBuilder.toJobParameters();
    }


}
