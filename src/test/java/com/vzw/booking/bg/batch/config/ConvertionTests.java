/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.config;


import com.vzw.booking.bg.batch.domain.casandra.FinancialMarket;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author smorcja
 */
public class ConvertionTests {

    @Test
    public void testModelIntoSQLConvertion() {
        FinancialMarket fm = new FinancialMarket();
        fm.setSidbid("DUB");
        fm.setGlmarketid("IRL");
        fm.setGllegalentityid("Europe");

        Field[] fields = fm.getClass().getDeclaredFields();
        StringBuilder names = new StringBuilder("insert into " + fm.getClass().getSimpleName().toLowerCase() + "(");
        StringBuilder values = new StringBuilder(") values(");
        int i=0;
        for (Field field : fields) {
            i++;
            field.setAccessible(true);            
            String value = "";
            try {
                Object raw = field.get(fm);
                if (raw==null) continue;
                if (raw instanceof String)
                    value = value.concat("'").concat(raw.toString()).concat("'");
                else
                    value = value.concat(raw.toString());
                
                names.append(field.getName());
                values.append(value);
                
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ConvertionTests.class.getName()).log(Level.SEVERE, null, ex);
            }            
            if (fields.length>i) {
                names.append(",");
                values.append(",");
            }            
        }
        System.out.println("***");
        String query = names.toString().concat(values.toString()).concat(");");
        System.out.println(query);
        System.out.println("***");
    }
}

