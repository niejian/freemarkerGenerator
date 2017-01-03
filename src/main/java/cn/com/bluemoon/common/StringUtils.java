package cn.com.bluemoon.common;

public class StringUtils {

	public static Boolean isEmpty(String str){
		Boolean isTrue = Boolean.FALSE;
		if(null == str || "".equals(str.trim())){
			isTrue = Boolean.TRUE;
		}
		return isTrue;
	}
}
