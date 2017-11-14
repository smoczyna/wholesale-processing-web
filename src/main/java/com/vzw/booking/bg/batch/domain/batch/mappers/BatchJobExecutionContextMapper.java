/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain.batch.mappers;

import com.vzw.booking.bg.batch.domain.batch.BatchJobExecutionContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author smorcja
 */
public class BatchJobExecutionContextMapper implements RowMapper<BatchJobExecutionContext> {
    
    @Override
    public BatchJobExecutionContext mapRow(ResultSet rs, int rowNum) throws SQLException {
        BatchJobExecutionContext result = new BatchJobExecutionContext();
        result.setJobExecutionId(rs.getLong("jobExecutionId"));
        result.setShortContext(rs.getString("shortContext"));
        result.setSerializedContext(rs.getString("serializedContext"));
        return result;
    }
}
