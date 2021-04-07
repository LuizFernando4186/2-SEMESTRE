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


import java.lang.reflect.Field;

public class Criterios extends Game{

	//Construtor
	public Criterios(int linhas, int colunas){
		super(linhas, colunas);
	}
    
	//Variáveis globais para armazenar a melhor solução, tempo e o valor.
	Solucao melhor = null;
  	int tamMelhor = 0;
  	double tempoMelhor = 0;
	int valorMelhor = 0;
 

	//Variáveis para calcular o tempo no método tentativa e erro.
	double calcula; 
	int peso;
	int pesoMelhor;
  	int pesoCaminho;
  	double tempoReal;
  	double tempoCaminho;
  
	//Variáveis para calcular o valor dos itens.
	int valorItemLabirinto = 0;
	int valorCaminho = 0;
	


	//////////////////////////////////////////////////////
	//							                    	//
	//		METODO RECURSIVO DE TENTATIVA E ERRO		//
	//													//
	//////////////////////////////////////////////////////

	public Solucao tentativa_e_erro(Posicao posicaoInicial, Posicao posicaoFinal, int criterio, Solucao solucaoAtual){
  
		String valor = retornarValorPosicaoLabirinto(posicaoInicial);		 

		if(!(valor.equals("X"))){
		
			solucaoAtual.adiciona(posicaoInicial);
		   
			//Se a posição XY Inicial for igual a Final === CASO BASE
			if(posicaoInicial.ehigual(posicaoFinal)){
				
				/* Toda vez que terminar um caminho, precisa percorrer a lista e calcular o tempo com base no peso. */
				for(Posicao p : solucaoAtual.filaCoordenadas){

					if(!(p.ehigual(posicaoFinal))){
						
						//Se uma posição tem um item, então os valores/qtds/pesos são armazenados
						if(temItem(p)){     
             
							peso += pesoItem(p);
							tempoReal += calculaTempo(peso);
							
							//Incrementar o valor/qtd do item de cada caminho.
							valorItemLabirinto += valorItem(p);
							solucaoAtual.incrementaItensColetados();   
							solucaoAtual.adicionaItemColetado(p);
							
						}else{
							tempoReal += calculaTempo(peso);
						}	
					}
				
					if(p.ehigual(posicaoFinal)){
						tempoCaminho = tempoReal;
						valorCaminho = valorItemLabirinto;
						pesoCaminho = peso;
					}			
				}
         
       

				//Validar com base no critério.
				switch(criterio){
					case 1: 
						this.criterio1(solucaoAtual,tempoCaminho,valorCaminho,pesoCaminho);
						break;
					case 2:
					this.criterio2(solucaoAtual,tempoCaminho,valorCaminho,pesoCaminho);	
						break;
					case 3:
						this.criterio3(solucaoAtual,tempoCaminho,valorCaminho,pesoCaminho);
						break;
					case 4:
						this.criterio4(solucaoAtual,tempoCaminho,valorCaminho,pesoCaminho);
						break;	
				}



				//System.out.println("Tempo Caminho " +"\t" + tempoCaminho);
				//System.out.println("Valor Labirinto " +"\t" + valorCaminho);


				//Resetar o tempo de cada caminho e o valor.
				if(posicaoInicial.ehigual(posicaoFinal)){

					//System.out.println("INICIO - calcula" +"\t"+ calcula);
					//System.out.println("INICIO - peso" +"\t"+ peso);
					
					tempoReal = 0;
					peso = 0;
					valorItemLabirinto = 0;
					solucaoAtual.resetQuantidadeItensColetados();
					solucaoAtual.limparCoordenadasItens();

					
					//System.out.println("FIM - calcula" +"\t"+ calcula);
					//System.out.println("FIM - peso" +"\t"+ peso);
				}

				solucaoAtual.remove();				
				
				return melhor;

			}


			//Se for possível ir para CIMA, então chama recursivamente...
			if(posicaoInicial.getPosX() > 0){

				Posicao novaPosicao = new Posicao(posicaoInicial.getPosX() - 1, posicaoInicial.getPosY());

				if(!(solucaoAtual.caminhoFoiPercorrido (novaPosicao))){
					Solucao solucao = tentativa_e_erro(novaPosicao, posicaoFinal, criterio, solucaoAtual);	
				}
			
			}

	
			//Se for possível ir para ESQUERDA, então chama recursivamente...
			if(posicaoInicial.getPosY() > 0){

				Posicao novaPosicao = new Posicao(posicaoInicial.getPosX(), posicaoInicial.getPosY() - 1);
				
				if(!(solucaoAtual.caminhoFoiPercorrido(novaPosicao))){					
					Solucao solucao = tentativa_e_erro(novaPosicao, posicaoFinal, criterio, solucaoAtual);
				}
		
			}


			//Se for possível ir para BAIXO, então chama recursivamente...
			if(posicaoInicial.getPosX() < getLines()-1){

				Posicao novaPosicao = new Posicao(posicaoInicial.getPosX() + 1, posicaoInicial.getPosY());
				
				if(!(solucaoAtual.caminhoFoiPercorrido(novaPosicao))){
					Solucao solucao = tentativa_e_erro(novaPosicao, posicaoFinal, criterio, solucaoAtual);
				}

			}


			//Se for possível ir para a DIREITA, então chama recursivamente...
			if(posicaoInicial.getPosY() < getColumn()-1){

				Posicao novaPosicao = new Posicao(posicaoInicial.getPosX(), posicaoInicial.getPosY() + 1);
				
				if(!(solucaoAtual.caminhoFoiPercorrido(novaPosicao))){
					Solucao solucao = tentativa_e_erro(novaPosicao, posicaoFinal, criterio, solucaoAtual);
				}
					
			}

			 
		//Desfazer os passos
		solucaoAtual.remove();

	}

		/*else
		System.out.println("Encontrei um X");*/

		return melhor;
  }



