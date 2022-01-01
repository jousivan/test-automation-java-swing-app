package br.com.ajss.automation.testutil.datapool;

import java.util.ArrayList;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("datapool")
public class Datapool {
	private ArrayList<Agencia> agencia = new ArrayList<>();	
	
	public Datapool() {
		
	}
	
	public ArrayList<Agencia> getAgencia() {
		return agencia;
	}

	public void setAgencia(ArrayList<Agencia> agencia) {
		this.agencia = agencia;
	}
}
