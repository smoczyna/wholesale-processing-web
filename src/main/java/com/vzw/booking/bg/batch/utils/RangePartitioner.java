/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 *
 * @author smorcja
 */
public class RangePartitioner implements Partitioner {

    private static final String PROPERTY_CSV_SOURCE_FILE_PATH = "csv.to.database.job.source.file.path";
    private final String resourceLocation;

    public RangePartitioner(Environment environment, String splitFolder) {
        this.resourceLocation = environment.getRequiredProperty(PROPERTY_CSV_SOURCE_FILE_PATH).concat(splitFolder);
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources;
        try {
            resources = resolver.getResources("file:" + resourceLocation.concat("*.csv"));
            for (Resource resource : resources) {
                ExecutionContext context = new ExecutionContext();
                String fileName = resource.getFilename();
                int fileNo=0;
                String fileNoStr = null;
                Pattern intsOnly = Pattern.compile("\\d+");
                Matcher makeMatch = intsOnly.matcher(fileName);
                if (makeMatch.find()) {
                    fileNoStr = makeMatch.group();
                    fileNo = Integer.parseInt(fileNoStr);                    
                }
                context.putString("sourceFileName", resourceLocation.concat(fileName));
                context.putString("destFileNo", fileNoStr);
                result.put("partition" + fileNo, context);
            }
        } catch (IOException ex) {
            Logger.getLogger(RangePartitioner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
