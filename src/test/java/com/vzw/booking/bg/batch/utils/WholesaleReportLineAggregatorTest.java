/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.utils;

import com.vzw.booking.bg.batch.domain.AggregateWholesaleReportDTO;
import com.vzw.booking.bg.batch.domain.ExternalizationMetadata;
import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;
import com.vzw.booking.bg.batch.domain.exceptions.ContentTooLongException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({StepScopeTestExecutionListener.class})
@ContextConfiguration
@PropertySource(value= {"classpath:*.properties"})
public class WholesaleReportLineAggregatorTest {
    private FixedLengthLineAggregator<AggregateWholesaleReportDTO> lineAggregator=null;
	private @Value("${com.wzw.springbatch.processor.writer.format.wholesale}") String wholeSaleFormat;
	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public Properties getPropertyFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        Properties p = new Properties();
        p.load(classLoader.getResourceAsStream("application.properties"));
        return p;
    }
       
    @Before
    public void setUp() {
        ExternalizationMetadata metaData = null;
        try {
        	wholeSaleFormat = (String)getPropertyFile().getProperty("com.wzw.springbatch.processor.writer.format.wholesale");
        	metaData = ReflectionsUtility.getParametersMap(AggregateWholesaleReportDTO.class, wholeSaleFormat);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Format="+wholeSaleFormat);
			System.exit(1);
		}
        this.lineAggregator = new FixedLengthLineAggregator<>();
        this.lineAggregator.setFormat(metaData);
    }
    
    @Test
    public void aggregateTest() {
        System.out.println("Testing wholesale report line aggregator - success");
        
        AggregateWholesaleReportDTO record = new AggregateWholesaleReportDTO();
        record.setBilledInd("Y");
        record.setHomeFinancialMarketId("DUB");
        record.setCycleMonthYear("201709");
        record.setTollDollarsAmt(345.78);
        record.setProductDiscountOfferId(123);
        record.setUsage3G(123456L);
        record.setUsage4G(4566575L);
        record.setDbCrInd("DB");
        record.setStartDate(this.sdf.format(new Date()));
        
        String result = lineAggregator.aggregate(record);
        System.out.println(result);
        System.out.println("Line length: "+result.length());
        assertEquals(181, result.length());
    }
    
    @Test //(expected = ContentTooLongException.class)
    public void aggregateFailTest() {
        System.out.println("Testing wholesale report line aggregator - failure");
        
        AggregateWholesaleReportDTO record = new AggregateWholesaleReportDTO();
        record.setBilledInd("Y");
        record.setHomeFinancialMarketId("Dublin");
        record.setCycleMonthYear("201709");
        record.setTollDollarsAmt(345.78);
        record.setProductDiscountOfferId(123);
        record.setUsage3G(123456L);
        record.setUsage4G(4566575L);
        
        String result = lineAggregator.aggregate(record);
        assertTrue(result==null);
    }
}
