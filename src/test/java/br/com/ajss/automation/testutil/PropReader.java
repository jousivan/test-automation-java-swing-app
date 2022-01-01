package br.com.ajss.automation.testutil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropReader {
	
	static Properties prop;
	
	public PropReader() {
		try (InputStream fileProp = new FileInputStream("../config.properties")) {

			prop = new Properties();
			
			prop.load(fileProp);
			
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public static Properties configProp() {
		try (InputStream fileProp = new FileInputStream("../config.properties")) {

			prop = new Properties();
			
			prop.load(fileProp);
			
		} catch (IOException io) {
			io.printStackTrace();
		}
		
		return prop;
	}

}
