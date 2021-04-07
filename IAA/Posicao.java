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


public class Posicao implements Cloneable{

	//Chamando de xy.
	private int posX; 
	private int posY; 
	
	//Iniciar a posição usando o 
	public Posicao(int PosX, int PosY) { 
		this.posX = PosX; 
		this.posY = PosY;
	}

	public int getPosX(){
		return this.posX; 
	} 

	public void setPosX(int posX){
		this.posX = posX; 
	}

	public int getPosY(){
		return this.posY; 
	} 

	public void setPosY(int posY){
		this.posY = posY; 
	} 

	public void imprimePosicoes(){
		System.out.print(this.getPosX() + " " + this.getPosY());
	}

	public boolean ehigual(Posicao compara){
		if(this.getPosX() == compara.getPosX() && this.getPosY() == compara.getPosY())
			return true;			
		
		return false;		
	}
	
}