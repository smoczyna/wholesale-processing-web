/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain;

/**
 * Admin Fees source payload class. This class represents the input file for admin fees: MTNADFEE.CSV
 * @author smorcja
 */
public class AdminFeeCsvFileDTO implements MinBookingInterface {
    private String sbid;
    private Integer productId;
    private Double adminChargeAmt;
    private Integer adminCount;
    private String financialMarket;
    private String debitcreditindicator;

    public String getSbid() {
        return sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getAdminChargeAmt() {
        return adminChargeAmt;
    }

    public void setAdminChargeAmt(Double adminChargeAmt) {
        this.adminChargeAmt = adminChargeAmt;
    }

    public Integer getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(Integer adminCount) {
        this.adminCount = adminCount;
    }

    public String getFinancialMarket() {
        return financialMarket;
    }

    public void setFinancialMarket(String financialMarket) {
        this.financialMarket = financialMarket;
    }

    public String getDebitcreditindicator() {
        return debitcreditindicator;
    }

    public void setDebitcreditindicator(String debitcreditindicator) {
        this.debitcreditindicator = debitcreditindicator;
    }

    @Override
    public String toString() {
        return "AdminFeeCsvFileDTO{" 
                + "sbid=" + sbid 
                + ", productId=" + productId 
                + ", adminChargeAmt=" + adminChargeAmt 
                + ", adminCount=" + adminCount 
                + ", financialMarket=" + financialMarket 
                + ", debitcreditindicator=" + debitcreditindicator 
                + '}';
    }
    
}
