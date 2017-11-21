/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.processors;

import com.vzw.booking.bg.batch.utils.WholesaleBookingProcessorHelper;
import com.vzw.booking.bg.batch.config.CassandraQueryManager;
import com.vzw.booking.bg.batch.constants.Constants;
import com.vzw.booking.bg.batch.domain.AdminFeeCsvFileDTO;
import com.vzw.booking.bg.batch.domain.AggregateWholesaleReportDTO;
import com.vzw.booking.bg.batch.domain.AltBookingCsvFileDTO;
import com.vzw.booking.bg.batch.domain.BilledCsvFileDTO;
import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;
import com.vzw.booking.bg.batch.domain.WholesaleProcessingOutput;
import com.vzw.booking.bg.batch.domain.BaseBookingInputInterface;
import com.vzw.booking.bg.batch.domain.MinBookingInterface;
import com.vzw.booking.bg.batch.domain.UnbilledCsvFileDTO;
import com.vzw.booking.bg.batch.domain.casandra.DataEvent;
import com.vzw.booking.bg.batch.domain.casandra.WholesalePrice;
import com.vzw.booking.bg.batch.domain.casandra.FinancialEventCategory;
import com.vzw.booking.bg.batch.domain.exceptions.CassandraQueryException;
import com.vzw.booking.bg.batch.domain.exceptions.MultipleRowsReturnedException;
import com.vzw.booking.bg.batch.domain.exceptions.NoResultsReturnedException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import com.vzw.booking.bg.batch.utils.ProcessingUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author smorcja
 * @param <T> - payload of the processor, it must be a class implementing
 * BaseBookingInputInterface or AdminFeeCsvFileDTO
 */
