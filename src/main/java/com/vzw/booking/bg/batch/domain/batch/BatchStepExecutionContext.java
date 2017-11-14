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
public class BatchStepExecutionContext implements Serializable {

    @Id
    private Long stepExecutionId;
    private String shortContext;
    private String serializedContext;

    public BatchStepExecutionContext() {
    }

    public BatchStepExecutionContext(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    public BatchStepExecutionContext(Long stepExecutionId, String shortContext) {
        this.stepExecutionId = stepExecutionId;
        this.shortContext = shortContext;
    }

    public Long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
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
