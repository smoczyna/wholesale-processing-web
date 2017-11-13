/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.processors;

import com.vzw.booking.bg.batch.domain.AltBookingCsvFileDTO;
import com.vzw.booking.bg.batch.utils.WholesaleBookingProcessorHelper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class AltBookingProcessor implements ItemProcessor<AltBookingCsvFileDTO, Boolean> {
    
    @Autowired
    private WholesaleBookingProcessorHelper wholesaleProcessingHelper;
    
    @Override
    public Boolean process(AltBookingCsvFileDTO altBook) throws Exception {
        this.wholesaleProcessingHelper.addAltBooking(altBook);
        return true;
    }
}
