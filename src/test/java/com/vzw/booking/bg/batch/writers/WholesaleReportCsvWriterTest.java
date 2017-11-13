/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import com.vzw.booking.bg.batch.domain.AggregateWholesaleReportDTO;
import com.vzw.booking.bg.batch.utils.ProcessingUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smoczyna
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({StepScopeTestExecutionListener.class})
@ContextConfiguration
public class WholesaleReportCsvWriterTest {

    private WholesaleReportCsvWriter writer;
    private String workingFoler;

    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        workingFoler = classLoader.getResource("./data").getPath();
        Logger.getLogger(WholesaleReportCsvWriterTest.class.getName()).info("Write path: "+workingFoler);
        writer = new WholesaleReportCsvWriter(workingFoler+"/wholesale_report.csv");
    }

    @Test
    public void testWriter() throws Exception {
        List<AggregateWholesaleReportDTO> report = new LinkedList();
        AggregateWholesaleReportDTO record = new AggregateWholesaleReportDTO();
        record.setBilledInd("ind1");
        record.setHomeFinancialMarketId("Dublin");
        record.setCycleMonthYear("201709");
        record.setTollDollarsAmt(345.78);
        record.setProductDiscountOfferId(123);
        record.setUsage3G(123456L);
        record.setUsage4G(4566575L);
        report.add(record);

        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        StepScopeTestUtils.doInStepScope(execution, () -> {
            try {
                writer.open(execution.getExecutionContext());
                writer.write(report);
                writer.close();
                verifyWrittenFile();
            } catch (Exception ex) {
                Logger.getLogger(WholesaleReportCsvWriterTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 1;
        });
    }

    /**
     * verify the result file:
     * - checking if it exists first
     * - reading the first line and splitting it by found delimiter 
     * - comparing the number of parsed fields with the AggregateWholesaleReportDTO java object
     * 
     * @throws IOException 
     */
    private void verifyWrittenFile() throws IOException {
        File file = new File(workingFoler+"/wholesale_report.csv");
        assertNotNull(file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String delimiter = ProcessingUtils.decodeDelimiter(line);
        String[] parsed = line.split(delimiter);
        assertEquals(21, parsed.length);
    }
}
