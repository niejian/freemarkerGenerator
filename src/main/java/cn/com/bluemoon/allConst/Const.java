package cn.com.bluemoon.allConst;

import java.text.SimpleDateFormat;

public class Const {
	/***
	 * java文件的基础包名
	 */
	public static final String BASE_PACKAGE_NAME = "cn.com.bluemoon";
	public static final String TABLE_COLUMN_SEPERATE = "_";
	/***
	 * java 文件生成路径
	 */
	public static final String BASE_JAVA_FILE_PATH = "\\src\\main\\java\\cn\\com\\bluemoon";
	
	/**
	 * 根据数据表来生成对应的java文件
	 */
	public static final String SQL_TABLE = "table";
	
	/**
	 * 根据自定义的sql生成对应的java文件
	 */
	public static final String SQL_VIEW = "view";
	
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public static final String ENCODE = "UTF-8";
}
