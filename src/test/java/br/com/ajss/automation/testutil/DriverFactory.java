package br.com.ajss.automation.testutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import net.sourceforge.marathon.javadriver.JavaDriver;
import net.sourceforge.marathon.javadriver.JavaProfile;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchMode;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchType;

public class DriverFactory
{
	private static JavaDriver driver;
	private JavaProfile profile;
	//private static String VERSAO = System.getProperty("versao");
	private static String VERSAO = PropReader.configProp().getProperty("VERSAO");
	private static String SIMULADOR = System.getProperty("simulador");
	private static String PATH_TEMP = "";
	private static File filePathTemp = new File(PATH_TEMP.toString());
	private static final String OUTPUT_DIRECTORY = "C:/opt/";
	private static File OUTPUT_PATH = new File(OUTPUT_DIRECTORY.toString());
	private static ClassLoader classLoader = new DriverFactory().getClass().getClassLoader();
	private static String swingInspectorPath = null;
	
	static String DIRETORIO_SWINGAPP = null;
	static String DIRETORIO_WEBAPP = null;
	static String JAVA_HOME = null;
	
	public JavaDriver getDriver()
	{
		if (driver==null) {

			identificaAmbiente();
			baixaVersao();
			
			
			profile = new JavaProfile(LaunchMode.JAVA_COMMAND_LINE)
			.setMainClass("br.com.ajss.main.Inicio")
			.addApplicationArguments(PropReader.configProp().getProperty("URL_APACHE"))
			.addApplicationArguments(VERSAO)
			.addVMArgument("-Dfile.encoding=ISO-8859-1")
			.addVMArgument("-Dusertimezone=bet")
			.addVMArgument("-Xmx512m", "-Xms512m")
			.setWorkingDirectory(DIRETORIO_SWINGAPP)
			.setJavaHome(JAVA_HOME)
			.setLaunchType(LaunchType.SWING_APPLICATION);

			try {
				profile.addVMArgument("-agentpath:" + swingInspectorPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			List<File> libsInFolder = null;
			try {
				libsInFolder = Files.walk(Paths.get(DIRETORIO_SWINGAPP + "lib"))
				        .filter(Files::isRegularFile)
				        .map(Path::toFile)
				        .collect(Collectors.toList());
				
				for (File lib : libsInFolder) {
					System.out.println(lib);
					profile.addClassPath(lib);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

	        LoggingPreferences prefs = new LoggingPreferences();
	        prefs.enable(LogType.DRIVER, Level.OFF);
	        DesiredCapabilities caps = JavaDriver.defaultCapabilities();
	        caps.setCapability(CapabilityType.LOGGING_PREFS, prefs);
	        
			driver = new JavaDriver(profile, caps);
			driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
			
		}
		return driver;
	}
	
	public static void identificaAmbiente() {
		switch (Ambiente.getOperatingSystemType()) {
		case Windows:
			System.out.println("Entrou no ambiente windows");
			DIRETORIO_SWINGAPP = "c:/opt/app/";
			JAVA_HOME = "C:/Program Files (x86)/Java/jdk1.6.0_45";
			swingInspectorPath = "C:\\opt\\swing-inspector\\windows\\Win32\\swing-inspector-agent.dll";
			break;
			
		case Linux:
			DIRETORIO_SWINGAPP = "/opt/app/";
			JAVA_HOME = "/usr/bin/java";
			swingInspectorPath = "/opt/swing-inspector/linux/i386/libswing-inspector-agent.so";
			break;
			
		case MacOS:
			DIRETORIO_SWINGAPP = "/opt/app/";
			JAVA_HOME = "/Library/Java/JavaVirtualMachines/jdk1.6.0_45.jdk/Contents/Home";
			swingInspectorPath = "/opt/swing-inspector/macos/libswing-inspector-agent.dylib";
			break;
			
		default:
			break;
		}
	}

}
