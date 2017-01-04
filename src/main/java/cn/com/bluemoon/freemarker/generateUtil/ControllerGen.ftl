package ${packageName};
<#list importPackages as importPackage>
import ${importPackage};
</#list>
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping(value="/${requestMapping}")
public class ${controllerName}{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ${autoWiredService} ${autoWiredServiceName};
	
	<#-- 命名：默认pc查询列表调用的名称是 getEntities -->
	@ResponseBody
	@RequestMapping(value="/${modelName}/queryEntities", method=RequestMethod.POST)
	public Map<String, Object> queryEntities(HttpServletRequest request, @RequestBody JSONObject json){
		List<${entity}> list = new ArrayList<${entity}>();
		Map<String,Object> resultMap= new HashMap<String, Object>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		int total = 0;
		
		try{
			<#-- 页码和每页条数从端穿过来，这个不变 -->
			// 页码和每页条数从pc端传过来，这个不变
			String pageIndex = json.getString("pageIndex");
			String pageSize = json.getString("pageSize");
			int size = Integer.parseInt(pageSize);
			int startIndex = Integer.parseInt(pageSize) * Integer.parseInt(pageIndex);
			<#-- 
				遍历这个entity的所有字段,字段都当作字符串来处理
				时间类型字段是在前台格式化好后传过来的
			-->
			
			<#list propertyBeans as beanProperty>
			if(json.has("${beanProperty.javaColumnName}")){
				String ${beanProperty.javaColumnName} = json.getString("${beanProperty.javaColumnName}");
				<#-- 放到查询条件中 -->
				paraMap.put("${beanProperty.javaColumnName}", ${beanProperty.javaColumnName});
			}
			</#list>
		
			<#-- 放入分页参数 -->
			paraMap.put("pageSize", size);
			paraMap.put("startIndex", startIndex);
			
			// 还需要查询这些条件得到的记录总数
			total = this.${autoWiredServiceName}.getTotalEntity(paraMap);
			// 查询记录
			list = this.${autoWiredServiceName}.queryEntity(paraMap);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		resultMap.put("total", total);
		resultMap.put("list", list);
		
		return resultMap;
	}
}