package br.com.ajss.automation.pageobjects.conta;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.ajss.automation.LoginPage;
import net.sourceforge.marathon.javadriver.JavaDriver;


public class EfetuaSaqueCartaoPage extends LoginPage{
	

	public EfetuaSaqueCartaoPage(JavaDriver driver) {
		super(driver);		
	}

	public void saqueCartao(String comprovante) throws InterruptedException{
		
		selecionaMenu("Conta > Pagamento > Saque Com Cartão");
		
		Thread.sleep(1000);
		
		selecionaJanelaAtiva();
		
		List<WebElement> options = getDriver().findElements(By.cssSelector("combo-box::all-options"));
		options.get(5).click();
		
		clicaBotao("OK");
	
		selecionaJanelaAtiva();
		
		esperaPorElementoVisivel(By.cssSelector("internal-frame"));
		
		List<WebElement> frames = getDriver().findElements(By.cssSelector("internal-frame"));
		frames = getDriver().findElements(By.cssSelector("internal-frame"));
		frames.get(0);
		
		List<WebElement> valor = getDriver().findElements(By.className("com.diebold.sisag.view.layout.objetos.JTextFieldSisag"));
		valor.get(1).getAttribute("nomeCampo");
		valor.get(1).sendKeys("500,00");
		
		escreveSenha("1212");
		
		clicaBotao("CONSULTAR");

		authTransaction();
		
		selecionaJanela("SAQUE COM CARTÃO");
		
		esperaPorElementoVisivelClassName("com.diebold.sisag.view.layout.objetos.JButtonSisag");
		
		clicaBotao("EXECUTAR");
		
		selecionaJanelaAtiva();
		
		if (comprovante.equals("autenticacao")) {
			
			clicaBotao("AUTENTICAR");
			
			autenticaTransacao();	
			
			fechaJanelaSimuladorImpressoraAutenticadora();
			
			autenticaTransacao();
			
			fechaJanelaSimuladorImpressoraAutenticadora();
			
			autenticaTransacao();
			
			fechaJanelaSimuladorImpressoraAutenticadora();
		} 
		
		else if(comprovante.equals("impressao")){
			
			clicaBotao("IMPRIMIR COMPROVANTE");
			
			fechaJanelaSimuladorImpressora();
			
			fechaJanelaSimuladorImpressora();
		}
	}
}
