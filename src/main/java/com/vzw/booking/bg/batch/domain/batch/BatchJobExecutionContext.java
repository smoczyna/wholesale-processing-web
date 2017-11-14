/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain.batch;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author smorcja
 */
@Entity
public class BatchJobExecutionContext implements Serializable {

    @Id
    private Long jobExecutionId;
    private String shortContext;
    private String serializedContext;

    public BatchJobExecutionContext() {
    }

    public BatchJobExecutionContext(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public BatchJobExecutionContext(Long jobExecutionId, String shortContext) {
        this.jobExecutionId = jobExecutionId;
        this.shortContext = shortContext;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public String getShortContext() {
        return shortContext;
    }

    public void setShortContext(String shortContext) {
        this.shortContext = shortContext;
    }

    public String getSerializedContext() {
        return serializedContext;
    }

    public void setSerializedContext(String serializedContext) {
        this.serializedContext = serializedContext;
    }
}
