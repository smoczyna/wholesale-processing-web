/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.writers;

import com.vzw.booking.bg.batch.domain.AggregateWholesaleReportDTO;

/**
 *
 * @author smorcja
 */
public class WholesaleReportFixedLengthFileWriter extends FixedLengthGenericFileWriter<AggregateWholesaleReportDTO> {

    public WholesaleReportFixedLengthFileWriter(String filePath) {
        super(filePath);
    }
}
