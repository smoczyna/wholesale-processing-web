/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain;

/**
 * Booking Dates source payload class. This class represents the input file of booking dates: bookdate.csv
 * @author smorcja
 */
public class BookDateCsvFileDTO {
    private String rptPerStartDate;
    private String rptPerEndDate;
    private String transPerStartDate;
    private String transPerEndDate;
    private String monthEndCycle;

    public BookDateCsvFileDTO() {        
    }
    
    public BookDateCsvFileDTO(String rptPerEndDate, String transPerEndDate) {
        this.rptPerEndDate = rptPerEndDate;
        this.transPerEndDate = transPerEndDate;
    }

    public String getRptPerStartDate() {
        return rptPerStartDate;
    }

    public void setRptPerStartDate(String rptPerStartDate) {
        this.rptPerStartDate = rptPerStartDate;
    }

    public String getRptPerEndDate() {
        return rptPerEndDate;
    }

    public void setRptPerEndDate(String rptPerEndDate) {
        this.rptPerEndDate = rptPerEndDate;
    }

    public String getTransPerStartDate() {
        return transPerStartDate;
    }

    public void setTransPerStartDate(String transPerStartDate) {
        this.transPerStartDate = transPerStartDate;
    }

    public String getTransPerEndDate() {
        return transPerEndDate;
    }

    public void setTransPerEndDate(String transPerEndDate) {
        this.transPerEndDate = transPerEndDate;
    }

    public String getMonthEndCycle() {
        return monthEndCycle;
    }

    public void setMonthEndCycle(String monthEndCycle) {
        this.monthEndCycle = monthEndCycle;
    }

    @Override
    public String toString() {
        return "BookDateCsvFileDTO{" + "rptPerStartDate=" + rptPerStartDate 
                + ", rptPerEndDate=" + rptPerEndDate 
                + ", transPerStartDate=" + transPerStartDate 
                + ", transPerEndDate=" + transPerEndDate 
                + ", monthEndCycle=" + monthEndCycle + '}';
    }
    
}
