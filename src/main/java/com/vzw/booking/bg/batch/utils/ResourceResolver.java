/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.utils;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

/**
 *
 * @author smorcja
 */
public class ResourceResolver {
    private ResourceLoader resourceLoader;

    @Autowired
    public ResourceResolver(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource[] loadResources(String location) throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(location);
    }
}
