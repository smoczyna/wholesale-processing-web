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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    
    /**
     * this method is suppose to give an overview of endpoints served here 
     * @return 
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ResponseEntity getRestInfo() {
        //List response = new ArrayList();
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();
        
        return ResponseEntity.created(location).build();        
    }
    
    @RequestMapping(value="/jobInstances", method = RequestMethod.GET)
    public ResponseEntity<List<BatchJobInstance>> getJobInstances() {
        JdbcTemplate jdbc = new JdbcTemplate(metaDataSource);
        String sql = "SELECT * FROM BATCH_JOB_INSTANCE";
        List<BatchJobInstance> result = jdbc.query(sql, new BatchJobInstanceMapper());        
        return this.prepareResponse(result);
    }

    @RequestMapping(value="/findJobExecutions", method = RequestMethod.GET)
    public ResponseEntity<List<BatchJobExecution>> findJobExecution(@RequestParam(required=false) Long fromJobId, 
                                                                    @RequestParam(required=false) Long toJobId) {        
        JdbcTemplate jdbc = new JdbcTemplate(metaDataSource);
        Object[] callParams = null;
        String sql = "SELECT * FROM BATCH_JOB_EXECUTION ";
        if (fromJobId==null && toJobId!=null) {
            sql = sql.concat("WHERE JOB_INSTANCE_ID < ?");
            callParams = new Object[]{toJobId};
        }          
        else if (fromJobId!=null && toJobId==null) {
            sql = sql.concat("WHERE JOB_INSTANCE_ID > ?");
            callParams = new Object[]{fromJobId};
        }
        else if (fromJobId!=null && toJobId!=null) {
            sql = sql.concat("WHERE JOB_INSTANCE_ID BETWEEN ? AND ?");
            callParams = new Object[]{fromJobId, toJobId};
        }
        
        List<BatchJobExecution> result = jdbc.query(sql, callParams, new BatchJobExecutionMapper());
        return this.prepareResponse(result);
    }

    @RequestMapping(value="/jobExecutions/{jobId}", method = RequestMethod.GET)
    public ResponseEntity<List<BatchJobExecution>> getJobExecution(@PathVariable Long jobId) {        
        JdbcTemplate jdbc = new JdbcTemplate(metaDataSource);
        String sql = "SELECT * FROM BATCH_JOB_EXECUTION WHERE JOB_INSTANCE_ID = ?";        
        List<BatchJobExecution> result = jdbc.query(sql, new Object[]{jobId}, new BatchJobExecutionMapper());
        return this.prepareResponse(result);
    }
    
//    public ReponseEntity stopRunningJob(@RequstParam Long jobId) {
//        
//    }
}
