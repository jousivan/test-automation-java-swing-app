package br.com.ajss.automation.tests;

import java.io.IOException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import br.com.ajss.automation.LoginPage;
import br.com.ajss.automation.Page;

public abstract class TestBase
{
	private Page page;
	
	public TestBase() {
	super();
		initPage();
	}
	
	@BeforeClass
	public void logar() throws IOException, Exception
	{
		//Limpeza Garbage Colector
		System.gc();
		
		String retorno = page.getDriver().getTitle();
		if(page.getDriver() != null && !retorno.contains("Caixa Econômica Federal")){
			LoginPage loginPage = (LoginPage) page;
			loginPage.logar();
		}else if(page.getDriver() != null && retorno.contains("Caixa Econômica Federal")) {
			System.out.println("Usuário já está logado.");
		}
	}
    
  @AfterSuite
   public void posCondicao() throws InterruptedException
   {   	
	  logOff();
	  closeDriver();
   }

	private void closeDriver()
	{		
		page.getDriver().quit();
		
		/** Mata processos do chromedriver.exe que fica em memória */
		Runtime rt = Runtime.getRuntime();
	    try {
	        rt.exec("taskkill /f /im chromedriver.exe /t");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private void logOff() throws InterruptedException
	{
		LoginPage loginPage = (LoginPage) page;
		loginPage.logoff();
	}
	
	private void initPage()
	{
		this.page = getInstacePage();
	}
	
	public abstract Page getInstacePage();

}
