/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.domain;

/**
 *
 * @author smorcja
 */
public class FinancialEventOffsetDTO {
    private Integer financialEvent;
    private Integer offsetFinancialCategory;

    public Integer getFinancialEvent() {
        return financialEvent;
    }

    public void setFinancialEvent(Integer financialEvent) {
        this.financialEvent = financialEvent;
    }

    public Integer getOffsetFinancialCategory() {
        return offsetFinancialCategory;
    }

    public void setOffsetFinancialCategory(Integer offsetFinancialCategory) {
        this.offsetFinancialCategory = offsetFinancialCategory;
    }
}
