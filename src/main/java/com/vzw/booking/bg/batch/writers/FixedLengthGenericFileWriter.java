/*
 * To change this license header, choose License Headers
 in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;

import com.vzw.booking.bg.batch.domain.Externalizable;
import com.vzw.booking.bg.batch.domain.ExternalizationMetadata;
import com.vzw.booking.bg.batch.utils.FixedLengthLineAggregator;
/**
 *
 * @author smorcja
 * @param <T>
 */
public class FixedLengthGenericFileWriter<T extends Externalizable> extends FlatFileItemWriter<T> {
    private FixedLengthLineAggregator<T> aggregator;
    
    public FixedLengthGenericFileWriter(String fileName) {
        super.setAppendAllowed(true);
        this.setResource(new FileSystemResource(fileName));
        aggregator = new FixedLengthLineAggregator<T>();
        this.setLineAggregator(aggregator);
    }
    
    public void setUpLineAggregator(ExternalizationMetadata metaData) {
    	this.aggregator.setFormat(metaData);
    }
}
