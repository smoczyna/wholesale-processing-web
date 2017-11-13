/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 *
 * @author smoczyna
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
//@ContextConfiguration
public class AggregatedSubLedgerWriterTest {
    
//    @Mock
//    SubLedgerProcessor tempSubLedgerOuput;
//    
//    @InjectMocks
//    AggregatedSubLedgerWriter writer = new AggregatedSubLedgerWriter();
//    
//    private Set<SummarySubLedgerDTO> createMockSubLedgerSety() {
//        Set<SummarySubLedgerDTO> result = new HashSet();
//        SummarySubLedgerDTO subLedger = new SummarySubLedgerDTO();
//        subLedger.setSubledgerSequenceNumber(1);
//        
//        result.add(subLedger);
//        return result;
//    }
//        
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);                
//        when(tempSubLedgerOuput.getAggregatedOutput()).thenReturn(createMockSubLedgerSety());
//    }
//    
//
//    /**
//     * Test of execute method, of class AggregatedSubLedgerWriter.
//     */
//    @Test
//    public void testExecute() throws Exception {
//        System.out.println("execute");
//        StepContribution sc = null;
//        ChunkContext cc = null;
//        
//
//        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
//        StepScopeTestUtils.doInStepScope(execution, () -> {
//            execution.getExecutionContext()
//        }
//             
//        RepeatStatus expResult = RepeatStatus.FINISHED;
//        RepeatStatus result = writer.execute(sc, cc);
//        assertEquals(expResult, result);        
//    }
    
}
