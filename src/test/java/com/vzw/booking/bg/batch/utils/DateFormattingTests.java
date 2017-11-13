/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.utils;

import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Test;

/**
 *
 * @author smorcja
 */
public class DateFormattingTests {
    
    @Test
    public void testZoneDateFormatting() {
        System.out.println("Date time formatting tests");
        Date date1 = new Date();
        System.out.println("java.util.Date formatting:");
        System.out.println(ProcessingUtils.dateTimeToStringWithourSpaces(date1) + "");
        System.out.println(ProcessingUtils.dateToString(date1, ProcessingUtils.STANDARD_FORMAT));
        System.out.println(ProcessingUtils.dateTimeToStringWithourSpaces(date1) + "");
        
        System.out.println("ZonedDateTime formatting:");
        ZonedDateTime date2 = ZonedDateTime.now();
        System.out.println(ProcessingUtils.zdtToString(date2, ProcessingUtils.STANDARD_FORMAT));
        System.out.println(ProcessingUtils.toStringWithoutText(date2) + "");
        System.out.println(ProcessingUtils.zdtToString(date2, ProcessingUtils.MAINFRAME_FORMAT));
    }
}
