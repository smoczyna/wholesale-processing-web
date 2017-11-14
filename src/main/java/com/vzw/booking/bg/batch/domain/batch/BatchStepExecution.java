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

/**
 *
 * @author smorcja
 */
@Entity
public class BatchStepExecution implements Serializable {

    @Id
    private Long stepExecutionId;
    private Long jobExecutionId;
    private long version;
    private String stepName;
    private Date startTime;
    private Date endTime;
    private String status;
    private BigInteger commitCount;
    private BigInteger readCount;
    private BigInteger filterCount;
    private BigInteger writeCount;
    private BigInteger readSkipCount;
    private BigInteger writeSkipCount;
    private BigInteger processSkipCount;
    private BigInteger rollbackCount;
    private String exitCode;
    private String exitMessage;
    private Date lastUpdated;

    public BatchStepExecution() {
    }

    public BatchStepExecution(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    public BatchStepExecution(Long stepExecutionId, long version, String stepName, Date startTime) {
        this.stepExecutionId = stepExecutionId;
        this.version = version;
        this.stepName = stepName;
        this.startTime = startTime;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public Long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
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

    public BigInteger getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(BigInteger commitCount) {
        this.commitCount = commitCount;
    }

    public BigInteger getReadCount() {
        return readCount;
    }

    public void setReadCount(BigInteger readCount) {
        this.readCount = readCount;
    }

    public BigInteger getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(BigInteger filterCount) {
        this.filterCount = filterCount;
    }

    public BigInteger getWriteCount() {
        return writeCount;
    }

    public void setWriteCount(BigInteger writeCount) {
        this.writeCount = writeCount;
    }

    public BigInteger getReadSkipCount() {
        return readSkipCount;
    }

    public void setReadSkipCount(BigInteger readSkipCount) {
        this.readSkipCount = readSkipCount;
    }

    public BigInteger getWriteSkipCount() {
        return writeSkipCount;
    }

    public void setWriteSkipCount(BigInteger writeSkipCount) {
        this.writeSkipCount = writeSkipCount;
    }

    public BigInteger getProcessSkipCount() {
        return processSkipCount;
    }

    public void setProcessSkipCount(BigInteger processSkipCount) {
        this.processSkipCount = processSkipCount;
    }

    public BigInteger getRollbackCount() {
        return rollbackCount;
    }

    public void setRollbackCount(BigInteger rollbackCount) {
        this.rollbackCount = rollbackCount;
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
}
