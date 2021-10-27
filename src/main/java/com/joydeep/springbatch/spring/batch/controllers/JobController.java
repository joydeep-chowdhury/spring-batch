package com.joydeep.springbatch.spring.batch.controllers;

import com.joydeep.springbatch.spring.batch.services.runners.JobRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/run")
public class JobController {

    private JobRunner jobRunner;

    public JobController(JobRunner jobRunner) {
        this.jobRunner = jobRunner;
    }

    @RequestMapping(value = "/job")
    public String runJob() {
        jobRunner.runBatchJob();
        return String.format("Job Demo1 submitted successfully.");
    }
}
