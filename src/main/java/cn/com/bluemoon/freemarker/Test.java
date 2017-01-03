package cn.com.bluemoon.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Test {
	 /**  
     * @param args  
     */   
    public   static   void  main(String[] args) {  
        //System.out.println(System.getProperty("user.dir")+"============");   
        Configuration cfg = new  Configuration(); 
        FileOutputStream fos = null;
        try  {  
            cfg.setClassForTemplateLoading(Test.class ,  "./" ); //指定模板所在的classpath目录   
            //自定义宏，将字段的首字母大写
            cfg.setSharedVariable("upperFC" ,  new  UpperFirstCharacter()); //添加一个"宏"共享变量用来将属性名首字母大写   
            Template t = cfg.getTemplate("bean.ftl" ); //指定模板   
            String javaPath = System.getProperty("user.dir");
            javaPath = javaPath + "\\src\\main\\java\\cn\\com\\bluemoon\\freemarker\\vo\\";
            File outPutFile = new File(javaPath);
            File file = new  File( outPutFile, "Student.java");
            //创建文件夹
            while(!outPutFile.exists()){
            	outPutFile.mkdirs();
            }
            //创建文件
            if(!file.exists()){
            	file.createNewFile();
            		
            		System.out.println(file.getPath());
            }else{
            	file.delete();
            }
            
            
//            File outPutFile = new File(file + "\\Student.java");
//            outPutFile.createNewFile();
            fos = new  FileOutputStream( file); //java文件的生成目录   
              
            //模拟数据源   
            Map<String, Object> data = new  HashMap<String, Object>();  
            data.put("package" ,  "cn.com.bluemoon.freemarker.vo" ); //包名   
            data.put("className" ,  "Student" );  
            data.put("serialVersionUID", 1L);
              
            List<Map<String, Object>> pros = new  ArrayList<Map<String, Object>>();  
            Map<String, Object> pro_1 = new  HashMap<String, Object>();  
            pro_1.put("proType" , String.class.getSimpleName());  
            pro_1.put("proName" ,  "name" );  
            pros.add(pro_1);  
              
            Map<String, Object> pro_2 = new  HashMap<String, Object>();  
            pro_2.put("proType" , String. class .getSimpleName());  
            pro_2.put("proName" ,  "sex" );  
            pros.add(pro_2);  
              
            Map<String, Object> pro_3 = new  HashMap<String, Object>();  
            pro_3.put("proType" , Integer.class.getSimpleName());  
            pro_3.put("proName" ,  "age" );  
            pros.add(pro_3);  
            
            Map<String, Object> pro_4 = new  HashMap<String, Object>();  
            pro_4.put("proType" , List.class.getSimpleName());  
            pro_4.put("proName" ,  "course" );
            pro_4.put("generic" ,  "<" + String.class.getSimpleName() + ">" );
            pros.add(pro_4);
              
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
}
