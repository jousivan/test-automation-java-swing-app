package br.com.ajss.automation.testutil;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavegadorUtil {

	private WebDriver driver;

	public NavegadorUtil(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public void fechar() {
		driver.quit();
	}


	public WebElement esperaPorElementoVisivel(By localizacao, int segundos) {
		return new WebDriverWait(driver, segundos).until(ExpectedConditions.visibilityOfElementLocated(localizacao));
	}

	public void esperaElementoDesaparecer(By localizacao, int segundos) {
		new WebDriverWait(driver, segundos).until(ExpectedConditions.invisibilityOfElementLocated(localizacao));
	}

	public WebElement procuraCampoPor(String atributo, String valor) {

		List<WebElement> elementos = driver.findElements(By.xpath("//input"));

		for (WebElement elemento : elementos) {
			if (elemento.getAttribute(atributo).contains(valor)) {
				return elemento;
			}
		}
		return null;
	}

	public WebElement procuraLinkPor(String atributo, String valor) {

		List<WebElement> elementos = driver.findElements(By.xpath("//a"));

		for (WebElement elemento : elementos) {
			if (elemento.getAttribute(atributo).contains(valor)) {
				return elemento;
			}
		}
		return null;
	}

	public boolean esperarCarregar() {

		boolean terminou = true;

		try {
			terminou = new WebDriverWait(driver, 15).until(ExpectedConditions
					.invisibilityOfElementLocated(By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content "
							+ "ui-corner-all ui-shadow loadingBRB ui-overlay-visible']")));

			if (!terminou)
				esperarCarregar();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return terminou;
	}

	public void esperar(int segundos) {
		try {
			new WebDriverWait(driver, segundos).until(ExpectedConditions.alertIsPresent());
		} catch (Exception e) {
		}
	}

	@Deprecated
	public NavegadorUtil moveSobre(WebElement elemento) {
		// new Actions(driver).moveToElement(elemento).perform();
		Actions actions = new Actions(driver);
		actions.moveToElement(elemento);
		actions.perform();
		return this;
	}

	@Deprecated
	public void mouseSobreEclicar(WebElement elemento) {
		Actions actions = new Actions(driver);
		actions.moveToElement(elemento);
		actions.click().perform();
	}

	public boolean telaContem(String texto) {
		esperar(2);
		String pagina = driver.findElement(By.id("conteudo")).getText();
		return pagina.contains(texto);
	}

	public void selecionaOpcaoNaLista(WebElement listaSelelecao, String opcao) {

		listaSelelecao.click();

		List<WebElement> opcoesLista = driver.findElements(By.xpath("//option"));

		for (WebElement option : opcoesLista) {
			if (option.getText().contains(opcao)) {
				option.click();
				break;
			}
		}
	}

	public void abrirNovaJanela(String url) {
		((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", url);
	}

	public void mudarParaJanela(String janela) {
		driver.switchTo().window(janela);
		// ((JavascriptExecutor) driver).executeScript("window.focus();");
	}

	public WebElement linhaTabelaCom(String texto) {
		// int posicao = 0;
		List<WebElement> linhas = driver.findElements(By.xpath("//tr"));

		for (WebElement linha : linhas) {
			if (linha.getText().contains(texto)) {
				return linha;
			}
		}
		return null;
	}

	public boolean linhaPossuiValor(WebElement linhaTabela, String valor) {
		boolean resultado = false;

		if (valor != null && valor != "")
			resultado = linhaTabela.getText().contains(valor);

		return resultado;
	}

	public String obtemConteudoDaTela() {
		return driver.getPageSource();
	}

	public List<WebElement> procuraElementosPor(By localizador) {
		esperaPorElementoVisivel(localizador, 10);
		return driver.findElements(localizador);
	}

	/**
	 * Define a plataforma 'Android' ou 'IOS' para executar pelo Ionic.
	 * 
	 * @param urlPlataforma
	 */



	public void maximizaJanela() {
		driver.manage().window().maximize();
	}

	public void carregando() {

		try {
			WebElement loading = getDriver().findElement(By.tagName("ion-loading"));
			while (loading.isDisplayed())
				esperar(1);
		} catch (Exception e) {
		}

		try {
			WebElement spinner = getDriver().findElement(By.tagName("ion-spinner"));
			while (spinner.isDisplayed())
				esperar(1);
		} catch (Exception e) {
		}

	}

	public WebElement esperarPorElementoClicavel(By localizacao, int segundos) {
		return new WebDriverWait(driver, segundos).until(ExpectedConditions.elementToBeClickable(localizacao));
	}

//	public void capturaErroAssertion(AssertionError e) {
//		String error = "\n";
//		for (int i = 0; i < e.getStackTrace().length; i++) {
//			error += e.getStackTrace()[i].toString() + "\n";
//		}
//		ArquivoControl.adicionar("> Resultado dos testes: FALHA!\n" + error + "\n");
//		Assert.fail(error);
//	}

//	public void capturaErroException(Exception e) {
//		String error = "\n";
//		for (int i = 0; i < e.getStackTrace().length; i++) {
//			error += e.getStackTrace()[i].toString() + "\n";
//		}
//		ArquivoControl.adicionar("> Resultado dos testes: FALHA!\n" + error + "\n");
//		Assert.fail(error);
//	}

	public boolean esperaFicarVisivel(WebElement elemento, int segundos) {

		boolean visivel = false;

		try {
			int tempo = 0;
			while (elemento.isDisplayed() == false && tempo < segundos) {
				esperar(1);
				tempo++;
			}

			visivel = elemento.isDisplayed();

		} catch (Exception e) {
		}
		return visivel;
	}

	public WebElement esperarPorElementoClicavel(WebElement elemento, int segundos) {
		return new WebDriverWait(driver, segundos).until(ExpectedConditions.elementToBeClickable(elemento));
	}

	public void focoEm(WebElement elemento) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elemento);
	}

	public void movePaginaPara(WebElement elemento, Keys key) {
		elemento.click();
		new Actions(driver).sendKeys(key).perform();
		esperar(1);
	}

	public void clicaArrasta(WebElement elemento, int x, int y) {
		new Actions(driver).clickAndHold(elemento).moveByOffset(x, y).perform();
	}

	@Deprecated
	public void seguraEArrastaDePara(WebElement elemento, int x, int y) {
		Actions actions = new Actions(driver);
		Action dragAndDrop = actions.clickAndHold(elemento).moveToElement(elemento, x, y).release().build();
		dragAndDrop.perform();
	}
	
//	 public void aguardarElementoVisivel(String name){
//		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
//	 }
	 
	 public void jse(String script){
		 JavascriptExecutor jse = (JavascriptExecutor) driver;
	     jse.executeScript(script);
	 }
 
	public void acessoMatriculaSenha(){
		driver.findElement(By.id("divAcessoMatriculaSenha")).click();
		aguardarTempo(2);
	}
	 
	public void aguardarTempo(int segundos) {
			try {
				new WebDriverWait(driver, segundos).until(ExpectedConditions.alertIsPresent());
			} catch (Exception e) {
			}
	}
		

}
