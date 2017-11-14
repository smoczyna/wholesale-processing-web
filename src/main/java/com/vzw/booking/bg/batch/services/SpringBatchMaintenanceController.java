/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.services;

import com.vzw.booking.bg.batch.domain.batch.BatchJobExecution;
import com.vzw.booking.bg.batch.domain.batch.BatchJobInstance;
import com.vzw.booking.bg.batch.domain.batch.mappers.BatchJobExecutionMapper;
import com.vzw.booking.bg.batch.domain.batch.mappers.BatchJobInstanceMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author smorcja
 */
@RestController
@RequestMapping("/SpringBatchMaintenance")
public class SpringBatchMaintenanceController {
    //private static final Logger LOGGER = LoggerFactory.getLogger(SpringBatchMaintenanceController.class);
    
    @Autowired
    private DataSource metaDataSource;

    private ResponseEntity prepareResponse(List payload) {
        if (payload==null || payload.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.accepted().body(payload);
    }
    
    @RequestMapping(value="/jobInstances", method = RequestMethod.GET)
    public ResponseEntity<List<BatchJobInstance>> getJobInstances() {
        JdbcTemplate jdbc = new JdbcTemplate(metaDataSource);
        String sql = "SELECT * FROM BATCH_JOB_INSTANCE";
        List<BatchJobInstance> result = jdbc.query(sql, new BatchJobInstanceMapper());        
        return this.prepareResponse(result);
    }

    @RequestMapping(value="/jobExecutions/{job}", method = RequestMethod.GET)
    public ResponseEntity<List<BatchJobExecution>> getJobExecutions(@PathVariable Long jobInstanceId) {        
        JdbcTemplate jdbc = new JdbcTemplate(metaDataSource);
        String sql = "SELECT * FROM BATCH_JOB_EXECUTION WHERE JOB_INSTANCE_ID = ?";        
        List<BatchJobExecution> result = jdbc.query(sql, new Object[]{jobInstanceId}, new BatchJobExecutionMapper());
        return this.prepareResponse(result);
    }

}
