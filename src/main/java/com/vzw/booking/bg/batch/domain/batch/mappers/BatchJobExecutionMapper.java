/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain.batch.mappers;

import java.sql.ResultSet;
import com.vzw.booking.bg.batch.domain.batch.BatchJobExecution;
import java.math.BigInteger;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author smorcja
 */
public class BatchJobExecutionMapper implements RowMapper<BatchJobExecution> {
    
    @Override
    public BatchJobExecution mapRow(ResultSet rs, int rowNum) throws SQLException {
        BatchJobExecution result = new BatchJobExecution();
        result.setJobInstanceId(rs.getLong("JOB_INSTANCE_ID"));
        result.setJobExecutionId(rs.getLong("JOB_EXECUTION_ID"));
        result.setCreateTime(rs.getDate("CREATE_TIME"));
        result.setStartTime(rs.getDate("START_TIME"));
        result.setEndTime(rs.getDate("END_TIME"));
        result.setStatus(rs.getString("STATUS"));
        result.setExitCode(rs.getString("EXIT_CODE"));
        result.setExitMessage(rs.getString("EXIT_MESSAGE"));
        result.setLastUpdated(rs.getDate("LAST_UPDATED"));
        result.setVersion(BigInteger.valueOf(rs.getLong("VERSION")));
        result.setJobConfigurationLocation(rs.getString("JOB_CONFIGURATION_LOCATION"));
        return result;
    }
}
