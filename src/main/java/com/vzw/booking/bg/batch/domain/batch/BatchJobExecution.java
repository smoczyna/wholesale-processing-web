/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain.batch;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

/**
 *
 */
@Entity
public class BatchJobExecution implements Serializable {

    @Id
    @Column(name="JOB_EXECUTION_ID")
    private Long jobExecutionId;
    
    @Column(name="JOB_INSTANCE_ID")
    private Long jobInstanceId; 
    
    @Column(name="VERSION")
    private BigInteger version;
    
    @Column(name="CREATE_TIME")
    private Date createTime;
    
    @Column(name="START_TIME")
    private Date startTime;
    
    @Column(name="END_TIME")
    private Date endTime;
    
    @Column(name="STATUS")
    private String status;
    
    @Column(name="EXIT_CODE")
    private String exitCode;
    
    @Column(name="EXIT_MESSAGE")
    private String exitMessage;
    
    @Column(name="LAST_UPDATED")
    private Date lastUpdated;
    
    @Column(name="JOB_CONFIGURATION_LOCATION")
    private String jobConfigurationLocation;

    public BatchJobExecution() {
    }

    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public String getExitMessage() {
        return exitMessage;
    }

    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getJobConfigurationLocation() {
        return jobConfigurationLocation;
    }

    public void setJobConfigurationLocation(String jobConfigurationLocation) {
        this.jobConfigurationLocation = jobConfigurationLocation;
    }
}
