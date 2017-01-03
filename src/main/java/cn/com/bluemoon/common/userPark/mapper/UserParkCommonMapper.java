package cn.com.bluemoon.common.userPark.mapper;
import java.util.List;
import java.util.Map;

public interface UserParkCommonMapper{
	public List<cn.com.bluemoon.common.userPark.po.UserPark> queryEntity(Map<String, Object> paraMap) throws Exception;
	
	/**
	* 获取记录总数
	*/
	public int getTotalEntity(Map<String, Object> paraMap) throws Exception;
}