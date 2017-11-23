/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.services;

import com.vzw.booking.bg.batch.domain.batch.BatchJobExecution;
import com.vzw.booking.bg.batch.domain.batch.mappers.SpringJobExecutionMapper;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author smorcja
 */
@RestController
@RequestMapping("/WholesaleBooking")
@CrossOrigin
@EnableAsync
public class JobLauncherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherController.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity info() {
        return ResponseEntity.ok("Wholesale Booking Processing App - status: up and running");
    }

    @RequestMapping(value="/launchJob", method = RequestMethod.GET)
    @Async
    public Future<ResponseEntity> launchJob() {
        try {
            Map<String, JobParameter> parameters = new HashMap<>();  
            parameters.put("currentTime", new JobParameter(new Date()));
            JobExecution jex = jobLauncher.run(job, new JobParameters(parameters));
            BatchJobExecution bjex = SpringJobExecutionMapper.convert(jex);
            return new AsyncResult<ResponseEntity>(ResponseEntity.ok(bjex));
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobRestartException ex) {
            LOGGER.error(ex.getMessage());
            return new AsyncResult<ResponseEntity>(ResponseEntity.badRequest().body(ex));
        }
    }
    
//    @RequestMapping(value = "/launchAsyncJob", method = RequestMethod.GET)
//    @Async
//    public DeferredResult<ResponseEntity<?>> launchAsyncJob() {
//
//        final DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<ResponseEntity<?>>(5000l);
//        deferredResult.onTimeout(new Runnable() {
//
//            @Override
//            public void run() { // Retry on timeout
//                deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred."));
//            }
//        });
//        
//        final ListeningExecutorService executor = MoreExecutors.sameThreadExecutor();
////        final ListenableFuture<JobExecution> future = executor.submit(new Callable<JobExecution> {
////            Map<String, JobParameter> parameters = new HashMap<>();
////            parameters.put("currentTime", new JobParameter(new Date()));
////            JobExecution jex = jobLauncher.run(job, new JobParameters(parameters));     
////        });
//        
//        ListenableFuture<JobExecution> future = executor.submit(() -> {
//            Map<String, JobParameter> parameters = new HashMap<>();
//            parameters.put("currentTime", new JobParameter(new Date()));
//            JobExecution jex = jobLauncher.run(job, new JobParameters(parameters));            
//        });
//        
//        future.addCallback(new ListenableFutureCallback<JobExecution>() {
//            
//            @Override
//            public void onSuccess(JobExecution result) {
//                if (result.getExitStatus().getExitCode().equals("COMPLETED")) {
//                    BatchJobExecution bjex = SpringJobExecutionMapper.convert(result);
//                    deferredResult.setResult(ResponseEntity.ok(bjex));
//                } else {
//                    deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.CONFLICT).body(result.getFailureExceptions()));
//                }                
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                LOGGER.error(t.getMessage());
//                deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t));
//            }
//        });
//        return deferredResult;
//    }
    
}
