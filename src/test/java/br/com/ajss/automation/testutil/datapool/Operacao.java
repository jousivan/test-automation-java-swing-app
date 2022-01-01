package br.com.ajss.automation.testutil.datapool;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("operacao")
public class Operacao {
	private String numOperacao;
	private String numConta;
	private String numOperacaoDestino;
	private String numContaDestino;
	private String agenciaDestino;
	private String agenciaDestinoComDigito;
	
	public String getAgenciaDestinoComDigito() {
		return agenciaDestinoComDigito;
	}

	public String getAgenciaDestino() {
		return agenciaDestino;
	}

	public String getNumOperacaoDestino() {
		return numOperacaoDestino;
	}

	public String getNumContaDestino() {
		return numContaDestino;
	}
	
	public String getNumOperacao() {
		return numOperacao;
	}
	
	public String getNumConta() {
		return numConta;
	}
}
