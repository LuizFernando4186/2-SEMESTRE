/*********************************************************************/
/**   ACH2023 - Algoritmos e Estruturas de Dados I                  **/
/**   EACH-USP - Segundo Semestre de 2020                           **/
/**   <94> - Prof. Luciano Antonio Digiampietri                     **/
/**                                                                 **/
/**   EP1 - Fila de Prioridade                                      **/
/**                                                                 **/
/**   <Luiz Fernando Conceição dos Santos>    <11840300>            **/
/**                                                                 **/
/*********************************************************************/

#include "filaDePrioridade.h"

PFILA criarFila(int max){
  PFILA res = (PFILA) malloc(sizeof(FILADEPRIORIDADE));
  res->maxElementos = max;
  res->arranjo = (PONT*) malloc(sizeof(PONT)*max);
  int i;
  for (i=0;i<max;i++) res->arranjo[i] = NULL;
  PONT cabeca = (PONT) malloc(sizeof(ELEMENTO));
  res->fila = cabeca;
  cabeca->ant = cabeca;
  cabeca->prox = cabeca;
  cabeca->id = -1;
  cabeca->prioridade = 1000000;
  return res;
}

void exibirLog(PFILA f){
  printf("Log [elementos: %i (alem do no cabeca)]\n", tamanho(f));
  PONT atual = f->fila;
  printf("%p[%i;%f;%p]%p ", atual->ant, atual->id, atual->prioridade, atual, atual->prox);
  atual = atual->prox;
  while (atual != f->fila){
  printf("%p[%i;%f;%p]%p ", atual->ant, atual->id, atual->prioridade, atual, atual->prox);
  atual = atual->prox;
  }
  printf("\nElementos validos: ");
  atual = atual->prox;
  while (atual != f->fila){
    printf("[%i;%f;%p] ", atual->id, atual->prioridade, atual);
    atual = atual->prox;
  }

  printf("\nValores do arrajo:\n\[ ");
  int x;
  for (x=0;x<f->maxElementos;x++) printf("%p ",f->arranjo[x]);
  printf("]\n\n");
}

//maxElemento é a quantidade máxima que a fila pode ter;
//fila é os elementos que possui uma prioridade para ser inserido no arranjo(endereço);
//arranjo armazena os endereços dos elementos;
//PFILA f é a fila de Prioridade;
//f->fila é o inicio da fila;
//cabeça é o primeiro elemento da fila, com id -1 e prioridade 1000000.


int tamanho(PFILA f){
int tam = 0; //Guarda o número de elemento.
PONT atual = f->fila->prox; //Recebe o endereço do primeiro registro e guarde no atual.

while(atual != f->fila) {//A cada elemento válido guarde na variável tam.
tam++; //Some mais 1.
atual = atual->prox;// Recebe o prox elemento da fila.
 
}
return tam; //Quando terminar o laço com o total de elementos, retorne tam com o total.
}

//Função de Busca.
ELEMENTO* buscaSeqExc(PFILA f, float prioridade,PONT* atual, PONT* ant) {
*ant = f->fila;//Recebe o nó cabeça. 
*atual = (*ant)->prox;//Primeiro elemento válido.
//Comparar a prioridade e o endereço da variável atual e cabeça. 
while((*atual)->prioridade > prioridade && (*atual) != f->fila) {
*ant = *atual;
*atual =(*atual)->prox;

}
}
//Função de troca. 
bool trocar(PFILA f, int id, float prioridade){
   
 // Primeiro retira o elemento que está na fila, para reposicioná-lo. 
(f->arranjo[id]->ant)->prox = f->arranjo[id]->prox;
(f->arranjo[id]->prox)->ant = f->arranjo[id]->ant;

//Verificar se o elemento está na fila. 
ELEMENTO* novo = f->fila->prox; // sentinela
while (novo != f->fila){
if (prioridade >= novo->prioridade) break;//Caso a prioridade seja maior que do novo elemento pare o laço.
    novo = novo->prox;//Caso contrário passe para o próximo. 
    }
//O lugar para reposicionar ele, tem que ser antes do elemento novo.
novo->ant->prox = f->arranjo[id];
f->arranjo[id]->ant = novo->ant;
novo->ant = f->arranjo[id];
f->arranjo[id]->prox = novo;
f->arranjo[id]->prioridade = prioridade;

   return true;
}

