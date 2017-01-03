package cn.com.bluemoon.freemarker.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.util.StringUtils;

import cn.com.bluemoon.allConst.Const;
import cn.com.bluemoon.freemarker.dbUtil.vo.BeanProperty;
import cn.com.bluemoon.util.PropertiesUtils;


public class JDBCConnections {

	public static Connection getConnection(){
		Properties properties = PropertiesUtils.getUrlProperties("jdbc.properties");
		String url = properties.getProperty("jdbc.url");
		String userName = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");
		String driver = properties.getProperty("jdbc.driver");
		Connection con = null;
		try {
			 Class.forName(driver) ;   
			con = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	

	/**
	 * <p>1.tableOrView == 'table';根据表名获取表结构 </p>
	 * <p>2.tableOrView == 'view';根据sql隐射出对应的实体机构</p>
	 * @param query 表的名字或者是视图的sql语句
	 * @param tableOrView ['table', 'view']
	 * @return
	 */
	public static Map<String, String> execute(String query, String tableOrView) {
		Connection con = null;
		Map<String, String> resultMap = new HashMap<String, String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			if(null != con){
				stmt = con.createStatement() ;   
			    //PreparedStatement pstmt = con.prepareStatement("show create table park") ;
				if(Const.SQL_TABLE.equals(tableOrView)){ // 如果只是单单的查询数据表
					rs = stmt.executeQuery("select * from " + query);
				}else if(Const.SQL_VIEW.equals(tableOrView)){
					rs = stmt.executeQuery(query);
				}
			    ResultSetMetaData datas = rs.getMetaData();
			  //  fields = datas.get
			  //  fields = datas.
			    if(null != datas && datas.getColumnCount() > 0){
			    	for(int i = 0; i < datas.getColumnCount(); i++){
			    		int index = i + 1;
			    		//数据表字段类型对应的java po类型
			    		String importCalss = datas.getColumnClassName(index);
			    		String columnName = datas.getColumnName(index);
			    		String type = datas.getColumnTypeName(index);
			    		System.out.println(datas.getColumnLabel(index) + columnName);
			    		System.out.println("==============");
			    		System.out.println(datas.getColumnClassName(index));
			    		System.out.println("==============");
			    		System.out.println(datas.getColumnTypeName(index));
			    		System.out.println("==============");
			    		System.out.println(datas.getSchemaName(index));
			    		System.out.println("==============");
			    		//String originColumnName = datas.get
			    		//System.out.println(data + "----" + type);
			    		resultMap.put(columnName, type);
			    	}
			    }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(null != con){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
			
			if(null != stmt){
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(null != rs){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return resultMap;
		
	}
	
	
	/**
	 * <p>1.tableOrView == 'table';根据表名获取表结构 </p>
	 * <p>2.tableOrView == 'view';根据sql隐射出对应的实体机构</p>
	 * @param query 表的名字或者是视图的sql语句
	 * @param tableOrView ['table', 'view']
	 * @return
	 */
	public static List<BeanProperty> execute_v2(String query, String tableOrView) {
		List<BeanProperty> propertyList = new ArrayList<BeanProperty>();
		Connection con = null;
//		Map<String, String> resultMap = new HashMap<String, String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			if(null != con){
				stmt = con.createStatement() ;   
			    //PreparedStatement pstmt = con.prepareStatement("show create table park") ;
				if(Const.SQL_TABLE.equals(tableOrView)){ // 如果只是单单的查询数据表
					rs = stmt.executeQuery("select * from " + query);
				}else if(Const.SQL_VIEW.equals(tableOrView)){
					rs = stmt.executeQuery(query);
				}
			    ResultSetMetaData datas = rs.getMetaData();
			  //  fields = datas.get
			  //  fields = datas.
			    if(null != datas && datas.getColumnCount() > 0){
			    	for(int i = 0; i < datas.getColumnCount(); i++){
			    		BeanProperty beanProperty = new BeanProperty();
			    		int index = i + 1;
			    		//数据表字段类型对应的java po类型
			    		/**java字段类型*/
			    		String javaType = datas.getColumnClassName(index);
			    		/**数据表字段类型*/
			    		String columnType = datas.getColumnTypeName(index);
			    		/**数据表字段名称*/
			    		String propertyName = datas.getColumnName(index);
			    		/**java字段名称*/
			    		String columnName = datas.getColumnLabel(index); 
			    		//String originColumnName = datas.get
			    		//System.out.println(data + "----" + type);
			    		if(!StringUtils.isEmpty(javaType) && !StringUtils.isEmpty(columnType) 
			    				&& !StringUtils.isEmpty(propertyName) && !StringUtils.isEmpty(columnName)){
			    			beanProperty.setJavaColumnName(columnName);
			    			beanProperty.setColumnType(columnType);
			    			beanProperty.setJavaType(javaType);
			    			beanProperty.setColumnName(propertyName);
			    			propertyList.add(beanProperty);
			    		}
			    	}
			    }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			propertyList = new ArrayList<BeanProperty>();
		}finally{
			if(null != con){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
			
			if(null != stmt){
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(null != rs){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return propertyList;
		
	}
	
	public static void main(String[] args) throws SQLException {
//		Map<String, String> map = execute("park");
//		System.out.println(map.toString());
		
		char[] cbuf = "total".toCharArray();
		cbuf[0] = Character.toUpperCase(cbuf[0]);
		System.out.println(cbuf);
	}
}
