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



import  java.util.ArrayList;
import  java.util.Collections ;
import  java.util.PriorityQueue ;

public class Solucao extends Object implements Cloneable{

	private int quantidadeItensColetados;

	//Criar 2 listas de coordenadas, para salvar as posições do trajeto completo e dos itens coletados
	ArrayList<Posicao> filaCoordenadas = new ArrayList<>();
	ArrayList<Posicao> filaCoordenadasItensColetados = new ArrayList<>();
  

	//Adicionam as coordenadas na filas
	public void adiciona(Posicao p){
		filaCoordenadas.add(p);   
	}
  
  	public void adicionaItemColetado(Posicao p){
		filaCoordenadasItensColetados.add(p);   
	}


	//Remove o ultimo elemento da fila
	public void remove(){
		filaCoordenadas.remove(filaCoordenadas.size()-1);
	}

	//Limpa a lista toda
	public void limparCoordenadasItens(){
		filaCoordenadasItensColetados.clear();
	}


	//Tamanho da lista.
	public int tamanho(){
		return filaCoordenadas.size();
	} 

	//Incrementa a quantidade de Itens capturados no caminho
	public void incrementaItensColetados(){
		this.quantidadeItensColetados++;
	}


	//Clona o objeto completo
	public Solucao clona(){
	
		Solucao novaSolucao = new Solucao();

		//Copia a Fila de Coordenadas com o trajeto completo
		for(Posicao p : this.filaCoordenadas){
			novaSolucao.adiciona(p);
		}

		//Copia a Fila de Coordenadas onde os Itens foram coletados
		for(Posicao p : this.filaCoordenadasItensColetados){
			novaSolucao.adicionaItemColetado(p);
		}

		novaSolucao.quantidadeItensColetados = this.quantidadeItensColetados;
    
		return novaSolucao;
    }



	public boolean caminhoFoiPercorrido(Posicao posicao){

		for(Posicao p : filaCoordenadas){
			if(posicao.ehigual(p)){
				//System.out.print("EH IGUAL!!");
				return true;
			}
    	}	

		return false;
	}



	//----------------------------------------------------//
	// --------------------- PRINT'S ---------------------//
	//---------------------------------------------------//

	public void imprimirCaminho(){

		//System.out.println("IMPRIMINDO CAMINHO...\nTAMANHO: " + this.tamanho());

		for(Posicao p : filaCoordenadas){
			p.imprimePosicoes();
			System.out.print("\n");
		}	
 	}

	public void imprimirCaminhoItens(){

		//System.out.println("IMPRIMINDO CAMINHO ITENS...\nTAMANHO: " + this.tamanho());

		for(Posicao p : filaCoordenadasItensColetados){
			p.imprimePosicoes();
			System.out.print("\n");
		}	
 	}


	//----------------------------------------------------//
	// ----------------- GET'S AND SET'S -----------------//
	//----------------------------------------------------//


	public int getQuantidadeItensColetados(){
		return this.quantidadeItensColetados;
	}

	public void resetQuantidadeItensColetados(){
		this.quantidadeItensColetados = 0;
	}

}