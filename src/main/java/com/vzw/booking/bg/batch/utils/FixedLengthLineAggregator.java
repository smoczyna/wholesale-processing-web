/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.utils;

import com.vzw.booking.bg.batch.domain.Externalizable;
import com.vzw.booking.bg.batch.domain.ExternalizationMetadata;
import com.vzw.booking.bg.batch.domain.exceptions.ExternalizationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.LineAggregator;

/**
 *
 * @author smorcja
 * @param <T>
 */
public class FixedLengthLineAggregator<T extends Externalizable> implements LineAggregator<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedLengthLineAggregator.class);

    private ExternalizationMetadata metaData;

    public FixedLengthLineAggregator() {
        super();
    }

    public void setFormat(ExternalizationMetadata metaData) {
        this.metaData = metaData;
    }

    @Override
    public String aggregate(T t) {
        try {
            return t.dump(metaData);
        } catch (ExternalizationException e) {
            LOGGER.error("Unable to parse record : {} ", e.getMessage());
            return null;
        }
    }
}
