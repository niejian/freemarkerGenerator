package cn.com.bluemoon.freemarker.dbUtil.vo;

import org.springframework.util.StringUtils;

/**
 *<p> 数据表的单个字段与java实体类的隐射关系，主要包括  </p>
 * <p>1.java类属性名称，简单理解：java字段名</p>
 * <p>2.数据表属性名称；简单理解：数据表字名称</p>
 * <p>3.java字段类型（String, timestamp ....）； </p>
 * <p>4.数据表字段类型</p>
 * 
 * @author niejian
 *
 */
public class BeanProperty {
	/** java字段名 */
	private String javaColumnName;
	/** 数据表字名称 */
	private String columnName;
	/** 数据表字段类型 */
	private String columnType;
	/** 数据表字段类型 */
	private String javaType;
	
	public String getJavaColumnName() {
		return javaColumnName;
	}
	public void setJavaColumnName(String javaColumnName) {
		this.javaColumnName = javaColumnName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

}
