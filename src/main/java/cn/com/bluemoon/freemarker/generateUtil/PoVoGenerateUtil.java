package cn.com.bluemoon.freemarker.generateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import cn.com.bluemoon.allConst.Const;
import cn.com.bluemoon.common.GenerateUtils;
import cn.com.bluemoon.freemarker.UpperFirstCharacter;
import cn.com.bluemoon.freemarker.dbUtil.JDBCConnections;
import cn.com.bluemoon.freemarker.dbUtil.vo.BeanProperty;
import cn.com.bluemoon.util.DateUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * po，vo生成实体类工具
 * @author niejian
 *
 */
public class PoVoGenerateUtil {

//	/**表的名字或者查询视图sql*/
//	private String querySql;
//	/**table or view*/
//	private String TableOrView;
//	/**自定义的视图隐射实体名*/
//	private String beanName;
//	
//	
//	
//	public PoVoGenerateUtil(String querySql, String TableOrView){
//		this.querySql = querySql;
//		this.TableOrView = TableOrView;
//	}
//	
//	public PoVoGenerateUtil(String querySql, String TableOrView, String beanName){
//		this.querySql = querySql;
//		this.TableOrView = TableOrView;
//		this.beanName = beanName;
//	}
	
	private GenerateUtils generateUtils = new GenerateUtils();
	
	
	/**
	 * 数据表或者查询视图转对应的vo、po
	 * @param querySql 查询的sql语句或者表名
	 * @param TableOrView 视图还是表名
	 * @param beanName 需要转换的实体名，如果querySql是视图，这个参数必填
	 */
	public void table2Bean(String querySql, String TableOrView, String beanName){
		@SuppressWarnings("deprecation")
		Configuration cfg = new  Configuration(); 
        FileOutputStream fos = null;
        /**java文件名，驼峰命名*/
        String javaFileName = "";
        /**路径名，不是驼峰*/
        String javaFileName1 = "";
        if(Const.SQL_TABLE.equals(TableOrView)){
        	javaFileName = generateUtils.convertColume(querySql);
        }else if(Const.SQL_VIEW.equals(TableOrView)){
        	javaFileName = generateUtils.convertColume(beanName);
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
            Map<String, Class<?>> columeMap = this.generateUtils.parseBean(querySql, TableOrView);
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
	 * 数据表或者查询视图转对应的vo、po第二版
	 * <p>改变的地方主要有：</p>
	 * <p>
	 * 	数据表(或者查询视图)中的字段类型全部可以从jdbc的matedata中获取得到。所以这里改变对应的模板。
	 * mapper的xml文件中需要知道字段在数据库中的类型、字段名称、转换后的java实体名称（java字段名）
	 * 视图的写法默认使用 as 驼峰法来重命名字段
	 * 
	 * </p>
	 * @param querySql 查询的sql语句或者表名
	 * @param TableOrView 视图还是表名
	 * @param beanName 需要转换的实体名，如果querySql是视图，这个参数必填
	 */
	public void table2Bean_v2(String querySql, String TableOrView, String beanName){
		@SuppressWarnings("deprecation")
		Configuration cfg = new  Configuration(); 
        FileOutputStream fos = null;
        /**java文件名，驼峰命名*/
        String javaFileName = "";
        /**路径名，不是驼峰*/
        String javaPathName = "";
        if(Const.SQL_TABLE.equals(TableOrView)){
        	javaFileName = generateUtils.convertColume(querySql);
        }else if(Const.SQL_VIEW.equals(TableOrView)){
        	javaFileName = generateUtils.convertColume(beanName);
        }
        
        javaPathName = javaFileName;
        char[] javaFileNameChars = javaFileName.toCharArray();
        //将首字母转大写
        javaFileNameChars[0] = Character.toUpperCase(javaFileNameChars[0]);
        javaFileName = new String(javaFileNameChars);
        
        try  {  
            cfg.setClassForTemplateLoading(this.getClass() ,  "./" ); //指定模板所在的classpath目录 ->本级目录下面的模板文件
            //自定义宏，将字段的首字母大写
            //也就是说在模板中引用定义的变量，会自动调用对应的方法进行转换
            cfg.setSharedVariable("upperFC" ,  new  UpperFirstCharacter()); //添加一个"宏"共享变量用来将属性名首字母大写   
            Template t = cfg.getTemplate("javaBean.ftl" ); //指定模板   
            String baseJavaPath = System.getProperty("user.dir");
            //javaPath = javaPath + "\\src\\main\\java\\cn\\com\\bluemoon\\freemarker\\vo\\";
            // po, vo类文件路径
            String javaPath = baseJavaPath + Const.BASE_JAVA_FILE_PATH + "\\common\\" + javaPathName + "\\po\\";
            
            // mybatis的xml，java文件坐在位置
            // cn.com.bluemoon.common.模块名.mapper.模块名Mapper
            String commonFilePath = baseJavaPath + Const.BASE_JAVA_FILE_PATH + "\\common\\" + javaPathName + "\\mapper\\";
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
            // 对应mapper文件中的BaseResultMap
            String baseResultMap = Const.BASE_PACKAGE_NAME + ".common." + javaPathName + ".po." + javaFileName;
            //po包名 cn.com.bluemoon.common.模块名.po
            String poPackageName = Const.BASE_PACKAGE_NAME + ".common." + javaPathName + ".po";
            data.put("package" , poPackageName);    
            data.put("className" , javaFileName );
            data.put("serialVersionUID", DateUtil.getTime() + "");
            List<Map<String, Object>> pros = new  ArrayList<Map<String, Object>>();
            Set<String> importPackages = new HashSet<String>();
            //Map<String, Class<?>> columeMap = this.parseBean();
            List<BeanProperty> propertyBeans = JDBCConnections.execute_v2(querySql, TableOrView);
            
            if(null != propertyBeans && propertyBeans.size() > 0){
            	for(int i = 0; i < propertyBeans.size(); i++){
            		BeanProperty bean = propertyBeans.get(i);
            		String importPackage = bean.getJavaType();
            		String proName = bean.getJavaColumnName();
            		String proType = bean.getJavaType();
            		Map<String, Object> modelProperty = new  HashMap<String, Object>();
            		modelProperty.put("proType", proType);
            		modelProperty.put("proName", generateUtils.convertColume(proName));
            		importPackages.add(importPackage);
            		pros.add(modelProperty);
            	}
            }
            
            data.put("importPackages", importPackages);
            data.put("properties" , pros);
            t.process(data, new  OutputStreamWriter(fos, "utf-8" )); //   
            fos.flush();  
            fos.close();
            // 实体类全路径
            poPackageName = poPackageName + "." +javaFileName;
            // 生成mapper文件
            sql2Mapper(querySql, propertyBeans, cfg, commonFilePath, javaFileName, baseResultMap, poPackageName);
            
            //3:生成controller文件
            
            
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
	 * 查询视图转转对应的mapper文件
	 * @param propertyBeans
	 * @param cfg
	 */
	public void sql2Mapper(String querySql, List<BeanProperty> propertyBeans, 
			Configuration cfg, String filePath, String fileName, String baseResultMap, String poPackageName){
		FileOutputStream fos = null;
        try {
        	
        	if(null != propertyBeans && propertyBeans.size() > 0){
        		
        		//List<BeanProperty> beans = new ArrayList<BeanProperty>();
        		// 获取mapper文件位置
        		Template template = cfg.getTemplate("XmlMapper.ftl");
        		// mapper文件中的namespace
        		String javaMapperName = fileName;
        		char[] javaFileNameChars = javaMapperName.toCharArray();
        		//将首字母转大写
        		javaFileNameChars[0] = Character.toLowerCase(javaFileNameChars[0]);
        		// cn.com.bluemoon.common.activity.mapper.MallWashActivityAuthMapper
        		javaMapperName = new String(javaFileNameChars);
        		String mapperName = fileName + "CommonMapper";
        		String namespace = Const.BASE_PACKAGE_NAME + ".common." + javaMapperName +".mapper."+ mapperName;
        		
        		File outPutFile = new File(filePath);
        		File file = new  File( outPutFile, fileName + "CommonMapper.xml");
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
        		
        		fos = new  FileOutputStream( file); //java文件的生成目录   
        		//模拟数据源   
        		Map<String, Object> data = new  HashMap<String, Object>();
        		data.put("beanProperties", propertyBeans);
        		data.put("querySql", querySql);
        		data.put("namespace", namespace);
        		data.put("baseResultMap", baseResultMap);
        		String countSql = this.convertQuerySql2CountSql(querySql);
        		if(!StringUtils.isEmpty(countSql)){
        			data.put("countSql", countSql);
        		}
        		template.process(data, new  OutputStreamWriter(fos, "utf-8" )); 
        		fos.flush();  
        		fos.close();
        		
        		// 生成对应的javamapper文件
        		template = cfg.getTemplate("JavaMapper.ftl");
        		
        		outPutFile = new File(filePath);
        		String javaMapperNameTmp = fileName + "CommonMapper";
        		file = new  File( outPutFile, javaMapperNameTmp + ".java");
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
        		
        		fos = new  FileOutputStream(file);
        		
        		String packageName = Const.BASE_PACKAGE_NAME + ".common." + javaMapperName +".mapper";
        		String className = mapperName;
        		String fullBeanName = poPackageName;
        		data = new  HashMap<String, Object>();
        		data.put("className", className);
        		data.put("packageName", packageName);
        		data.put("fullBeanName", fullBeanName);
        		
        		template.process(data, new OutputStreamWriter(fos, "utf-8"));
        		
        		fos.flush();  
        		fos.close();
        		
        		// 加载模板
        		template = cfg.getTemplate("ControllerGen.ftl");
        		
        		//数据载体
        		data = new  HashMap<String, Object>();
        		
        		// 生成Controller文件
        		String requestMapping = javaMapperName + "Controller";
        		data.put("requestMapping", requestMapping);
        		String controllerName = fileName + "Controller";
        		data.put("controllerName", controllerName);
        		// autowried, 注入service
        		String autoWiredService = javaMapperNameTmp;
        		data.put("autoWiredService", autoWiredService);
        		// 找到这个mapper的包路径
        		List<String> importPackages = new ArrayList<String>();
        		String importPackage = packageName + "." +javaMapperNameTmp;
        		importPackages.add(importPackage);
        		data.put("importPackages", importPackages);
        		// xml中的baseResultMap也是返回实体的全路径
        		String entity = baseResultMap;
        		data.put("entity", entity);
        		// service 名称
        		String autoWiredServiceName = javaMapperName + "CommonMapper";
        		data.put("autoWiredServiceName", autoWiredServiceName);
        		String modelName = javaMapperName;
        		data.put("modelName", modelName);
        		data.put("propertyBeans", propertyBeans);
        		
        		//package name
        		// package cn.com.bluemoon.manager.auth
        		packageName = Const.BASE_PACKAGE_NAME + ".manager." + javaMapperName;
        		data.put("packageName", packageName);
        		// controller 包名如下： cn.com.bluemoon.manager.模块名.xxController
        		 String baseJavaPath = System.getProperty("user.dir");
                 String controllerPath = baseJavaPath + Const.BASE_JAVA_FILE_PATH + "\\manager\\" + javaMapperName + "\\";
        		//String controllerPath = 
        		outPutFile = new File(controllerPath);
        		//String javaMapperNameTmp = fileName + "CommonMapper";
        		file = new  File( outPutFile, controllerName + ".java");
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
        		
        		fos = new  FileOutputStream(file);
        		template.process(data, new OutputStreamWriter(fos, "utf-8"));
        		
        		fos.flush();  
        		fos.close();
        		
        	}
		} catch (Exception e) {
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
	 * 将查询sql转换问统计sq。
	 * 规定sql中的from关键字小写或者大写，其他的不支持
	 * @param querySql
	 * @return
	 */
	private String convertQuerySql2CountSql(String querySql){
		int index = -1;
		String countSql = querySql;
		if(countSql.contains("from") || countSql.contains("FROM")){
			index = countSql.indexOf("from");
		}else if(countSql.contains("FROM")){
			index = countSql.indexOf("from");
		}
		
		if(index > 0){
			countSql = "select count(1) as num " + countSql.substring(index);
		}
		
		return countSql;
	}
}
