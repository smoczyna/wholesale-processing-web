/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.readers;

import com.vzw.booking.bg.batch.domain.AltBookingCsvFileDTO;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({StepScopeTestExecutionListener.class})
@ContextConfiguration
public class AltBookingFileReaderTest {
    
    private AltBookingFileReader reader;
   
    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("./data/altbook.csv").getFile());
        if (file.exists())
            reader = new AltBookingFileReader(file.getAbsolutePath());
        else
            fail("Source file missing !!!");
    }

    @Test
    public void testReader() {
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        int count = 0;
        try {
            count = StepScopeTestUtils.doInStepScope(execution, () -> {
                reader.open(execution.getExecutionContext());
                AltBookingCsvFileDTO inputRecord;
                int readCount = 0;
                System.out.println("*** Input data ***");
                try {                    
                    while ((inputRecord = reader.read()) != null) {
                        assertNotNull(inputRecord);                        
                        System.out.println(inputRecord.toString());                        
                        readCount++;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(BookDateCsvFileReaderTest.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try { reader.close(); } catch (ItemStreamException e) { fail(e.toString());
                    }
                }
                System.out.println("*** End of data ***");
                return readCount;                
            });
        } catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(421, count);
    }
    
}
