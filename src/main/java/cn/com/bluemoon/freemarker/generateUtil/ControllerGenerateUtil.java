package cn.com.bluemoon.freemarker.generateUtil;

/**
 * 根据查询sql生成对应的controller
 * 默认每个字段都会查询
 * 在生成controller的同时，还要生成对应的mapper文件(针对使用mybatis)
 * 
 * 生成顺序：
 * <p>
 * 	1.首先根据对应的SQL，生成对应的mapper文件（分页查询，条件是所有的字段）
 * 	构造出mapper的模板文件（xmlMapper， javaMapper）
 * </p>
 * <p>2.controller调用生成的mapper</p>
 * @author niejian
 *
 */
public class ControllerGenerateUtil {
	
}
