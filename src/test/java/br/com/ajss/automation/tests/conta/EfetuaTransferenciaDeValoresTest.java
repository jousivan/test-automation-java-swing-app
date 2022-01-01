package br.com.ajss.automation.tests.conta;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.ajss.automation.pageobjects.conta.EfetuaTransferenciaDeValoresPage;
import org.testng.annotations.Test;

import br.com.ajss.automation.Page;
import br.com.ajss.automation.tests.TestBase;
import br.com.ajss.automation.testutil.DriverFactory;
import br.com.ajss.automation.testutil.ScreenshotUtil;

public class EfetuaTransferenciaDeValoresTest extends TestBase {
	EfetuaTransferenciaDeValoresPage efetuaTransferenciaDeValores;

	@Override
	public Page getInstacePage() {
		efetuaTransferenciaDeValores = new EfetuaTransferenciaDeValoresPage(new DriverFactory().getDriver());
		return efetuaTransferenciaDeValores;
	}
	
	@Test(enabled = true)
	public void efetuaTransferenciaDeValoresPorOperacao() throws Exception {
		efetuaTransferenciaDeValores.efetuaTransferenciaDeValores("operacao");
		efetuaTransferenciaDeValores.estornaTransacao();
		ScreenshotUtil.screenshot(efetuaTransferenciaDeValores.getDriver(), this.getClass().getPackage(), this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName() + "-telaSucesso");
		assertThat(efetuaTransferenciaDeValores.getMensagemSucesso().equals("SUCESSO NA EXECUÇÃO DO SERVIÇO"));
	}
	
	@Test(enabled = true)
	public void efetuaTransferenciaDeValoresPorProduto() throws Exception {
		efetuaTransferenciaDeValores.efetuaTransferenciaDeValores("produto");
		efetuaTransferenciaDeValores.estornaTransacao();
		ScreenshotUtil.screenshot(efetuaTransferenciaDeValores.getDriver(), this.getClass().getPackage(), this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName() + "-telaSucesso");
		assertThat(efetuaTransferenciaDeValores.getMensagemSucesso().equals("SUCESSO NA EXECUÇÃO DO SERVIÇO"));
	}
} 