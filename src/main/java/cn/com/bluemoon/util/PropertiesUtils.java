package cn.com.bluemoon.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


public class PropertiesUtils {
	public static Properties getUrlProperties(String url) {
		Properties p = new Properties();
		try {
			InputStreamReader inputStream =new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(url), "UTF-8");
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p;
	}
}
