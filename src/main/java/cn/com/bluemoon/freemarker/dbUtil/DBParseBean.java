package cn.com.bluemoon.freemarker.dbUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.com.bluemoon.allConst.Const;
import cn.com.bluemoon.freemarker.UpperFirstCharacter;
import cn.com.bluemoon.freemarker.dbUtil.vo.BeanProperty;
import cn.com.bluemoon.util.DateUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DBParseBean {
	
	/**表的名字或者查询视图sql*/
	private String querySql;
	/**table or view*/
	private String TableOrView;
	/**自定义的视图隐射实体名*/
	private String beanName;
	
	public DBParseBean(String querySql, String TableOrView){
		this.querySql = querySql;
		this.TableOrView = TableOrView;
	}
	
	public DBParseBean(String querySql, String TableOrView, String beanName){
		this.querySql = querySql;
		this.TableOrView = TableOrView;
		this.beanName = beanName;
	}
	
	public Map<String, Class<?>> parseBean(){
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
	
	/**
	 * 数据表转换为javabean
	 */
	public void table2Bean(){
		@SuppressWarnings("deprecation")
		Configuration cfg = new  Configuration(); 
        FileOutputStream fos = null;
        /**java文件名，驼峰命名*/
        String javaFileName = "";
        /**路径名，不是驼峰*/
        String javaFileName1 = "";
        if(Const.SQL_TABLE.equals(this.TableOrView)){
        	javaFileName = this.convertColume(this.querySql);
        }else if(Const.SQL_VIEW.equals(this.TableOrView)){
        	javaFileName = this.convertColume(this.beanName);
        }
        
        javaFileName1 = javaFileName;
        char[] javaFileNameChars = javaFileName.toCharArray();
        //将首字母转大写
        javaFileNameChars[0] = Character.toUpperCase(javaFileNameChars[0]);
        
        try  {  
            cfg.setClassForTemplateLoading(this.getClass() ,  "./" ); //指定模板所在的classpath目录 ->本级目录下面的模板文件
            //自定义宏，将字段的首字母大写
            //也就是说在模板中引用定义的变量，会自动调用对应的方法进行转换
            cfg.setSharedVariable("upperFC" ,  new  UpperFirstCharacter()); //添加一个"宏"共享变量用来将属性名首字母大写   
            Template t = cfg.getTemplate("javaBean.ftl" ); //指定模板   
            String javaPath = System.getProperty("user.dir");
            //javaPath = javaPath + "\\src\\main\\java\\cn\\com\\bluemoon\\freemarker\\vo\\";
            javaPath = javaPath + Const.BASE_JAVA_FILE_PATH + "//" + javaFileName1 + "\\po\\";
            File outPutFile = new File(javaPath);
            File file = new  File( outPutFile, javaFileName + ".java");
            //创建文件夹
            while(!outPutFile.exists()){
            	outPutFile.mkdirs();
            }
            //创建文件
            if(!file.exists()){
            	file.createNewFile();
            }else{
            	file.delete();
            }
            
//	            File outPutFile = new File(file + "\\Student.java");
//	            outPutFile.createNewFile();
            fos = new  FileOutputStream( file); //java文件的生成目录   
              
            //模拟数据源   
            Map<String, Object> data = new  HashMap<String, Object>();  
            data.put("package" ,  Const.BASE_PACKAGE_NAME + "." + javaFileName + ".po" ); //包名   
            data.put("className" ,  javaFileName );
            data.put("serialVersionUID", DateUtil.getTime() + "");
            List<Map<String, Object>> pros = new  ArrayList<Map<String, Object>>();
            Set<String> importPackages = new HashSet<String>();
            Map<String, Class<?>> columeMap = this.parseBean();
            if(null != columeMap && columeMap.size() > 0){
            	for(Entry<String, Class<?>> entry : columeMap.entrySet()){
            		String column = entry.getKey();
            		Class<?> clazz = entry.getValue();
            		Map<String, Object> beanProperty = new  HashMap<String, Object>();
            		beanProperty.put("proType", clazz.getSimpleName());
            		beanProperty.put("proName", column);
            		importPackages.add(clazz.getName());
            		pros.add(beanProperty);
            	}
            }
            
            data.put("importPackages", importPackages);
            data.put("properties" , pros);
            t.process(data, new  OutputStreamWriter(fos, "utf-8" )); //   
            fos.flush();  
            fos.close();  
        } catch  (IOException e) {  
            e.printStackTrace();  
        } catch  (TemplateException e) {  
            e.printStackTrace();  
        }finally{
        	if(null != fos){
        		try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}
	
	
	/**
	 * 数据表转换为javabean
	 */
	public void table2Bean_v2(){
		@SuppressWarnings("deprecation")
		Configuration cfg = new  Configuration(); 
        FileOutputStream fos = null;
        /**java文件名，驼峰命名*/
        String javaFileName = "";
        /**路径名，不是驼峰*/
        String javaFileName1 = "";
        if(Const.SQL_TABLE.equals(this.TableOrView)){
        	javaFileName = this.convertColume(this.querySql);
        }else if(Const.SQL_VIEW.equals(this.TableOrView)){
        	javaFileName = this.convertColume(this.beanName);
        }
        
        javaFileName1 = javaFileName;
        char[] javaFileNameChars = javaFileName.toCharArray();
        //将首字母转大写
        javaFileNameChars[0] = Character.toUpperCase(javaFileNameChars[0]);
        
        try  {  
            cfg.setClassForTemplateLoading(this.getClass() ,  "./" ); //指定模板所在的classpath目录 ->本级目录下面的模板文件
            //自定义宏，将字段的首字母大写
            //也就是说在模板中引用定义的变量，会自动调用对应的方法进行转换
            cfg.setSharedVariable("upperFC" ,  new  UpperFirstCharacter()); //添加一个"宏"共享变量用来将属性名首字母大写   
            Template t = cfg.getTemplate("javaBean.ftl" ); //指定模板   
            String javaPath = System.getProperty("user.dir");
            //javaPath = javaPath + "\\src\\main\\java\\cn\\com\\bluemoon\\freemarker\\vo\\";
            javaPath = javaPath + Const.BASE_JAVA_FILE_PATH + "//" + javaFileName1 + "\\po\\";
            File outPutFile = new File(javaPath);
            File file = new  File( outPutFile, javaFileName + ".java");
            //创建文件夹
            while(!outPutFile.exists()){
            	outPutFile.mkdirs();
            }
            //创建文件
            if(!file.exists()){
            	file.createNewFile();
            }else{
            	file.delete();
            }
            
//	            File outPutFile = new File(file + "\\Student.java");
//	            outPutFile.createNewFile();
            fos = new  FileOutputStream( file); //java文件的生成目录   
              
            //模拟数据源   
            Map<String, Object> data = new  HashMap<String, Object>();  
            data.put("package" ,  Const.BASE_PACKAGE_NAME + "." + javaFileName + ".po" ); //包名   
            data.put("className" ,  javaFileName );
            data.put("serialVersionUID", DateUtil.getTime() + "");
            List<Map<String, Object>> pros = new  ArrayList<Map<String, Object>>();
            Set<String> importPackages = new HashSet<String>();
            //Map<String, Class<?>> columeMap = this.parseBean();
            List<BeanProperty> propertyBeans = JDBCConnections.execute_v2(this.querySql, this.TableOrView);
            
            if(null != propertyBeans && propertyBeans.size() > 0){
            	for(int i = 0; i < propertyBeans.size(); i++){
            		BeanProperty bean = propertyBeans.get(i);
            		String importPackage = bean.getJavaType();
            		String proName = bean.getJavaColumnName();
            		String proType = bean.getColumnType();
            		Map<String, Object> modelProperty = new  HashMap<String, Object>();
            		modelProperty.put("proType", proType);
            		modelProperty.put("proName", proName);
            		importPackages.add(importPackage);
            		pros.add(modelProperty);
            	}
            }
            
//            if(null != columeMap && columeMap.size() > 0){
//            	for(Entry<String, Class<?>> entry : columeMap.entrySet()){
//            		String column = entry.getKey();
//            		Class<?> clazz = entry.getValue();
//            		Map<String, Object> beanProperty = new  HashMap<String, Object>();
//            		beanProperty.put("proType", clazz.getSimpleName());
//            		beanProperty.put("proName", column);
//            		importPackages.add(clazz.getName());
//            		pros.add(beanProperty);
//            	}
//            }
            
            data.put("importPackages", importPackages);
            data.put("properties" , pros);
            t.process(data, new  OutputStreamWriter(fos, "utf-8" )); //   
            fos.flush();  
            fos.close();  
        } catch  (IOException e) {  
            e.printStackTrace();  
        } catch  (TemplateException e) {  
            e.printStackTrace();  
        }finally{
        	if(null != fos){
        		try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}
	
	private String convertColume(String columnName){
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
	
	private Class<?> mysqlTypeConvertJavaType(String columnType){
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
}
