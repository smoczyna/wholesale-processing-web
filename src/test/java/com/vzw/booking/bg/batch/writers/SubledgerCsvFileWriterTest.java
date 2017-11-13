/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;
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
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({StepScopeTestExecutionListener.class})
@ContextConfiguration
public class SubledgerCsvFileWriterTest {
    
    private SubledgerCsvFileWriter writer;
    private String workingFoler;
    
    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        workingFoler = classLoader.getResource("./data").getPath();
        Logger.getLogger(SubledgerCsvFileWriterTest.class.getName()).info("Write path: "+workingFoler);
        writer = new SubledgerCsvFileWriter(workingFoler+"/subledger_summary.csv");
    }
    
    @Test
    public void testWriter() throws Exception {
        List<SummarySubLedgerDTO> list = new LinkedList();
        SummarySubLedgerDTO item = new SummarySubLedgerDTO();
        item.setFinancialCategory(123);
        item.setFinancialEventNumber(456789);
        item.setFinancialmarketId("Dublin");
        item.setSubledgerTotalCreditAmount(-1234.78);
        item.setSubledgerTotalDebitAmount(86654.89);
        list.add(item);
        
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        StepScopeTestUtils.doInStepScope(execution, () -> {
            try {
                writer.open(execution.getExecutionContext());
                writer.write(list);
                writer.close();
                verifyWrittenFile();
            } catch (Exception ex) {
                Logger.getLogger(SubledgerCsvFileWriterTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 1;
        });
    }
    
    private void verifyWrittenFile() throws IOException {
        File file = new File(workingFoler+"/subledger_summary.csv");
        assertNotNull(file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String delimiter = ProcessingUtils.decodeDelimiter(line);
        String[] parsed = line.split(delimiter);
        assertEquals(22, parsed.length);
    }
}
