/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.processors;

import com.vzw.booking.bg.batch.domain.WholesaleProcessingOutput;
import com.vzw.booking.bg.batch.utils.WholesaleBookingProcessorHelper;
import com.vzw.booking.bg.batch.config.CassandraQueryManager;
import com.vzw.booking.bg.batch.domain.AdminFeeCsvFileDTO;
import com.vzw.booking.bg.batch.domain.AggregateWholesaleReportDTO;
import com.vzw.booking.bg.batch.domain.BilledCsvFileDTO;
import com.vzw.booking.bg.batch.domain.BookDateCsvFileDTO;
import com.vzw.booking.bg.batch.domain.FinancialEventOffsetDTO;
import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;
import com.vzw.booking.bg.batch.domain.UnbilledCsvFileDTO;
import com.vzw.booking.bg.batch.domain.casandra.DataEvent;
import com.vzw.booking.bg.batch.domain.casandra.FinancialEventCategory;
import com.vzw.booking.bg.batch.domain.casandra.FinancialMarket;
import com.vzw.booking.bg.batch.domain.casandra.WholesalePrice;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.*;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 *
 * @author smoczyna
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class WholesaleReportProcessorTest {

    @Mock
    private WholesaleBookingProcessorHelper processHelper;

    @Mock
    private CassandraQueryManager queryManager;
      
    @Mock //@InjectMocks
    private WholesaleBookingProcessor wholesaleBookingProcessor;
    
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //when(CassandraQueryManager.getCassandraSession()).thenCallRealMethod();
        
        when(processHelper.addWholesaleReport()).thenReturn(new AggregateWholesaleReportDTO());
        when(processHelper.addSubledger()).thenReturn(new SummarySubLedgerDTO());
        when(processHelper.getDates()).thenReturn(this.createBookDateRecord());
        //when(processHelper.getFinancialEventOffset()).thenReturn(this.createFinancialEventOffset());
        
        //when(wholesaleBookingProcessor.getFinancialMarketFromDb(anyString())).thenReturn(this.createFinancialMarket());
        when(wholesaleBookingProcessor.getEventCategoryFromDb(anyInt(), anyString(), anyBoolean(), anyInt(), anyString())).thenReturn(this.createEventCategory(true));
        when(wholesaleBookingProcessor.getDataEventFromDb(anyInt())).thenReturn(this.createDataEvent(true));
        when(wholesaleBookingProcessor.getWholesalePriceFromDb(anyInt(), anyString())).thenReturn(this.createWholesalePrice());
    }

    private BookDateCsvFileDTO createBookDateRecord() {
        BookDateCsvFileDTO bookDates = new BookDateCsvFileDTO();
        bookDates.setRptPerStartDate("08/01/2017");
        bookDates.setRptPerEndDate("08/31/2017");
        bookDates.setTransPerStartDate("07/26/2017");
        bookDates.setTransPerEndDate("08/25/2017");
        return bookDates;
    }
    
    private BilledCsvFileDTO createBilledBookingsInputRecord() {
        BilledCsvFileDTO record = new BilledCsvFileDTO();
        record.setAirBillSeconds(1235L);
        record.setAirProdId(1);
        record.setAirSurcharge(123.45d);
        record.setAirSurchargeProductId(1);
        record.setDeviceType("mobile");
        record.setFinancialMarket("Ireland");
        record.setHomeSbid("dublin");
        record.setIncompleteCallSurcharge(45.67d);
        record.setIncompleteInd("2");
        record.setIncompleteProdId(1);
        record.setInterExchangeCarrierCode(1);
        record.setLocalAirTax(12.23d);
        record.setMessageSource("B");
        record.setServingSbid("dublin");
        record.setSpace("space");
        record.setStateAirTax(34.45d);
        record.setTollBillSeconds(123L);
        record.setTollProductId(1);
        record.setTollCharge(465.76d);
        record.setTollLocalTax(11.23d);
        record.setTollSurcharge(876.23d);
        record.setTollSurchargeProductId(2);
        record.setWholesaleOffpeakAirCharge(2345.67d);
        record.setWholesalePeakAirCharge(3456.78d);
        record.setWholesaleTollChargeLDOther(345.65d);
        record.setWholesaleTollChargeLDPeak(765.34d);
        record.setWholesaleUsageBytes(34567l);
        record.setDebitcreditindicator("DR");
        return record;
    }
    
    private UnbilledCsvFileDTO createUnbilledBookingsInputRecord() {
        UnbilledCsvFileDTO record = new UnbilledCsvFileDTO();
        record.setHomeSbid("athlone");
        record.setServingSbid("dublin");
        record.setAirBillSeconds(457687L);
        record.setAirProdId(100);
        record.setFinancialMarket("Ireland");
        record.setMessageSource("U");
        record.setSource("unit test");
        record.setTotalWholesaleUsage(34878L);
        record.setWholesaleOffpeakAirCharge(3465.87);
        record.setWholesalePeakAirCharge(464567.87);
        return record;
    }
    
    private Set<FinancialEventOffsetDTO> createFinancialEventOffset() {
        Set<FinancialEventOffsetDTO> items = new HashSet();
        FinancialEventOffsetDTO offset = new FinancialEventOffsetDTO();
        offset.setFinancialEvent(1);
        offset.setOffsetFinancialCategory(2);
        items.add(offset);
        return items;
    }
    
    private AdminFeeCsvFileDTO createAdminFeesBookingInputRecord() {
        AdminFeeCsvFileDTO record = new AdminFeeCsvFileDTO();
        record.setSbid("galway");
        record.setProductId(123);
        record.setFinancialMarket("Ireland");
        record.setAdminChargeAmt(34756.87);
        record.setAdminCount(7867);
        return record;
    }
    
