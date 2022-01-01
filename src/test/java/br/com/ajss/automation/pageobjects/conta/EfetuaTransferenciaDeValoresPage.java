package br.com.ajss.automation.pageobjects.conta;

import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;
import com.github.javafaker.Faker;

import br.com.ajss.automation.LoginPage;
import br.com.ajss.automation.testutil.datapool.DatapoolUtil;
import net.sourceforge.marathon.javadriver.JavaDriver;

public class EfetuaTransferenciaDeValoresPage extends LoginPage {

	public EfetuaTransferenciaDeValoresPage(JavaDriver driver) {
		super(driver);
	}
	Faker faker = new Faker(new Locale("pt-BR"));
	private String nsu = null;
	
	public void efetuaTransferenciaDeValores(String tipo) throws Exception {		
		selecionaMenu("Conta > TEV - Transferência de Valores > TEV - Transferência de Valores");
		
		esperaTempo(1);
		escreveCampoTextField("CAMPO_VALOR", "1000,00");
		
			if (tipo.equals("operacao")) {
			escreveCampoTextField("CAMPO_OPERACAO_DEBITO", DatapoolUtil.InformacoesAgencia().getOperacao("001").getNumOperacao());	
			escreveCampoJFormattedTextField("CAMPO_CONTA_DV_DEBITO", DatapoolUtil.InformacoesAgencia().getOperacao("001").getNumConta());
			}   
			else if(tipo.equals("produto")){
			escreveCampoTextField("CAMPO_PRODUTO_DEBITO", DatapoolUtil.InformacoesAgencia().getProduto());			
			escreveCampoJFormattedTextField("CAMPO_CONTA_DV_DEBITO", DatapoolUtil.InformacoesAgencia().getContaNsgd());
			}
	
		escreveCampoSenhaId("CAMPO_SENHA", "1212");
		
		clicaBotao("CONSULTAR");
		
		esperaTempo(2);
		escreveCampoTextField("CAMPO_AGENCIA_CREDITO", DatapoolUtil.InformacoesAgencia().getNumAgencia());	
		escreveCampoTextField("CAMPO_OPERACAO_CREDITO", DatapoolUtil.InformacoesAgencia().getOperacao("001").getNumOperacao());
		escreveCampoJFormattedTextField("CAMPO_CONTA_DV_CREDITO", DatapoolUtil.InformacoesAgencia().getOperacao("001").getNumConta());
		
		esperaTempo(2);
		selecionaFrame("TEV - TRANSFERÊNCIA DE VALORES");
		clicaBotao("EXECUTAR");
		
		esperaTempo(2);
		selecionaJanela("TEV - TRANSFERÊNCIA DE VALORES");
			
			//RN991118 – Caso “Indicador de Conta CGU” (Bit 031) igual a 1, habilitar campo “Motivo”
			if (verificaCampoTextFieldHabilitado("CAMPO_MOTIVO")) {
			escreveCampoTextField("CAMPO_MOTIVO", faker.gameOfThrones().character());
			}
		
		clicaBotaoId("BOTAO_EXECUTAR");
		
		esperaTempo(2);
		selecionaJanelaAtiva();
		autorizaTransacao();
		
		esperaTempo(2);
		selecionaJanelaAtiva();
		setNsu(getTextoCampoJLabelGenerico());
		focaBotaoGenerico("OK");
		clicaBotaoGenerico("OK");
		esperaTempo(2);
		
		fechaJanelaSimuladorImpressora();
		
			if(tipo.equals("produto")){
			esperaTempo(2);
			selecionaFrame("TEV - TRANSFERÊNCIA DE VALORES");
			clicaBotaoGenerico("OK");
			esperaTempo(2);
			fechaJanelaSimuladorImpressora();
		}
	}
	
	public String getNsu() {
		return nsu;
	}
	
	public void setNsu(String stringNsu) {
		stringNsu = StringUtils.substringBetween(stringNsu, "<HTML><CENTER>TRANSFERÊNCIA ENVIADA, NSU ", "</CENTER></HTML>");
		System.out.println(stringNsu);
		this.nsu = stringNsu;
	}
	
	public void estornaTransacao() {
		selecionaJanela("Caixa Econômica Federal");
		selecionaMenu("Caixa > Estorno de Transações");
		
		esperaTempo(1);
		selecionaFrame("ESTORNO DE TRANSAÇÕES");
		escreveCampoTextField("CAMPO_NSU", getNsu());
		
		clicaBotao("CONSULTAR");
		esperaTempo(1);

		clicaBotao("EXECUTAR");
		
		esperaTempo(2);
		
		authTransaction("C897374");
	}
}
