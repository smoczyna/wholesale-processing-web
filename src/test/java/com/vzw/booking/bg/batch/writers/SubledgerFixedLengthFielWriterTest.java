/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vzw.booking.bg.batch.domain.ExternalizationMetadata;
import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;
import com.vzw.booking.bg.batch.utils.ReflectionsUtility;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({StepScopeTestExecutionListener.class})
@ContextConfiguration
@PropertySource(value= {"classpath:*.properties"})
public class SubledgerFixedLengthFielWriterTest {
    
	private @Value("${com.wzw.springbatch.processor.writer.format.subledger}") String subLedgerFormat;
    private SubledgerFixedLengthFileWriter writer;
    private String workingFoler;

    public Properties getPropertyFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        Properties p = new Properties();
        p.load(classLoader.getResourceAsStream("application.properties"));
        return p;
    }
   
    @Before
    public void setUp() {
        ExternalizationMetadata subledgetMetaData = null;
        try {
        	subLedgerFormat = (String)getPropertyFile().getProperty("com.wzw.springbatch.processor.writer.format.subledger");
			subledgetMetaData = ReflectionsUtility.getParametersMap(SummarySubLedgerDTO.class, subLedgerFormat);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Format="+subLedgerFormat);
			System.exit(1);
		}
        ClassLoader classLoader = getClass().getClassLoader();
        workingFoler = classLoader.getResource("./data").getPath();
        LoggerFactory.getLogger(SubledgerFixedLengthFielWriterTest.class).info("Write path: {0}", workingFoler);
        writer = new SubledgerFixedLengthFileWriter(workingFoler+"/fixed_length_subledger_report.txt");
        writer.setUpLineAggregator(subledgetMetaData);
    }
    
    @Test
    public void testWriter() throws Exception {
        List<SummarySubLedgerDTO> list = new LinkedList();
        SummarySubLedgerDTO item = new SummarySubLedgerDTO();
        item.setFinancialCategory(123);
        item.setFinancialEventNumber(456789);
        item.setFinancialmarketId("DUB");
        item.setSubledgerTotalCreditAmount(-1234.78);
        item.setSubledgerTotalDebitAmount(86654.89);
        item.setUpdateUserId("WSBTest1"); // default value is too long !!!
        list.add(item);
        
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        StepScopeTestUtils.doInStepScope(execution, () -> {
            try {
                writer.open(execution.getExecutionContext());
                writer.write(list);
                writer.close();
                verifyWrittenFile();
            } catch (Exception ex) {
            	LoggerFactory.getLogger(SubledgerFixedLengthFielWriterTest.class).error(ex.getMessage());
            }
            return 1;
        });
    }
    
    private void verifyWrittenFile() throws IOException, Exception {
        File file = new File(workingFoler+"/fixed_length_subledger_report.txt");
        assertNotNull(file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        assertTrue(line.length()==171); //It should be 171 and not 170
        // add above fields presence check 
    }
}
