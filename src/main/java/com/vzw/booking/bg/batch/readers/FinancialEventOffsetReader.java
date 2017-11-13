/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.readers;

import com.vzw.booking.bg.batch.domain.FinancialEventOffsetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class FinancialEventOffsetReader extends CsvFileGenericReader<FinancialEventOffsetDTO> {
    private static final String PROPERTY_CSV_SOURCE_FILE_PATH = "csv.to.database.job.source.file.path";
    private static final String[] COLUMN_NAMES = new String[] {"financialEvent", "offsetFinancialCategory"};
    
    @Autowired
    public FinancialEventOffsetReader(Environment environment, String filename) {
        super(FinancialEventOffsetDTO.class, environment.getRequiredProperty(PROPERTY_CSV_SOURCE_FILE_PATH).concat(filename), COLUMN_NAMES, ",", 0);
    }
    
    public FinancialEventOffsetReader(String filePath, String delimiter) {
        super(FinancialEventOffsetDTO.class, filePath, COLUMN_NAMES, delimiter, 0);
    }
}
