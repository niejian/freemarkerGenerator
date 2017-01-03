package cn.com.bluemoon.template.test;

import org.junit.Ignore;
import org.junit.Test;

import cn.com.bluemoon.allConst.Const;
import cn.com.bluemoon.freemarker.dbUtil.DBParseBean;
import cn.com.bluemoon.freemarker.generateUtil.PoVoGenerateUtil;

public class ConvertTest {

	@Ignore
	@Test
	public void test1(){
		DBParseBean bean = new DBParseBean("car_place", Const.SQL_TABLE);
		bean.table2Bean();
		String sql = "SELECT a.nick_name as nickName, a.`name`, a.mobile, a.plate,"
				+ " c.address "
				+ " from sys_user a "
				+ "LEFT JOIN  place_rent b on a.id = b.owner_id "
				+ "LEFT JOIN park c on b.park_id = c.id "
				+ "where b.owner_id is not null";
		DBParseBean bean2 = new DBParseBean(sql, Const.SQL_VIEW, "userPark");
		bean2.table2Bean();
	}
	
	//@Ignore
	@Test
	public void test2(){
		PoVoGenerateUtil generateUtil = new PoVoGenerateUtil();
		String querySql = "SELECT c.update_time as updateTime, a.nick_name as nickName, a.`name` as name, a.mobile as mobile, a.plate as plate,"
				+ " c.address as address "
				+ " from sys_user a "
				+ "LEFT JOIN  place_rent b on a.id = b.owner_id "
				+ "LEFT JOIN park c on b.park_id = c.id "
				+ "where b.owner_id is not null";
		
		String TableOrView = Const.SQL_VIEW;
		String beanName = "userPark";
		generateUtil.table2Bean_v2(querySql, TableOrView, beanName);
		//generateUtil.table2Bean_v2("ofo_meeting_summary_base", Const.SQL_TABLE, "");
	}
	
	@Ignore
	@Test
	public void test3(){
		String querySql = "SELECT c.update_time as updateTime, a.nick_name as nickName, a.`name` as name, a.mobile as mobile, a.plate as plate,"
				+ " c.address as address "
				+ " from sys_user a "
				+ "LEFT JOIN  place_rent b on a.id = b.owner_id "
				+ "LEFT JOIN park c on b.park_id = c.id "
				+ "where b.owner_id is not null";
		
		int index = -1;
		if(querySql.contains("from") || querySql.contains("FROM")){
			index = querySql.indexOf("from");
		}else if(querySql.contains("FROM")){
			index = querySql.indexOf("from");
		}
		
		if(index > 0){
			querySql = "select count(1) as num " + querySql.substring(index);
		}
		System.out.println(querySql);
	}
}
