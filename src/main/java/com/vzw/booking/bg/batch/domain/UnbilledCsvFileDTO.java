/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain;

/**
 * Unbilled Bookings source payload class. This class represents the input file for billed bookings file: cmdunld.csv
 * @author smorcja
 */
public class UnbilledCsvFileDTO implements BaseBookingInputInterface {
    private String homeSbid;
    private String servingSbid;
    private String messageSource;
    private Integer airProdId;
    private Double wholesalePeakAirCharge;
    private Double wholesaleOffpeakAirCharge;
    private String source;
    private String financialMarket;
    private Long airBillSeconds;
    private Long totalWholesaleUsage;
    private String debitcreditindicator;

    @Override
    public String getHomeSbid() {
        return homeSbid;
    }

    public void setHomeSbid(String homeSbid) {
        this.homeSbid = homeSbid;
    }

    @Override
    public String getServingSbid() {
        return servingSbid;
    }

    public void setServingSbid(String servingSbid) {
        this.servingSbid = servingSbid;
    }

    @Override
    public String getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(String messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Integer getAirProdId() {
        return airProdId;
    }

    public void setAirProdId(Integer airProdId) {
        this.airProdId = airProdId;
    }

    @Override
    public Double getWholesalePeakAirCharge() {
        return wholesalePeakAirCharge;
    }

    public void setWholesalePeakAirCharge(Double wholesalePeakAirCharge) {
        this.wholesalePeakAirCharge = wholesalePeakAirCharge;
    }

    @Override
    public Double getWholesaleOffpeakAirCharge() {
        return wholesaleOffpeakAirCharge;
    }

    public void setWholesaleOffpeakAirCharge(Double wholesaleOffpeakAirCharge) {
        this.wholesaleOffpeakAirCharge = wholesaleOffpeakAirCharge;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getFinancialMarket() {
        return financialMarket;
    }

    public void setFinancialMarket(String financialMarket) {
        this.financialMarket = financialMarket;
    }

    @Override
    public Long getAirBillSeconds() {
        return airBillSeconds;
    }

    public void setAirBillSeconds(Long airBillSeconds) {
        this.airBillSeconds = airBillSeconds;
    }

    public Long getTotalWholesaleUsage() {
        return totalWholesaleUsage;
    }

    public void setTotalWholesaleUsage(Long totalWholesaleUsage) {
        this.totalWholesaleUsage = totalWholesaleUsage;
    }

    @Override
    public String getDebitcreditindicator() {
        return debitcreditindicator;
    }

    public void setDebitcreditindicator(String debitcreditindicator) {
        this.debitcreditindicator = debitcreditindicator;
    }

    @Override
    public String toString() {
        return "UnbilledCsvFileDTO{" 
                + "homeSbid=" + homeSbid 
                + ", servingSbid=" + servingSbid 
                + ", messageSource=" + messageSource 
                + ", airProdId=" + airProdId 
                + ", wholesalePeakAirCharge=" + wholesalePeakAirCharge 
                + ", wholesaleOffpeakAirCharge=" + wholesaleOffpeakAirCharge 
                + ", source=" + source 
                + ", financialMarket=" + financialMarket 
                + ", airBillSeconds=" + airBillSeconds 
                + ", totalWholesaleUsage=" + totalWholesaleUsage
                + ", debitcreditindicator=" + debitcreditindicator
                + '}';
    }
}
