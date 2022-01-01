package br.com.ajss.automation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.ajss.automation.testutil.GetCPFCNPJ;
import br.com.ajss.automation.testutil.datapool.DatapoolUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.Reporter;

import la.foton..automation.simulador.SimuladorCertificado;
import net.sourceforge.marathon.javadriver.JavaDriver;

public class Page {

	private JavaDriver driver;
	private WebDriverWait wait;
	private String agencia;
	private String stringCertificadoGerado;
	
	public Page(JavaDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	private enum TipoElemento {
		
		BUTTON_GROUP("view.layout.objetos.JButtonGroup"),
		BUTTON("view.layout.objetos.JButton"),
		CHECKBOX_MENU_ITEM("view.layout.objetos.JCheckBoxMenuItem"),
		CHECKBOX("view.layout.objetos.JCheckBox"),
		COMBOBOX("view.layout.objetos.JComboBox"),
		FORMATTED_TEXTFIELD("view.layout.objetos.JFormattedTextField"),
		LABEL_SCROLL("view.layout.objetos.JLabelScroll"),
		LABEL("view.layout.objetos.JLabel"),
		LIST_FITA_AUDITORIA("view.layout.objetos.JListFitaAuditoria"),
		OPTION_COMBO("view.layout.objetos.JOptionCombo"),
		PASSWORD_FIELD("view.layout.objetos.JPasswordField"),
		RADIO_BUTTON("view.layout.objetos.JRadioButton"),
		SCROLL_TEXTAREA("view.layout.objetos.JScrollForTextArea"),
		SEPARATOR("view.layout.objetos.JSeparator"),
		TABLE_LINHAS_CORES_ALTERNADAS("view.layout.objetos.JTableLinhasCoresAlternadas"),
		TABLE("view.layout.objetos.JTable"),
		TABLE_CUSTOM("view.layout.objetos.JTableCustom"),
		TEXTAREA_INTERNO("view.layout.objetos.JTextAreaInterno"),
		TEXTAREA_SCROLL("view.layout.objetos.JTextAreaScroll"),
		TEXTAREA("view.layout.objetos.JTextArea"),
		TEXTFIELD("view.layout.objetos.JTextField"),
		TEXTPANE("view.layout.objetos.JTextPane"),
		TABLE_LINHA("view.layout.objetos.LinhaJTable"),
		;
		
		private TipoElemento(String className) {
			this.className = className;
		}
		
		private  String className;

		public String getClassName() {
			return className;
		}
		
		
	}

	public JavaDriver getDriver() {
		return driver;
	}
	
	// Seleciona Janela
	public void selecionaJanelaAtiva() {
		esperaTempo(1);
		driver.switchTo().window(driver.getTitle());
	}
	
	// Seleciona Janela
	public void selecionaJanela(String window) {
		esperaTempo(2);
		if(driver.switchTo().window(window) != null) {
			driver.switchTo().window(window);
		}
	}
	
	// Seleciona Janela
	public String getTextoJanela() {
		esperaTempo(2);
		return driver.getTitle();
	}
	
	// Seleciona Janela
	public boolean verificaJanelaSelecionada(String window) {
		boolean ret = false;
		esperaTempo(2);
		
		try {
			driver.switchTo().window(window);
			ret = true;
		} catch (Exception e) {
			System.out.println("Tela não encontrada");
		}
//		if(driver.switchTo().window(window) != null) {
//			driver.switchTo().window(window);
//			ret = true;
//		}

		return ret;
	}
		
	/** Verifica se Frame está selecionado **/
	public boolean verificaFrame(String frame) {
		boolean retorno = false;
		
		esperaTempo(1);
		selecionaJanelaAtiva();
		try {

	        List<WebElement> frames = driver.findElements(By.cssSelector("internal-frame"));		
	        for (WebElement frameElement : frames) {
				if (frameElement.getAttribute("title").equals(frame)) {
					if (frameElement.isDisplayed()) {
						retorno = true;
					}else {
						driver.switchTo().window(frame);
						retorno = true;
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return retorno;
	}
	
	/** Seleciona Frame **/
	public void selecionaFrame(String frame) {
		esperaTempo(1);
		selecionaJanelaAtiva();
		try {
	        List<WebElement> frames = driver.findElements(By.cssSelector("internal-frame"));		
	        for (WebElement frameElement : frames) {
	        	System.out.println(frameElement.getAttribute("title"));
				if (frameElement.getAttribute("title").equals(frame)) {
					if (frameElement.isDisplayed()) {
						
					}else {
						driver.switchTo().window(frame);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/** Seleciona InternalFrame **/
	public void selecionaInternalFrame() {
		esperaTempo(1);
		/** Espera pelo frame */
		//esperaPorElementoVisivel(By.cssSelector("internal-frame"));
		esperaPorElementoVisivel(By.className("view.JInternalFrame"));
		
		/** Dar foco no frame */
		List<WebElement> frames = getDriver().findElements(By.cssSelector("internal-frame"));
		frames = getDriver().findElements(By.cssSelector("internal-frame"));
		frames.get(0);
	}

	/** Selecionar Menu passando o caminho da funcionalidade
	 * exemplo: Conta > Pagamento > Saque Com Cartão **/
	public void selecionaMenu(String caminhoMenu) {
		selecionaJanela("");
		String[] menuCompleto = caminhoMenu.split(" > ", 15);
		System.out.println("Menu completo informado: " + Arrays.toString(menuCompleto));
		//selecionaJanelaAtiva();
		
		for (int i = 0; i < menuCompleto.length; i++) {
		//for (String menu : menuCompleto) {
			List<WebElement> opcoesMenus1 = driver.findElements(By.className("javax.swing.JMenu"));
			opcoesMenus1.addAll( driver.findElements(By.className("javax.swing.JMenuItem")));
			for (WebElement webElement : opcoesMenus1) {
				if(menuCompleto[i].equals(webElement.getText())) {
					if ((menuCompleto.length -1 == i)) {
						esperaTempo(1);
						System.out.println("Acessando Menu Selenium: " + webElement.getText());
						webElement.click();
						esperaTempo(1);
					}else {
						esperaTempo(1);
						System.out.println("Acessando Menu Selenium: " + webElement.getText());
						webElement.click();
						esperaTempo(1);
						break;
					}
				}
			}
		}
	}

	/** Abre funcionalidade do atendimento integrado e fica disponível para acessar a funcionalidade específica **/
	public void iniciarAtendimentoIntegrado() {
		fechaAtendimentoIntegrado();
		
		selecionaMenu("Atendimento Integrado > Iniciar Atendimento");
		selecionaJanelaAtiva();
		esperaTempo(1);
		//selecionaFrame("ATENDIMENTO INTEGRADO");
		selecionaInternalFrame();
		
		escreveCampoJFormattedTextField("CAMPO_CPF_CNPJ", GetCPFCNPJ.geraCPF());
		clicaBotao("EXECUTAR");
		
		//selecionaFrame("ATENDIMENTO INTEGRADO");
		esperaBotao("DÉBITO COM CARTÃO");
	}
	
	/** Clica numa aba específica no Simulador Leitor de Código de Barras **/
	public void clicaAbaSimuladorLeitorCodBarras(String nomeElemento) {
		selecionaJanela("Simulador Leitor de Código de Barras");
        List<WebElement> tabs = getWebElementBySelector(("tabbed-pane::all-tabs"));
        for (WebElement tab : tabs) {
			if (tab.getText().equals(nomeElemento)) {
				tab.click();
			}
		}
	}
	
	/** Fecha Janela Simulador Leitor CodBarras **/
	public void fechaJanelaSimuladorLeitorCodBarras() {
		//esperaPorElementoVisivel(By.className("view.Dialog"));
		try {
			esperaTempo(1);
			selecionaJanela("Simulador Leitor de Código de Barras");
			clicaBotao("Cancelar");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/** fecha Janela Simulador Cartao **/
	public void fechaJanelaSimuladorCartao() {
		try {
			esperaTempo(1);
			selecionaJanela("Simulador Cartão");
			clicaBotao("Cancelar");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/** Captura e retorna mensagem de sucesso na tela do  **/
	public String getMensagemSucesso(){
		selecionaJanela("");
		//selecionaJanelaAtiva();
		esperaPorElementoVisivel(By.cssSelector("text-area"));
		
		 List<WebElement> msgSucesso = getDriver().findElements(By.cssSelector("text-area"));
		 System.out.println(msgSucesso.get(0).getText());
		 String mensagemRetorno = null;
		 
		 int tamanhoMsgSucesso = msgSucesso.size();
		 for (int i = 0; i < tamanhoMsgSucesso; i++) {
			 if (msgSucesso.get(i).getText().equals("SUCESSO NA EXECUÇÃO DO SERVIÇO")) {
				 //assertThat(msgSucesso.get(i).getText().equals("SUCESSO NA EXECUÇÃO DO SERVIÇO"));
				 mensagemRetorno = msgSucesso.get(i).getText();
			}
		}
		return mensagemRetorno;
	}
	
	/** Captura e retorna mensagem na tela principal do  **/
	public String getMensagemFinal(String mensagemEsperada){
		selecionaJanelaAtiva();
		esperaPorElementoVisivel(By.cssSelector("text-area"));
		
		List<WebElement> msgSucesso = getDriver().findElements(By.cssSelector("text-area"));
		System.out.println(msgSucesso.get(0).getText());
		String mensagemRetorno = null;
		
		for (int i = 0; i < msgSucesso.size(); i++) {
			if (msgSucesso.get(i).getText().contains(mensagemEsperada)) {
				mensagemRetorno = msgSucesso.get(i).getText();
			}
		}
		return mensagemRetorno;
	}

	
	/** Autoriza Transação considerando que o campo matricula já está informado **/
	public void authTransaction() {
		selecionaJanela("AUTORIZAÇÃO");
		escreveSenhaPadrao();
		
		clicaBotao("EXECUTAR");
	}
	
	/** Autoriza Transação considerando que o campo matricula já está informado **/
	public void autorizaTransacao() {
		selecionaJanela("AUTORIZAÇÃO");
		escreveSenhaPadrao();
		
		clicaBotao("EXECUTAR");
	}
	
	/** Autoriza Transação informando matrícula específica **/
	public void authTransaction(String matricula) {
		selecionaJanela("AUTORIZAÇÃO");
		WebElement matriculaAcumulador = getDriver().findElementByClassName("view.layout.objetos.JFormattedTextField");
		matriculaAcumulador.sendKeys(matricula);
		
		escreveSenhaPadrao();
		
		clicaBotao("EXECUTAR");
	}
	
	/** Clica em botão numa lista de botões por Selector CSS **/
	public void clicaBotaoPorSelectorNaPosicao(int position) {
		List<WebElement> buttons = driver.findElements(By.cssSelector("button"));
	    buttons.get(position).click();
	}
	
	/** Clica em botão por Selector CSS **/
	public void clicaBotaoPorSelector() {
		WebElement button = driver.findElement(By.cssSelector("button"));
	    button.click();
	}

	/** Clica em botão na Lista por ClassName **/
	public void clicaBotaoPorClassNameNaPosicao(int position) {
		List<WebElement> buttons = driver.findElementsByClassName("view.layout.objetos.JButton");
		buttons.get(position).getAttribute("focoInicial");
		//buttons.get(position).click();
		
		List<WebElement> teste = driver.findElements(By.className("view.layout.objetos.JTextField"));
		//List<WebElement> teste = driver.findElementsByClassName("view.layout.objetos.JTextField");
		teste.get(position).getAttribute("nomeCampo");
	}
	
	public void clicaBotao(String nome) {
		//selecionaJanelaAtiva();
		List<WebElement> botoes = driver.findElementsByClassName("view.layout.objetos.JButton");
		for (WebElement botao : botoes) {
			if (botao.getText().equals(nome)) {
				botao.click();
				break;
			}
		}
	}
	
	public void clicaBotaoGenerico(String nome) {
		//selecionaJanelaAtiva();
		List<WebElement> botoes = driver.findElementsByClassName("javax.swing.JButton");
		for (WebElement botao : botoes) {
			if (botao.getText().equals(nome)) {
				botao.click();
				break;
			}
		}
	}
	
	public void clicaBotaoGenericoEnter() {
		WebElement kb = getDriver().findElementByClassName("javax.swing.JButton");
		kb.sendKeys(Keys.ENTER);
	}
	
	public void focaBotaoGenerico(String nome) {
		//selecionaJanelaAtiva();
		List<WebElement> botoes = driver.findElementsByClassName("javax.swing.JButton");
		for (WebElement botao : botoes) {
			if (botao.getText().equals(nome)) {
				botao.sendKeys("");
				break;
			}
		}
	}
	
	
	public WebElement elementoGenerico(String classe, String nome) {
		WebElement retorno = null;
		List<WebElement> elementos = driver.findElementsByClassName(classe);
		for (WebElement elemento : elementos) {
			if (elemento.getText().equals(nome)) {
				retorno = elemento;
				break;
			}
		}
		return retorno;
	}
	
	public void clicaBotaoId(String nome) {
		//selecionaJanelaAtiva();
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.BUTTON).getClassName());
		for (WebElement botao : botoes) {
			if (botao.getAttribute("idCampo").equals(nome)) {
				botao.click();
			}
		}
	}
	
	//Escrever em campo TextField por ClassName
	public void clicaCampoJFormattedTextField(String id) {
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.FORMATTED_TEXTFIELD).getClassName());
		for (WebElement botao : botoes) {
			System.out.println(botao.getAttribute("idCampo"));
			if (botao.getAttribute("idCampo").equals(id)) {
				botao.click();
			}
		}
	}
	
	//Escrever em campo TextField
	public void clicaCampoTextField(String id) {
		getWebElement(TipoElemento.TEXTFIELD, id).click();
		
	}
	
	/** Foca botao**/
	public void focaBotaoId(String nome) {
		selecionaJanelaAtiva();
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.BUTTON).getClassName());
		for (WebElement botao : botoes) {
			if (botao.getAttribute("idCampo").equals(nome)) {
				botao.isDisplayed();
				botao.sendKeys("");
			}
		}
	}
	
	/** Retorna um elemento botão**/
	public WebElement getElementBotaoId(String nome) {
		WebElement retorno = null;
		selecionaJanelaAtiva();
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.BUTTON).getClassName());
		for (WebElement botao : botoes) {
			if (botao.getAttribute("idCampo").equals(nome)) {
				botao.isDisplayed();
				retorno =  botao;
			}
		}
		return retorno;
	}
	
	
	//Clica em botão por ClassName
	public void clicaBotaoPorClassName() {
		WebElement button = driver.findElementByClassName("view.layout.objetos.JButton");
	    button.click();
	}
	
	//Digita Senha Gerente Geral
	public void escreveMatriculaGerenteGeral() {
		WebElement matriculaAcumulador = driver.findElementByClassName("view.layout.objetos.JFormattedTextField");
		matriculaAcumulador.sendKeys("C897374");
		
	}
	
	//Informar senha padrão
	public void escreveSenhaPadrao() {
		WebElement senhaAcumulador = driver.findElementByClassName("view.layout.objetos.JPasswordField");
		senhaAcumulador.sendKeys("123456");
	}
	
	//Informar senha cartão
	public void escreveSenha(String password) {
		WebElement senhaAcumulador = driver.findElementByClassName("view.layout.objetos.JPasswordField");
		senhaAcumulador.sendKeys(password);
	}

	public void escreveCampoSenhaId(String id, String dado) {
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.PASSWORD_FIELD).getClassName());
		for (WebElement botao : botoes) {
			//System.out.println(botao.getAttribute("idCampo"));
			if (botao.getAttribute("idCampo").equals(id)) {
				//botao.click();
				botao.sendKeys(dado);
				break;
			}
		}
		
	}
	
	//Escrever em campo TextField por CSS Selector
	public void escreveCampoTextFieldPorSelector(String data) {
		 WebElement TextFiel = driver.findElement(By.cssSelector("text-field"));
	     //WebElement textClass = driver.findElementByClassName("view.layout.objetos.JTextField");
		 TextFiel.sendKeys(data);
		
	}
	
	public void escreveCampoTextFieldPorSelectorNaPosicao(int position, String value) {
		 List<WebElement> textField = driver.findElements(By.cssSelector("text-field"));
	     //WebElement textClass = driver.findElementByClassName("view.layout.objetos.JTextField");
		 textField.get(position).sendKeys(value);
		
	}
	
	//Escrever em campo TextField por ClassName
	public void escreveCampoTextFieldByClassName(String data) {
	     WebElement TextField = driver.findElementByClassName("view.layout.objetos.JTextField");
		 TextField.sendKeys(data);
		
	}

	public void escreveCampoTextField(String id, String dado) {
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.TEXTFIELD).getClassName());
		for (WebElement botao : botoes) {
			//System.out.println(botao.getAttribute("idCampo"));
			if (botao.getAttribute("idCampo").equals(id)) {
				//botao.click();
				botao.sendKeys(dado);
				break;
			}
		}
		
	}
	
	public WebElement getJTextPane(String id) {
		WebElement retorno = null;
		List<WebElement> elements = getDriver().findElementsByClassName((TipoElemento.TEXTPANE).getClassName());
		for (WebElement element : elements) {
			//System.out.println(botao.getAttribute("idCampo"));
			if (element.getAttribute("idCampo").equals(id)) {
				retorno = element;
				break;
			}
		}
		return retorno;
		
	}
	
	 //Verifica se campo JTextField está habilitado
	public boolean verificaCampoTextFieldHabilitado(String id) {
		return getWebElement(TipoElemento.TEXTFIELD, id).isEnabled();

	}
	
	public String getTextoCampoTextField(String id) {
		return getWebElement(TipoElemento.TEXTFIELD, id).getText();
	}
	
	public String getTextoCampoJLabel(String id) {
		return getWebElement(TipoElemento.LABEL, id).getText();
	}
	
	public String getTextoCampoJLabelGenerico() {
		WebElement label = getDriver().findElementByClassName("javax.swing.JLabel");
		return label.getText();
	}
	
	//Escrever em campo TextField por ClassName numa Lista
	public void escreveCampoTextFieldPorClassNameNaPosicao(int position, String value) {
		 List<WebElement> textField = driver.findElementsByClassName("view.layout.objetos.JTextField");
		 textField.get(position).sendKeys(value);
	}
	
	public void clicaLinhaNaTabela(int rowNum) {
		
		WebElement table = getDriver().findElementByClassName("view.layout.objetos.JTableLinhasCoresAlternadas");

		WebElement row = table.findElement(By.cssSelector(".::mnth-cell(" + (rowNum) + ",3)"));
        row.click();
	}
	
	public void clicaCelulaNaTabela(int rowNum, int colNum) {
		
		WebElement table = getDriver().findElementByClassName("view.layout.objetos.JTableLinhasCoresAlternadas");
		
		WebElement row = table.findElement(By.cssSelector(".::mnth-cell(" + (rowNum) + "," + colNum + ")"));
		row.click();
	}
	
	public String getValorCelulaNaTabela(int rowNum, int colNum) {
		WebElement table = getDriver().findElementByClassName("view.layout.objetos.JTableLinhasCoresAlternadas");
		WebElement row = table.findElement(By.cssSelector(".::mnth-cell(" + (rowNum) + "," + colNum + ")"));
        return row.getText();
	}
	
	public void getLinhaNaTabela(int rowNum) {
		
		WebElement table = getDriver().findElementByClassName("view.layout.objetos.JTableLinhasCoresAlternadas");
		
        Object[][] data = { { "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) },
                { "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
                { "Sue", "Black", "Knitting", new Integer(2), new Boolean(false) },
                { "Jane", "White", "Speed reading", new Integer(20), new Boolean(true) },
                { "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };
        
        for (int i = 0, ii = 1; i < data.length; i++, ii++) {
            for (int j = 0, jj = 1; j < data[i].length; j++, jj++) {
                //AssertJUnit.assertEquals(data[i][j].toString(),
                String cell = driver.findElement(By.cssSelector("table::mnth-cell(" + ii + ", " + jj + ")")).getText();
                
            }
        }
        // Check the value from the check-boxes
        for (int i = 0, ii = 1; i < data.length; i++, ii++) {
            WebElement cbCell = driver.findElement(By.cssSelector("table::mnth-cell(" + ii + ", " + 5 + ")"));
            AssertJUnit.assertEquals(data[i][4].toString(), cbCell.getAttribute("selected"));
        }
	}
	
	//Escrever em campo TextField por ClassName
	public void escreveCampoJFormattedTextField(String id, String data) {
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.FORMATTED_TEXTFIELD).getClassName());
		for (WebElement botao : botoes) {
			//System.out.println(botao.getAttribute("idCampo"));
			if (botao.getAttribute("idCampo").equals(id)) {
				//botao.click();
				botao.sendKeys(data);
				break;
			}
		}
	}
	
	public WebElement getWebElementById(String classname, String id) {
		WebElement retorno = null;
		List<WebElement> botoes = getDriver().findElementsByClassName(classname);
		for (WebElement botao : botoes) {
			//System.out.println(botao.getAttribute("idCampo"));
			if (botao.getAttribute("idCampo").equals(id)) {
				retorno =  botao;
				break;
			}
		}
		return retorno;
	}
	
	//Escrever em campo TextArea pelo 
	public void escreveCampoTextArea(String id, String data) {
		List<WebElement> campos = getDriver().findElementsByClassName((TipoElemento.TEXTAREA).getClassName());
		for (WebElement campo : campos) {
			if (campo.getAttribute("idCampo").equals(id)) {
				campo.sendKeys(data);
				//break;
			}
		}
	}
	
	//Escrever em campo TextArea pelo nome
	public void escreveTextAreaPorNome(String id, String data) {
		List<WebElement> campos = getDriver().findElementsByClassName((TipoElemento.TEXTAREA).getClassName());
		for (WebElement campo : campos) {
			if (campo.getAttribute("nomeCampo").equals(id)) {
				campo.sendKeys(data);
				//break;
			}
		}
	}
	
	//Envia um comando de teclado para a aplicação
	public void escreveCampoJFormattedTextField(String id, Keys key) {
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.FORMATTED_TEXTFIELD).getClassName());
		for (WebElement botao : botoes) {
			System.out.println(botao.getAttribute("idCampo"));
			if (botao.getAttribute("idCampo").equals(id)) {
				//botao.click();
				botao.sendKeys(key);
				//break;
			}
		}
	}
	
	//Escrever em campo TextField por ClassName
	public void focaCampoJFormattedTextField(String id) {
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.FORMATTED_TEXTFIELD).getClassName());
		for (WebElement botao : botoes) {
			System.out.println(botao.getAttribute("idCampo"));
			if (botao.getAttribute("idCampo").equals(id)) {
				botao.click();
				//break;
			}
		}
	}
	
