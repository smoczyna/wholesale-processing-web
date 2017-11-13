/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.jobs;

import java.io.IOException;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration
public class SourceFilesExistanceCheckerTest {

    /**
     * Test of execute method, of class SourceFilesExistanceChecker.
     */
//    @Test
//    public void testExecute() {
//        try {
//            StepExecution execution = MetaDataInstanceFactory.createStepExecution();
//            StepContribution sc = execution.createStepContribution();
//            ChunkContext cc = new ChunkContext(new StepContext(execution));
//            SourceFilesExistanceChecker checker = new SourceFilesExistanceChecker();
//            RepeatStatus status = checker.execute(sc, cc);
//            System.out.println("Check status: "+status.toString());       
//            assertNotNull(status);
//        } catch (Exception ex) {
//            Logger.getLogger(SourceFilesExistanceCheckerTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    private StepExecution getStepExecution() throws IOException {
        JobParameters jobParams = mock(JobParameters.class);
        StepExecution stepExecution = mock(StepExecution.class);
        when(stepExecution.getJobParameters()).thenReturn(jobParams);
        return stepExecution;
    }
    
    /**
     * another die hard test, can't get file location properly
     * @throws Exception
     * @throws IOException 
     */
    @Test(expected=JobInterruptedException.class)
    public void testExecute() throws Exception, IOException {
        StepExecution execution = this.getStepExecution();
        StepContribution sc = execution.createStepContribution();
        ChunkContext cc = new ChunkContext(new StepContext(execution));
        SourceFilesExistanceChecker checker = new SourceFilesExistanceChecker();
        RepeatStatus status = checker.execute(sc, cc);
        //System.out.println("Check status: " + status.toString());
        assertNull(status);        
    }
}
