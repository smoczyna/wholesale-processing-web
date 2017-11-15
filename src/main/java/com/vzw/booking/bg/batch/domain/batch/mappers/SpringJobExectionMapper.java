/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain.batch.mappers;

import com.vzw.booking.bg.batch.domain.batch.BatchJobExecution;
import java.math.BigInteger;
import org.springframework.batch.core.JobExecution;

/**
 *
 * @author smorcja
 */
public class SpringJobExectionMapper {
    
    public static BatchJobExecution convert(JobExecution jex) {
        BatchJobExecution result = new BatchJobExecution();
        result.setJobInstanceId(jex.getJobId());
        result.setJobExecutionId(jex.getId());
        result.setCreateTime(jex.getCreateTime());
        result.setStartTime(jex.getStartTime());
        result.setEndTime(jex.getEndTime());
        result.setStatus(jex.getStatus().name());
        result.setExitCode(jex.getExitStatus().getExitCode());
        result.setExitMessage(jex.getExitStatus().getExitDescription());
        result.setLastUpdated(jex.getLastUpdated());
        result.setVersion(BigInteger.valueOf(jex.getVersion()));
        result.setJobConfigurationLocation(jex.getJobConfigurationName());
        return result;
    }
}
