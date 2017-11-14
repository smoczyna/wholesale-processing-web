/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain.batch.mappers;

import com.vzw.booking.bg.batch.domain.batch.BatchJobInstance;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author smorcja
 */
public class BatchJobInstanceMapper implements RowMapper<BatchJobInstance> {
    
    @Override
    public BatchJobInstance mapRow(ResultSet rs, int rowNum) throws SQLException {
        BatchJobInstance result = new BatchJobInstance();
        result.setJobInstanceId(rs.getLong("JOB_INSTANCE_ID"));
        result.setJobName(rs.getString("JOB_NAME"));
        result.setJobKey(rs.getString("JOB_KEY"));
        result.setVersion(BigInteger.valueOf(rs.getLong("VERSION")));
        return result;
    }
}
