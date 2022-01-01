package br.com.ajss.automation.testutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;

import br.com.ajss.automation.Page;
import net.sourceforge.marathon.javadriver.JavaDriver;


public class ScreenshotUtil
{
	//private static String fileSeperator = System.getProperty("file.separator");
	private Page page;
	
	private static ScreenshotUtil uniqueInstance;
	 
	    private ScreenshotUtil(Package pack, String classe, String nomeTeste) throws IOException {
	    	String pacote = pack.toString();
			pacote = pacote.replace("pageobjects", "tests");
			pacote = StringUtils.substringAfter(pacote, "tests.");
			
			classe = StringUtils.substringBefore(classe, "Test");
			classe = StringUtils.substringBefore(classe, "Page");
			
			String path = (System.getProperty("user.dir") + "/target/surefire-reports/screnshots/" + pacote + "/" + classe + "/");
			
			Path pat = Paths.get(path);
			File filePath = new File(pat.toString());
			
			if (!filePath.exists()) {
				filePath.mkdir();
				System.out.println("File created " + filePath);
			}
			
			File scrFile = ((TakesScreenshot) page.getDriver()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(path + nomeTeste + ".png"));
			String pathScreenshot = (path + nomeTeste + ".png");
			Reporter.log("<br /><td><a href='" + pathScreenshot + "'><img src='" + pathScreenshot + "' height='100' width='100' /></a></td><br />");
	    }
	 
	    public static synchronized ScreenshotUtil getInstance(Package pack, String classe, String nomeTeste) throws IOException {
	        if (uniqueInstance == null)
	            uniqueInstance = new ScreenshotUtil(pack, classe, nomeTeste);
	 
	        return uniqueInstance;
	    }
	
	
	
	
	
	public static void takeScreenshot(JavaDriver driver, String nomeTeste) throws Exception {
		File screenShotName;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/target/surefire-reports/screnshots/";
		screenShotName = new File(path + nomeTeste + ".png");
		FileUtils.copyFile(scrFile, screenShotName);
		String filePath = screenShotName.toString();
		Reporter.log("<br /><td><a href='" + filePath + "'><img src='" + filePath + "' height='100' width='100' /></a></td><br />");
	}

	public static void screenshot(JavaDriver driver, Package pack, String classe, String nomeTeste){
		
		String pacote = pack.toString();
		pacote = pacote.replace("pageobjects", "tests");
		pacote = StringUtils.substringAfter(pacote, "tests.");
		
		classe = StringUtils.substringBefore(classe, "Test");
		classe = StringUtils.substringBefore(classe, "Page");
		
		String path = (System.getProperty("user.dir") + "/target/surefire-reports/screnshots/" + pacote + "/" + classe + "/");
		
		Path pat = Paths.get(path);
		File filePath = new File(pat.toString());
		
		if (!filePath.exists()) {
			filePath.mkdir();
			System.out.println("File created " + filePath);
		}
		
		//erro na linha abaixo heap space
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(path + nomeTeste + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String pathScreenshot = (path + nomeTeste + ".png");
		Reporter.log("<br /><td><a href='" + pathScreenshot + "'><img src='" + pathScreenshot + "' height='100' width='100' /></a></td><br />");
	}
}
