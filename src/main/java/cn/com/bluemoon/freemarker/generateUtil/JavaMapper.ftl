package ${packageName};
import java.util.List;
import java.util.Map;

public interface ${className}{
	public List<${fullBeanName}> queryEntity(Map<String, Object> paraMap) throws Exception;
	
	/**
	* 获取记录总数
	*/
	public int getTotalEntity(Map<String, Object> paraMap) throws Exception;
}