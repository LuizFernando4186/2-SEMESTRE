//#################################################################################################
//###																							###
//###  	NOME:		JOSE RAFAEL RODRIGUES NASCIMENTO		Nº USP: 	11847801				###					
//###  	NOME:		LUIZ FERNANDO CONCEIÇÃO DOS SANTOS      Nº USP:		11840300				###
//###							                                                  				###
//###  	PROFESSOR:	FLAVIO COUTINHO							TURMA: ACH2002-2020294    			###
//###																							###
//###	ATIVIDADE: 	ALGORITMO PARA RESOLUÇÃO DE LABIRINTO UTILIZANDO TENTATIVA E ERRO			###
//###																							###
//#################################################################################################


import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

public abstract class Game{

	//Variaveis para dados básicos do jogo
	private int linhas, colunas;
	private String[][] tabuleiro;
 	private Posicao posInicial; 
	private int qtdItensColetados;
  	private int valorItensColetados;
 	private int pesoItensColetados;
	private double tempoGasto;
	private int[][] itens;
	private int numeroItens;

	//Construtor
	Game(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.tabuleiro = new String[linhas][colunas];
    	this.qtdItensColetados = qtdItensColetados;
		this.valorItensColetados = valorItensColetados;
		this.pesoItensColetados = pesoItensColetados;
		this.tempoGasto = tempoGasto;
	}

	//	Método que recebe uma String com todas as peças e preenche um array
	//	baseado na linha, também recebida por parâmetro
 	public void preencheTabuleiro(String pecas, int linha) {

		String[] pecasProntas = pecas.split("");				//Converte a String PECAS em um array de Strings

		//Loop pelas colunas para inserir as peças
		for (int coluna = 0; coluna < this.colunas; coluna++){			
			this.tabuleiro[linha][coluna] = pecasProntas[coluna];			
			//System.out.println("Convertendo linha \t" + linha + "\tPeca: " + pecasProntas[coluna]);		//DEBUG
		}

	}

	
	//	Método para calcular o tempo gasto com base no peso.
	public double calculaTempo(double peso){
		double t;
		t = (1 + (peso/10));
	  	t = t*t;
		return t;
	}



	public Solucao play(Posicao posicaoInicial, Posicao posicaoFinal, int criterio){

		/***	DEBUG	***
		System.out.println("\n\n\t----- !!! Iniciando TENTATIVA E ERRO !!! -----\n\n");
		System.out.print("Inicio:\t\t");
		posicaoInicial.imprimePosicoes();

		System.out.print("\nDestino:\t");
		posicaoFinal.imprimePosicoes();
		System.out.println("\n");
		*/
    
		Solucao solucao = new Solucao();
		solucao.resetQuantidadeItensColetados();
		solucao = tentativa_e_erro(posicaoInicial, posicaoFinal, criterio, solucao);

		if(solucao == null){
			Solucao semSolucao = new Solucao();
			return semSolucao;
		}

		return solucao;
	}

	abstract public Solucao tentativa_e_erro(Posicao posicaoInicial, Posicao posicaoFinal, int criterio, Solucao solucao);


	//--------------------------------------------------//
	// -------------------- PRINT'S --------------------//
	//--------------------------------------------------//

	public void imprimirTabuleiro() { 
		
		for(int i = 0; i < this.linhas; i++) {
			for(int j = 0; j < this.colunas; j++) { 				
				System.out.print(this.tabuleiro[i][j] + "\t"); 
			}
			System.out.println(); 			
		}
	}

  
  	//--------------------------------------------------//
	// -------------------- ITENS ----------------------//
	//--------------------------------------------------//
  
  
	/*	Método que recebe uma String com todos os itens e 
	//	preenche um array, baseado na linha, também recebida por parâmetro
	*/
	public void preencheItens(String itens, int linha){

		String[] detalhesDosItens = itens.split(" ");//Converte a String ITENS em um array de Strings, com detalhes dos itens

		int posicaoX = Integer.parseInt(detalhesDosItens[0]);
		int posicaoY = Integer.parseInt(detalhesDosItens[1]);
		valorItensColetados = Integer.parseInt(detalhesDosItens[2]);
		pesoItensColetados = Integer.parseInt(detalhesDosItens[3]);

		this.itens[linha][0] = posicaoX;
		this.itens[linha][1] = posicaoY;
		this.itens[linha][2] = valorItensColetados;
		this.itens[linha][3] = pesoItensColetados;

   		//System.out.println("peso"+  "\t" + getPesoItensColetados() + "\t" + posicaoX + posicaoY);

	}



  	public void imprimirItens(){
		System.out.println("\nItens no tabuleiro...");
		for(int i=0; i<this.numeroItens; i++){
			System.out.print("X: " + this.itens[i][0] + "\tY: " + this.itens[i][1] + "\tVALOR: " + this.itens[i][2] + "\tPESO: " + this.itens[i][3] + "\n");
		}
	}

	
	public boolean temItem(Posicao posicao){

		for(int i = 0; i < getNumeroItens(); i++ ){
			if(posicao.getPosX() == itens[i][0] && posicao.getPosY() == itens[i][1])
				return true;
		}

		return false;
	}

  	
	/*	Metodo que passa por toda a matriz de Itens e retorna o PESO
		Se nenhum item for encontrado, retorna ZERO.
	*/
	public int pesoItem(Posicao posicao){

		for(int i = 0; i < getNumeroItens(); i++ ){
			if(posicao.getPosX() == itens[i][0] && posicao.getPosY() == itens[i][1])
				return itens[i][3];
		}

		return 0;
	}


  	/*	Metodo que passa por toda a matriz de Itens e retorna o VALOR
		Se nenhum item for encontrado, retorna ZERO.
	*/
	public int valorItem(Posicao posicao){

		for(int i = 0; i < getNumeroItens(); i++ ){
			if(posicao.getPosX() == itens[i][0] && posicao.getPosY() == itens[i][1])
				return itens[i][2];
		}

		return 0;
	}



	//----------------------------------------------------//
	// ----------------- GET'S AND SET'S -----------------//
	//----------------------------------------------------//

	public int getLines() {
		return this.linhas;
	}
	
	public int getColumn() { 
    	return this.colunas;
    }
  	
	public void setTempoGasto(double tempo){
		this.tempoGasto = tempo;
	}
  	
	public int getNumeroItens(){
		return this.numeroItens;
	}

  	public void setNumeroItens(int itens){
		this.numeroItens = itens;
		this.itens = new int[numeroItens][4];
	}

	public void incrementaQuantidadeItensColetados(){
		this.qtdItensColetados += 1;
	}

	public void setValorItensColetados(int valor){
		this.valorItensColetados = valor;
	}

	public void setPesoItensColetados(int peso){
		this.pesoItensColetados = peso;
	}

	public double getTempoGasto(){
		return this.tempoGasto;
	}

	public int getQuantidadeItensColetados(){
		return this.qtdItensColetados;
	}

	public int getValorItensColetados(){
		return this.valorItensColetados;
	}

	public int getPesoItensColetados(){
		return this.pesoItensColetados;
	}


  	//Retorna o valor contido dentro da posição do tabuleiro 
	//  "."	 ou	 "X"
	public String retornarValorPosicaoLabirinto(Posicao posicao) { 
	 return this.tabuleiro[posicao.getPosX()][posicao.getPosY()];
	}


}