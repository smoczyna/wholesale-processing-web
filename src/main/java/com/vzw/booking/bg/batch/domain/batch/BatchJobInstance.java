/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain.batch;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author smorcja
 */
@Entity
public class BatchJobInstance implements Serializable {

    @Id
    @Column(name="JOB_INSTANCE_ID")
    private Long jobInstanceId;
    
    @Column(name="VERSION")
    private BigInteger version;
    
    @Column(name="JOB_NAME")
    private String jobName;
    
    @Column(name="JOB_KEY")
    private String jobKey;
    
    public BatchJobInstance() {
    }

    public BatchJobInstance(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public BatchJobInstance(Long jobInstanceId, String jobName, String jobKey) {
        this.jobInstanceId = jobInstanceId;
        this.jobName = jobName;
        this.jobKey = jobKey;
    }

    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }
}
