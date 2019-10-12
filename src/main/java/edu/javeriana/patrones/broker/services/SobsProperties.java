/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.javeriana.patrones.broker.services;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author cristianmendi
 */
public class SobsProperties {
    private final static Logger LOGGER = Logger.getLogger(SobsProperties.class);

	private Properties prop = new Properties();
	
	public SobsProperties() {
		try {
			String propFileName = "config.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
			}
			inputStream.close();
		} catch (Exception e) {
			LOGGER.error("Exception: " + e);
		}
	}
	

	public String getPropValues(String value)  {
		return prop.getProperty(value);
	}
}
