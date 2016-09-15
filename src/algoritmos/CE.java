package algoritmos;
/**
 * @author Jonathan
 * @turma SI
 */

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CE {
	private static boolean debug = true;
	
	public static ArrayList<Integer> caminhoDisponiveisList;
	public static ArrayList<Point2D> posicaoCidadesList;	
	
	public static final int CAMINHOS	= 12,
							PC		= 66,
							PM		= 2,
							TAMANHO	= 4; 	
	
	public static int 	populacao[][], 
						populacaoTemp[][],
						individuoSalvo[];
	
	public static double 	fitness[],
							fitnessMedio;
	
	public static Random random;
	
	/**
	 * Inicializa todas variáveis necessárias
	 */
	static {
		random = new Random();
		populacao = new int[TAMANHO][CAMINHOS];
		populacaoTemp = new int[TAMANHO][CAMINHOS];
		fitness = new double[TAMANHO];
		individuoSalvo = new int[CAMINHOS];
	}

	/**
	 * Inicializa Posição das Cidades
	 */
	public static void inicializarPosicaoCidadeList(){
		posicaoCidadesList = new ArrayList<Point2D>();
		
		posicaoCidadesList.add( new Point2D.Double(1, 5));
		posicaoCidadesList.add( new Point2D.Double(4, 6));
		posicaoCidadesList.add( new Point2D.Double(7, 5));
		posicaoCidadesList.add( new Point2D.Double(5, 4));
		posicaoCidadesList.add( new Point2D.Double(9, 4));
		posicaoCidadesList.add( new Point2D.Double(2, 3));
		posicaoCidadesList.add( new Point2D.Double(4, 2));
		posicaoCidadesList.add( new Point2D.Double(6, 2));
		posicaoCidadesList.add( new Point2D.Double(1, 1));
		posicaoCidadesList.add( new Point2D.Double(5, 1));
		posicaoCidadesList.add( new Point2D.Double(3, 0));
		posicaoCidadesList.add( new Point2D.Double(9, 0));
	}
	
	/**
	 * Gera População Inicial
	 */
	public static void geraPopulacaoInicial(){
	     for (int i = 0; i < TAMANHO; i++){
	    	 preencherCaminhoDisponivel();
	    	 for (int j = 0; j < CAMINHOS; j++) {
	        	 int randomico = random.nextInt( caminhoDisponiveisList.size() );
	             int caminho = caminhoDisponiveisList.remove( randomico );

	             populacao[i][j] = caminho;
			}
	     }
	     avaliaFitness();
	}
	
	/**
	 * Executra Todas as rotinas
	 * Roleta
	 * Cross Over
	 * Mutacao
	 * Avaliação de Fitness
	 * Utlizando Salvacionismo
	 * @param crossover
	 * @param mutacao
	 */
	public static void executar(String crossover, String mutacao, boolean salvacionismo, boolean elitismo) {
		if(salvacionismo){
			salvarMelhorIndividuo();
		}
		selecionaIndRoleta();
		if(salvacionismo){
			recuperarMelhorIndividuo();
		}
		
		if( crossover.equals("PMX") ){
			partiallyMappedCrossOver(elitismo);	
		}else if(crossover.equals("OX") ){
			orderOperatorCrossOver(elitismo);
		}else if(crossover.equals("CX") ){
			cycleCrossOver(elitismo);
		}
		
		if( mutacao.equals("Reciprocal Exchange") ){
			reciprocalExchange(elitismo);	
		}else if( mutacao.equals("Inversion") ){
			inversion(elitismo);
		}
		
		avaliaFitness();
	}

	/**
	 * Coloca o Melhor Indivíduo salvo em um dos filhos
	 */
	private static void recuperarMelhorIndividuo(){
		for (int i = 0; i < CAMINHOS; i++) {
			populacaoTemp[0][i] = individuoSalvo[i];
		}
	}
	
	/**
	 * Escolha o melhor indivíduo em guarda ele
	 */
	private static void salvarMelhorIndividuo(){
		logln("CE.salvarMelhorIndividuo()");
		double melhorFitness = fitness[0];
		int indice = 0;
		for (int i = 0; i < TAMANHO; i++) {
			
			if ( fitness[i] <= melhorFitness ){
				indice = i;
				melhorFitness = fitness[i];
				for (int j = 0; j < CAMINHOS; j++) {
					individuoSalvo[j] = populacao[i][j];
				}
			}
		}
		logln("\tIndivíduo Salvo = "+indice);
	}

	/**
	 * Avalia Fitness.
	 * É avaliado a distancia de todas cidades.
	 * Quanto menor a distância melhor o fitness.
	 */
	public static void avaliaFitness(){
		double distanciaTotal = 0,
			fitnessTotal = 0;
	     for ( int i = 0; i < TAMANHO; i++ ){
	    	 distanciaTotal = 0;
	    	 for (int j = 0; j < CAMINHOS; j++) {
		    	 int cidadeA = populacao[i][j] - 1;
		    	 int cidadeB = populacao[i][(j+1) % CAMINHOS] -1;
		    	 
		    	 double distancia = distanciaEuclidiana(
		    			 posicaoCidadesList.get(cidadeA).getX(), posicaoCidadesList.get(cidadeA).getY(), 
		    			 posicaoCidadesList.get(cidadeB).getX(), posicaoCidadesList.get(cidadeB).getY());
		    	 
		    	 distanciaTotal += distancia;
	    	 }
	    	 fitness[i] = distanciaTotal;
	    	 fitnessTotal += fitness[i];
	     }
	     fitnessMedio = fitnessTotal / TAMANHO;
	}
	
	/**
	 * Seleciona os indivíduos para a próxima geração através do método de roleta.
	 */
	static void selecionaIndRoleta(){
		logln("CE.selecionaIndRoleta()");
	     int roleta[], moeda,ganhador;
	     double tot_fit = 0.0,
	     	inicio = 0.0;
	     
	     roleta = new int[TAMANHO]; 
	     
	     for ( int i = 0; i < TAMANHO; i++ ){
	         tot_fit += fitness[i];
	     }

	     for ( int i = 0; i < TAMANHO; i++ ){
	         inicio = inicio + ( fitness[i] * 360 ) / tot_fit;
	         roleta[i] = (int) inicio;
	     }
	     
	     /* sorteando os elementos vencedores pela roleta para a proxima geração */
	     for ( int i = 0; i < TAMANHO; i++ ){
	    	 ganhador = 0;
	    	 moeda = random.nextInt(361);
	    	 for( int j = 0; j < TAMANHO; j++){
	    		 if (roleta[j] > moeda){
	    			 ganhador = j;
	    			 break;
	    		 }
	    	 }
	    	 logln("\tGanhador da roleta = "+ganhador);
	    	 for ( int k = 0; k < CAMINHOS; k++ ){
	    		 populacaoTemp[i][k] = populacao[ganhador][k];
	    	 }
	     }
	     
	     for (int i=0;i<TAMANHO;i++){
	    	 log("\t");
		        for (int j=0;j<CAMINHOS;j++){
		            log(populacaoTemp[i][j]+" ");
		       }
		       logln("");
	     }

	}

	/**
	 * Cross Over Usando a técnica PMX (Parttially Mapped Cross Over)
	 * @param elitismo 
	 */
	static void partiallyMappedCrossOver(boolean elitismo){
		logln("CE.partiallyMappedCrossOver()");
		int cross = 0,
			ind = 0,
			corte1,
			corte2;
		
		HashMap<Integer, Integer> tabelaPai1, tabelaPai2;
		
		for (int i = 0; i < TAMANHO; i++) {
			for (int j = 0; j < CAMINHOS; j++) {
				populacao[i][j] = -1;
			}
		}
		
		for ( int i = 0; i < TAMANHO; i += 2 ){
			cross = 1 + random.nextInt(100);
			
			if(elitismo && i == 0){
				cross = 100;
			}
			
			if( cross <= PC ){

				//Pega Um Número
				corte1 = random.nextInt( CAMINHOS );
				
				//Pega outro Número
				corte2 = getNumeroDiferenteDe(corte1);
				
				//Verifica se o corte 1 é maior se não troca com corte 2
				if(corte1 > corte2){
					int temp = corte1;
					corte1 = corte2;
					corte2 = temp;
				}
				
				crossOverPrint(i, corte1, corte2);
				
				tabelaPai1 = new HashMap<Integer, Integer>();
				tabelaPai2 = new HashMap<Integer, Integer>();
				
				//Faz o cruzamento da area de corte
				for (int j = corte1; j <= corte2; j++) {
					populacao[i][j] 	= populacaoTemp[i+1][j];
					populacao[i+1][j] 	= populacaoTemp[i][j];
					
					tabelaPai1.put(populacao[i][j], populacao[i+1][j]);
					tabelaPai2.put(populacao[i+1][j], populacao[i][j]);
					
				}

				//For de Corte 2 até o final
				for (int j = corte2+1; j < CAMINHOS; j++) {
					int elemento;
					
					//FILHO 1
					elemento = populacaoTemp[i][j];
					while( hasElement(populacao[i], elemento) ){
						elemento = tabelaPai1.get(elemento);
					}
					populacao[i][j] = elemento;		
					
					//Filho 2
					elemento = populacaoTemp[i+1][j];
					while( hasElement(populacao[i+1], elemento) ){
						elemento = tabelaPai2.get(elemento);
					}
					populacao[i+1][j] = elemento;

				}

				//For de corte1 até 0
				for (int j = corte1-1; j >= 0 ; j--) {
					int elemento;
					
					//FILHO 1
					elemento = populacaoTemp[i][j];
					while( hasElement(populacao[i], elemento) ){
						elemento = tabelaPai1.get(elemento);
					}
					populacao[i][j] = elemento;		
					
					//Filho 2
					elemento = populacaoTemp[i+1][j];
					while( hasElement(populacao[i+1], elemento) ){
						elemento = tabelaPai2.get(elemento);
					}
					populacao[i+1][j] = elemento;
				}
				
				posCrossOverPrint(i);
			}else{
				for( int k = 0; k < CAMINHOS; k++ ){
					populacao[ind][k] = populacaoTemp[i][k];
					populacao[ind+1][k] = populacaoTemp[i+1][k];
				}
				
			}
			ind += 2;
		}
	}

	/**
	 * Cross Over Usando a técnica OX (Order Operator Cross Over)
	 * @param elitismo 
	 */
	static void orderOperatorCrossOver(boolean elitismo){
		logln("CE.orderOperatorCrossOver()");
		int cross,
			ind = 0,
			corte1,
			corte2;
		
		for (int i = 0; i < TAMANHO; i++) {
			for (int j = 0; j < CAMINHOS; j++) {
				populacao[i][j] = -1;
			}
		}
		
		for ( int i = 0; i < TAMANHO; i += 2 ){
			cross = 1 + random.nextInt(100);
			
			if(elitismo && i == 0){
				cross = 100;
			}
			
			if( cross <= PC ){
		
				//Pega Um Número
				corte1 = random.nextInt( CAMINHOS );
				
				//Pega outro Número
				corte2 = getNumeroDiferenteDe(corte1);
				
				//Verifica se o corte 1 é maior se não troca com corte 2
				if(corte1 > corte2){
					int temp = corte1;
					corte1 = corte2;
					corte2 = temp;
				}

				//Copia a area de corte para o filho
				for (int j = corte1; j <= corte2; j++) {
					populacao[i][j] 	= populacaoTemp[i][j];
					populacao[i+1][j] 	= populacaoTemp[i+1][j];
				}
				
				crossOverPrint(i, corte1, corte2);
				
				int contFilho1, contFilho2, cond = CAMINHOS - (corte2 - corte1);
				contFilho1 = corte2;
				contFilho2 = corte2;

				for (int j = 0; j < cond-1; j++) {
					contFilho1 = (contFilho1 + 1)  % CAMINHOS;
					contFilho2 = (contFilho2 + 1)  % CAMINHOS;

					//Filho 1
					while( hasElement(populacao[i], populacaoTemp[i+1][contFilho1]) ){
						//try{Thread.sleep(1000);}catch (Exception e) {}
						contFilho1 = (contFilho1 + 1)  % CAMINHOS;
					}
					populacao[i][(corte2+j+1) % CAMINHOS] = populacaoTemp[i+1][contFilho1];
					
					while( hasElement(populacao[i+1], populacaoTemp[i][contFilho2]) ){
						//try{Thread.sleep(1000);}catch (Exception e) {}
						contFilho2 = (contFilho2 + 1)  % CAMINHOS;
 					}
					populacao[i+1][(corte2+j+1) % CAMINHOS] = populacaoTemp[i][contFilho2];
				}
				
				posCrossOverPrint(i);
			}else{
				for( int k = 0; k < CAMINHOS; k++ ){
					populacao[ind][k] = populacaoTemp[i][k];
					populacao[ind+1][k] = populacaoTemp[i+1][k];
				}
				
			}
			ind += 2;
		}
	}
	
	/**
	 * Cross Over Usando a técnica CX (Cycle Cross Over)
	 * @param elitismo 
	 */
	static void cycleCrossOver(boolean elitismo){
		logln("CE.orderOperatorCrossOver()");
		int cross,
			ind = 0;
		
		for (int i = 0; i < TAMANHO; i++) {
			for (int j = 0; j < CAMINHOS; j++) {
				populacao[i][j] = -1;
			}
		}
		
		for ( int i = 0; i < TAMANHO; i += 2 ){
			cross = 1 + random.nextInt(100);
		
			if(elitismo && i == 0){
				cross = 100;
			}
			
			if( cross <= PC ){
				
				crossOverPrint(i, -1, -1);
				
				int elementFilho1 = populacaoTemp[i][0];
				int elementFilho2 = populacaoTemp[i+1][0];
				
				int contadorFilho1 = 0;
				int contadorFilho2 = 0;
				
				int indice;
				int valor;
				
				//FILHO 1
				populacao[i][contadorFilho1] = elementFilho1;
				valor = populacao[i][contadorFilho1]; 
				indice = indexOf(populacaoTemp[i+1], valor);
				while( populacao[i][indice] == -1){
					populacao[i][indice] = populacaoTemp[i][indice];
					
					valor = populacaoTemp[i][indice];
					indice = indexOf(populacaoTemp[i+1], valor);
				}
				
				//FILHO 2
				populacao[i+1][contadorFilho2] = elementFilho2;
				valor = populacao[i+1][contadorFilho2];
				indice = indexOf(populacaoTemp[i], valor); 
				while( populacao[i+1][indice] == -1){
					populacao[i+1][indice] = populacaoTemp[i+1][indice];
					
					valor = populacaoTemp[i+1][indice];
					indice = indexOf(populacaoTemp[i], valor);
				}				
				
				posCrossOverPrint(i);
				
				//Complentando após fim de clico
				for (int j = 0; j < CAMINHOS; j++) {
					if(populacao[i][j] == -1){
						populacao[i][j] = populacaoTemp[i+1][j];
					}
					if(populacao[i+1][j] == -1){
						populacao[i+1][j] = populacaoTemp[i][j];
					}
				}
				
				posCrossOverPrint(i);
				
			}else{
				for( int k = 0; k < CAMINHOS; k++ ){
					populacao[ind][k] = populacaoTemp[i][k];
					populacao[ind+1][k] = populacaoTemp[i+1][k];
				}
				
			}
			ind += 2;
		}
	}
	
	/**
	 * Pritando o vetor antes do Cross Over
	 * @param i
	 * @param corte1
	 * @param corte2
	 */
	static void crossOverPrint(int i , int corte1, int corte2){
		logln("\tCorte 1 em = "+corte1+" Corte 2 em "+corte2+" i = "+i);
		logln("\tAntes Do Cross Over");
		for (int j = 0; j < CAMINHOS; j++) {
			if(j == corte1){
				log("|"+populacaoTemp[i][j]+" ");
			}else if(j == corte2){
				log(populacaoTemp[i][j]+"| ");
			}else{
				log(populacaoTemp[i][j]+" ");
			}
		}
		logln("");
		for (int j = 0; j < CAMINHOS; j++) {
			if(j == corte1){
				log("|"+populacaoTemp[i+1][j]+" ");
			}else if(j == corte2){
				log(populacaoTemp[i+1][j]+"| ");
			}else{
				log(populacaoTemp[i+1][j]+" ");
			}
		}
		logln("");
	}
	
	/**
	 * Pritando o vetor após o Cross Over
	 * @param i
	 */
	static void posCrossOverPrint(int i){
		logln("\tAPOS CROSS OVER\t");
		for (int j = 0; j < CAMINHOS; j++) {
			log(populacao[i][j]+" ");
		}
		logln("\t");
		for (int j = 0; j < CAMINHOS; j++) {
			log(populacao[i+1][j]+" ");
		}
		logln("");
	}

	/**
	 * Reciprocal Exchange
	 * Mutação que troca um elemento por outro
	 */
	static void reciprocalExchange(boolean elitismo){
		logln("CE.reciprocalExchange()");
		int mutacao,
			temp,
			troca;
		
		int i = 0;
		
		if(elitismo){
			i = 1;
		}
		
		for ( ; i < TAMANHO; i ++ ){
			for (int j = 0; j < CAMINHOS; j++) {
			
				mutacao = 1 + random.nextInt(100);
				
				if (mutacao <= PM){
					//Escolhe um numero randomico da metade ao fim
					troca = getNumeroDiferenteDe(j);
					
					logln("\tFAZENDO RECIPROCAL EXCHANGE NO PAI = "+i+" NAS POSICOES = "+j+" e "+troca);
					
					temp = populacao[i][j];
					populacao[i][j] = populacao[i][troca];
					populacao[i][troca] = temp;
				}				
			}
		}
	}
	
	/**
	 * Inversion
	 * Mutação que inverte um segmento de elementos
	 * @param elitismo 
	 */
	static void inversion(boolean elitismo){
		int corte1,
			corte2,
			mutacao,
			tempPop[];
		
		int i = 0;
		
		if(elitismo){
			i = 1;
		}
		
		
		for ( ; i < TAMANHO; i ++ ){
			for (int j = 0; j < CAMINHOS; j++) {

				mutacao = 1 + random.nextInt(100);
				
				if (mutacao <= PM){
					corte1 = j;
					corte2 = getNumeroDiferenteDe(corte1);
					
					if(corte1 > corte2){
						int temp = corte1;
						corte1 = corte2;
						corte2 = temp;
					}
					
					logln("\tFazendo Inversion entre = "+corte1+" e "+corte2+" no filho "+i);
					
					tempPop = new int[corte2 - corte1];
					
					//Preenche tempPop
					for (int j2 = 0; j2 < tempPop.length; j2++) {
						tempPop[j2] = populacao[i][ corte1 + j2 ];
					}
					
					for (int j2 = tempPop.length-1, cont = 0; j2 >= 0; j2--, cont++) {
						populacao[i][corte1 + cont] = tempPop[j2];
					}
				}
			}
		}
	}
	
	/**
	 * Calcula a distância Euclidiana entre dois pontos
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return distancia
	 */
	public static double distanciaEuclidiana( double x1, double y1, double x2, double y2 ){
		return Math.sqrt( Math.pow( ( x1 - x2 ), 2) + Math.pow( ( y1 - y2 ), 2) );
	}
	
	/**
	 * Método pra verificar se um elemento existe em um array 
	 * @param vector
	 * @param elemento
	 * @return elemento Existe
	 */
	private static boolean hasElement( int[] vector, int elemento ){
		boolean retorno = false;
		
		for (int i = 0; i < vector.length; i++) {
			if(elemento == vector[i]){
				retorno = true;
				break;
			}
		}
		
		return retorno;
	}
	
	/**
	 * Método para verificar a posição de um elemento em um array
	 * @param vector
	 * @param elemento
	 * @return localizacao
	 */
	private static int indexOf( int[] vector, int elemento ){
		int indice = -1;
		for (int i = 0; i < vector.length; i++) {
			if(elemento == vector[i]){
				indice = i;
				break;
			}
		}
		
		return indice;
	}
	
	/**
	 * Método que pega um numero de 0 a 11
	 * diferente do número passado por parâmetro
	 * @param numero
	 * @return numero
	 */
	private static int getNumeroDiferenteDe( int numero ){
		int rand;
		do{
			rand = random.nextInt(CAMINHOS);
		}while ( numero == rand );
		return rand;
	}
	
	/**
	 * Método que preenche a lista de caminhos disponíveis
	 */
	private static void preencherCaminhoDisponivel(){
		caminhoDisponiveisList = new ArrayList<Integer>();
		for (int i = 0; i < posicaoCidadesList.size(); i++) {
			caminhoDisponiveisList.add( i + 1 );	
		}
	}

	/**
	 * Mostra mensagem sem \n se debug = true
	 * @param msg
	 */
	private static void log(String msg){
		if(debug)
			System.out.print(msg);
	}

	/**
	 * Mostra mensagem com \n se debug = true
	 * @param msg
	 */
	private static void logln(String msg){
		if(debug)
			System.out.println(msg);
	}
}