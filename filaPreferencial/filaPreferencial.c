/*********************************************************************/
/**   ACH2023 - Algoritmos e Estruturas de Dados I                  **/
/**   EACH-USP - Seugndo Semestre de 2020                           **/
/**   <94> - Prof. Luciano Antonio Digiampietri                     **/
/**                                                                 **/
/**   EP2 - Fila Preferencial                                       **/
/**                                                                 **/
/**   <Luiz Fernando Conceição dos Santos>      <11840300>          **/
/**                                                                 **/
/*********************************************************************/

#include "filapreferencial.h"

PFILA criarFila(){
  PFILA res = (PFILA) malloc(sizeof(FILAPREFERENCIAL));
  res->inicioPref = NULL;
  res->fimPref = NULL;
  res->inicioGeral = NULL;
  res->fimGeral = NULL;
  return res;
}

int tamanho(PFILA f){
  PONT atual = f->inicioGeral;
  int tam = 0;
  while (atual) {
    atual = atual->prox;
    tam++;
  }
  return tam;
}

int tamanhoFilaPreferencial(PFILA f){
  PONT atual = f->inicioPref;
  int tam = 0;
  while (atual) {
    atual = atual->prox;
    tam++;
  }
  return tam;
}

PONT buscarID(PFILA f, int id){
  PONT atual = f->inicioGeral;
   while (atual) {
    if (atual->id == id) return atual;
    atual = atual->prox;
  }
  return NULL;
}

PONT buscarID2(PFILA f, int id){
  PONT atual = f->inicioPref;
   while (atual) {
    if (atual->id == id) return atual;
    atual = atual->prox;
  }
  return NULL;
}

PONT buscaSequencialExc(PFILA f, int id, PONT* ant){
*ant = NULL; 
PONT atual = f->inicioPref;
while((atual!=NULL) && atual->id != id){ 
*ant = atual; 
atual = atual->prox; 
}
return atual;
}
//Busca Sequencial para atualizar o campo anterior do elemento excluído.
PONT buscaSequencialExc2(PFILA f, int id, PONT* ant){
*ant = NULL; 
PONT atual = f->inicioGeral;
while((atual!=NULL) && atual->id != id){ 
*ant = atual; 
atual = atual->prox; 
}
return atual;
}
	

void exibirLog(PFILA f){
  int numElementos = tamanho(f);
  printf("\nLog fila geral [elementos: %i] - Inicio:", numElementos);
  PONT atual = f->inicioGeral;
  while (atual){
    printf(" [%i;%i]", atual->id, atual->ehPreferencial);
    atual = atual->prox;
  }
  printf("\n");
  numElementos = tamanhoFilaPreferencial(f);
  printf("\nLog fila preferencial [elementos: %i] - Inicio:", numElementos);
  atual = f->inicioPref;
  while (atual){
    printf(" [%i;%i]", atual->id, atual->ehPreferencial);
    atual = atual->prox;
  }
  printf("\n\n");
}
//Duas filas uma geral e outra preferencial, a geral contém todos os elementos;
//Função BuscaSequencial é usado para buscar e ordenar o elemento anterior; 
//BuscaID achar o elemento específico;
//f->inicioGeral ou f->inicioPref é o primeiro elemento da fila;
//f->fimGeral ou f->fimPref é o último elemento da fila;
//ehPreferencial caso tenha direito a fila preferencial. 

bool inserirPessoaNaFila(PFILA f, int id, int ehPreferencial){
  bool resposta = false;
 PONT  novoElemento;
 PONT  novoElemento1;
 //Validação do id. 
 if(id < 0) return resposta;
 PONT res = buscarID(f, id);
 //O elemento buscado está na fila. Então não pode adicionar elementos repetidos. 
 if (res != NULL) return resposta;
//Alocar memória para esse novoElemento.
 novoElemento = (PONT) malloc(sizeof(ELEMENTO));
 novoElemento->id = id;//Preencher o campo id.
 novoElemento->prox = NULL;//O próximo dele tem que valer NULL. 
 novoElemento->ehPreferencial = ehPreferencial;//Preencher se ele possui atendimento preferencial. 
 if(f->inicioGeral==NULL) f->inicioGeral = novoElemento;//Se o início estiver vazio.
 else f->fimGeral->prox = novoElemento;//Caso contrário coloque no fim da fila.
 f->fimGeral = novoElemento;
 //Porém o elemento tem direito ao atendimento preferencial. 
 if(ehPreferencial == true) {
 novoElemento1 = (PONT) malloc(sizeof(ELEMENTO));//Alocar memória para esse novoElemento. 
 novoElemento1->id = id;//Preencher o campo id.
 novoElemento1->prox = NULL;//O próximo dele tem que valer NULL.
 novoElemento1->ehPreferencial = ehPreferencial;//Preencher o campo ehPreferencial. 
 if(f->inicioPref==NULL) f->inicioPref = novoElemento1;//Se o início estiver vazio.
 else f->fimPref->prox = novoElemento1;//Caso contrário coloque no fim da fila.
 f->fimPref = novoElemento1;
 }
  resposta = true;
  return resposta;
}



