/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.readers;

import com.vzw.booking.bg.batch.domain.AltBookingCsvFileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class AltBookingFileReader extends CsvFileGenericReader<AltBookingCsvFileDTO> {
    private static final String PROPERTY_CSV_SOURCE_FILE_PATH = "csv.to.database.job.source.file.path";
    private static final String[] COLUMN_NAMES = new String[] {"sbid", "altBookType", "glMarketId", "legalEntityId", "glMktMapTyp"};
    
    @Autowired
    public AltBookingFileReader(Environment environment, String filename) {
        super(AltBookingCsvFileDTO.class, environment.getRequiredProperty(PROPERTY_CSV_SOURCE_FILE_PATH).concat(filename), COLUMN_NAMES, ",", 0);
    }
    
    public AltBookingFileReader(String filePath) {
        super(AltBookingCsvFileDTO.class, filePath, COLUMN_NAMES, ",", 0);
    }
}
