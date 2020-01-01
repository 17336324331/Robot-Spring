package com.xingguang.utils;

import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesUtil {

	private ResourceBundle rb;

	public PropertiesUtil() {

	}

	public PropertiesUtil(String fileName) throws Exception {
		InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
		rb = new PropertyResourceBundle(is);
	}
	
	public String getProperty(String key) {
		String value = "";
		if(null != rb) {
			value = rb.getString(key);
		}
		return value;
	}
}
