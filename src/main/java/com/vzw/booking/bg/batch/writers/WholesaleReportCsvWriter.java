/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import com.vzw.booking.bg.batch.constants.Constants;
import com.vzw.booking.bg.batch.domain.AggregateWholesaleReportDTO;

/**
 *
 * @author smorcja
 */
public class WholesaleReportCsvWriter extends CsvFileGenericWriter<AggregateWholesaleReportDTO> {
    private static final String[] COLUMN_NAMES = new String[] {
        "cycleMonthYear",
        "startDate",
        "endDate",
        "homeLegalEntity",
        "servingLegalEntity",
        "homeFinancialMarketId",
        "servingFinancialMarketId",
        "productDiscountOfferId",
        "contractTermId",
        "peakDollarAmt",
        "offpeakDollarAmt",
        "voiceMinutes",
        "tollDollarsAmt",
        "tollMinutes",
        "dollarAmt3G",
        "usage3G",
        "dollarAmt4G",
        "usage4G",
        "dollarAmtOther",
        "dbCrInd",
        "billedInd"};

    public WholesaleReportCsvWriter(String filename) {
        super(filename, COLUMN_NAMES, Constants.DEFAULT_CSV_FIELDS_DELIMITER);
    }
}