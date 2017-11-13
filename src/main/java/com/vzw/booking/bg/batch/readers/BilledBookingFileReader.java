/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.readers;

import com.vzw.booking.bg.batch.domain.BilledCsvFileDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class BilledBookingFileReader extends CsvFileGenericReader<BilledCsvFileDTO> {
    private static final String[] COLUMN_NAMES = new String[] {
            "homeSbid",
            "servingSbid",
            "messageSource",
            "incompleteInd",
            "airProdId",
            "incompleteProdId",
            "incompleteCallSurcharge",
            "airSurchargeProductId",
            "airSurcharge",
            "interExchangeCarrierCode",
            "tollProductId",
            "tollCharge",
            "tollSurchargeProductId",
            "tollSurcharge",
            "tollStateTax",
            "tollLocalTax",
            "localAirTax",
            "stateAirTax",
            "wholesalePeakAirCharge",
            "wholesaleOffPeakAirCharge",
            "wholesaleTollChargeLDPeak",
            "wholesaleTollChargeLDOther",
            "space",
            "financialMarket",
            "deviceType",
            "airBillSeconds",
            "tollBillSeconds",
            "wholesaleUsageBytes",
            "debitcreditindicator"};
    
    @Autowired
    public BilledBookingFileReader(String filePath) {
        super(BilledCsvFileDTO.class, filePath, COLUMN_NAMES, ",", 0);        
    }
    
    public BilledBookingFileReader(String filePath, String delimiter) {
        super(BilledCsvFileDTO.class, filePath, COLUMN_NAMES, delimiter, 0);
    }
}
