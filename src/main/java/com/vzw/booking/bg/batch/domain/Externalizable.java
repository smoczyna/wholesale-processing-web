package com.vzw.booking.bg.batch.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.google.common.base.Strings;
import com.vzw.booking.bg.batch.constants.ExternalizationFormat;
import com.vzw.booking.bg.batch.domain.ExternalizationMetadata.FieldMetaData;
import com.vzw.booking.bg.batch.domain.exceptions.ExternalizationException;
import com.vzw.booking.bg.batch.utils.ReflectionsUtility;

public interface Externalizable {

	/**
	 * Produce appropriate output, according to fields and required output format.
	 * @param metadata MetaData used for parsing output
	 * @return output value for externalization
	 */
	default String dump(ExternalizationMetadata metadata) throws ExternalizationException {
		String line = "";
		String currentField="[none]"; 
		try {
			for (FieldMetaData meta: metadata.getMetaData()) {
				currentField = meta.getFieldName();
					if ( metadata.getFormat() == ExternalizationFormat.COMMA_SEPARATED_FORMAT ) {
						line += (line.length()==0 ? "" : ",")  + ReflectionsUtility.objectToString(meta.getGetterMethod().invoke(this));
					} else {
						line += ReflectionsUtility.objectToString(meta.getGetterMethod().invoke(this), meta.getOutputType(), '0', ' ', meta.getLength());
					}
			}
		} catch (Exception e) {
			try {
				throw new ExternalizationException("Error merging line <field: "+currentField+"> : " + asStringDescriptor(metadata) + " => " + e.getMessage());
			} catch (Exception e1) {
				throw new ExternalizationException("Error merging line <field: "+currentField+"> : <NOT PARSABLE> => " + e.getMessage());
			}
		}
		return line;
	}

	default String asStringDescriptor(ExternalizationMetadata metadata) throws ExternalizationException {
		String line = "";
		try {
			for (FieldMetaData meta: metadata.getMetaData()) {
				if (metadata.getFormat()==ExternalizationFormat.COMMA_SEPARATED_FORMAT)
					line += (line.length()==0 ? "" : "|")  + ReflectionsUtility.objectToString(meta.getGetterMethod().invoke(this));
				else
					line += (line.length()==0 ? "" : "|")  + ReflectionsUtility.objectToString(meta.getGetterMethod().invoke(this)) + "#" + meta.getLength();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExternalizationException("Error collating line => " + e.getMessage());
		}
		return line;
	}
}
