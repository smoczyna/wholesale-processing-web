/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.batch.utils;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author smorcja
 * @param <T>
 */
public class GenericSqlConverter<T> {
    private final Class paylaodClass;
    
    public GenericSqlConverter(Class clazz) {
        this.paylaodClass = clazz;
    }
    
    public String createQueryFromModel(T instance) {
        Field[] fields = paylaodClass.getDeclaredFields();
        StringBuilder names = new StringBuilder("insert into " + instance.getClass().getSimpleName().toLowerCase() + "(");
        StringBuilder values = new StringBuilder(") values(");
        int i=0;
        for (Field field : fields) {
            i++;
            field.setAccessible(true);            
            String value = "";
            try {
                Object raw = field.get(instance);
                if (raw==null) continue;
                if (raw instanceof String)
                    value = value.concat("'").concat(raw.toString()).concat("'");
                else
                    value = value.concat(raw.toString());
                
                names.append(field.getName());
                values.append(value);
                
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(GenericSqlConverter.class.getName()).log(Level.SEVERE, null, ex);
            }            
            if (fields.length>i) {
                names.append(",");
                values.append(",");
            }            
        }
        return names.toString().concat(values.toString()).concat(");");
    }
}
