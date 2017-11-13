/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import static org.junit.Assert.assertNotNull;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
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

import com.vzw.booking.bg.batch.domain.AggregateWholesaleReportDTO;
import com.vzw.booking.bg.batch.domain.ExternalizationMetadata;
import com.vzw.booking.bg.batch.utils.ReflectionsUtility;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({StepScopeTestExecutionListener.class})
@ContextConfiguration
@PropertySource(value= {"classpath:*.properties"})
public class WholesaleReportFixedLengthFileWriterTest {
    
    
	private String wholeSaleFormat;
    private WholesaleReportFixedLengthFileWriter writer;
    private String workingFoler;

    public Properties getPropertyFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        Properties p = new Properties();
        p.load(classLoader.getResourceAsStream("application.properties"));
        return p;
    }
    
    @Before
    public void setUp() {
        ExternalizationMetadata wholesaleMetaData = null;
        try {
//        	getPropertyFile().save(System.out, "");
        	wholeSaleFormat = (String)getPropertyFile().getProperty("com.wzw.springbatch.processor.writer.format.wholesale");
			wholesaleMetaData = ReflectionsUtility.getParametersMap(AggregateWholesaleReportDTO.class, wholeSaleFormat);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Format="+wholeSaleFormat);
			System.exit(1);
		}
        ClassLoader classLoader = getClass().getClassLoader();
        workingFoler = classLoader.getResource("./data").getPath();
        LoggerFactory.getLogger(WholesaleReportFixedLengthFileWriterTest.class).info("Write path: {0}", workingFoler);
        writer = new WholesaleReportFixedLengthFileWriter(workingFoler+"/fixed_length_wholesale_report.txt");
        writer.setUpLineAggregator(wholesaleMetaData);
    }
    
    @Test
    public void testWriter() throws Exception {
        List<AggregateWholesaleReportDTO> report = new LinkedList();
        AggregateWholesaleReportDTO record = new AggregateWholesaleReportDTO();
        record.setBilledInd("Y");
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
                LoggerFactory.getLogger(WholesaleReportFixedLengthFileWriterTest.class).error(ex.getMessage());
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
        File file = new File(workingFoler+"/fixed_length_wholesale_report.txt");
        assertNotNull(file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        System.out.println(line);
    }
}
