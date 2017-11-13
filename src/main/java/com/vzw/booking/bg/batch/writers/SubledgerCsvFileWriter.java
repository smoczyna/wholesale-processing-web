/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import com.vzw.booking.bg.batch.constants.Constants;
import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;

/**
 *
 * @author smorcja
 */
public class SubledgerCsvFileWriter extends CsvFileGenericWriter<SummarySubLedgerDTO> {
    private static final String[] COLUMN_NAMES = new String[] {
        "jemsApplId",
        "reportStartDate",
        "jemsApplTransactioDate",
        "financialEventNumber",
        "financialCategory", 
        "financialmarketId",
        "subledgerSequenceNumber",
        "subledgerTotalDebitAmount",
        "subledgerTotalCreditAmount",
        "jurnalEventNumber",
        "jurnalEventExceptionCode",
        "jurnalEventReadInd",
        "generalLedgerTransactionNumber",
        "billCycleNumber",
        "billTypeCode",
        "billCycleMonthYear",
        "billPhaseType",
        "billMonthInd",
        "billAccrualIndicator",
        "paymentSourceCode",
        "discountOfferId",
        "updateUserId",
        "updateTimestamp"};

    public SubledgerCsvFileWriter(String filename) {        
        super(filename, COLUMN_NAMES, Constants.DEFAULT_CSV_FIELDS_DELIMITER);
    }   
}
