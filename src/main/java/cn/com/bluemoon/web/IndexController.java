package cn.com.bluemoon.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.bluemoon.service.user.service.UserService;
import cn.com.bluemoon.vo.user.User;

@Controller
@RequestMapping(value="/index")
public class IndexController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(method=RequestMethod.POST, value="/index")
	public ModelAndView getUserInfo(){
		
		ModelAndView model = new ModelAndView("/userInfo.btl");
		try {
			List<User> users = this.userService.getUserInfo();
			model.addObject("users", users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/index2")
	public String goTo(){
		return "forward:/index/index";
	}
}
