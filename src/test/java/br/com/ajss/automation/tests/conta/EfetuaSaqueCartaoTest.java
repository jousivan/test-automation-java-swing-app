package br.com.ajss.automation.tests.conta;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.ajss.automation.pageobjects.conta.EfetuaSaqueCartaoPage;
import org.testng.annotations.Test;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import br.com.ajss.automation.Page;
import br.com.ajss.automation.tests.TestBase;
import br.com.ajss.automation.testutil.DriverFactory;
import br.com.ajss.automation.testutil.ScreenshotUtil;

public class EfetuaSaqueCartaoTest extends TestBase {
	private EfetuaSaqueCartaoPage saqueCartao;

	public Page getInstacePage() {
		saqueCartao = new EfetuaSaqueCartaoPage(new DriverFactory().getDriver());
		return saqueCartao;
	}

	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void efetuaSaqueCartaoImprimeComprovante() throws Exception {
		saqueCartao.saqueCartao("impressao");
		ScreenshotUtil.takeScreenshot(saqueCartao.getDriver(), this.getClass().getPackage() + this.getClass().getSimpleName() + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(saqueCartao.getMensagemSucesso().equals("SUCESSO NA EXECUÇÃO DO SERVIÇO"));
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled=false)
	public void efetuaSaqueCartaoAutenticaTransacao() throws Exception {
		
		saqueCartao.saqueCartao("autenticacao");
		ScreenshotUtil.takeScreenshot(saqueCartao.getDriver(), this.getClass().getPackage() + this.getClass().getSimpleName() + Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat(saqueCartao.getMensagemSucesso().equals("SUCESSO NA EXECUÇÃO DO SERVIÇO"));
	}
}

