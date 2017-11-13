/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.listeners;

import com.vzw.booking.bg.batch.constants.Constants;
import com.vzw.booking.bg.batch.utils.WholesaleBookingProcessorHelper;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class GenericStepExecutionListener implements StepExecutionListener {

    @Autowired
    private WholesaleBookingProcessorHelper processingHelper;
    
    @Override
    public void beforeStep(StepExecution se) {
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        System.out.println(String.format(Constants.JOB_EXECUTION_FINISHED, se.getReadCount(), se.getWriteCount()));        
        System.out.println(String.format(Constants.WHOLESALE_REPORT_NO, this.processingHelper.getCounter(Constants.WHOLESALES_REPORT)));
        System.out.println(String.format(Constants.SUBLEDGER_REPORD_NO, this.processingHelper.getCounter(Constants.SUBLEDGER)));
        System.out.println(String.format(Constants.ZERO_CHARGE_NO, this.processingHelper.getCounter(Constants.ZERO_CHARGES)));
        System.out.println(String.format(Constants.CODE_GAPS_NO, this.processingHelper.getCounter(Constants.GAPS)));
        System.out.println(String.format(Constants.DATA_ERRORS_NO, this.processingHelper.getCounter(Constants.DATA_ERRORS)));
        System.out.println(String.format(Constants.BYPASS_NO, this.processingHelper.getCounter(Constants.BYPASS)));
        this.processingHelper.clearCounters();
        return se.getExitStatus();
    }

    
}
