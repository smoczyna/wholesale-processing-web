/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain;

import org.springframework.stereotype.Component;

/**
 * Summary Sub Ledger internal payload class. 
 * This class represents the output file of the application.
 * @author smorcja
 */
@Component
public class SummarySubLedgerDTO implements Cloneable, Externalizable {
    
    private String jemsApplId = "BL";
    private String reportStartDate;
    private String jemsApplTransactioDate;
    private Integer financialEventNumber;
    private Integer financialCategory; 
    private String financialmarketId;
    private Integer subledgerSequenceNumber = 1;
    private Double subledgerTotalDebitAmount;
    private Double subledgerTotalCreditAmount;
    private String jurnalEventNumber;
    private String jurnalEventExceptionCode;
    private String jurnalEventReadInd = "N";
    private Integer generalLedgerTransactionNumber = 0;
    private String billCycleNumber = "00";
    private String billTypeCode = "C ";
    private String billCycleMonthYear;
    private String billPhaseType = "J6";
    private String billMonthInd = "Y";
    private String billAccrualIndicator = "N";
    private String paymentSourceCode;
    private Integer discountOfferId = 0;
    private String updateUserId = "WBApp";
    private String updateTimestamp;

    public String getJemsApplId() {
        return jemsApplId;
    }

    public void setJemsApplId(String jemsApplId) {
        this.jemsApplId = jemsApplId;
    }

    public String getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(String reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public String getJemsApplTransactioDate() {
        return jemsApplTransactioDate;
    }

    public void setJemsApplTransactioDate(String jemsApplTransactioDate) {
        this.jemsApplTransactioDate = jemsApplTransactioDate;
    }

    public Integer getFinancialEventNumber() {
        return financialEventNumber;
    }

    public void setFinancialEventNumber(Integer financialEventNumber) {
        this.financialEventNumber = financialEventNumber;
    }

    public Integer getFinancialCategory() {
        return financialCategory;
    }

    public void setFinancialCategory(Integer financialCategory) {
        this.financialCategory = financialCategory;
    }

    public String getFinancialmarketId() {
        return financialmarketId;
    }

    public void setFinancialmarketId(String financialmarketId) {
        this.financialmarketId = financialmarketId;
    }

    public Integer getSubledgerSequenceNumber() {
        return subledgerSequenceNumber;
    }

    public void setSubledgerSequenceNumber(Integer subledgerSequenceNumber) {
        this.subledgerSequenceNumber = subledgerSequenceNumber;
    }

    public Double getSubledgerTotalDebitAmount() {
        return subledgerTotalDebitAmount;
    }

    public void setSubledgerTotalDebitAmount(Double subledgerTotalDebitAmount) {
        this.subledgerTotalDebitAmount = subledgerTotalDebitAmount;
    }

    public Double getSubledgerTotalCreditAmount() {
        return subledgerTotalCreditAmount;
    }

    public void setSubledgerTotalCreditAmount(Double subledgerTotalCreditAmount) {
        this.subledgerTotalCreditAmount = subledgerTotalCreditAmount;
    }

    public String getJurnalEventNumber() {
        return jurnalEventNumber;
    }

    public void setJurnalEventNumber(String jurnalEventNumber) {
        this.jurnalEventNumber = jurnalEventNumber;
    }

    public String getJurnalEventExceptionCode() {
        return jurnalEventExceptionCode;
    }

    public void setJurnalEventExceptionCode(String jurnalEventExceptionCode) {
        this.jurnalEventExceptionCode = jurnalEventExceptionCode;
    }

    public String getJurnalEventReadInd() {
        return jurnalEventReadInd;
    }

    public void setJurnalEventReadInd(String jurnalEventReadInd) {
        this.jurnalEventReadInd = jurnalEventReadInd;
    }

    public Integer getGeneralLedgerTransactionNumber() {
        return generalLedgerTransactionNumber;
    }

    public void setGeneralLedgerTransactionNumber(Integer generalLedgerTransactionNumber) {
        this.generalLedgerTransactionNumber = generalLedgerTransactionNumber;
    }

    public String getBillCycleNumber() {
        return billCycleNumber;
    }

    public void setBillCycleNumber(String billCycleNumber) {
        this.billCycleNumber = billCycleNumber;
    }

    public String getBillTypeCode() {
        return billTypeCode;
    }

    public void setBillTypeCode(String billTypeCode) {
        this.billTypeCode = billTypeCode;
    }

    public String getBillCycleMonthYear() {
        return billCycleMonthYear;
    }

    public void setBillCycleMonthYear(String billCycleMonthYear) {
        this.billCycleMonthYear = billCycleMonthYear;
    }

    public String getBillPhaseType() {
        return billPhaseType;
    }

    public void setBillPhaseType(String billPhaseType) {
        this.billPhaseType = billPhaseType;
    }

    public String getBillMonthInd() {
        return billMonthInd;
    }

    public void setBillMonthInd(String billMonthInd) {
        this.billMonthInd = billMonthInd;
    }

    public String getBillAccrualIndicator() {
        return billAccrualIndicator;
    }

    public void setBillAccrualIndicator(String billAccrualIndicator) {
        this.billAccrualIndicator = billAccrualIndicator;
    }

    public String getPaymentSourceCode() {
        return paymentSourceCode;
    }

    public void setPaymentSourceCode(String paymentSourceCode) {
        this.paymentSourceCode = paymentSourceCode;
    }

    public Integer getDiscountOfferId() {
        return discountOfferId;
    }

    public void setDiscountOfferId(Integer discountOfferId) {
        this.discountOfferId = discountOfferId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
    
    @Override
    public SummarySubLedgerDTO clone() throws CloneNotSupportedException {
        return (SummarySubLedgerDTO) super.clone();
    }
}
