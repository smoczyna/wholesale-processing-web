/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.utils;

import com.vzw.booking.bg.batch.domain.ExternalizationMetadata;
import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource(value = {"classpath:*.properties"})
public class SubledgerLineAggregatorTest {

    private FixedLengthLineAggregator<SummarySubLedgerDTO> lineAggregator;
    private String subLedgerFormat;

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
            subLedgerFormat = (String) getPropertyFile().getProperty("com.wzw.springbatch.processor.writer.format.subledger");
            metaData = ReflectionsUtility.getParametersMap(SummarySubLedgerDTO.class, subLedgerFormat);
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException e) {            
            System.out.println("Format=" + subLedgerFormat);
        }
        this.lineAggregator = new FixedLengthLineAggregator();
        this.lineAggregator.setFormat(metaData);
    }

    @Test
    public void aggregateTest() {
        System.out.println("Testing subledger line aggregator - success");

        SummarySubLedgerDTO item = new SummarySubLedgerDTO();
        item.setFinancialEventNumber(456789);
        item.setFinancialmarketId("DUB");
        item.setFinancialCategory(789);
        item.setSubledgerTotalCreditAmount(-1234.78);
        item.setSubledgerTotalDebitAmount(86654.89);
        item.setUpdateUserId("WSBTest"); // default value is too long !!!

        String result = lineAggregator.aggregate(item);

        System.out.println(result);
        System.out.println("Line length: " + result.length());
        assertEquals(171, result.length()); //should it be 171 ??? Not 170
    }
}
