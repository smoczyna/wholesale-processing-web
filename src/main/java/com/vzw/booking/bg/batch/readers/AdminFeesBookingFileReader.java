/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.readers;

import com.vzw.booking.bg.batch.domain.AdminFeeCsvFileDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class AdminFeesBookingFileReader extends CsvFileGenericReader<AdminFeeCsvFileDTO> {
    private static final String[] COLUMN_NAMES = new String[] {
        "sbid",
        "productId",
        "adminChargeAmt",
        "adminCount",
        "financialMarket",
        "debitcreditindicator"};
    
    @Autowired
    public AdminFeesBookingFileReader(String filename) {
        super(AdminFeeCsvFileDTO.class, filename, COLUMN_NAMES, ",", 0);
    }
    
    public AdminFeesBookingFileReader(String filePath, String delimiter) {
        super(AdminFeeCsvFileDTO.class, filePath, COLUMN_NAMES, delimiter, 0);
    }
}
