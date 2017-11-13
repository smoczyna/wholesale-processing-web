/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.readers;

import com.vzw.booking.bg.batch.domain.UnbilledCsvFileDTO;
import java.io.File;
import org.junit.Before;

/**
 *
 * @author smoczyna
 */
public class UnbilledBookingFileReaderTest extends GenericCsvFileReaderTest<UnbilledCsvFileDTO> {

    private UnbilledBookingFileReader reader;
    private final String delimiter = ",";
    
    @Before
    public void setUp() {
        this.numberOfRecords = 50;
    }
    
    @Override
    public CsvFileGenericReader getReader() {
        if (this.reader==null) {
            this.reader = new UnbilledBookingFileReader(this.getFile().getAbsolutePath(), this.delimiter);
        }        
        return this.reader;
    }

    @Override
    public File getFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("./data/unbilled.csv").getFile());
        return file;
    }
}
