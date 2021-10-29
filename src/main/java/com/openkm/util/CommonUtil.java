package com.openkm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CommonUtil {
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	public static String getStringConfig(String conf, String key) {
		try {
			ResourceBundle rb = ResourceBundle.getBundle(conf);
			return rb.getString(key);
		} catch (MissingResourceException var2) {
			log.debug("Init parameter " + key + " ERROR:" + var2.getMessage());
			return "";
		}
	}
}