	public void focaCampoTextField(String id) {
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.TEXTFIELD).getClassName());
		for (WebElement botao : botoes) {
			System.out.println(botao.getAttribute("idCampo"));
			if (botao.getAttribute("idCampo").equals(id)) {
				botao.click();
			}
		}
	}
	
	public void limpaCampoJFormattedTextField(String id) {
		List<WebElement> botoes = getDriver().findElementsByClassName((TipoElemento.FORMATTED_TEXTFIELD).getClassName());
		for (WebElement botao : botoes) {
			System.out.println(botao.getAttribute("idCampo"));
			if (botao.getAttribute("idCampo").equals(id)) {
				botao.clear();
				//break;
			}
		}
	}
	
	//Escrever em campo TextField por ClassName numa Lista
	public void escreveCampoJFormattedTextFieldPorClassNameNaPosicao(int position, String value) {
		List<WebElement> textField = driver.findElementsByClassName("view.layout.objetos.JFormattedTextField");
		textField.get(position).sendKeys(value);
	}
	
	
	/** Pega Texto de label e elementos*/
	public String getElemento(String texto){
		selecionaJanelaAtiva();
		esperaPorElementoVisivel(By.cssSelector("label"));
		
		 List<WebElement> msgSucesso = getDriver().findElements(By.cssSelector("label"));
		 System.out.println(msgSucesso.get(0).getText());
		 String mensagemRetorno = null;
		 
		 for (int i = 0; i < msgSucesso.size(); i++) {
			 if (msgSucesso.get(i).getText().contains(texto)) {
				 mensagemRetorno = msgSucesso.get(i).getText();
			}
		}
		return mensagemRetorno;
	}
	
	public void sendKeyToKeyboard(String className, Keys key) {
		WebElement kb = getDriver().findElementByClassName("view.layout.objetos."+className);
		kb.sendKeys(key);
	}
	
	/**
	 * Retorna o objeto Wait. A partir do objeto wait pode-se colocar uma condição
	 * especifica de espera que deve ser atendida
	 **/
	public WebDriverWait getWait() {
		this.wait = new WebDriverWait(driver, 30);
		return wait;
	}
	
	public Wait<WebDriver> waitDriver() {
		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		return wait;
	}

	private void escreve(By by, String texto) {
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(texto);
	}

	public void escrever(String id_campo, String texto) {
		escreve(By.name(id_campo), texto);
	}

	public void escreverPorName(String id_campo, String texto) {
		escreve(By.name(id_campo), texto);
	}

	public void escreverPorId(String id, String texto) {
		escreve(By.id(id), texto);
	}

	public void escreverSemLimpar(String id, String texto) {
		getDriver().findElement(By.id(id)).sendKeys(texto);
	}

	// Executar javascript
	public void jsexecutor(String script) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript(script);
	}

	// Selecionar item na lista - atualizar
	public void selecionaOpcaoNaLista(WebElement listaSelecao, String opcao) {

		listaSelecao.click();

		List<WebElement> opcoesLista = driver.findElements(By.xpath("//option"));

		for (WebElement option : opcoesLista) {
			if (option.getText().contains(opcao)) {
				option.click();
				break;
			}
		}
	}

	public void selecionaItem(String campo, String opcao) {
		Select listaSelecao = new Select(obterElementoPorId(campo));
		listaSelecao.selectByVisibleText(opcao);

		
	}
	
	public void selecionaOpcao(String idCampo, String valor) {
		
		//List<WebElement> jcombobox = getDriver().findElements(By.className("view.layout.objetos.JComboBox"));
		//List<WebElement> jcombobox = getDriver().findElements(By.cssSelector("combo-box::all-options"));
		List<WebElement> jcombobox = getDriver().findElements(By.cssSelector("combo-box"));
		
		for (int i = 0; i < jcombobox.size() -1 ; i++) {
			
			if (jcombobox.get(i).getAttribute("idCampo").equals(idCampo)) {
				
//				System.out.println(jcombobox.get(i).getAttribute("idCampo"));
//				System.out.println(jcombobox.get(i).getAttribute("content"));
//				System.out.println(jcombobox.get(i).getAttribute("selectedIndex"));
				jcombobox.get(i).click();
				
				String teste = jcombobox.get(i).getAttribute("content");
				teste = teste.replace("[[", "");
				teste = teste.replace("]]", "");
				teste = teste.replace("\"", "");
				String[] teste2 = teste.split(",");
				
				
				//System.out.println(Arrays.toString(teste2));
				
				WebElement option;
				 for (int k = 0; k < teste2.length; k++) {
			            option = jcombobox.get(i).findElement(By.cssSelector(".::nth-option(" + (k + 1) + ")"));
			            
			            if (option.getText().equals(valor)) {
			            	option.click();
			            	break;
						}	
			        }			
			}
			
		}
	}
	
	public void selecionaOpcaoContendo(String idCampo, String valor) {
		
		List<WebElement> jcombobox = getDriver().findElementsByClassName("view.layout.objetos.JComboBox");
		
		for (int i = 0; i < jcombobox.size(); i++) {
			
			if (jcombobox.get(i).getAttribute("idCampo").equals(idCampo)) {
				jcombobox.get(i).click();
				
				String teste = jcombobox.get(i).getAttribute("content");
				teste = teste.replace("[[", "");
				teste = teste.replace("]]", "");
				teste = teste.replace("\"", "");
				String[] teste2 = teste.split(",");
				
				
				System.out.println(Arrays.toString(teste2));
				
				WebElement option;
				for (int k = 0; k < teste2.length; k++) {
					esperaTempo(2);
					option = jcombobox.get(i).findElement(By.cssSelector(".::nth-option(" + (k + 1) + ")"));
					
					if (option.getText().contains(valor)) {
						option.click();
						break;
					}	
				}			
			}
			
		}
	}

	/** Método espera receber o IdCampo e a posição da lista que deseja selecionar**/
	public void selecionaOpcaoNaPosicao(String idCampo, int posicao) {
		
		List<WebElement> jcombobox = getDriver().findElements(By.className("view.layout.objetos.JComboBox"));
		
		for (int i = 0; i < jcombobox.size(); i++) {
			
			if (jcombobox.get(i).getAttribute("idCampo").equals(idCampo)) {
				
				System.out.println(jcombobox.get(i).getAttribute("idCampo"));
				System.out.println(jcombobox.get(i).getAttribute("content"));
				System.out.println(jcombobox.get(i).getAttribute("selectedIndex"));
				jcombobox.get(i).click();
				esperaTempo(1);
				WebElement option = jcombobox.get(i).findElement(By.cssSelector(".::nth-option(" + 1 + posicao + ")"));
				option.click();	
			}
		}
	}	
	
	 public void clickItem(int posicao) throws Throwable {
	        WebElement combo = getDriver().findElement(By.cssSelector("combo-box"));

	        String[] patternExamples = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
	        //AssertJUnit.assertEquals("" + 4, combo.getAttribute("selectedIndex"));
	        WebElement option;
	        for (int i = 0; i < patternExamples.length; i++) {
	            option = getDriver().findElement(By.cssSelector("combo-box::nth-option(" + (i + 1) + ")"));
	            option.click();
	            AssertJUnit.assertEquals("" + i, combo.getAttribute("selectedIndex"));
	        }
	        for (int i = patternExamples.length - 1; i >= 0; i--) {
	            option = driver.findElement(By.cssSelector("combo-box::nth-option(" + (i + 1) + ")"));
	            option.click();
	            AssertJUnit.assertEquals("" + i, combo.getAttribute("selectedIndex"));
	        }
	    }
	
	/** Esperas **/

	 public void esperaTempo(int segundos) {
		 try {
			TimeUnit.SECONDS.sleep(segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 
	 }
	 
	public static void screenshot(JavaDriver driver, Package pack, String classe, String nomeTeste) throws Exception {
		String pacote = pack.toString();
		pacote = pacote.replace("pageobjects", "tests");
		pacote = StringUtils.substringAfter(pacote, "tests.");
		
		String path = (System.getProperty("user.dir") + "/target/surefire-reports/screnshots/" + pacote + "/" + classe + "/");
		
		Path pat = Paths.get(path);
		File filePath = new File(pat.toString());
		
		if (!filePath.exists()) {
			filePath.mkdir();
			System.out.println("File created " + filePath);
		}

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(path + nomeTeste + ".png"));
		String pathScreenshot = (path + nomeTeste + ".png");
		Reporter.log("<br /><td><a href='" + pathScreenshot + "'><img src='" + pathScreenshot + "' height='100' width='100' /></a></td><br />");
	}
	 
	 
	public WebElement esperaPorElementoVisivel(By localizacao, int segundos) {
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(localizacao));
	}
	
	public WebElement esperaPorElementoVisivel(By localizacao) {
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(localizacao));
	}

	public WebElement esperaPorElementoVisivelXPath(String localizacao) {
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(localizacao)));
	}

	public WebElement esperaPorElementoVisivelSelector(String selector) {
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
		
	}

	public WebElement esperaPorElementoVisivelClassName(String className) {
		return getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
	}

	public WebElement esperaElementoClicavel(By localizacao, int segundos) {
		return getWait().until(ExpectedConditions.elementToBeClickable(localizacao));
	}
	
	public WebElement esperaElementoClicavelSelector(String selector) {
		return getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
	}
	
	public WebElement esperaElementoClicavelClassName(String className) {
		return getWait().until(ExpectedConditions.elementToBeClickable(By.className(className)));
	}
	
	//Corrigir
	public WebElement esperaBotao(String nome) {
		selecionaJanelaAtiva();
		List<WebElement> botoes = driver.findElementsByClassName("view.layout.objetos.JButton");
		for (WebElement botao : botoes) {
			if (botao.getText().equals(nome)) {
				return getWait().until(ExpectedConditions.visibilityOfElementLocated(By.className("view.layout.objetos.JButton")));
			}
		}
		return null;
		
	}

	/********* Botao ************/
	public void clicarBotaoXPath(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	public void clicarBotaoSeletor(String className) {
		driver.findElement(By.className(className)).click();
	}

	public void clicarBotaoName(String name) {
		driver.findElement(By.name(name)).click();
	}

	/********* Limpar Conteúdo *********/
	public void limparConteudo(String atributo) {
		driver.findElement(By.id(atributo)).clear();
	}
	
	/********* Obter valores *********/
	public String obterValorElemento(String atributo) {
		return driver.findElement(By.id(atributo)).getText();
	}

	public String obterValorPorAtributo(String atributo) {
		return driver.findElement(By.id(atributo)).getAttribute("value");
	}

	public String obterValorElementoXPath(String xpath) {
		return driver.findElement(By.xpath(xpath)).getText();
	}

	public String obterValorPorAtributoXpath(String xpath) {
		return driver.findElement(By.xpath(xpath)).getAttribute("value");
	}

	public boolean elementoPresente(String id) {
		// return driver.findElement(By.id(id)).getAttribute("value");
		return driver.findElement(By.id(id)).isDisplayed();
	}

	public WebElement obterElementoPorId(String id) {
		// return driver.findElement(By.id(id)).getAttribute("value");
		return driver.findElement(By.id(id));
	}

	/** Metodo EF */
	public WebElement obterElementoPorClassName(String className) {
		return driver.findElement(By.className(className));
	}
	
	public WebElement obterElementoPorNome(String name) {
		// return driver.findElement(By.id(id)).getAttribute("value");
		return driver.findElement(By.name(name));
	}
	
	public WebElement obterElementoPorXpath(String xpath) {
		// return driver.findElement(By.id(id)).getAttribute("value");
		return driver.findElement(By.xpath(xpath));
	}

	/********* Link ************/

	public void clicarLink(String link) {
		driver.findElement(By.linkText(link)).click();
	}

	/********* Clicar em Radio ************/
	public void clicaRadioButton(String id) {
		getWebElement(TipoElemento.RADIO_BUTTON, id).click();
	}
	
	public WebElement getWebElement(TipoElemento tipo,String idCampo) {
		WebElement webElementSelecionado = null;
		
		List<WebElement> botoes = getWebElementByClassName(tipo.getClassName());
		for (WebElement botao : botoes) {
			System.out.println(botao.getText());
			if (botao.getAttribute("idCampo").equals(idCampo)) {
				webElementSelecionado = botao;
				//break;
			}
		}
		return webElementSelecionado;
	}

	private List<WebElement> getWebElementByClassName(String className) {
		return driver.findElementsByClassName(className);
	}
	
	private List<WebElement> getWebElementBySelector(String selector) {
		return driver.findElements(By.cssSelector(selector));
	}

	public void clicarRadioXpath(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}
	
	/********* Clicar em Checkbox ************/
	public void clicarCheckbox(String id) {
		getWebElement(TipoElemento.CHECKBOX, id).click();
	}
	
	
	public void clicarCheckboxPorNome(String name) {
		driver.findElement(By.name(name)).click();
	}
	
	
	
	/********* Verificar se Checkbox/Radio está marcado 
	 * @return ************/
	public boolean estaMarcado(String id) {
		return driver.findElement(By.id(id)).isSelected();
	}	

	public void clicarCheckboxXPath(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	/********* Autoriza Transacao por Certificado Digital 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws InterruptedException ************/


	public String usuarioLogado() {
		String usuarioLogado = getDriver().getPageSource();
		Pattern pattern = Pattern.compile("C[0-9]{6}");
		Matcher matcher = pattern.matcher(usuarioLogado);
		if (matcher.find()) {
			usuarioLogado = matcher.group(0);
		}
		return usuarioLogado;
	}

	/**
	 * @param - xpath do elemento a ser verificado
	 * @return true caso o elemento seja visível, false caso o elemento não esteja presente na tela
	 * 
	 * Verifica se o elemento esta visível na tela, caso não esteja, 
	 * esse método captura a exception sem comprometer a execução dos demais testes.
	 */
	
	public boolean verificaElementoVisivelXpath(String xpath) {
	    WebElement element;
		try {
	        element = obterElementoPorXpath(xpath);
	    } catch (org.openqa.selenium.NoSuchElementException e)  {
	        System.out.println("NoSuchElementException!!");
	        return false;
	    } catch (org.openqa.selenium.ElementNotVisibleException e) {
			return false;
		}
		

	    element.isDisplayed();
	    return true;
	}
	
	/**
	 * @param - id do elemento a ser verificado
	 * @return true caso o elemento seja visível, false caso o elemento não esteja presente na tela
	 * 
	 * Verifica através do id se o elemento esta visível na tela, caso não esteja, 
	 * esse método captura a exception sem comprometer a execução dos demais testes.
	 */
	public boolean verificaElementoVisivelId(String id) {
	    WebElement element;
		try {
	        element = obterElementoPorId(id);
	    } catch (org.openqa.selenium.NoSuchElementException e)  {
	        System.out.println("NoSuchElementException!!");
	        return false;
	    } catch (org.openqa.selenium.ElementNotVisibleException e) {
			return false;
		}
		
	    element.isDisplayed();
	    return true;
	}
	
	/** Método que verifica conteúdo de um PDF
	 * @throws InterruptedException 
	 * @throws IOException */
	public String verifyPDFContent(String stringUrl, String contains) throws InterruptedException, IOException {
		
		URL url;
		String pdfContent = null;
		
		try {
			url = new URL(stringUrl);
			InputStream is = url.openStream();
			BufferedInputStream fileParse = new BufferedInputStream(is);
			
			PDDocument document = null;
			
			document = PDDocument.load(fileParse);
			
			pdfContent = new PDFTextStripper().getText(document);
			System.out.println(pdfContent);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return pdfContent;
	}
	
	/**Metodo que autentica transação
	 * 
	 */
	public void autenticaTransacao() {
		try {
			esperaTempo(1);	
			selecionaJanela("Autenticação");
			clicaBotao("OK");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**Metodo que autentica transação Opcional
	 * 
	 */
	public void autenticaTransacaoOpcional() {
		try {
			esperaTempo(1);	
			selecionaJanela("Autenticação Opcional");
			clicaBotaoGenerico("CANCELAR");
			esperaTempo(1);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**Metodo que cancela impressão opcinal
	 * 
	 */
	public void imprimeOpcional() {
		esperaTempo(1);	
		selecionaJanelaAtiva();
		clicaBotaoGenerico("CANCELAR");
		esperaTempo(1);
	}
}