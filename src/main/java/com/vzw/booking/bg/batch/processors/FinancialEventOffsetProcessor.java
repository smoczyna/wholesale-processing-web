/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.processors;

import com.vzw.booking.bg.batch.utils.WholesaleBookingProcessorHelper;
import com.vzw.booking.bg.batch.domain.FinancialEventOffsetDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class FinancialEventOffsetProcessor implements ItemProcessor<FinancialEventOffsetDTO, Boolean> {
    
    @Autowired
    private WholesaleBookingProcessorHelper tempSubLedgerOuput;
    
    @Override
    public Boolean process(FinancialEventOffsetDTO offset) throws Exception {
        return this.tempSubLedgerOuput.addOffset(offset);
    }
}
