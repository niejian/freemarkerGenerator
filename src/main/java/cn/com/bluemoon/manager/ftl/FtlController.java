package cn.com.bluemoon.manager.ftl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.bluemoon.freemarker.feature.method.IndexOfMeThod;

@Controller
@RequestMapping(value="/ftl")
public class FtlController {

	@RequestMapping(value="/test")
	public ModelAndView test(){
		ModelAndView modelAndView = new ModelAndView("test/ftlTest");
		modelAndView.addObject("date", new Date());
		modelAndView.addObject("txt", "goto");
		modelAndView.addObject("color", "blue");
		// 使用自定义方法
		modelAndView.addObject("indexOf", new IndexOfMeThod());
		return modelAndView;
		
	}
}
