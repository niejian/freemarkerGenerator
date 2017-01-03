package cn.com.bluemoon.freemarker.model;

import java.io.Serializable;

public class ModelProperty implements Serializable {
	private static final long serialVersionUID = 1L;
	private String columnName;
	private String propertyName;
	private String columnType;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
}
