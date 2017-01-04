package cn.com.bluemoon.manager.userPark;
import cn.com.bluemoon.common.userPark.mapper.UserParkCommonMapper;
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
@RequestMapping(value="/userParkController")
public class UserParkController{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserParkCommonMapper userParkCommonMapper;
	
	@ResponseBody
	@RequestMapping(value="/userPark/queryEntities", method=RequestMethod.POST)
	public Map<String, Object> queryEntities(HttpServletRequest request, @RequestBody JSONObject json){
		List<cn.com.bluemoon.common.userPark.po.UserPark> list = new ArrayList<cn.com.bluemoon.common.userPark.po.UserPark>();
		Map<String,Object> resultMap= new HashMap<String, Object>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		int total = 0;
		
		try{
			// 页码和每页条数从pc端传过来，这个不变
			String pageIndex = json.getString("pageIndex");
			String pageSize = json.getString("pageSize");
			int size = Integer.parseInt(pageSize);
			int startIndex = Integer.parseInt(pageSize) * Integer.parseInt(pageIndex);
			
				String updateTime = json.getString("updateTime");
				paraMap.put("updateTime", updateTime);
				String nickName = json.getString("nickName");
				paraMap.put("nickName", nickName);
				String name = json.getString("name");
				paraMap.put("name", name);
				String mobile = json.getString("mobile");
				paraMap.put("mobile", mobile);
				String plate = json.getString("plate");
				paraMap.put("plate", plate);
				String address = json.getString("address");
				paraMap.put("address", address);
			
		
			paraMap.put("pageSize", size);
			paraMap.put("startIndex", startIndex);
			
			// 还需要查询这些条件得到的记录总数
			total = this.userParkCommonMapper.getTotalEntity(paraMap);
			// 查询记录
			list = this.userParkCommonMapper.queryEntity(paraMap);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		resultMap.put("total", total);
		resultMap.put("list", list);
		
		return resultMap;
	}
}