<#-- mapper的xml模板文件 -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${namespace}" >
  <resultMap id="BaseResultMap" type="${baseResultMap}" >
  	<#-- 遍历循环 beanName 里面的实体字段名 -->
  	<#list beanProperties as property>
  		<#-- 
  			
  			property: 变量实体，类结构类似：{columnName,columnType}
  			javaColumnName: 实体的属性
  			columnName： 数据表字段名
  			columnType： 数据表字段类型
  		  -->
  		<id column="${property.columnName}" property="${property.javaColumnName}" jdbcType="${property.columnType}" />
  	</#list>
    
  </resultMap>
  	<#-- 分页查询模板 -->
  	<#--
  		fullBeanName ： 返回实体类的类名全路径：cn.com.bluemoon.common.activity.po.MallWashActivityInfo
  		querySql : 查询语句
  		
  	    -->
  <select id="queryEntity" parameterType="java.util.HashMap" resultType="BaseResultMap"> 
		${querySql}
			where 1=1
		<#-- 
			遍历这个实体类，和查询语句每个字段
			过滤掉时间的判断，时间判断是比较复杂的，到底是大于、小于还是怎么样要根据自己实际的业务来添加
		 -->
		<#list beanProperties as property>
			<#if property.columnType != 'DATETIME'>
				<if test="${property.javaColumnName} != '' and null != ${property.javaColumnName}" >
					AND ${property.javaColumnName} = ${r"#{" + property.javaColumnName + "}"}
				</if>
			</#if>
		</#list>
	 limit ${r"#{startIndex}"}, ${r"${pageSize}"}
  </select>
  
  
  <!--根据前台条件，获取记录总数,注意：这里不支持子查询、union查询,如果有时间的过滤，需要自己手动添加-->
  <select id="getTotalEntity" parameterType="java.util.HashMap" resultType="java.lang.Integer"> 
	${countSql}
		where 1=1
	<#-- 
		遍历这个实体类，和查询语句每个字段
		过滤掉时间的判断，时间判断是比较复杂的，到底是大于、小于还是怎么样要根据自己实际的业务来添加
	 -->
	<#list beanProperties as property>
		<#if property.columnType != 'DATETIME'>
			<if test="${property.javaColumnName} != '' and null != ${property.javaColumnName}" >
				AND ${property.javaColumnName} = ${r"#{" + property.javaColumnName + "}"}
			</if>
		</#if>
	</#list>
  </select>
  
</mapper>