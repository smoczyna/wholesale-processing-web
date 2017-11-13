/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author smorcja
 */
public class WholesaleProcessingOutput {
    private final List<AggregateWholesaleReportDTO> wholesaleReportRecords;
    private final List<SummarySubLedgerDTO> subledgerRecords;
    
    public WholesaleProcessingOutput() {
        this.wholesaleReportRecords = new ArrayList();
        this.subledgerRecords = new ArrayList();
    }
    
    public List<AggregateWholesaleReportDTO> getWholesaleReportRecords() {
        return wholesaleReportRecords;
    }

    public List<SummarySubLedgerDTO> getSubledgerRecords() {
        return subledgerRecords;
    }
    
    public void addWholesaleReportRecord(AggregateWholesaleReportDTO wholesaleReportRecord) {
        this.wholesaleReportRecords.add(wholesaleReportRecord);
    }

    public void addSubledgerRecord(SummarySubLedgerDTO subledgerRecord) {
        this.subledgerRecords.add(subledgerRecord);
    }
}