bool inserirElemento(PFILA f, int id, float prioridade){
  bool resposta = false;
  PONT ant, atual;
  ELEMENTO* novoElemento;
  PONT* arranjo;
 //Busca o elemento a ser incluído. 
 novoElemento=buscaSeqExc(f,prioridade,&atual,&ant);
 if(f->arranjo[id] != NULL) return false; //Não incluir elementos repetidos. 
 //id precisa ser válido e menor que maxElementos na estrutura. 
 if(id < 0 || id >= f->maxElementos) return false;
 //Alocar memória para esse elemento. 
 novoElemento = (PONT) malloc(sizeof(ELEMENTO));
//Guarde ele no arranjo de endereços. 
 f->arranjo[id] = novoElemento;
//Acertando os ponteiros anterior e próximo. 
 novoElemento->ant = ant;//o anterior do novoElemento recebe o anterior. 
 novoElemento->prox = ant->prox;//O próximo do novoElemento será o próximo do anterior do elemento.
 ant->prox = novoElemento;//O anterior que está apontando para o próximo recebe o novo elemento. 
 novoElemento->prox->ant = novoElemento;//O próximo do novo elemento que está apontando para o anterior dele, recebe ele mesmo.
 novoElemento->prioridade = prioridade;
 novoElemento->id = id;

  resposta = true;
  return resposta;
}

bool aumentarPrioridade(PFILA f, int id, float novaPrioridade){
  bool resposta = false;
 
 if (f == NULL)  return false; // fila vazia
 if (id < 0)     return false; // id negativo
 if (id >= (f->maxElementos)) return false; // id > maximo
 //Verificar se os elementos do arranjo são válidos ou se tem alguém ou não. 
 if ((f->arranjo[id] == NULL) || (f->arranjo[id]->ant == NULL)) return false;
// Caso a prioridade de um determinado elemento for maior que a variável passado como parâmetro retorne false. 
 if (f->arranjo[id]->prioridade  >= novaPrioridade) return false;
 
 //Trocar caso seja menor que a novaPrioridade. 
   trocar(f, id, novaPrioridade);
 
  resposta = true;
  return resposta;
}

bool reduzirPrioridade(PFILA f, int id, float novaPrioridade){
  bool resposta = false;

   if (f == NULL) return false; // fila vazia
   if (id < 0) return false; // id negativo
   if (id >= (f->maxElementos)) return false; // id > maximo
    // Se não tem na fila retorne false. 
   if ((f->arranjo[id] == NULL) || (f->arranjo[id]->ant == NULL)) return false;
   // Caso a prioridade de um determinado elemento for menor que a variável passado como parâmetro retorne false.
   if (f->arranjo[id]->prioridade <= novaPrioridade) return false;

    // O elemento não existia com maior prioridade.
   trocar(f, id, novaPrioridade);

 
  resposta = true;

  return resposta;
}

PONT removerElemento(PFILA f){
  PONT resposta = NULL;
  PONT ant;
  PONT* arranjo;
  int id;
 
  // retorna NULL se não tem ninguém na fila.
  if (f->fila->prox == f->fila) return resposta;
   
ELEMENTO* apagar = f->fila->prox;//Sentinela.
    int idApagar = apagar->id;//Guarde o endereço do elemento apontada pelo sentinela.
  //Acertar os ponteiros para o próximo da fila apontar para o elemento depois do apagado. 
    f->fila->prox = apagar->prox;
    apagar->prox->ant = f->fila;
    f->arranjo[idApagar] = NULL; //Guarde NULL no lugar do elemento apagado.
 
   resposta = apagar;//Retorne o endereço do mesmo. 
    return resposta;
}
 
bool consultarPrioridade(PFILA f, int id, float* resposta){
  bool resp = false;

  if (f == NULL) return false; // fila vazia
  if (id < 0) return false; // id negativo
  if (id >= (f->maxElementos)) return false; // id > maximo
  if ((f->arranjo[id] == NULL) || (f->arranjo[id]->ant == NULL)) return false;//Se os elementos da fila for válido.

  *resposta = f->arranjo[id]->prioridade;//Colocar na memória o valor da prioridade.
 
  resp = true;
  return resp;
}