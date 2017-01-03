package cn.com.bluemoon.service.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.com.bluemoon.service.user.service.UserService;
import cn.com.bluemoon.vo.user.User;

@Service(value="userService")
public class UserServiceImpl implements UserService {

	@Override
	public List<User> getUserInfo() throws Exception {
		List<User> users = new ArrayList<User>();
		for(int i = 0; i < 10; i++){
			User user = new User();
			user.setId(new Date().getTime());
			user.setAge( i + 1);
			user.setUserName("test-" + i);
			users.add(user);
		}
		return users;
	}

}
