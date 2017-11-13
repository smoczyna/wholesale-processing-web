/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.utils;

import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;
import com.vzw.booking.bg.batch.readers.CsvFileGenericReader;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class SubledgerFixedLengthFileReader extends CsvFileGenericReader<SummarySubLedgerDTO> {
    private static final String[] FIELDNAMES;
    private static final Range[] COLUMNS_FORMAT;
    static {
        FIELDNAMES = new String[]{
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
            "updateTimestamp"
        };
        COLUMNS_FORMAT = new Range[] {
            new Range(0, 1), 
            new Range(2, 11),
            new Range(12, 21),
            new Range(22, 31),
            new Range(32, 41),
            new Range(42, 44),
            new Range(45, 54),
            new Range(55, 68),
            new Range(69, 82),
            new Range(83, 91),
            new Range(92, 95),
            new Range(96, 96),
            new Range(97, 106),
            new Range(107, 108),
            new Range(109, 110),
            new Range(111, 116),
            new Range(117, 118),
            new Range(119, 119),
            new Range(120, 120),
            new Range(121, 125),
            new Range(126, 135),
            new Range(136, 143),
            new Range(144, 169)
        };
    }
    
    public SubledgerFixedLengthFileReader(String filename) {
        super(SummarySubLedgerDTO.class, filename, FIELDNAMES, null, 0);
    }
    
    @Override
    protected LineTokenizer createLineTokenizer(String[] fieldNames, String delimiter) {
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setColumns(COLUMNS_FORMAT);
        tokenizer.setNames(fieldNames);
        return tokenizer;
    }
}