//    protected FinancialMarket createFinancialMarket() {
//        FinancialMarket record = new FinancialMarket();
//        record.setSidbid("dublin");
//        record.setAlternatebookingtype("A");
//        record.setGllegalentityid("Ireland");
//        record.setGlmarketid("Leinster");
//        return record;
//    }
    
    protected FinancialEventCategory createEventCategory(boolean validForBooking) {
        FinancialEventCategory financialEventCategory = new FinancialEventCategory();
        financialEventCategory.setBamsaffiliateindicator("N");
        if (validForBooking) financialEventCategory.setCompanycode(" ");
        else financialEventCategory.setCompanycode("CDN");         // value here causes booking bypass for 'B' file
        financialEventCategory.setForeignservedindicator("Y");
        financialEventCategory.setHomesidequalsservingsidindicator("Y");
        financialEventCategory.setFinancialeventnormalsign("DR");
        financialEventCategory.setDebitcreditindicator("DR");
        financialEventCategory.setBillingaccrualindicator("Y");
        financialEventCategory.setFinancialeventnumber(4756);
        financialEventCategory.setFinancialcategory(678);
        financialEventCategory.setFinancialmarketid("FM1");
        financialEventCategory.setAlternatebookingindicator("Z");  
        financialEventCategory.setDebitcreditindicator("DB");
        return financialEventCategory;
    } 
    
    protected DataEvent createDataEvent(boolean is3G) {
        DataEvent event = new DataEvent();
        event.setProductid(100);
        if (is3G) event.setDataeventsubtype("DEFLT"); // 3G        
        else event.setDataeventsubtype("DEF4G"); // 4G
        return event;
    }
    
    protected WholesalePrice createWholesalePrice() {
        WholesalePrice wholesalePrice = new WholesalePrice();
        wholesalePrice.setProductwholesaleprice(new BigDecimal(351.45));
        return wholesalePrice;
    }
    
    /**
     * Test of process method, of class WholesaleReportProcessor.
     * All 3 methods return null because payload objects are incomplete or do not match the logic creating output records
     * @throws java.lang.Exception
     */
    
    @Test
    public void testBilledBookingProcess() throws Exception {
        BilledCsvFileDTO billedBookingRecord = createBilledBookingsInputRecord();
        WholesaleProcessingOutput result = wholesaleBookingProcessor.process(billedBookingRecord);
        //verify(tempSubLedgerOuput, times(1)).addSubledger();
        assertNull(result);
        //assertTrue(tempSubLedgerOuput.getAggregatedOutput().size()>0);
    }

    @Test
    public void testUnbilledBookingProcess() throws Exception {
        UnbilledCsvFileDTO unbilledBookingRecord = createUnbilledBookingsInputRecord();
        WholesaleProcessingOutput result = wholesaleBookingProcessor.process(unbilledBookingRecord);
        //verify(tempSubLedgerOuput, times(1)).addSubledger();
        assertNull(result);
    }
    
    @Test
    public void testAdmiFeesBookingProcess() throws Exception {
        AdminFeeCsvFileDTO adminFeesBookingRecord = createAdminFeesBookingInputRecord();
        WholesaleProcessingOutput result = wholesaleBookingProcessor.process(adminFeesBookingRecord);
        //verify(tempSubLedgerOuput, times(1)).addSubledger();
        assertNull(result);
    }
}
