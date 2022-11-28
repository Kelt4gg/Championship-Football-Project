package projetocampeonato;

import java.util.ArrayList;
import java.util.Random;

public class Brasileirao extends Campeonato{
    private ArrayList<ClubeBrasileirao> clubes; //Array que armazena as equipes que participarão do brasileirão
    private ArrayList<ClubeBrasileirao> clubesAux; //Array auxiliar usado para organizar e no final da competição será passado para o array colocados
    private ArrayList<ClubeBrasileirao> clubesDisponiveis; //Array que armazena os clubes que estão disponiveis para jogar na rodada

    public Brasileirao() {
        super.setRodada(0);
    }
    
    /***********************************************************************************************
     * Printa uma tablea com nomes, pontuação, vitorias, derrotas e empates de cada clube
        * Ordenada pela pontuação de cada clube
    ***********************************************************************************************/
    @Override
    public void tabela() {
        organizaEscalacao();

        System.out.printf("Classificação da %d° rodada:\n\n", this.getRodada());
        int posicao = 1; //Armazena a colocação de cada clube
        int espaços = 20; //Quantidade de espaços entre a colocação e o nome do clube

        System.out.println('+'+"-".repeat(48)+"+"); // Printa uma divisoria
        for(ClubeBrasileirao clube : this.getClubesAux()) {
            if(posicao == 10) {
                espaços = 19;
            }
            System.out.printf("%d°%"+espaços+"s| %d pontos| %d V| %d D| %d E|\n",posicao++,clube.getNome(),clube.getPontuacao(),clube.getPartidas()[0],clube.getPartidas()[1],clube.getPartidas()[2]);
        }
        System.out.println('+'+"-".repeat(48)+"+"); // Printa uma divisoria
    }

    /***********************************************************************************************
     * Organiza o Array de ClubesAuxiliar, ordenando pela quantidade de pontos de cada clube
    ***********************************************************************************************/
    public void organizaEscalacao(){ //Organiza o array de clubesAux com a maior pontuação acima, usado o array de clubesAuxiliar para não mudar a ordem de confrontos dos clubes
        this.setClubesAux((getClubes()));
        for(int k = 0; k < this.getClubesAux().size(); k++){
            for(int kk = k+1; kk < this.getClubesAux().size(); kk++){

                if(this.getClubesAux().get(k).getPontuacao() < this.getClubesAux().get(kk).getPontuacao()){ // Se a pontuação do clube N for menor que a posição do clube NN
                    this.getClubesAux().add(k, getClubesAux().get(kk)); // Adiciona o clube N na posição do clube NN
                    this.getClubesAux().remove(kk+1); // Remove o clube da posição N da posição anterior
                } 
            }
        }
    }

    /***********************************************************************************************
     * Simula uma partida entre dois clubes
        * Cada time tem por padrão a probabilidade de 45% de chance de ganhar
        * Tem 10% de chance de empatar
    ***********************************************************************************************/
    public void partida(ClubeBrasileirao clube1, ClubeBrasileirao clube2) {
        int probabilidade = 44; //Variavel que armazena a probalidade de cada clube ganhar
        probabilidade += (clube1.getScore() - clube2.getScore()) / 2; // Adiciona a subtração dos score dos dois clubes

        int empate = probabilidade + 11; // Armazena a chance de empate

        Random randomificador = new Random();
        int numero = randomificador.nextInt(101);


        if(numero <= probabilidade) { // Se o valor de numero aleoatorio for menor ou igual o valor da probabilidade, o clube1 vence
            this.adicionaPontuacao(clube1, clube2, 1);

        } else if(numero > empate) { // Se o valor de numero aleoatorio for maior que o valor de empate, o clube2 vence
            this.adicionaPontuacao(clube2, clube1, 1);

        } else {
            // Se houver empate, é adicionado 1 ponto a cada clube e printado que o clube1 empatou com o clube2
            this.adicionaPontuacao(clube1, clube2, 2);
        }
    }

