/**
 * 
 */
package com.vzw.booking.bg.batch.domain;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vzw.booking.bg.batch.constants.ExternalizationFormat;

/**
 * @author torelfa
 *
 */
public class ExternalizationMetadata {
	
	private ExternalizationFormat format=ExternalizationFormat.COMMA_SEPARATED_FORMAT;
	
	private int counter=0;
	
	private List<FieldMetaData> metaDataList = new ArrayList<>(0);
	
	/**
	 * Constructor
	 */
	public ExternalizationMetadata(ExternalizationFormat format) {
		super();
		this.format=format;
	}
	
	/**
	 * Constructor
	 */
	public ExternalizationMetadata() {
		super();
	}

	
	/**
	 * Get Externalization Type
	 * @return the format
	 */
	public final ExternalizationFormat getFormat() {
		return format;
	}
	
	/**
	 * Get Externalization Type
	 * @return the format
	 */
	public final void setFormat(ExternalizationFormat format) {
		this.format=format;
	}

	public void addFieldMetaData(FieldMetaData metaData) {
		if (metaData!=null) {
			synchronized(this) {
				metaData.order = counter;
				counter++;
				this.metaDataList.add(metaData);
			}
		}
		Collections.sort(this.metaDataList, new FieldMetaDataComparator());
	}
	
	public List<FieldMetaData> getMetaData() {
		return this.metaDataList;
	}

	private static final class FieldMetaDataComparator implements Comparator<FieldMetaData> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(FieldMetaData o1, FieldMetaData o2) {
			return o1.order-o2.order;
		}
		
	}
	
	public static final class FieldMetaData {
		private Method getterMethod;
		private Class<?> outputType;
		private String fieldName;
		private int length;
		private int order;
		
		/**
		 * Constructor
		 * @param getterMethod
		 * @param outputType
		 * @param fieldName
		 * @param order
		 */
		public FieldMetaData(Method getterMethod, Class<?> outputType, String fieldName, int order) {
			super();
			this.getterMethod = getterMethod;
			this.outputType = outputType;
			this.fieldName = fieldName;
			this.order = order;
		}

		/**
		 * Constructor
		 * @param getterMethod
		 * @param outputType
		 * @param fieldName
		 * @param length
		 * @param order
		 */
		public FieldMetaData(Method getterMethod, Class<?> outputType, String fieldName, int length, int order) {
			super();
			this.getterMethod = getterMethod;
			this.outputType = outputType;
			this.fieldName = fieldName;
			this.length = length;
			this.order = order;
		}

		/**
		 * Get Method of type
		 * @return the getterMethod
		 */
		public Method getGetterMethod() {
			return getterMethod;
		}

		/**
		 * Get Getter Method Return type
		 * @return the outputType
		 */
		public Class<?> getOutputType() {
			return outputType;
		}

		/**
		 * Get Field name
		 * @return the fieldName
		 */
		public String getFieldName() {
			return fieldName;
		}

		/**
		 * Get Fixed Length size of Field
		 * @return the length
		 */
		public int getLength() {
			return length;
		}

		/**
		 * Get Order
		 * @return the order
		 */
		public int getOrder() {
			return order;
		}
		
	}
}
