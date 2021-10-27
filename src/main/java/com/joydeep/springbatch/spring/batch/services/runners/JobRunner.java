package com.joydeep.springbatch.spring.batch.services.runners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobRunner {
    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);
    public static final String FILE_NAME_CONTEXT_KEY = "fileName";


    private JobLauncher simpleJobLauncher;
    private Job job;

    public JobRunner(Job job, JobLauncher jobLauncher) {
        this.simpleJobLauncher = jobLauncher;
        this.job = job;
    }


    @Async
    public void runBatchJob() {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString(FILE_NAME_CONTEXT_KEY, "employees.csv");
        jobParametersBuilder.addDate("date", new Date(), true);
        runJob(job, jobParametersBuilder.toJobParameters());
    }


    public void runJob(Job job, JobParameters parameters) {
        try {
            JobExecution jobExecution = simpleJobLauncher.run(job, parameters);
        } catch (JobExecutionAlreadyRunningException e) {
            logger.info("Job with fileName={} is already running.", parameters.getParameters().get(FILE_NAME_CONTEXT_KEY));
        } catch (JobRestartException e) {
            logger.info("Job with fileName={} was not restarted.", parameters.getParameters().get(FILE_NAME_CONTEXT_KEY));
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.info("Job with fileName={} already completed.", parameters.getParameters().get(FILE_NAME_CONTEXT_KEY));
        } catch (JobParametersInvalidException e) {
            logger.info("Invalid job parameters.", parameters.getParameters().get(FILE_NAME_CONTEXT_KEY));
        }
    }

}
