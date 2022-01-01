
package br.com.ajss.automation;

import java.io.IOException;

import br.com.ajss.automation.testutil.PropReader;
import la.foton.sisag.automation.sisag.dao.ParametrosSistemaDao;
import la.foton.sisag.automation.sisag.pojo.Sagtb008Paramentro;
import net.sourceforge.marathon.javadriver.JavaDriver;

public class LoginPage extends Page
{
	public LoginPage(JavaDriver driver) {
		super(driver);		
	}
	
	public void logar() throws IOException, Exception
	{


		if(System.getProperty("flag") == "S")
		{
			loginCertificadoDigital();

		}else if (System.getProperty("flag") == "N" || System.getProperty("flag") != "N"){
			loginMatriculaSenha(PropReader.configProp().getProperty("LOGIN"));
		}

		esperaTempo(1);
        
	}
	
	public void logoff() throws InterruptedException
	{
		selecionaJanela("Titulo Janela");
		selecionaMenu("Logoff");
		esperaTempo(1);
		selecionaFrame("LOGOFF");
		esperaTempo(1);
		clicaBotao("EXECUTAR");
		esperaTempo(1);

		selecionaJanelaAtiva();
    }
	
	
	public void loginMatriculaSenha(String matricula) throws InterruptedException {
		esperaTempo(10);
		selecionaJanelaAtiva();

		esperaElementoClicavelSelector("button");
		/** Clica no Login Matricula e Senha*/

		clicaBotaoPorSelectorNaPosicao(1);
		
		/** Seleciona Janela de Login*/
		selecionaJanelaAtiva();
        
		/** Informa Matricula para Login*/

		esperaTempo(2);

		escreveCampoTextFieldPorSelector(matricula);
        
		/** Informa Senha Padrão */
        escreveSenhaPadrao();
        
        /** Clica no botão para realizar Login*/
        clicaBotaoPorSelectorNaPosicao(1);
        
        selecionaJanelaAtiva();

        clicaBotaoPorSelectorNaPosicao(0);

        try {
            selecionaJanela("Atenção");
            clicaBotaoGenerico("OK");
            esperaTempo(1);
		} catch (Exception e) {
		}

	}
	
	public void loginCertificadoDigital() throws Exception {
		jsexecutor("javascript:function()");
		clicaBotao("");
		geraStringCertificadoDigital();
		
		clicarBotaoXPath("//button[contains(text(),'OK')]");

		clicaBotao("imgSelecione");
	}
	
	public void setaFormaAutenticacao(String parametro) {

	}
}