  //Caminho mais curto
	public void criterio1(Solucao solucao, double tempo, int valorItem, int pesoItem){
       
		int tamanhoSolucao = solucao.tamanho();
		double tempoSolucao = tempo;
    	int valorDosItens_Solucao = valorItem;
    	int pesoSolucao = pesoItem;
    
		//Se for a PRIMEIRA execucao do metodo ou se a SOLUCAO for menor doq a MELHOR solucao ate o momento
		if(melhor == null || tamMelhor >= tamanhoSolucao){			
			melhor = solucao.clona();
      		tamMelhor = tamanhoSolucao;
			tempoMelhor = tempoSolucao;
      		pesoMelhor = pesoSolucao;
      		valorMelhor = valorDosItens_Solucao;
		} else{ 
    		//System.out.println("--------   A nova solucao não é menor!!  ----------\n\n");
    	}
      

    	/***	DEBUG	***
		System.out.println("TEMPO CRITÉRIO1\t" + tempoMelhor);
	  	System.out.println("CAMINHO CRITÉRIO1\t" + melhor);
      	System.out.println("TAMANHO MELHOR\t" + tamMelhor);
		System.out.println("\n\n");
      */
  	}

  
  	//Caminho mais longo e o tempo maior.
	public void criterio2(Solucao solucao, double tempo, int valorItem, int pesoItem){
		
		int tamanhoSolucao = solucao.tamanho();
		double tempoSolucao = tempo;
	  	int valorDosItens_Solucao = valorItem;
       	int pesoSolucao = pesoItem;
		
		if(melhor == null || tamMelhor <= tamanhoSolucao && tempoMelhor <= tempoSolucao){	
			melhor = solucao.clona();
      		tamMelhor = tamanhoSolucao;
			tempoMelhor = tempoSolucao;
      		pesoMelhor = pesoSolucao;
      		valorMelhor = valorDosItens_Solucao;
  		}
		else{
  			//System.out.println("--------   A nova solucao nao eh maior!!  ----------\n\n");
    	}
   		
	 	/***	DEBUG	***
		 System.out.println("TEMPO CRITÉRIO2\t" + tempoMelhor);
	 	System.out.println("TAMANHO\t" + tamMelhor);
	 	System.out.println("\n\n");
  		*/
	}
				
	
                            
  	//Caminho mais lucrativo.
	public void criterio3(Solucao solucao, double tempo, int valorItem, int pesoItem){
  
      	int tamanhoSolucao = solucao.tamanho();
    	double tempoSolucao = tempo;
	  	int valorDosItens_Solucao = valorItem;    
       	int pesoSolucao = pesoItem;
    
    	if(melhor == null || valorDosItens_Solucao >= valorMelhor){
			melhor = solucao.clona();
      		tamMelhor = tamanhoSolucao;
		  	tempoMelhor = tempoSolucao;
          	pesoMelhor = pesoSolucao;
			valorMelhor = valorDosItens_Solucao;
	  	} 
		else{
			//System.out.println("--------   O Valor da nova solucao não é maior!!  ----------\n\n");
		}
		
		/***	DEBUG	***
		System.out.println("Valor maior é\t" + valorMelhor);
    	System.out.println("TEMPO CRITÉRIO3\t" + tempoMelhor);
    	System.out.println("MELHOR END CRITÉRIO3\t" + melhor.tamanho());
		System.out.println("\n\n");
		*/
	}

	
  //Caminho que minimiza o tempo.
	public void criterio4(Solucao solucao, double tempo, int valorItem, int pesoItem){
		
		double tempoSolucao = tempo;
		int tamanhoSolucao = solucao.tamanho();
    	int valorDosItens_Solucao = valorItem;
       	int pesoSolucao = pesoItem;

		if(melhor == null || tempoSolucao <= tempoMelhor){			
			melhor = solucao.clona();
      		tamMelhor = tamanhoSolucao;
      		tempoMelhor = tempoSolucao;
           	pesoMelhor = pesoSolucao;
      		valorMelhor = valorDosItens_Solucao;			
		} 
		else{
			//System.out.println("--------   O tempo da nova solucao não é menor!!  ----------\n\n");
		}
		
		/***	DEBUG	***
		System.out.println("TEMPO CRITÉRIO4\t" + tempoMelhor);
    	System.out.println("CAMINHO CRITÉRIO4\t" + melhor.tamanho());
		System.out.println("\n\n");
    	*/
	}




}