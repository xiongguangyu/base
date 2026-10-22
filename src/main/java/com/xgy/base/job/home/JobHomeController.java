package com.xgy.base.job.home;

import com.xgy.base.common.JobRunRequest;
import com.xgy.base.common.Result;
import com.xgy.base.job.launcher.JobLauncherService;
import com.xgy.base.utils.ApplicationContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobHome")
public class JobHomeController {

    private final JobLauncherService jobLauncherService;

    @PostMapping(value = "/runJob",produces = "application/json")
    public Result runJob(@RequestBody JobRunRequest request, BindingResult bindingResult) throws Exception {
        log.info("执行批处理请求参数:{}",request);
        if (bindingResult.hasErrors()) {
            return Result.error("参数错误");
        }
        try {
            Job job = ApplicationContextUtil.getBean(request.getJobName(), Job.class);
            Future<JobExecution> future = jobLauncherService.runJob(job, request);
            while (request.isWaitThread() && !future.isDone() && !future.isCancelled()) {
                Thread.sleep(1000);
            }
            job = null;
        } catch (Exception e) {

            log.error("执行批处理异常:", e);
            return Result.error("执行批处理异常");
        }
        return Result.success();
    }

    @PostMapping("restart")
    public Result restart(@RequestBody JobRunRequest request) throws Exception {
        log.info("重启批处理请求参数:{}",request);
//        jobLauncherService.restart(request.getJobExecutionId());
        return Result.success();
    }
}
