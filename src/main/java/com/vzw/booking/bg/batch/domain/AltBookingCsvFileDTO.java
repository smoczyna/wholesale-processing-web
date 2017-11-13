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
public class AltBookingCsvFileDTO {
    private String sbid;
    private String altBookType;
    private String glMarketId;
    private String legalEntityId;
    private String glMktMapTyp;

    public String getSbid() {
        return sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid;
    }

    public String getAltBookType() {
        return altBookType;
    }

    public void setAltBookType(String altBookType) {
        this.altBookType = altBookType;
    }

    public String getGlMarketId() {
        return glMarketId;
    }

    public void setGlMarketId(String glMarketId) {
        this.glMarketId = glMarketId;
    }

    public String getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(String legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public String getGlMktMapTyp() {
        return glMktMapTyp;
    }

    public void setGlMktMapTyp(String glMktMapTyp) {
        this.glMktMapTyp = glMktMapTyp;
    }
    
}
