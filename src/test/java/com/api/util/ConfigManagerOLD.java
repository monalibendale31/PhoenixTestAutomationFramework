package com.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.annotations.Test;

public abstract class ConfigManagerOLD {

	private ConfigManagerOLD() {

	}

	// wap to read the properties file from
	// \src\test\resources\config\config.properties

	private static Properties prop = new Properties();

	static {

		// Operation of loading the properties file in the memory!
		// static block it will executed once dusring the class loading time

		// Load the properties file using the load method

		// "D:\\Selenium\\Projects\\PhoenixTestAutomationFramework_Mona\\src\\test\\resources\\config\\config.properties"

		File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "config" + File.separator + "config.properties");
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			prop.load(fis);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Test
	public static String readProperties(String key) {
		// Create object of properties class

		return prop.getProperty(key);
	}

}
