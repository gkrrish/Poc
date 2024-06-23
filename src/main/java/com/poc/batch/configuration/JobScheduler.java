package com.poc.batch.configuration;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.poc.auser.master.entity.BatchJob;
import com.poc.auser.master.repository.BatchJobRepository;

@Component
public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job processNewspapersJob;

    @Autowired
    private BatchJobRepository masterBatchJobRepository;

    @Scheduled(fixedRate = 60000) // Adjust as per your need
    public void scheduleJobs() throws Exception {
        List<BatchJob> batchJobs = masterBatchJobRepository.findAll();
        
        for (BatchJob batchJob : batchJobs) {
            jobLauncher.run(processNewspapersJob, new JobParametersBuilder()
                    .addString("batchId", batchJob.getBatchId().toString())
                    .toJobParameters());
        }
    }
}
