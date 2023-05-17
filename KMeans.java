import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KMeans
{
    private static double[][] dados;
    private static int k;
    private static double [][]centroides;
    
    public static void main(String args[]){
        //busca os dados do arquivo iris.data
        carga();
        escreveDados();
        
        //define o número de clusters
        k=3;
        
        //define os centroide aleatoriamente
        inicializaCentroides();
        escreveCentroides();
        
        //executa o algoritmo de agrupamento
        executaKMeans();
        
        //exibe os dados agrupados
        escreveDados();
    }
    
    /**
     * Carrega dos dados do arquivo iris.data para a matriz dados. A classe da planta, o 5o atributo, não é considerado.
     */
    public static void carga(){
        dados = new double[150][5];
        Scanner ler = new Scanner(System.in);
        String nome = "iris.data";
 
        System.out.printf("\nConteúdo do arquivo texto:\n");
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);
 
            String linha = lerArq.readLine(); 
            int i = 0;
            while (linha != null) {
                System.out.printf("%s\n", linha);

                String[] valor = linha.split(",");       
                linha = lerArq.readLine(); 
                if(valor.length == 5) {
                    for(int j = 0; j < 4; j++)  dados[i][j] = Double.parseDouble(valor[j]);
                    dados[i][4]=-1;
                    i++;
                }
   
            }
            arq.close();
        } 
        catch (IOException e) {
                System.err.printf("Erro na abertura do arquivo: %s.\n",
                e.getMessage());
        }
    }
    
    /**
     * Escreve os dados com seus respectivos clusters. Inicialmente os cluters dos dados são rotulados como -1,
     * ou seja, sem cluster definido.
     */
    public static void escreveDados(){
        System.out.println("\n\n--------- D A D O S ---------");
        System.out.println("     x1  x2  x3  x4  cluster");
        for(int i = 0; i < dados.length; i++) {
                System.out.print((i+1)+ " : ");
                for(int j = 0; j < dados[i].length; j++) {
                    System.out.print(dados[i][j] + " ");
                }
                System.out.println();
        }
    }
    
    /**
     * Escreve o valor atual dos centróides
     */
    public static void escreveCentroides(){
        System.out.println("\n--------- CENTROIDES ---------");
        System.out.println("             x1  x2  x3  x4");
        for(int i = 0; i < centroides.length; i++) {
                System.out.print("Centroide " + i + ": ");
                for(int j = 0; j < centroides[i].length; j++) {
                    System.out.print(centroides[i][j] + " ");
                }
                System.out.println();
        }
    }
    
    /**
     * Randomiza k dados para inicializar os centróides
     */
    public static void inicializaCentroides(){
        centroides = new double[k][4];
        Random gera = new Random();
        
        for(int i=0; i<k; i++){
            int s = gera.nextInt(dados.length);
            for(int j=0; j<4; j++){
                centroides[i][j]=dados[s][j];
            }
        }
    }
    
    /**
     * Calcula a distância eucliadiana entre um dado corrente (atual) e um centroide.
     */
    public static double distancia(double[] atual, double[] centroide) {
      double soma = 0;
      for(int i = 0; i < centroide.length; i++) {
          soma += Math.pow(atual[i] - centroide[i], 2);
      }
      return Math.sqrt(soma);
    }
    
    /**
     * Agrupamento usando k-Means
     */
    public static void executaKMeans(){
        boolean estabilizou = false;
        int i = 0, max = 1000, cluster;
        double dist,menor;
        
        while (!estabilizou && i<max){ 
            i++;
            estabilizou = true;
            
            for(int d=0; d<dados.length; d++){
                //calcula distância de cada dado d para os k centróides
                menor = 999;
                cluster = -1;
                for(int c=0; c<centroides.length; c++){
                    dist = distancia(dados[d],centroides[c]);
                    if(dist<menor){
                        menor = dist;
                        cluster = c;
                    }
                }
                if(dados[d][4]!=cluster){
                    dados[d][4] = cluster;
                    estabilizou = false;
                }
            }
            
            //recalcula centroides
            System.out.println("\n\n>>>Iteração: " + i);
            calculaCentroides();
            escreveCentroides();
        }
    }
    
    /**
     * Calcula os centróides: vetores médios
     */
    public static void calculaCentroides(){
        int quant;
        double soma; 
        for(int c=0; c<centroides.length; c++){
          for(int j=0; j<centroides[c].length; j++){
             soma = 0;
             quant = 0;
             for(int i=0; i<dados.length; i++){ 
                 if(dados[i][4]==c){
                     soma = soma + dados[i][j];
                     quant++;
                 }
            }
            centroides[c][j] = soma/quant;
        }
    }
   }
}
