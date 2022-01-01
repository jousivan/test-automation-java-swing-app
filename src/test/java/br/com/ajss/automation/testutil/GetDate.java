package br.com.ajss.automation.testutil;

import java.util.Calendar;

public class GetDate {
	private static Calendar cal;
	
	public static String geraDataFutura() {

		// Objeto que armazena data atual
		cal = Calendar.getInstance();

		// diaDoMes recebe o dia atual + 5
		int diaDoMes = cal.get(Calendar.DAY_OF_MONTH) + 15;

		// recupera o mes
		int mes = cal.get(Calendar.MONTH);
		String diaFormatado = "";
		String mesFormatado = "";

		// alterando o valor do dia do objeto calendar para o diaDoMes;
		cal.set(Calendar.DAY_OF_MONTH, diaDoMes);
		cal.set(Calendar.MONTH, mes);

		// Verifica se o diaDoMes é maior que 28, se sim, adiciona mais 5 ao dia
		// do mes do objeto cal
		if ((cal.get(Calendar.DAY_OF_MONTH) + 1) > 28) {
			cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 5);

			// Verifica se o diaDoMes é sabado, se sim, adiciona mais 3 ao dia
			// do mes do objeto cal
			if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
				cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 3);
			}

			// Verifica se o diaDoMes é domingo, se sim, adiciona mais 1 ao dia
			// do mes do objeto cal
			else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
				cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 1);
			}
		}

		// variável que armazena o dia da semana (1:Domingo, 2:segunda, 3:terça,
		// 4:Quarta, 5:Quinta, 6:Sexta, 7:Sabado)
		// int diaDaSemana = cal.get(Calendar.DAY_OF_WEEK);

		// Verifica se o diaDoMes é sabado, se sim, adiciona mais 3 ao dia do
		// mes do objeto cal
		if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
			cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 3);
		}
		// Verifica se o diaDoMes é domingo, se sim, adiciona mais 1 ao dia do
		// mes do objeto cal
		else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 1);
		}

		// verifica se o dia e menor do que 10, se sim, acrescenta um 0 a
		// esqueda
		if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
			diaFormatado = "0" + cal.get(Calendar.DAY_OF_MONTH);
		} else {// caso contrário o dia formatado recebe o valor exato do dia do
				// objeto cal
			diaFormatado = "" + cal.get(Calendar.DAY_OF_MONTH);
		}

		// formata o mes para caso mes for menor que zero acrescenta um 0 a
		// esquerda
		if ((cal.get(Calendar.MONTH) + 1) < 10) {
			mesFormatado = "0" + (cal.get(Calendar.MONTH) + 1);
		} else {
			mesFormatado = "" + (cal.get(Calendar.MONTH) + 1);
		}

		String dataFormatada = diaFormatado + "/" + mesFormatado + "/" + cal.get(Calendar.YEAR);

		return dataFormatada;
	}
	
	public static String geraDataPassada(int quantidadeDias) {

		// Objeto que armazena data atual
		cal = Calendar.getInstance();

		// diaDoMes recebe o dia atual + 5
		int diaDoMes = cal.get(Calendar.DAY_OF_MONTH) - quantidadeDias;

		// recupera o mes
		int mes = cal.get(Calendar.MONTH);
		String diaFormatado = "";
		String mesFormatado = "";

		// alterando o valor do dia do objeto calendar para o diaDoMes;
		cal.set(Calendar.DAY_OF_MONTH, diaDoMes);
		cal.set(Calendar.MONTH, mes);

		// Verifica se o diaDoMes é maior que 28, se sim, adiciona mais 5 ao dia
		// do mes do objeto cal
		if ((cal.get(Calendar.DAY_OF_MONTH) + 1) > 28) {
			cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 5);

			// Verifica se o diaDoMes é sabado, se sim, adiciona mais 3 ao dia
			// do mes do objeto cal
			if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
				cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 3);
			}

			// Verifica se o diaDoMes é domingo, se sim, adiciona mais 1 ao dia
			// do mes do objeto cal
			else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
				cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 1);
			}
		}

		// variável que armazena o dia da semana (1:Domingo, 2:segunda, 3:terça,
		// 4:Quarta, 5:Quinta, 6:Sexta, 7:Sabado)
		// int diaDaSemana = cal.get(Calendar.DAY_OF_WEEK);

		// Verifica se o diaDoMes é sabado, se sim, adiciona mais 3 ao dia do
		// mes do objeto cal
		if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
			cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 3);
		}
		// Verifica se o diaDoMes é domingo, se sim, adiciona mais 1 ao dia do
		// mes do objeto cal
		else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			cal.set(Calendar.DAY_OF_MONTH, diaDoMes + 1);
		}

		// verifica se o dia e menor do que 10, se sim, acrescenta um 0 a
		// esqueda
		if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
			diaFormatado = "0" + cal.get(Calendar.DAY_OF_MONTH);
		} else {// caso contrário o dia formatado recebe o valor exato do dia do
				// objeto cal
			diaFormatado = "" + cal.get(Calendar.DAY_OF_MONTH);
		}

		// formata o mes para caso mes for menor que zero acrescenta um 0 a
		// esquerda
		if ((cal.get(Calendar.MONTH) + 1) < 10) {
			mesFormatado = "0" + (cal.get(Calendar.MONTH) + 1);
		} else {
			mesFormatado = "" + (cal.get(Calendar.MONTH) + 1);
		}

		String dataFormatada = diaFormatado + "/" + mesFormatado + "/" + cal.get(Calendar.YEAR);

		return dataFormatada;
	}
	
	public static String geraDataAtual() {
		
		StringBuilder dataFormatada = new StringBuilder();
		//cal = Calendar.getInstance();
		cal = Calendar.getInstance();
		
		if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
			dataFormatada.append("0" + cal.get(Calendar.DAY_OF_MONTH));
		} else {// caso contrário o dia formatado recebe o valor exato do dia do
				// objeto cal
			dataFormatada.append(cal.get(Calendar.DAY_OF_MONTH));
		}
		
		dataFormatada.append("/");
		
		System.out.println(cal.get(Calendar.MONTH));
		
		int mesAtual = cal.get(Calendar.MONTH) + 1; 
		
		if(mesAtual < 10){
			dataFormatada.append("0")
				.append(mesAtual);
		}else {
			dataFormatada.append(mesAtual);
		}
		
		dataFormatada.append("/")
			.append(cal.get(Calendar.YEAR));
		
		return dataFormatada.toString();
	}
	
	
	/** Método responsável por gerar data contendo Ano e Mês no formato AAAA-MM **/
	public static String geraDataAnoMes() {
		StringBuilder dataFormatada = new StringBuilder();
		cal = Calendar.getInstance();
		
		dataFormatada.append(cal.get(Calendar.YEAR))
					 .append("-");
		if(cal.get(Calendar.MONTH) < 10){
			dataFormatada.append("0")
			.append(cal.get(Calendar.MONTH) + 1);
		}else {
			dataFormatada.append(cal.get(Calendar.MONTH));
		}
		return dataFormatada.toString();
	}
	
	/** Método responsável por gerar data contendo Mês e Ano no formato MM/AAAA **/
	public static String geraDataMesAno() {
		StringBuilder dataFormatada = new StringBuilder();
		cal = Calendar.getInstance();
		
		if(cal.get(Calendar.MONTH) < 10){
			dataFormatada.append("0")
			.append(cal.get(Calendar.MONTH) + 1);
		}else {
			dataFormatada.append(cal.get(Calendar.MONTH) + 1);
		}
		
		dataFormatada.append("/")
			.append(cal.get(Calendar.YEAR));
		
		return dataFormatada.toString();
	}

	/** Método responsável por gerar data contendo Mês anterior e Ano no formato MM/AAAA 
	 * Metodo trata ate 16 meses anteriores, se precisar mais que isso, tem que fazer o tratamento**/
	public static String geraDataMesAnteriorAno(int qtdMeses) {
		StringBuilder dataFormatada = new StringBuilder();
		cal = Calendar.getInstance();
		
		int mesAnterior = ((cal.get(Calendar.MONTH) + 1) - qtdMeses);
		
		if (mesAnterior <= 0) {

			mesAnterior = ((cal.get(Calendar.MONTH) + 13) - qtdMeses);
			
			if(mesAnterior < 10){
				dataFormatada.append("0")
							 .append(mesAnterior);
			}else {
				dataFormatada.append(mesAnterior);
			}
						
			dataFormatada.append("/")
						 .append(cal.get(Calendar.YEAR) - 1);
		} else {
			if((mesAnterior > 0) && (mesAnterior < 10)) {
				dataFormatada.append("0")
				 .append(mesAnterior)
				 .append("/")
				 .append(cal.get(Calendar.YEAR));
			}else {
				dataFormatada.append(mesAnterior)
				.append("/")
				.append(cal.get(Calendar.YEAR));
			}		
		}
		return dataFormatada.toString();
	}
}
