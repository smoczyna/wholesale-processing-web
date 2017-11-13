package com.vzw.booking.bg.batch.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>DateTimeUtils</h1> DateTimeUtils is a utility class that provides
 * convenience routines for date handling.
 * <p>
 */
public class ProcessingUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessingUtils.class);
    
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSZ";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String FILE_DATE_FORMAT = "MM/dd/yyyy";
    public static final String REALY_SHORT_FORMAT = "yyyyMM";
    public static final String SHORT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_DATETIME_FORMAT_NOSPACE = "yyyy-MM-dd-HHmmss";
    public static final String MAINFRAME_FORMAT = "yyyy-MM-dd-HH:mm:ss.SSSZ";
    public static final String DECIMAL_ROUND_FORMAT = "#.##";
    public static final String UNKNOWN_DELIMITER = "None of known delimiters found %s";    
    private static final String LBRACKET = "[";
    
    /**
     * formats zone date according to given format
     * @param zdt
     * @param format
     * @return 
     */
    public static String zdtToString(ZonedDateTime zdt, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(zdt);
    }

    /**
     * formats regular date according to given format
     * @param date
     * @param format
     * @return 
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    /**
     * Formats a ZonedDateTime into a standard string.
     *
     * @param zdt
     * @return
     */
    public static String toStringWithoutText(ZonedDateTime zdt) {
        String zdtOut = zdtToString(zdt, STANDARD_FORMAT);
        int bracketIndex = zdtOut.indexOf(LBRACKET);
        if (bracketIndex != -1) {
            zdtOut = zdtOut.substring(0, bracketIndex);
        }
        return zdtOut;
    }
    
    public static String getYearAndMonthFromStrDate(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FILE_DATE_FORMAT);
            Date date = sdf.parse(strDate);
            SimpleDateFormat sdf2 = new SimpleDateFormat(REALY_SHORT_FORMAT);
            return sdf2.format(date);
        } catch (ParseException ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }
    
    public static String dateTimeToStringWithourSpaces(Date date) {
        String strDate = dateToString(date, SHORT_DATETIME_FORMAT);
        return strDate.replace(" ", ".");
    }
    
    /**
     * analyzing the line of CSV input file to determine delimiter of the fields
     * @param line
     * @return 
     */
    public static String decodeDelimiter(String line) {
        String[] delimiters = {",", ";", "|", "¦"};
        for (String delimiter : delimiters) {
            if (line.contains(delimiter))
                return delimiter;
        }
        return String.format(UNKNOWN_DELIMITER, Arrays.toString(delimiters));
    }
    
    public static Double roundDecimalNumber(Double inputNumber) {
        DecimalFormat df = new DecimalFormat(DECIMAL_ROUND_FORMAT);
        return Double.valueOf(df.format(inputNumber));
    }
}
