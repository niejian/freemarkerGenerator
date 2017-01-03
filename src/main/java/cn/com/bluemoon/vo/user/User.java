package cn.com.bluemoon.vo.user;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -5666753178707355481L;
	private long id;
	private String userName;
	private Integer age;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	

}
