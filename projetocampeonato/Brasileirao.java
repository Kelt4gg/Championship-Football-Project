package projetocampeonato;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Brasileirao extends Campeonato{
    private ArrayList<ClubeBrasileirao> clubes;
    private ArrayList<ClubeBrasileirao> clubesAux;
    private ArrayList<ClubeBrasileirao> clubesDisponiveis;

    public Brasileirao() {
        setClubes(new ArrayList<ClubeBrasileirao>());
        super.setRodada(0);
        this.EscalaTimes();
        this.setClubesAux(new ArrayList<ClubeBrasileirao>(getClubes()));
    }

    private void EscalaTimes() {
        String path = "projetocampeonato/clubes.csv";
        File file = null;
        Scanner scan = null;
        try {
            file = new File(path);
            scan = new Scanner(file);
            scan.nextLine();
            while(scan.hasNext()) {
                String line = scan.nextLine();
                String[] separeted = line.split(",");
                this.clubes.add(new ClubeBrasileirao(separeted[0], Integer.parseInt(separeted[1]), separeted[2], Double.parseDouble(separeted[3]), Integer.parseInt(separeted[4])));
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            scan.close();
        }
    }
    
    @Override
    public void classificacao() {
        organizaEscalacao();

        System.out.printf("Classificação da %d° rodada:\n\n", this.getRodada() - 1);
        int posicao = 1;
        for(ClubeBrasileirao clube : this.getClubesAux()) {
            System.out.printf("%d°%20s| %d pontos|\n",posicao++,clube.getNome(),clube.getPontuacao());
        }
        System.out.println("-".repeat(50));
    }

    public void organizaEscalacao(){
        for(int k = 0; k < this.getClubesAux().size(); k++){
            for(int kk = k+1; kk < this.getClubesAux().size(); kk++){
                if(this.getClubesAux().get(k).getPontuacao() < this.getClubesAux().get(kk).getPontuacao()){
                    this.getClubesAux().add(k, getClubesAux().get(kk));
                    this.getClubesAux().remove(kk+1);
                } 
            }
        }
    }

    public void match(ClubeBrasileirao clube1, ClubeBrasileirao clube2) {
        int probabilidade = 44;
        probabilidade += clube1.getScore() - clube2.getScore();
        int empate = probabilidade + 10;
        Random randomizer = new Random();
        int number = randomizer.nextInt(100);
        int pontuacaoTime1 = clube1.getPontuacao();
        int pontuacaoTime2 = clube2.getPontuacao();
        if(number <= probabilidade) {
            this.getClubes().get(this.getClubes().indexOf(clube1)).setPontuacao(pontuacaoTime1 + 3);
            System.out.printf("O %s ganhou do %s\n", clube1.getNome(), clube2.getNome());
        } else if(number > empate) {
            this.getClubes().get(this.getClubes().indexOf(clube2)).setPontuacao(pontuacaoTime2 + 3);
            System.out.printf("O %s ganhou do %s\n", clube2.getNome(), clube1.getNome());
        } else {
            this.getClubes().get(this.getClubes().indexOf(clube1)).setPontuacao(pontuacaoTime1 + 1);
            this.getClubes().get(this.getClubes().indexOf(clube2)).setPontuacao(pontuacaoTime2 + 1);
            System.out.printf("O %s empatou com o %s\n", clube1.getNome(), clube2.getNome());
        }
    }

    public ClubeBrasileirao verificaDisponiveis(ClubeBrasileirao clube1, ClubeBrasileirao clube2) {
        while(clube1 == clube2 || this.getClubes().get(this.getClubes().indexOf(clube1)).getConfrontos().contains(clube2)) {
            clube2 = this.getClubesDisponiveis().get(getClubesDisponiveis().indexOf(clube2) + 1);
        }
        return clube2;

    }

    @Override
    public void rodada(int qRodada) {
        for(int k = 0; k < qRodada; k++) {
            this.setRodada(this.getRodada()+1);
            this.setClubesDisponiveis(new ArrayList<ClubeBrasileirao>(this.getClubes()));
            if(!verificaRodada()) {
                System.out.println("O brasileirão chegou ao fim!!");
                return;
            }
            System.out.printf("Na %d° rodada:\n\n", this.getRodada());
            while(!this.getClubesDisponiveis().isEmpty()) {
                ClubeBrasileirao clube1 = this.getClubesDisponiveis().get(0);
                ClubeBrasileirao clube2 = this.getClubesDisponiveis().get(1);
                clube2 = verificaDisponiveis(clube1, clube2);
                
                this.getClubesDisponiveis().remove(getClubes().get(this.getClubes().indexOf(clube1)));
                this.getClubesDisponiveis().remove(getClubes().get(this.getClubes().indexOf(clube2)));
                match(clube1, clube2);
                this.getClubes().get(getClubes().indexOf(clube1)).setConfrontos(clube2);
                this.getClubes().get(getClubes().indexOf(clube2)).setConfrontos(clube1);
            }
            System.out.println("-".repeat(50));
        }
    }

    public boolean verificaRodada() {
        if(this.getRodada() == 16) {
            for(ClubeBrasileirao clubes : this.getClubes()) {
                System.out.print(clubes.getNome()+": ");
                clubes.getConfrontos().clear();
                System.out.println();
            }
        } else if (this.getRodada() == 31) {
            return false;
        }
        return true;
    } 


    @Override
    public void torneio() {
        while(this.getRodada() != 31) {
            rodada(1);
        }
        
    }

    public ArrayList<ClubeCopaBrasil> passarColocados() {
        ArrayList<ClubeCopaBrasil> aux = new ArrayList<ClubeCopaBrasil>();
        int counter = 0;
        for(ClubeBrasileirao clubes: this.getClubesAux()) {
            if(counter == 8) {
                break;
            }
            String nome = clubes.getNome();
            int fundacao = clubes.getFundacao();
            String local = clubes.getLocal();
            double torcida = clubes.getTorcida();
            int score = clubes.getScore();
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