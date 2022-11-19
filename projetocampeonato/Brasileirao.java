package projetocampeonato;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Brasileirao extends Campeonato{
    private ArrayList<ClubeBrasileirao> clubes; //Array que armazena as equipes que participaram do brasileirão
    private ArrayList<ClubeBrasileirao> clubesAux; //Array auxiliar usado para organizar e no final da competição será passado para o array colocados
    private ArrayList<ClubeBrasileirao> clubesDisponiveis; //Array que armazena os clubes que não jogaram na rodada

    public Brasileirao() {
        setClubes(new ArrayList<ClubeBrasileirao>());
        super.setRodada(0);// Inicia a rodada em 0
        this.EscalaTimes();
        this.setClubesAux(new ArrayList<ClubeBrasileirao>(getClubes()));
    }

    private void EscalaTimes() { // Pega cada time no csv e adiciona no array clubes
        String path = "projetocampeonato/clubes.csv";
        File file = null;
        Scanner scan = null;
        try { // Faz uma verificação se o arquivo de clubes existe
            file = new File(path);
            scan = new Scanner(file);
            scan.nextLine(); //Pula a primeira linha de cabeçalho
            while(scan.hasNext()) { // Adiciona cada clube no csv dentro do array de clubes enquanto existir algum clube no csv
                String line = scan.nextLine(); // Pega a linha do csv
                String[] atributosClube = line.split(","); // Separa a linha do csv em virgulas e coloca dentro do array de atributos
                
                ;//Instancia cada time colocando cada atributo separado na matriz atributosClube
                this.getClubes().add(new ClubeBrasileirao(atributosClube[0], // Nome do Clube
                                         Integer.parseInt(atributosClube[1]), // Ano de fundação do Clube
                                                          atributosClube[2], // Estadio do Clube
                                        Long.parseLong(atributosClube[3]), // Quantidade da torcida do Clube
                                         Integer.parseInt(atributosClube[4]))); // Pontuação do Clube
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            scan.close();
        }
    }

    public void cadastraClube(String nomeClube, int fundacao, String estadio, long torcida, int nota) { //Cadastra um novo clube
        if(getRodada() > 0) {
            System.out.println("Clubes só podem ser cadastrados antes da competição começar");
            return;
        }
        this.getClubesAux().add(new ClubeBrasileirao(nomeClube, fundacao, estadio, torcida, nota));
        this.getClubes().add(new ClubeBrasileirao(nomeClube, fundacao, estadio, torcida, nota));
    }
    
    @Override
    public void classificacao() { //Printa uma tabela com o nome de cada time, sua posição na tabela e no topo a rodada em questão
        organizaEscalacao();

        System.out.printf("Classificação da %d° rodada:\n\n", this.getRodada());
        int posicao = 1;
        for(ClubeBrasileirao clube : this.getClubesAux()) {
            System.out.printf("%d°%20s| %d pontos|\n",posicao++,clube.getNome(),clube.getPontuacao());
        }
        System.out.println("-".repeat(50)); // Printa uma linha depois da exibição de cada clube
    }

    public void organizaEscalacao(){ //Organiza o array de clubesAux com a maior pontuação acima, usado o array de clubesAuxiliar para não mudar a ordem de confrontos dos clubes
        for(int k = 0; k < this.getClubesAux().size(); k++){
            for(int kk = k+1; kk < this.getClubesAux().size(); kk++){

                if(this.getClubesAux().get(k).getPontuacao() < this.getClubesAux().get(kk).getPontuacao()){ // Ordena da maior pontuação de cada clube para a menor
                    this.getClubesAux().add(k, getClubesAux().get(kk)); // Adiciona o clube na posição nova
                    this.getClubesAux().remove(kk+1); // Remove o clube da posição anterior
                } 
            }
        }
    }

    public void match(ClubeBrasileirao clube1, ClubeBrasileirao clube2) { // Metodo que simula uma partida entre dois clubes
        int probabilidade = 44; // Variável probabilidade que será usada para medir a chance de um time ganahr de outro
        probabilidade += clube1.getScore() - clube2.getScore(); //
        //A probabilidade das partidas se baseia que cada equipe tem 45% de chance base de ganhar, 45% para o clube1 e 45% para o clube2 e 10% de chance de empate
        //Se o clube1 tem 100 de score e o clube2 tem 70, é subtraido e somado para a variável probabilidade, então a probabilidade do clube1 ganhar é 74% e do clube2 gaanhar é de 14%

        int empate = probabilidade + 11; // Armazena a chance de empate

        Random randomizer = new Random(); // Classe que criará números aleatório 
        int number = randomizer.nextInt(101); // É Criado um número aleatório de 0 a 100

         // Armazena a quantidade de pontos de cada clube
        int pontuacaoTime1 = clube1.getPontuacao();
        int pontuacaoTime2 = clube2.getPontuacao();

        //Se a probabilidade estiver em 70, se cair um número de 0 a 70, o Clube1 ganha, Se o número cair sentre 71 e 80 dá empate e se cair entre 81 e 100 o clube2 vence
        if(number <= probabilidade) {
            // Se o clube1 ganhar é adicionado 3 pontos e printado que o clube1 ganhou do clube2
            this.getClubes().get(this.getClubes().indexOf(clube1)).setPontuacao(pontuacaoTime1 + 3); 
            System.out.printf("O %s ganhou do %s\n", clube1.getNome(), clube2.getNome());

        } else if(number > empate) {
            // Se o clube2 ganhar é adicionado 3 pontos e printado que o clube2 ganhou do clube1
            this.getClubes().get(this.getClubes().indexOf(clube2)).setPontuacao(pontuacaoTime2 + 3);
            System.out.printf("O %s ganhou do %s\n", clube2.getNome(), clube1.getNome());

        } else {
            // Se houver empate, é adicionado 1 ponto a cada clube e printado que o clube1 empatou com o clube2
            this.getClubes().get(this.getClubes().indexOf(clube1)).setPontuacao(pontuacaoTime1 + 1);
            this.getClubes().get(this.getClubes().indexOf(clube2)).setPontuacao(pontuacaoTime2 + 1);
            System.out.printf("O %s empatou com o %s\n", clube1.getNome(), clube2.getNome());
        }
    }

    public ClubeBrasileirao verificaDisponiveis(ClubeBrasileirao clube1, ClubeBrasileirao clube2) { // Verifica se um clube pode jogar com o outro
        //Verifica se o clube1 e o clube2 são iguais e se o clube1 já confrontou o clube2
        //Se já enfrentou ou se são iguais é procurado o proximo clube da tabela e verifica novamente até que ache um clube disponivel
        while(clube1 == clube2 || this.getClubes().get(this.getClubes().indexOf(clube1)).getConfrontos().contains(clube2)) {

            clube2 = this.getClubesDisponiveis().get(getClubesDisponiveis().indexOf(clube2) + 1);
        }
        
        return clube2;
    }

    @Override
    public void rodada(int qRodada) { // Simula a quantidade de N rodadas, em uma rodada cada equipe joga contra o outro
        for(int k = 0; k < qRodada; k++) {

            this.setClubesDisponiveis(new ArrayList<ClubeBrasileirao>(this.getClubes())); // Coloca os times disponiveis a cada rodada

            if(!verificaRodada()) { // Se verificaRodada retornar false, é por que o todas as equipes já fizeram seus jogos e o brasileirão chegou ao fim
                System.out.println("O brasileirão chegou ao fim!!");
                return;
            }

            this.setRodada(this.getRodada()+1); //Aumenta 1 em rodada a cada iteração
            System.out.printf("Na %d° rodada:\n\n", this.getRodada());//Printa a rodada em questão

            while(!this.getClubesDisponiveis().isEmpty()) { //Enquanto houver clubes disponiveis no array, o loop continua iterando
                ClubeBrasileirao clube1 = this.getClubesDisponiveis().get(0); //Pega o primeiro clube da tabela
                ClubeBrasileirao clube2 = this.getClubesDisponiveis().get(1); //Pega o segundo clube da tabela

                clube2 = verificaDisponiveis(clube1, clube2); //Verifica se os clubes são disponiveis a jogar
                
                //Remove o clube1 e o clube2 do array de ClubesDisponiveis para jogar esta rodada
                this.getClubesDisponiveis().remove(getClubes().get(this.getClubes().indexOf(clube1))); 
                this.getClubesDisponiveis().remove(getClubes().get(this.getClubes().indexOf(clube2)));

                match(clube1, clube2); // Roda uma partida entre o clube1 e o clube2

                this.getClubes().get(getClubes().indexOf(clube1)).setConfrontos(clube2); //Adiciona ao array de confrontos do clube1 o clube2, para que não possam jogar novamente
                this.getClubes().get(getClubes().indexOf(clube2)).setConfrontos(clube1); //Adiciona ao array de confrontos do clube2 o clube1, para que não possam jogar novamente
            }
            System.out.println("-".repeat(50)); //Printa uma divisoria de linhas
        }
    }

    public boolean verificaRodada() { // Verifica se ainda tem rodada disponiveis a jogar, se sim, retorna true

        if(this.getRodada() == this.getClubes().size() - 1) { // Se o número de rodadas chegar a 15, é limpo o array de confrotos de cada clube para que possam jogar de novo 
            for(ClubeBrasileirao clubes : this.getClubes()) {
                System.out.print(clubes.getNome()+": ");
                clubes.getConfrontos().clear();
                System.out.println();
            }
        } else if (this.getRodada() == this.getClubes().size() * 2 - 2) { // Se o número de rodadas chegar a 30, acabou o brasileirão
            return false;
        }
        return true;
    } 


    @Override
    public void torneio() { //Roda todas as as rodadas disponiveis para o basileirão acabar
        while(this.getRodada() != 30) {
            rodada(1);
        }
        System.out.println("O brasileirão chegou ao fim!!");
        
    }

    public ArrayList<ClubeCopaBrasil> passarColocados() { // Retorna o array 8 clubes que se colocaram para jogar na copa do brasil

        if(this.getRodada() < 30) { // Só retorn o array de clubes quando o brasileirão acabar
            return null;
        }

        ArrayList<ClubeCopaBrasil> aux = new ArrayList<ClubeCopaBrasil>(); //Inicia um array auxiliar para retornar o array de clubes que passaram

        int counter = 0;
        for(ClubeBrasileirao clubes: this.getClubesAux()) { // Pega as primeiras 8 equipes do array de clubesAux
            if(counter == 8) {
                break;
            }
            //Armazena os atributos do clube
            String nome = clubes.getNome();
            int fundacao = clubes.getFundacao();
            String local = clubes.getLocal();
            long torcida = clubes.getTorcida();
            int score = clubes.getScore();

            //Cria um clube, passa os atributos e adiciona
            ClubeCopaBrasil clubeCB = new ClubeCopaBrasil(nome,fundacao,local,torcida,score);
            aux.add(clubeCB);
            counter++;
        }
        return aux;
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