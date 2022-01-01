package br.com.ajss.automation.testutil.datapool;

import java.util.ArrayList;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("agencia")
public class Agencia {
	
	private String numAgencia;
	private String contaSidec;
	private String produto;
	private String contaNsgd;
	private String operador;
	private String operadorAutorizadorCertificado;
	private String senha;
	private String estacaCofreReciclador;
	
	@XStreamImplicit
	private ArrayList<Operacao> operacao;
	
	
	public Operacao getOperacao(String ope) {
		Operacao retorno = null;
		int n = operacao.size();
		for (int i=0; i<n; i++) {
			if (operacao.get(i).getNumOperacao().equals(ope)){
				retorno = operacao.get(i);
				break;
			}
		}
		return retorno;
	}
	
	public String getOperador() {
		return operador;
	}
	
	public String getOperadorAutorizadorCertificado() {
		return operadorAutorizadorCertificado;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getNumAgencia() {
		return numAgencia;
	}
	public void setNumAgencia(String numAgencia) {
		this.numAgencia = numAgencia;
	}
	public String getContaSidec() {
		return contaSidec;
	}
	public void setContaSidec(String contaSidec) {
		this.contaSidec = contaSidec;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public String getContaNsgd() {
		return contaNsgd;
	}
	public void setContaNsgd(String contaNsgd) {
		this.contaNsgd = contaNsgd;
	}
	public String getEstacaCofreReciclador() {
		return estacaCofreReciclador;
	}
}
