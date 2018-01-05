package com.atguigu.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	public static String getPath(String properties_path, String key) {
		Properties properties = new Properties();
		try {
			properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(properties_path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String image_path = properties.getProperty(key);
		return image_path;
	}


	
}
