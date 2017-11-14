/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain.batch.mappers;

import com.vzw.booking.bg.batch.domain.batch.BatchJobExecutionParams;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author smorcja
 */
public class BatchJobExecutionParamsMapper implements RowMapper<BatchJobExecutionParams> {
    
    @Override
    public BatchJobExecutionParams mapRow(ResultSet rs, int rowNum) throws SQLException {
        BatchJobExecutionParams result = new BatchJobExecutionParams();
        result.setJobExecutionId(rs.getLong("jobExecutionId"));
        result.setTypeCd(rs.getString("typeCd"));
        result.setKeyName(rs.getString("keyName"));
        result.setStringVal(rs.getString("stringVal"));
        result.setDateVal(rs.getDate("dateVal"));
        result.setLongVal(rs.getLong("longVal"));
        result.setDoubleVal(rs.getDouble("doubleVal"));
        result.setIdentifying(rs.getString("identifying"));        
        return result;
    }
}