bool atenderPrimeiraDaFilaPreferencial(PFILA f, int* id){
  bool resposta = false;
  PONT ant, i;
 
  //Retornar falso caso a fila geral esteja vazia.
  if(f->inicioGeral == NULL) return resposta;

 //Caso a fila preferencial estiver vazia.
  if(f->inicioPref == NULL && f->inicioGeral != NULL){
  *id = f->inicioGeral->id;//Colocar o endereço do primeiro elemento da fila geral no ponteiro id.
  PONT apagar3 = f->inicioGeral;//Sentinela. 
  f->inicioGeral = f->inicioGeral->prox;
  free(apagar3);//Liberar memória. 
  if (f->inicioGeral==NULL) f->fimGeral=NULL;//Atualizar o campo fim. 
  return true;
  }

  if(f->inicioPref != NULL) {
  //Caso Contrário, colocar o endereço do primeiro elemento no ponteiro id.
  *id = f->inicioPref->id;//Colocar o endereço do primeiro elemento da fila geral no ponteiro id.
  PONT apagar = f->inicioPref;//Sentinela.
  f->inicioPref = f->inicioPref->prox;//Atualizar o início.
  free(apagar);  
  if(f->inicioPref == NULL) f->fimPref = NULL;//Atualizar o campo fim.
  }
  PONT res = buscarID(f, *id);
  //Verificar se o elemento existe.
  if(res != NULL) {
  i = buscaSequencialExc2(f,*id,&ant);//Busca pelo elemento passado como parâmentro.
  if(ant == NULL) f->inicioGeral = i->prox;//Caso esteja no início. 
  else ant->prox = i->prox;//Caso contrário está no fim.
  free(i);//Liberar memória.
  if (f->inicioGeral==NULL) f->fimGeral=NULL;//Atualizar o campo fim. 
  }
  resposta = true;
  return resposta;
}



bool atenderPrimeiraDaFilaGeral(PFILA f, int* id){
  bool resposta = false;
  int ehPreferencial;
  PONT ant;

  //Fila geral vazia.
  if(f->inicioGeral == NULL) return resposta;
  PONT buscaPref = buscarID2(f,*id); 
  //O início não pode está vazio. 
  if(f->inicioGeral != NULL){
  //Caso o elemento está na fila geral e preferencial, precisa excluir de ambas as filas. 
  if(f->inicioGeral->ehPreferencial == true){
  *id = f->inicioGeral->id;
  PONT apagar = f->inicioGeral;  
  f->inicioGeral = f->inicioGeral->prox;
  free(apagar);
  if(f->inicioGeral == NULL) f->fimGeral = NULL;//Atualizar o campo fim. 
  }
  if(buscaPref != NULL) { 
  PONT end = buscaSequencialExc(f,*id,&ant); 
  if(ant == NULL) f->inicioPref = end->prox; 
  else ant->prox = end->prox;
  free(end);  
  if(f->inicioPref == NULL) f->fimPref = NULL; //Atualizar o campo fim. 
  }
  }
  //O retorno do elemento da fila geral e não tem direito a fila preferencial. 
 if(f->inicioGeral->ehPreferencial == false){
  *id = f->inicioGeral->id;
  PONT apagar4 = f->inicioGeral;
  f->inicioGeral = f->inicioGeral->prox;
  free(apagar4);
  if(f->inicioGeral == NULL) f->fimGeral = NULL;//Atualizar o campo fim. 
  resposta = true;
  return resposta;
  }
  resposta = true;
  return resposta;
  }
  
 

bool desistirDaFila(PFILA f, int id){
  bool resposta = false;
  PONT ant, end, i,end2, i2; 
  //Função para buscar o elemento que está na fila geral.
  end = buscarID(f, id);
  if(end == NULL) return resposta;
  //Verificar se o elemento existe. 
  if(end->ehPreferencial == true){ 
  if(end != NULL){
  i = buscaSequencialExc2(f,id,&ant);//Busca pelo elemento passado como parâmentro. 
  if(ant == NULL) f->inicioGeral = i->prox;//Caso esteja no início. 
  else ant->prox = i->prox;//Caso contrário está no fim.
  free(i); 
  //Caso o inicio for vazio, precisa atualizar o fim.
  if(f->inicioGeral == NULL) f->fimGeral = NULL;//Atualizar o campo fim. 
  }
  //Verificar se o elemento existe. 
  end2 = buscarID2(f, id);
  if(end2 != NULL){ 
  i2 = buscaSequencialExc(f,id,&ant); 
  if(ant == NULL) f->inicioPref = i2->prox; 
  else ant->prox = i2->prox;
  free(i2); 
  //Caso o inicio for vazio, precisa atualizar o fim.
  if(f->inicioPref == NULL) f->fimPref = NULL;//Atualizar o campo fim. 
  }
  return true; 
  }
  //o elemento buscado não possui atendimento preferencial.
  if(end->ehPreferencial == false){ 
  if(end != NULL){ 
  i = buscaSequencialExc2(f,id,&ant); 
  if(ant == NULL) f->inicioGeral = i->prox; 
  else ant->prox = i->prox;
  free(i); 
  //Caso o inicio for vazio, precisa atualizar o fim.
  if(f->inicioGeral == NULL) f->fimGeral = NULL;  
  }
  return true; 
  }
  //Se a pessoa for a única da fila preferencial.
  int tamanhoPref= tamanhoFilaPreferencial(f);
  if(tamanhoPref == 0) {
  f->inicioPref= NULL;
  f->fimPref = NULL;
  }
  //Se a pessoa for a única da fila geral.
  int tamanhoGeral= tamanho(f);
  if(tamanhoGeral == 0) {
  f->inicioGeral= NULL;
  f->fimGeral= NULL;
  }
 
  //Caso a pessoa que quer desistir for a última da fila.
  if(f->fimGeral->prox == NULL) f->fimGeral = NULL;
  if(f->fimPref->prox == NULL) f->fimPref = NULL;
  }