package br.com.ajss.automation.testutil.datapool;

public class TesteDatapool {

	/**
	 * Classe criada para testar e documentar a utilização do Datapool criado. 
	 **/
	public static void main(String[] args) {
		/**
		 * Chamada de Conta de uma Operação passada por parâmetro. Verificar no XML e na funcionalidade qual a transação desejada.
		 */
		DatapoolUtil.InformacoesAgencia().getOperacao("001").getNumConta();
		
		/**
		 * Chamada de Operação conforme Operação passada por parâmetro. 
		 **/
		DatapoolUtil.InformacoesAgencia().getOperacao("001").getNumOperacao();
		
		/**
		 * Chamada de Operação Destino conforme Operação passada por parâmetro.
		 **/
		DatapoolUtil.InformacoesAgencia().getOperacao("001").getNumOperacaoDestino();
		
		/**
		 * Chamada de Conta Destino de uma Operação passada por parâmetro. Verificar no XML e na funcionalidade qual a transação desejada.
		 **/
		DatapoolUtil.InformacoesAgencia().getOperacao("001").getNumContaDestino();

		/**
		 * Chamada de Agencia utilizada nos testes. Caso não seja passada nenhuma Agência por parâmetro
		 * o teste assume que a agência é a 3965
		 **/
		DatapoolUtil.InformacoesAgencia().getNumAgencia();
		
		/**
		 * Para verificar as demais opções de dados disponíveis no Datapool, pode verificar em
		 * DatapoolUtil.InformacoesAgencia().
		 * Após o ponto usar ctrl + space para que o Eclipse liste as opções disponíveis.
		 * Pode ser verificar no Datapool.xml também para verificar a estrutura do arquivo. 
		 **/
		
	}

}