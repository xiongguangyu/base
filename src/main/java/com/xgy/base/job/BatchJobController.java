package com.xgy.base.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 提供 REST 接口手动触发批处理 Job。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/batch/jobHome")
public class BatchJobController {

    private final JobLauncher jobLauncher;

    private final Job sampleJob;

    @PostMapping("/run")
    public Map<String, Object> run() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        JobExecution execution = this.jobLauncher.run(this.sampleJob, jobParameters);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("jobName", execution.getJobInstance().getJobName());
        result.put("jobId", execution.getId());
        result.put("status", execution.getStatus().name());
        result.put("startTime", execution.getStartTime());
        result.put("endTime", execution.getEndTime());
        return result;
    }
}
