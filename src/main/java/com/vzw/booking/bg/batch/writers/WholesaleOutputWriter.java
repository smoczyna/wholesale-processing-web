/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.vzw.booking.bg.batch.constants.Constants;
import com.vzw.booking.bg.batch.domain.AggregateWholesaleReportDTO;
import com.vzw.booking.bg.batch.domain.ExternalizationMetadata;
import com.vzw.booking.bg.batch.domain.SummarySubLedgerDTO;
import com.vzw.booking.bg.batch.domain.WholesaleProcessingOutput;
import com.vzw.booking.bg.batch.utils.ReflectionsUtility;

/**
 *
 * @author smorcja
 */
@Component
public class WholesaleOutputWriter implements ItemStreamWriter<WholesaleProcessingOutput> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WholesaleOutputWriter.class);

    private static final Map<Class<?>, ExternalizationMetadata> metaDataMap = new HashMap<>(0);

    private static final String PROPERTY_CSV_EXPORT_FILE_PATH = "database.to.csv.job.export.file.path";
    private static final String PROPERTY_WHOLESALE_FORMAT = "com.wzw.springbatch.processor.writer.format.wholesale";
    private static final String PROPERTY_SUBLEDGER_FORMAT = "com.wzw.springbatch.processor.writer.format.subledger";
    private final WholesaleReportFixedLengthFileWriter wholesaleReportWriter;
    private final SubledgerFixedLengthFileWriter subledgerWriter;

    public WholesaleOutputWriter(Environment environment, String fileNo) {
        String wholesaleFormat = environment.getRequiredProperty(PROPERTY_WHOLESALE_FORMAT);
        String subledgerFormat = environment.getRequiredProperty(PROPERTY_SUBLEDGER_FORMAT);
        ExternalizationMetadata wholesaleMetaData = null;
        ExternalizationMetadata subledgetMetaData = null;
        try {
            synchronized (metaDataMap) {
                if (!metaDataMap.containsKey(AggregateWholesaleReportDTO.class)) {
                    wholesaleMetaData = ReflectionsUtility.getParametersMap(AggregateWholesaleReportDTO.class, wholesaleFormat);
                    metaDataMap.put(AggregateWholesaleReportDTO.class, wholesaleMetaData);
                } else {
                    wholesaleMetaData = metaDataMap.get(AggregateWholesaleReportDTO.class);
                }
                if (!metaDataMap.containsKey(SummarySubLedgerDTO.class)) {
                    subledgetMetaData = ReflectionsUtility.getParametersMap(SummarySubLedgerDTO.class, subledgerFormat);
                    metaDataMap.put(AggregateWholesaleReportDTO.class, wholesaleMetaData);
                } else {
                    subledgetMetaData = metaDataMap.get(SummarySubLedgerDTO.class);
                }
            }
        } catch (Exception e) {
            LOGGER.error(Constants.WRITERS_CONFIG_FATAL_ERROR, e.getMessage());
            System.exit(1);
        }
        String filename = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat(Constants.WHOLESALE_REPORT_FILENAME_PATTERN).concat("_").concat(fileNo).concat(".csv");
        this.wholesaleReportWriter = new WholesaleReportFixedLengthFileWriter(filename);
        this.wholesaleReportWriter.setUpLineAggregator(wholesaleMetaData);
        this.wholesaleReportWriter.setTransactional(false);
        filename = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat(Constants.SUBLEDGER_SUMMARY_FILENAME_PATTERN).concat("_").concat(fileNo).concat(".csv");
        this.subledgerWriter = new SubledgerFixedLengthFileWriter(filename);
        this.subledgerWriter.setUpLineAggregator(subledgetMetaData);
        this.subledgerWriter.setTransactional(false);
    }

    @Override
    public void open(ExecutionContext ec) throws ItemStreamException {
        wholesaleReportWriter.open(ec);
        subledgerWriter.open(ec);
    }

    @Override
    public void update(ExecutionContext ec) throws ItemStreamException {
        wholesaleReportWriter.update(ec);
        subledgerWriter.update(ec);
    }

    @Override
    public void close() throws ItemStreamException {
        wholesaleReportWriter.close();
        subledgerWriter.close();
    }

    @Override
    public void write(List<? extends WholesaleProcessingOutput> list) throws Exception {
        for (WholesaleProcessingOutput item : list) {
            wholesaleReportWriter.write(item.getWholesaleReportRecords());
            subledgerWriter.write(item.getSubledgerRecords());
        }
    }
}