    /***********************************************************************************************
     * Atualiza a quantidade de pontos, vitorias, derrotas e empates do clube1 e clube2
    ***********************************************************************************************/
    private void adicionaPontuacao(ClubeBrasileirao clube1, ClubeBrasileirao clube2, int option) {
        // Armazena a quantidade de pontos de cada clube
        int pontuacaoClube1 = clube1.getPontuacao();
        int pontuacaoClube2 = clube2.getPontuacao();

        switch (option) {
            case 1: // Se option for 1, o clube1 ganhou do clube2
                this.getClubes().get(this.getClubes().indexOf(clube1)).setPontuacao(pontuacaoClube1 + 3); //Adjciona 3 pontos ao clube1
                this.getClubes().get(this.getClubes().indexOf(clube1)).getPartidas()[0] += 1; //Adiciona uma vitória ao clube1
                this.getClubes().get(this.getClubes().indexOf(clube2)).getPartidas()[1] += 1; //Adiciona uma derrota ao clube2
                System.out.printf("O %s ganhou do %s\n", clube1.getNome(), clube2.getNome());
                break;
            case 2: //Se option for 2, houve empate entre o clube1 e o clube2
                this.getClubes().get(this.getClubes().indexOf(clube1)).setPontuacao(pontuacaoClube1 + 1);
                this.getClubes().get(this.getClubes().indexOf(clube2)).setPontuacao(pontuacaoClube2 + 1);
                this.getClubes().get(this.getClubes().indexOf(clube1)).getPartidas()[2] += 1; //Adiciona um empate ao clube1
                this.getClubes().get(this.getClubes().indexOf(clube2)).getPartidas()[2] += 1; //Adiciona um empate ao clube2
                System.out.printf("O %s Empatou com %s\n", clube1.getNome(), clube2.getNome());
                break;
            default:
                break;
        }
    }

    /***********************************************************************************************
     * Verifica se o clube1 já enfrentou o clube2
        * Retorna um clube que o clube1 não enfrentou ainda
    ***********************************************************************************************/
    public ClubeBrasileirao verificaDisponiveis(ClubeBrasileirao clube1, ClubeBrasileirao clube2) { // Verifica se um clube pode jogar com o outro
        
        //Enquanto o clube1 e o clube 2 forem iguais ou já tiverem se enfrentado, procura um novo clube para enfrentar o clube1
        while(clube1 == clube2 || this.getClubes().get(this.getClubes().indexOf(clube1)).getConfrontos().contains(clube2)) {

            clube2 = this.getClubesDisponiveis().get(getClubesDisponiveis().indexOf(clube2) + 1);
        }
        
        return clube2;
    }

    /***********************************************************************************************
     * Simula uma quantidade N de rodadas
        * Cada rodada tendo uma quantidade N de partidas
     * Parâmetros:
     * Recebe um número entre 1 e 30 que será a quantidade de rodadas desejadas a serem simuladas
    ***********************************************************************************************/
    @Override
    public void rodada(int qRodada) {
        for(int k = 0; k < qRodada; k++) {

            this.setClubesDisponiveis(new ArrayList<ClubeBrasileirao>(this.getClubes())); // Re-atualiza os clubes disponiveis a cada rodada

            if(!verificaRodada()) {
                System.out.println("O brasileirão chegou ao fim!!");
                this.printColocados();
                return;
            }

            this.setRodada(this.getRodada()+1);
            System.out.printf("Na %d° rodada:\n\n", this.getRodada());

            while(!this.getClubesDisponiveis().isEmpty()) { //Enquanto houver clubes disponiveis no array, o loop continua iterando
                ClubeBrasileirao clube1 = this.getClubesDisponiveis().get(0); //Pega o clube do index 0 de clubesDisponiveis
                ClubeBrasileirao clube2 = this.getClubesDisponiveis().get(1); //Pega o clube do index 1 de clubesDisponiveis

                clube2 = verificaDisponiveis(clube1, clube2);
                
                //Remove o clube1 e o clube2 do array de ClubesDisponiveis
                this.getClubesDisponiveis().remove(getClubes().get(this.getClubes().indexOf(clube1))); 
                this.getClubesDisponiveis().remove(getClubes().get(this.getClubes().indexOf(clube2)));

                this.partida(clube1, clube2); // Roda uma partida entre o clube1 e o clube2

                this.getClubes().get(getClubes().indexOf(clube1)).setConfrontos(clube2); //Adiciona ao array de confrontos do clube1 o clube2, para que não possam jogar novamente
                this.getClubes().get(getClubes().indexOf(clube2)).setConfrontos(clube1); //Adiciona ao array de confrontos do clube2 o clube1, para que não possam jogar novamente
            }
            System.out.println("-".repeat(50)); //Printa uma divisoria
        }
    }

