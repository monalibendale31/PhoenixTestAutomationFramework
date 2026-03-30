package com.api.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.annotations.Test;

public abstract class ConfigManager {

	private static Properties prop = new Properties();
	private static String path = "config//config.properties";
	private static String env;

	private ConfigManager() {
		// private constructor
	}

	static {

		env = System.getProperty("env", "qa");
		env = env.toLowerCase().trim();
		System.out.println("Running tests in ENV : " + env);

		switch (env) {
		case "dev" -> path = "config//config.dev.properties";

		case "qa" -> path = "config//config.qa.properties";

		case "uat" -> path = "config//config.uat.properties";

		default -> path = "config/config.qa.properties";

		}

		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (input == null) {
			throw new RuntimeException("Can not find the file at the path : " + path);
		}
		try {
			prop.load(input);
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
