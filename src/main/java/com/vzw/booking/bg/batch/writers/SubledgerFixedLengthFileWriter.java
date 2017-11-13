/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;

/**
 *
 * @author smorcja
 */
public class SubledgerFixedLengthFileWriter extends FixedLengthGenericFileWriter<SummarySubLedgerDTO> {
    
    public SubledgerFixedLengthFileWriter(String filePath) {
        super(filePath);
    }    
}
