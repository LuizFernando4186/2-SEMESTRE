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


import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

class Main {

  // Abre o arquivo e conforme vai lendo cada linha, vai chamando as funções
  public static void readComandsToStart(String[] args) {

    try {

		// Instancia os objetos para leitura de arquivos e captura as linhas
		Scanner input = new Scanner(System.in);
		File file = new File(args[0]);
		input = new Scanner(file);


		// Inicializa um objeto  do tipo GAME
		// com as configuracoes presentes na primeira linha do arquivo TXT
		Criterios gm = new Criterios(input.nextInt(), input.nextInt());
		input.nextLine();	//Limpeza de buffer de quebra de linha
    

		// Loop para preencher as peças do tabuleiro
		for (int linha = 0; linha < gm.getLines(); linha++) {
			String linhaTabuleiro = input.nextLine();			
			gm.preencheTabuleiro(linhaTabuleiro, linha);
			//System.out.println("\nInserindo no tabuleiro na posicao \t" + linha + "\tPecas: \t" + linhaTabuleiro);	
		}

   
		// Carregar no jogo os itens adicionais
		gm.setNumeroItens(Integer.parseInt(input.nextLine()));
		
		for(int linha = 0; linha < gm.getNumeroItens(); linha++){
			String linhaPecas = input.nextLine();				
			gm.preencheItens(linhaPecas, linha) ;
      
			//System.out.println("\nInserindo item \t" + linha + "\tDetalhes: \t" + linhaPecas);
		}
		

		// Carregar a origem -> destino
		Posicao posicaoInicial = new Posicao(input.nextInt(), input.nextInt());
		Posicao posicaoFinal = new Posicao(input.nextInt(), input.nextInt());
    

		// Roda a simulação conforme argumento recebido
		int criterio = Integer.parseInt(args[1]);
		Solucao solucaoFinal = gm.play(posicaoInicial, posicaoFinal, criterio);
	

		//	Imprimi os resultados
		System.out.print(solucaoFinal.tamanho() + " ");
	  	System.out.println(gm.tempoMelhor);		

		solucaoFinal.imprimirCaminho();
		
		System.out.print(solucaoFinal.getQuantidadeItensColetados() + " ");
		System.out.print(gm.valorMelhor + " ");
		System.out.print(gm.pesoMelhor + " \n");

		solucaoFinal.imprimirCaminhoItens();


		// Fecha o arquivo
		input.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {

    if (args.length > 1)
      readComandsToStart(args); // Se os parâmetros foram recebidos, então o processo pode começar
    else
      System.out.println("Argumentos invalidos");

  }
}