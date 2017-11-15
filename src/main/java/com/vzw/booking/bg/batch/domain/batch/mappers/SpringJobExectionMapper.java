/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain.batch.mappers;

import com.vzw.booking.bg.batch.domain.batch.BatchJobExecution;
import com.vzw.booking.bg.batch.domain.batch.BatchJobExecutionParam;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;

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
        Set<BatchJobExecutionParam> params = null;
        if (jex.getJobParameters()!=null) {
            params = new HashSet();
            for (JobParameter param : jex.getJobParameters().getParameters().values()) {
                params.add(convert(jex.getId(), param));
            }
        }
        result.setBatchJobExecutionParams(params);
        return result;
    }
    
    public static BatchJobExecutionParam convert(Long jobExecutionId, JobParameter jobPar) {
        BatchJobExecutionParam param = new BatchJobExecutionParam();
        param.setJobExecutionId(jobExecutionId);
        param.setTypeCd(jobPar.getType().name());        
        switch (param.getTypeCd()) {
            case "DATE":
                param.setDateVal((Date) jobPar.getValue());
                break;
            case "STRING":
                param.setStringVal((String) jobPar.getValue());
                break;
            case "LONG":
                param.setLongVal((Long) jobPar.getValue());
                break;
            case "DOUBLE":
                param.setDoubleVal((Double) jobPar.getValue());
                break;
            default:
                param.setIdentifying("Unrecognized data type");
                break;
        }        
        return param;
    }
}
