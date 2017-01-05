package cn.com.bluemoon.common;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.bluemoon.allConst.Const;
import cn.com.bluemoon.freemarker.dbUtil.JDBCConnections;

public class GenerateUtils {

	/**
	 * 数据表字段转java
	 * @param columnName
	 * @return
	 */
	public String convertColume(String columnName){
		String newColumnName = columnName;
		if(newColumnName.indexOf(Const.TABLE_COLUMN_SEPERATE) > 0){
			String[] columnNames = newColumnName.split(Const.TABLE_COLUMN_SEPERATE);
			if(null != columnNames && columnNames.length > 0){
				StringBuffer name = new StringBuffer(columnNames[0]);
				int length = columnNames.length;
				// 数据列名转驼峰命名方式，从分割数组的第二个开始装换
				for(int i = 1; i < length; i++){
					String tmp = columnNames[i];
					// 将字符串转换为char数组
					char[] chars = tmp.toCharArray();
					//将第一个字母转换为大写
					chars[0] = Character.toUpperCase(chars[0]);
					name.append(chars);
				}
				newColumnName = name.toString();
			}
		}
		return newColumnName;
	}
	
	/**
	 * 数据库表字段类型转java类型
	 * @param columnType
	 * @return
	 */
	public Class<?> mysqlTypeConvertJavaType(String columnType){
		Class<?> clazz = String.class;
		switch (columnType) {
		case "INT":
			clazz = Integer.class;
			break;
		case "DECIMAL":
			clazz = BigDecimal.class;
			break;
			
		case "INTEGER":
			clazz = Long.class;
			break;
		case "TINYINT":
			clazz = Integer.class;
			break;
		case "DATE":
			clazz = Date.class;
			break;
		case "TIME":
			clazz = Date.class;;
			break;
		case "DATETIME":
			clazz = Timestamp.class;
			break;
		case "TIMESTAMP":
			clazz = Timestamp.class;
			break;
		default:
			clazz = String.class;
			break;
		}
		
		return clazz;
	}
	
	/**
	 * 通过sql语句或者表名来转换对应的实体类
	 * @param querySql
	 * @param TableOrView
	 * @return
	 */
	public Map<String, Class<?>> parseBean(String querySql, String TableOrView){
		Map<String, Class<?>> map = new HashMap<String, Class<?>>();
		Map<String, String> resultMap = JDBCConnections.execute(querySql, TableOrView);
		if(null != resultMap && resultMap.size() > 0){
			for(Entry<String, String> entry : resultMap.entrySet()){
				// 列名
				String column = entry.getKey();
				column = this.convertColume(column);
				// 列类型
				String columnType = entry.getValue().toUpperCase();
				Class<?> clazz = this.mysqlTypeConvertJavaType(columnType);
				//System.out.println(column + "--" + clazz.getName() );
				map.put(column, clazz);
			}
		}
		
		return map;
	}
	
	public static void main(String[] args) {
		  BigDecimal   c = new BigDecimal(123456789.00);
		   DecimalFormat myformat = new java.text.DecimalFormat("#.00");
		   String str = myformat.format(c);
		   System.out.println(new BigDecimal(str));
	}
}