    /***********************************************************************************************
     *  // Verifica se ainda tem rodadas disponiveis a jogar, se sim, retorna true
    ***********************************************************************************************/
    public boolean verificaRodada() {

        if(this.getRodada() == this.getClubes().size() - 1) { // Se o número de rodadas chegar a 15,array de confrotos de cada clube é limpo para que possam jogar de novo 
            for(ClubeBrasileirao clubes : this.getClubes()) {
                clubes.getConfrontos().clear();
            }
        } else if (this.getRodada() == this.getClubes().size() * 2 - 2) { // Se o número de rodadas chegar a 30, acabou o brasileirão
            return false;
        }
        return true;
    } 

    /***********************************************************************************************
     * Simula o torneio inteiro do começo ao fim
        * Se o brasileirão chegou ao fim, printa uma frase e os colocados que passaram para a copa do brasil
    ***********************************************************************************************/
    @Override
    public void torneio() { //Roda todas as as rodadas disponiveis para o basileirão acabar
        while(this.getRodada() != 30) {
            rodada(1);
        }
        System.out.println("O brasileirão chegou ao fim!!");
        this.printColocados();
        
    }

    /***********************************************************************************************
     * Retorna o array 8 clubes que ficaram nas primeiras 8 posições da tabela
    ***********************************************************************************************/
    public ArrayList<Clube> passarColocados() { 
        this.organizaEscalacao();
        if(this.getRodada() < 30) { // Só retorna o array de clubes quando o brasileirão acabar
            return null;
        }

        ArrayList<Clube> aux = new ArrayList<Clube>(); //Inicia um array auxiliar para retornar o array de clubes que passaram

        for(int k = 0; k < this.getClubesAux().size() / 2; k++) {
            aux.add(this.getClubesAux().get(k));
        }
        return aux;
    }

    /***********************************************************************************************
     * Printa o nome dos N clubes que passaram para a copa do brasil em uma tabela
    ***********************************************************************************************/
    private void printColocados() {
        System.out.println("Clubes que vão para a Copa do Brasil: ");
        System.out.println("+"+"-".repeat(15)+"+");
        for(Clube clube : this.passarColocados()) {
            System.out.println(clube.getNome());
        }
        System.out.println("+"+"-".repeat(15)+"+");
    }

    public ArrayList<ClubeBrasileirao> getClubes() {
        return clubes;
    }

    public void setClubes(ArrayList<ClubeBrasileirao> clubes) {
        this.clubes = clubes;
    }

    public ArrayList<ClubeBrasileirao> getClubesDisponiveis() {
        return clubesDisponiveis;
    }

    public void setClubesDisponiveis(ArrayList<ClubeBrasileirao> clubesDisponiveis) {
        this.clubesDisponiveis = clubesDisponiveis;
    }

    public ArrayList<ClubeBrasileirao> getClubesAux() {
        return clubesAux;
    }

    public void setClubesAux(ArrayList<ClubeBrasileirao> clubesColocados) {
        this.clubesAux = clubesColocados;
    }
}