public class WholesaleBookingProcessor<T> implements ItemProcessor<T, WholesaleProcessingOutput> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WholesaleBookingProcessor.class);

    @Autowired
    private WholesaleBookingProcessorHelper processingHelper;

    @Autowired
    private CassandraQueryManager queryManager;

    String searchServingSbid;
    String searchHomeSbid;
    boolean homeEqualsServingSbid;
    String financialMarket;
    String fileSource;
    int tmpProdId;
    double tmpChargeAmt;
    int tmpInterExchangeCarrierCode;
    final Set<Integer> PROD_IDS_TOLL = new HashSet(Arrays.asList(new Integer[]{95, 12872, 12873, 36201}));
    final Set<Integer> PROD_IDS = new HashSet(Arrays.asList(new Integer[]{95, 12872, 12873, 13537, 13538, 36201}));

    /**
     * this method is a working version of booking logic, it is exact
     * representation of the spec and need to be tuned when finished
     *
     * @param inRec
     * @return
     * @throws Exception
     */
    @Override
    public WholesaleProcessingOutput process(T inRec) throws Exception {
        this.searchServingSbid = null;
        this.searchHomeSbid = null;
        this.financialMarket = "";
        this.homeEqualsServingSbid = false;
        this.fileSource = null;
        this.tmpProdId = 0;
        this.tmpChargeAmt = 0;
        this.tmpInterExchangeCarrierCode = 0;

        if (inRec instanceof BilledCsvFileDTO) {
            return processBilledRecord((BilledCsvFileDTO) inRec);
        } else if (inRec instanceof UnbilledCsvFileDTO) {
            return processUnbilledRecord((UnbilledCsvFileDTO) inRec);
        } else if (inRec instanceof AdminFeeCsvFileDTO) {
            return processAdminFeesRecord((AdminFeeCsvFileDTO) inRec);
        } else {
            return null;
        }
    }

    private SummarySubLedgerDTO createSubLedgerBooking(Double tmpChargeAmt, FinancialEventCategory financialEventCategory, String financialMarket, String dbcrIndicatorFromFile) {
        if (tmpChargeAmt == null || financialEventCategory == null || financialMarket == null) {
            return null;
        }
        SummarySubLedgerDTO subLedgerOutput = this.processingHelper.addSubledger();

        if (financialEventCategory.getFinancialeventnormalsign().equals(Constants.DEBIT_CODE)) {
            if (financialEventCategory.getDebitcreditindicator().equals(Constants.DEBIT_CODE)) {
                if (financialEventCategory.getBillingaccrualindicator().equals("Y") || dbcrIndicatorFromFile.equals("DR")) {
                    if (tmpChargeAmt > 0) {
                        subLedgerOutput.setSubledgerTotalDebitAmount(tmpChargeAmt);
                        subLedgerOutput.setSubledgerTotalCreditAmount(0d);
                    } else {
                        subLedgerOutput.setSubledgerTotalCreditAmount(tmpChargeAmt);
                        subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                    }
                } else if (financialEventCategory.getBillingaccrualindicator().equals("N")) {
                    if (tmpChargeAmt > 0) {
                        subLedgerOutput.setSubledgerTotalCreditAmount(tmpChargeAmt);
                        subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                    } else {
                        subLedgerOutput.setSubledgerTotalDebitAmount(tmpChargeAmt);
                        subLedgerOutput.setSubledgerTotalCreditAmount(0d);
                    }
                }
            }
        } else if (financialEventCategory.getFinancialeventnormalsign().equals(Constants.CREDIT_CODE)) {
            if (financialEventCategory.getDebitcreditindicator().equals(Constants.CREDIT_CODE)) {
                if (tmpChargeAmt > 0) {
                    subLedgerOutput.setSubledgerTotalCreditAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                } else {
                    subLedgerOutput.setSubledgerTotalDebitAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalCreditAmount(0d);
                }
            } else if (financialEventCategory.getDebitcreditindicator().equals(Constants.DEBIT_CODE)) {
                if (tmpChargeAmt > 0) {
                    subLedgerOutput.setSubledgerTotalDebitAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalCreditAmount(0d);
                } else {
                    subLedgerOutput.setSubledgerTotalCreditAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                }
            }
        }
        subLedgerOutput.setFinancialEventNumber(financialEventCategory.getFinancialeventnumber());
        if (this.fileSource.equals("M") && this.financialMarket.equals("003") && dbcrIndicatorFromFile.equals("CR"))
            subLedgerOutput.setFinancialCategory(677);
        else
            subLedgerOutput.setFinancialCategory(financialEventCategory.getFinancialcategory());
        
        subLedgerOutput.setFinancialmarketId(financialMarket);
        subLedgerOutput.setBillCycleMonthYear(ProcessingUtils.getYearAndMonthFromStrDate(this.processingHelper.getDates().getRptPerEndDate()));
        subLedgerOutput.setBillAccrualIndicator(financialEventCategory.getBillingaccrualindicator());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss.ssssss");
        subLedgerOutput.setUpdateTimestamp(sdf.format(new Date()));
        return subLedgerOutput;
    }

    private SummarySubLedgerDTO createOffsetBooking(SummarySubLedgerDTO subLedgerOutput) {
        SummarySubLedgerDTO clone = null;
        Integer offsetFinCat = this.processingHelper.findOffsetFinCat(subLedgerOutput.getFinancialEventNumber());
        if (offsetFinCat == null) {
            LOGGER.warn(Constants.OFFSET_NOT_FOUND);
        } else {
            Double debitAmt = subLedgerOutput.getSubledgerTotalDebitAmount();
            Double creditAmt = subLedgerOutput.getSubledgerTotalCreditAmount();

            if (debitAmt == 0d && creditAmt == 0d) {
                LOGGER.warn(Constants.ZERO_SUBLEDGER_AMOUNT);
            }

            try {
                clone = subLedgerOutput.clone();
            } catch (CloneNotSupportedException ex) {
                LOGGER.error(Constants.FAILED_TO_CREATE_OFFSET);
            }
            clone.setFinancialCategory(offsetFinCat);
            clone.setSubledgerTotalDebitAmount(creditAmt);
            clone.setSubledgerTotalCreditAmount(debitAmt);
        }
        this.processingHelper.incrementCounter(Constants.SUBLEDGER);
        return clone;
    }

    private boolean isAlternateBookingApplicable(BaseBookingInputInterface inRec) {
        boolean altBookingInd = false;
        String homeGlMarketId = " ";
        String homeLegalEntityId = " ";
        String servingGlMarketId = "";
        String servingLegalEntityId = "";

        searchHomeSbid = inRec.getHomeSbid();
        if (inRec.getServingSbid().trim().isEmpty()) {
            searchServingSbid = searchHomeSbid;
        } else {
            searchServingSbid = inRec.getServingSbid();
        }
        if (searchHomeSbid.equals(searchServingSbid)) {
            homeEqualsServingSbid = true;
        }        
        AltBookingCsvFileDTO altBooking = this.processingHelper.getAltBooking(searchHomeSbid);
        if (altBooking!=null && !homeEqualsServingSbid) {
            if (altBooking.getAltBookType().equals("P")) {
                homeLegalEntityId = altBooking.getLegalEntityId();
                altBooking = this.processingHelper.getAltBooking(searchServingSbid);
                if (altBooking.getAltBookType().equals("P"))
                    servingLegalEntityId = altBooking.getLegalEntityId();

                if (homeLegalEntityId.equals(servingLegalEntityId))
                    altBookingInd = true;
            }
            else if (altBooking.getAltBookType().equals("M")) {
                homeGlMarketId = altBooking.getGlMarketId();
                altBooking = this.processingHelper.getAltBooking(searchServingSbid);
                if (altBooking.getAltBookType().equals("M"))
                    servingGlMarketId = altBooking.getGlMarketId();

                if (homeGlMarketId.equals(servingGlMarketId))
                    altBookingInd = true;
            }
        }            
        return altBookingInd;
    }
    
    private boolean bypassBooking(FinancialEventCategory financialEventCategory, boolean altBookingInd) {
        boolean bypassBooking = false;

        if (!financialEventCategory.getBamsaffiliateindicator().equals("N") || !financialEventCategory.getCompanycode().trim().isEmpty()) {
            bypassBooking = true;
        }

        if (this.fileSource.equals("M") && financialEventCategory.getHomesidequalsservingsidindicator().trim().isEmpty()) {
            bypassBooking = false;
        }

        if (this.fileSource.equals("U") && financialEventCategory.getBillingaccrualindicator().equals("Y")) {
            bypassBooking = false;
        }

        if (this.fileSource.equals("B")) {
            if (financialEventCategory.getHomesidequalsservingsidindicator().equals("Y") && searchHomeSbid.equals(searchServingSbid)) {
                bypassBooking = false;
            }

            if (financialEventCategory.getHomesidequalsservingsidindicator().equals("N") && !searchHomeSbid.equals(searchServingSbid)) {
                if ((financialEventCategory.getAlternatebookingindicator().equals("N") && !altBookingInd)
                        || (financialEventCategory.getAlternatebookingindicator().equals("Y") && altBookingInd)) {
                    bypassBooking = false;
                }
            }
        }
        
        if (bypassBooking && (searchHomeSbid.equals(searchServingSbid))) {
            if (!financialEventCategory.getForeignservedindicator().trim().isEmpty() || financialEventCategory.getForeignservedindicator().equals("N")) {
                bypassBooking = true;
            }
        }
        return bypassBooking;
    }

    private void makeBookings(MinBookingInterface inRec, WholesaleProcessingOutput outRec, int iecCode) {
        boolean altBookingInd = false;
        String tmpHomeEqualsServingSbid = " ";
        if (inRec instanceof BilledCsvFileDTO) {
            altBookingInd = this.isAlternateBookingApplicable((BilledCsvFileDTO) inRec);
            tmpHomeEqualsServingSbid = this.homeEqualsServingSbid ? "Y" : "N";
        }
        FinancialEventCategory financialEventCategory = null;
        
        financialEventCategory = this.getEventCategoryFromDb(this.tmpProdId, tmpHomeEqualsServingSbid, altBookingInd, iecCode, inRec.getDebitcreditindicator());
        boolean bypassBooking = this.bypassBooking(financialEventCategory, altBookingInd);
        
        if (bypassBooking) {
            LOGGER.warn(Constants.BOOKING_BYPASS_DETECTED);
            this.processingHelper.incrementCounter(Constants.BYPASS);
        } else {
            if (this.tmpChargeAmt == 0) {
                LOGGER.warn(Constants.ZERO_CHARGES_DETECTED);
                this.processingHelper.incrementCounter(Constants.ZERO_CHARGES);
            } else {
                SummarySubLedgerDTO subledger = this.createSubLedgerBooking(this.tmpChargeAmt, financialEventCategory, this.financialMarket, inRec.getDebitcreditindicator());
                outRec.addSubledgerRecord(subledger);
                outRec.addSubledgerRecord(this.createOffsetBooking(subledger));
            }
        }
    }
    
    private WholesaleProcessingOutput processBilledRecord(BilledCsvFileDTO billedRec) {
        WholesaleProcessingOutput outRec = new WholesaleProcessingOutput();
        AggregateWholesaleReportDTO report = this.processingHelper.addWholesaleReport();
        
        boolean zeroAirCharge = false;
        boolean zeroTollCharge = false;

        report.setBilledInd("Y");
        this.fileSource = "B";

        if (billedRec.getFinancialMarket().trim().isEmpty()) {
            this.financialMarket = "HUB";
        } else {
            this.financialMarket = billedRec.getFinancialMarket();
        }

        if (billedRec.getWholesalePeakAirCharge() == 0 && billedRec.getWholesaleOffpeakAirCharge() == 0 && billedRec.getTollCharge() == 0) {
            LOGGER.warn(Constants.ZERO_CHARGES_SKIP);
            this.processingHelper.incrementCounter(Constants.ZERO_CHARGES);
            return null;
        }

        if (billedRec.getAirProdId() > 0 && (billedRec.getWholesalePeakAirCharge() > 0 || billedRec.getWholesaleOffpeakAirCharge() > 0)) {
            report.setPeakDollarAmt(billedRec.getWholesalePeakAirCharge());
            report.setOffpeakDollarAmt(billedRec.getWholesaleOffpeakAirCharge());
            report.setDollarAmtOther(0d);
            report.setVoiceMinutes(billedRec.getAirBillSeconds() / 60);
            tmpChargeAmt = ProcessingUtils.roundDecimalNumber(billedRec.getWholesalePeakAirCharge() + billedRec.getWholesaleOffpeakAirCharge());

            if (billedRec.getAirProdId().equals(190)) {
                this.tmpProdId = 1;
            } else {
                this.tmpProdId = billedRec.getAirProdId();
            }
            this.makeBookings(billedRec, outRec, tmpInterExchangeCarrierCode);
        } else {
            zeroAirCharge = true;
        }

        if (billedRec.getTollProductId() > 0 && billedRec.getTollCharge() > 0 && billedRec.getIncompleteInd().equals("D")) {
            if ((billedRec.getInterExchangeCarrierCode().equals(5050) && billedRec.getDebitcreditindicator().equals("CR"))
                    || billedRec.getIncompleteInd().equals("D")
                    || (!billedRec.getHomeSbid().equals(billedRec.getServingSbid()) && billedRec.getDebitcreditindicator().equals("CR"))
                    || (billedRec.getAirProdId().equals(190) && billedRec.getWholesaleTollChargeLDPeak() > 0 && billedRec.getWholesaleTollChargeLDOther() > 0)) {

                if (billedRec.getAirProdId().equals(190)) {
                    this.tmpProdId = 95;
                } else {
                    this.tmpProdId = billedRec.getTollProductId();
                }

                if (billedRec.getInterExchangeCarrierCode().equals(5050)) {
                    this.tmpChargeAmt = ProcessingUtils.roundDecimalNumber(billedRec.getTollCharge());
                    report.setDollarAmtOther(this.tmpChargeAmt);
                } else if (billedRec.getIncompleteInd().equals("D")) {
                    this.tmpChargeAmt = ProcessingUtils.roundDecimalNumber(billedRec.getTollCharge());
                    report.setDollarAmtOther(this.tmpChargeAmt);

                    DataEvent dataEvent = this.getDataEventFromDb(this.tmpProdId);

                    /* compute data usage */
                    if (dataEvent.getDataeventsubtype().equals("DEFLT")) {
                        report.setDollarAmt3G(this.tmpChargeAmt);
                        report.setUsage3G(Math.round(billedRec.getWholesaleUsageBytes().doubleValue() / 1024));
                    } else if (dataEvent.getDataeventsubtype().equals("DEF4G")) {
                        report.setDollarAmt4G(this.tmpChargeAmt);
                        report.setUsage4G(Math.round(billedRec.getWholesaleUsageBytes().doubleValue() / 1024));
                    }
                } else {
                    tmpChargeAmt = ProcessingUtils.roundDecimalNumber(billedRec.getWholesaleTollChargeLDPeak() + billedRec.getWholesaleTollChargeLDOther());
                    report.setTollDollarsAmt(this.tmpChargeAmt);
                    report.setTollMinutes(billedRec.getTollBillSeconds() / 60);
                }
                if (!billedRec.getInterExchangeCarrierCode().equals(5050)) {
                    tmpInterExchangeCarrierCode = 0;
                } else {
                    tmpInterExchangeCarrierCode = billedRec.getInterExchangeCarrierCode();
                }

                report.setPeakDollarAmt(0d);
                this.makeBookings(billedRec, outRec, tmpInterExchangeCarrierCode);
            } else {
                LOGGER.warn(Constants.GAP_DETECTED);
                this.processingHelper.incrementCounter(Constants.GAPS);
            }
        } else {
            zeroTollCharge = true;
        }

        if (zeroAirCharge && zeroTollCharge) {
            LOGGER.warn(Constants.INVALID_INPUT);
            this.processingHelper.incrementCounter(Constants.DATA_ERRORS);
            return null;
        }
        outRec.addWholesaleReportRecord(report);
        return outRec;
    }

    private WholesaleProcessingOutput processUnbilledRecord(UnbilledCsvFileDTO unbilledRec) {
        if (unbilledRec.getAirProdId() > 0 && (unbilledRec.getWholesalePeakAirCharge() > 0 || unbilledRec.getWholesaleOffpeakAirCharge() > 0)) {
            WholesaleProcessingOutput outRec = new WholesaleProcessingOutput();
            AggregateWholesaleReportDTO report = this.processingHelper.addWholesaleReport();
            
            report.setBilledInd("N");
            this.fileSource = "U";
            financialMarket = unbilledRec.getFinancialMarket();

            this.searchHomeSbid = unbilledRec.getHomeSbid();
            if (unbilledRec.getServingSbid().trim().isEmpty()) {
                this.searchServingSbid = unbilledRec.getHomeSbid();
            } else {
                this.searchServingSbid = unbilledRec.getServingSbid();
            }
            if (this.searchHomeSbid.equals(this.searchServingSbid)) {
                this.homeEqualsServingSbid = true;
            }
            if (unbilledRec.getAirProdId().equals(190)) {
                this.tmpProdId = 1;
            } else {
                this.tmpProdId = unbilledRec.getAirProdId();
            }
            this.tmpChargeAmt = ProcessingUtils.roundDecimalNumber(unbilledRec.getWholesalePeakAirCharge() + unbilledRec.getWholesaleOffpeakAirCharge());
            if (unbilledRec.getMessageSource().trim().isEmpty()) {
                report.setPeakDollarAmt(unbilledRec.getWholesalePeakAirCharge());
                report.setDollarAmtOther(0d);
                report.setVoiceMinutes(unbilledRec.getAirBillSeconds() / 60);
            } else if (unbilledRec.getMessageSource().equals("D")) {
                report.setDollarAmtOther(unbilledRec.getWholesalePeakAirCharge());
                report.setPeakDollarAmt(0d);
            }
            report.setOffpeakDollarAmt(unbilledRec.getWholesaleOffpeakAirCharge());

            if (unbilledRec.getSource().equals("D")) {
                DataEvent dataEvent = this.getDataEventFromDb(this.tmpProdId);

                if (dataEvent.getDataeventsubtype().equals("DEFLT")) {
                    report.setDollarAmt3G(this.tmpChargeAmt);
                    report.setUsage3G(Math.round(unbilledRec.getTotalWholesaleUsage().doubleValue() / 1024));
                } else if (dataEvent.getDataeventsubtype().equals("DEF4G")) {
                    report.setDollarAmt4G(this.tmpChargeAmt);
                    report.setUsage4G(Math.round(unbilledRec.getTotalWholesaleUsage().doubleValue() / 1024));
                }
            }
            outRec.addWholesaleReportRecord(report);
            this.makeBookings(unbilledRec, outRec, tmpInterExchangeCarrierCode);
            return outRec;
        } else {
            this.processingHelper.incrementCounter(Constants.ZERO_CHARGES);
            return null;
        }
    }

    private WholesaleProcessingOutput processAdminFeesRecord(AdminFeeCsvFileDTO adminFeesRec) {
        if (! adminFeesRec.getProductId().equals(19182)) {        
            WholesaleProcessingOutput outRec = new WholesaleProcessingOutput();
            AggregateWholesaleReportDTO report = this.processingHelper.addWholesaleReport();
            report.setBilledInd("Y");
            this.fileSource = "M";
            this.searchHomeSbid = adminFeesRec.getSbid();
            this.tmpProdId = adminFeesRec.getProductId();
            this.financialMarket = adminFeesRec.getFinancialMarket().isEmpty() ? "003" : adminFeesRec.getFinancialMarket();

            WholesalePrice wholesalePrice = this.getWholesalePriceFromDb(this.tmpProdId, this.searchHomeSbid);

            this.tmpChargeAmt = wholesalePrice.getProductwholesaleprice().multiply(BigDecimal.valueOf(adminFeesRec.getAdminCount())).floatValue();
            report.setDollarAmtOther(this.tmpChargeAmt);
            outRec.addWholesaleReportRecord(report);

            this.makeBookings(adminFeesRec, outRec, tmpInterExchangeCarrierCode);
        return outRec;
        } else {
            this.processingHelper.incrementCounter(Constants.GAPS);
            return null;
        }
    }

    protected FinancialEventCategory getEventCategoryFromDb(Integer tmpProdId, String homeEqualsServingSbid,
            boolean altBookingInd, int interExchangeCarrierCode, String financialeventnormalsign) {
        FinancialEventCategory result = null;
        List<FinancialEventCategory> dbResult;
        try {
            dbResult = queryManager.getFinancialEventCategoryNoClusteringRecord(
                    tmpProdId, homeEqualsServingSbid, altBookingInd ? "Y" : "N", interExchangeCarrierCode, financialeventnormalsign);

            // for admin fees without fin market value change fin cat to 677 (no suitable record in cassandra table)
            // this thing deosn't work, it affects other records too
//            if (this.fileSource.equals("M") && financialeventnormalsign.equals("CR") && this.financialMarket.equals("003"))
//                dbResult.get(0).setFinancialcategory(677);
            
        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "FinancialEventCategory", ex.getMessage()));
            dbResult = null;
        }
        if (dbResult == null && financialeventnormalsign.equals("DR")) {
            LOGGER.warn(Constants.FEC_NOT_FOUND_MESSAGE);
            if (this.fileSource.equals("M")) {  // for admin fees call 0 product and 1 as inter exchange code
                tmpProdId = 0;
                if (!this.financialMarket.equals("003"))
                    interExchangeCarrierCode = 1;                
            } else {
                tmpProdId = 36;                 // for the rest 2 files call 36 product
            }
            try {
                dbResult = queryManager.getFinancialEventCategoryNoClusteringRecord(
                        tmpProdId, homeEqualsServingSbid, altBookingInd ? "Y" : "N", interExchangeCarrierCode, financialeventnormalsign);

                LOGGER.warn(Constants.DEFAULT_FEC_OBTAINED);
            } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
                LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "FinancialEventCategory", ex.getMessage()));
                LOGGER.error(Constants.DEFAULT_FEC_NOT_FOUND);
            }
        }
        if (dbResult.size() == 1) {
            result = dbResult.get(0);
        }
        return result;
    }

    protected DataEvent getDataEventFromDb(Integer productId) {
        DataEvent result = null;
        try {
            List<DataEvent> dbResult = queryManager.getDataEventRecords(productId);
            if (dbResult.size() == 1) {
                result = dbResult.get(0);
            }
        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "DataEvent", ex.getMessage()));
        }
        return result;
    }

    protected WholesalePrice getWholesalePriceFromDb(Integer tmpProdId, String searchHomeSbid) {
        WholesalePrice result = null;
        List<WholesalePrice> dbResult;
        try {
            dbResult = queryManager.getWholesalePriceRecords(tmpProdId, searchHomeSbid);
            
        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "WholesalePrice", ex.getMessage()));
            dbResult = null;
        }
        if (dbResult==null) {
            LOGGER.warn(Constants.WHOLESALE_PRICE_NOT_FOUND);
            try {
                dbResult = queryManager.getWholesalePriceRecords(tmpProdId, "00000");
                LOGGER.warn(Constants.DEFAULT_WP_OBTAINED);
            } catch (CassandraQueryException | MultipleRowsReturnedException | NoResultsReturnedException ex) {
                LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "WholesalePrice", ex.getMessage()));
                LOGGER.error(Constants.DEFAULT_WP_NOT_FOUND);
            }
        }
        if (dbResult.size() == 1) {
            result = dbResult.get(0);
        }
        return result;
    }
}
