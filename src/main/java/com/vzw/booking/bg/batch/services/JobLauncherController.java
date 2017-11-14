/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author smorcja
 */
@RestController
@RequestMapping("/WholesaleBooking")
public class JobLauncherController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherController.class);
    
    @Autowired
    JobLauncher jobLauncher;
    
    @Autowired
    Job job;
    
    @RequestMapping(value="/info", method = RequestMethod.GET)
    public ResponseEntity info() {
        return ResponseEntity.ok("Wholesale Booking Processing App - status: up and running");
    }
    
    @RequestMapping(value="/launchJob", method = RequestMethod.GET)
    public ResponseEntity launchJob() {
        try {
            Map<String, JobParameter> parameters = new HashMap<>();  
            parameters.put("currentTime", new JobParameter(new Date()));
            JobExecution jex = jobLauncher.run(job, new JobParameters(parameters));
            return ResponseEntity.accepted().body(jex);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.badRequest().body(ex);
        }
    }
}
