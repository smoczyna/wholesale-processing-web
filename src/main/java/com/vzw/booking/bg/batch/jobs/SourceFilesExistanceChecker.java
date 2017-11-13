/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.jobs;

import com.vzw.booking.bg.batch.constants.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author smorcja
 */
public class SourceFilesExistanceChecker implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceFilesExistanceChecker.class);
    
    @Value("${csv.to.database.job.source.file.path}")
    private String SOURCE_FILES_PATH;
    
    @Value("${csv.to.database.job.source.file.splitSize}")
    private int SOURCE_FILES_SPLIT_SIZE;

    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
        LOGGER.info(Constants.CHECK_IF_FILES_EXIST);
        if (SOURCE_FILES_PATH==null || SOURCE_FILES_PATH.isEmpty())
            throw new JobInterruptedException(Constants.SOURCE_LOCATION_MISSING_MESSAGE);
            
        File f1 = new File(SOURCE_FILES_PATH.concat(Constants.BOOK_DATE_FILENAME));
        File f2 = new File(SOURCE_FILES_PATH.concat(Constants.FINANCIAL_EVENT_OFFSET_FILENAME));
        File f3 = new File(SOURCE_FILES_PATH.concat(Constants.ALT_BOOKING_FILENAME));
        File f4 = new File(SOURCE_FILES_PATH.concat(Constants.BILLED_BOOKING_FILENAME));
        File f5 = new File(SOURCE_FILES_PATH.concat(Constants.UNBILLED_BOOKING_FILENAME));
        File f6 = new File(SOURCE_FILES_PATH.concat(Constants.ADMIN_FEES_FILENAME));
        if ((!f1.exists() || f1.isDirectory()) ||
            (!f2.exists() || f2.isDirectory()) ||
            (!f3.exists() || f3.isDirectory()) ||
            (!f4.exists() || f4.isDirectory()) ||
            (!f5.exists() || f5.isDirectory()) ||
            (!f6.exists() || f6.isDirectory())) {
            LOGGER.error(Constants.FILES_NOT_FOUND_JOB_ABORTED);
            throw new JobInterruptedException(Constants.FILES_NOT_FOUND_MESSAGE);
        } else {
            splitTextFile(f4, SOURCE_FILES_SPLIT_SIZE);
            splitTextFile(f5, SOURCE_FILES_SPLIT_SIZE);
            splitTextFile(f6, SOURCE_FILES_SPLIT_SIZE);
            return RepeatStatus.FINISHED;
        }
    }
    
    public static void splitTextFile(File bigFile, int maxRows) throws IOException {
        int i = 1;
        String ext = FilenameUtils.getExtension(bigFile.getName());
        String fileNoExt = bigFile.getName().replace("."+ext, "");        
        File newDir = new File(bigFile.getParent() + "/" + fileNoExt + "_split");
        if (!newDir.exists()) newDir.mkdirs();        
        try (BufferedReader reader = Files.newBufferedReader(bigFile.toPath())) {
            String line;
            int lineNum = 1;
            Path splitFile = Paths.get(newDir.getPath() + "/" + fileNoExt + "_" + String.format("%03d", i) + "." + ext);
            BufferedWriter writer = Files.newBufferedWriter(splitFile, StandardOpenOption.CREATE);
            while ((line = reader.readLine()) != null) {
                writer.append(line);
                writer.newLine();
                lineNum++;
                if (lineNum > maxRows) {
                    writer.close();
                    lineNum = 1;
                    i++;
                    splitFile = Paths.get(newDir.getPath() + "/" + fileNoExt + "_" + String.format("%03d", i) + "." +  ext);
                    writer = Files.newBufferedWriter(splitFile, StandardOpenOption.CREATE);
                }
            }            
            writer.close();
        }
        LOGGER.info(String.format(Constants.FILE_SPLIT_MESSAGE, bigFile.getName(), i));
    }
}
