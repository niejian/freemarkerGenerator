package cn.com.bluemoon.manager.generate;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.bluemoon.freemarker.generateUtil.PoVoGenerateUtil_v2;


@Controller
@RequestMapping(value="/generate")
public class GenerateController {

	@RequestMapping(value="/generateBySql", method=RequestMethod.POST)
	public void generateBySql(HttpServletRequest request){
		PoVoGenerateUtil_v2 generateUtil_v2 = new PoVoGenerateUtil_v2();
		System.out.println(request.getParameter("sql"));
	}
}
