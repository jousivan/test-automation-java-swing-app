package br.com.ajss.automation.listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Listener implements ITestListener, ISuiteListener, IInvokedMethodListener {
	
	public void onStart(ISuite arg0) {

		Reporter.log("Iniciando a execução da Suite " + arg0.getName(), true);

	}

	public void onFinish(ISuite arg0) {

		Reporter.log("Finalizando a execução da Suite " + arg0.getName(), true);

	}

	public void onStart(ITestContext arg0) {
		
		Reporter.log("Iniciando a execução do Teste  " + arg0.getName(), true);

	}

	public void onFinish(ITestContext arg0) {

		Reporter.log("Finalizando a execução do teste " + arg0.getName(), true);

	}

	public void onTestSuccess(ITestResult arg0) {
			printTestResults(arg0);
	}

	public void onTestFailure(ITestResult arg0) {

		printTestResults(arg0);

	}

	public void onTestStart(ITestResult arg0) {

		System.out.println("A execução do teste principal iniciou agora");

	}

	public void onTestSkipped(ITestResult arg0) {
		
		printTestResults(arg0);

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	private void printTestResults(ITestResult result) {

		Reporter.log("O Método de Teste está localizado em " + result.getTestClass().getName(), true);

		if (result.getParameters().length != 0) {

			String params = null;

			for (Object parameter : result.getParameters()) {

				params += parameter.toString() + ",";

			}

			Reporter.log("O Método de Teste tem os seguintes parametros : " + params, true);

		}

		String status = null;

		switch (result.getStatus()) {

		case ITestResult.SUCCESS:

			status = "Passou";

			break;

		case ITestResult.FAILURE:

			status = "Falhou";

			break;

		case ITestResult.SKIP:

			status = "Ignorado";

		}

		Reporter.log("Status do Teste: " + status, true);

	}

	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "Iniciando a execução do seguinte Método: " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);

	}

	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "Finalizando a execução do seguinte Método: " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);

	}

	private String returnMethodName(ITestNGMethod method) {
		return method.getRealClass().getSimpleName() + "." + method.getMethodName();

	}
}