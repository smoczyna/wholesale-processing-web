package com.vzw.booking.bg.batch.validation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

/**
 *
 * @author smorcja
 */
public class CsvFileVerificationSkipper implements SkipPolicy {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileVerificationSkipper.class);

    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
        boolean result = false;
        if (exception instanceof FlatFileParseException) {
            FlatFileParseException ffpe = (FlatFileParseException) exception;
            LOGGER.error( ffpe.getMessage());
            result = true;
        } else if (exception instanceof NullPointerException) {
            result = true;
        }
        return result;
    }
}
