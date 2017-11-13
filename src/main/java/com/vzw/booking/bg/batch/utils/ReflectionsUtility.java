/**
 *
 */
package com.vzw.booking.bg.batch.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.vzw.booking.bg.batch.constants.Constants;
import com.vzw.booking.bg.batch.constants.ExternalizationFormat;
import com.vzw.booking.bg.batch.domain.ExternalizationMetadata;
import com.vzw.booking.bg.batch.domain.exceptions.ContentTooLongException;
import com.vzw.booking.bg.batch.domain.exceptions.ExternalizationException;

/**
 * @author torelfa
 *
 */
public class ReflectionsUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionsUtility.class);

    public static final Method extractGetterMethod(Class<?> clazz, String parameter) throws NoSuchMethodException, SecurityException {
        return clazz.getDeclaredMethod("get" + ("" + parameter.charAt(0)).toUpperCase() + parameter.substring(1));
    }

    public static final ExternalizationMetadata getParametersMap(Class<?> clazz, String format) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
        LOGGER.info("FORMAT {}", format);
        ExternalizationMetadata metaData = new ExternalizationMetadata();
        String[] fieldsDatas = format.split("\\|");
        for (String fieldsData : fieldsDatas) {
            LOGGER.debug("Field {}", fieldsData);
            String[] data = fieldsData.split("#");
            LOGGER.debug("Field name {}", data[0]);
            LOGGER.debug("Field type {}", data[1]);
            Method m = extractGetterMethod(clazz, data[0]);
            if (data.length < 3) {
                //CSV file
                metaData.addFieldMetaData(new ExternalizationMetadata.FieldMetaData(m, Class.forName(data[1]), data[0], 0));
            } else {
                //Fixed length file
                LOGGER.debug("Field length {}", data[2]);
                metaData.setFormat(ExternalizationFormat.FIXED_LENGTH_FORMAT);
                metaData.addFieldMetaData(new ExternalizationMetadata.FieldMetaData(m, Class.forName(data[1]), data[0], Integer.parseInt(data[2]), 0));
            }
        }
        return metaData;
    }

    public static final String objectToString(Object value) throws ExternalizationException {
        if (value == null) {
            return "";
        } else if (String.class.isAssignableFrom(value.getClass())) {
            return value.toString();
        } else if (value instanceof BigInteger) {
            return String.valueOf((BigInteger) value);
        } else if (value instanceof BigDecimal) {
            return removeTrailingZeros((BigDecimal) value);
        } else if (value instanceof Double) {
            return String.format("%.2f", (Double) value);
        } else if (value instanceof Integer) {
            return value.toString();
        } else if (value instanceof Long) {
            return value.toString();
        } else if (value instanceof Short) {
            return value.toString();
        } else if (value instanceof Byte) {
            return value.toString();
        } else if (value instanceof Float) {
            return value.toString();
        }
        throw new ExternalizationException("Unrecognized field type: " + value.getClass().getName());
    }

    public static String removeTrailingZeros(BigDecimal tempDecimal) {
        tempDecimal = tempDecimal.stripTrailingZeros();
        String tempString = tempDecimal.toPlainString();
        return tempString;
    }

    public static final String objectToString(Object value, Class<?> clazz, char padChar, char otherPadChar, int length) throws ExternalizationException, ContentTooLongException {
        String valueStr = objectToString(value);
        if (valueStr.length() < length) {
            return Strings.padStart(valueStr, length, padChar);
        } else if (valueStr.length() > length) {
            throw new ContentTooLongException(String.format(Constants.CONTENTS_TOO_LONG, valueStr.length(), length));
        } else {
            return valueStr;
        }
    }
}
