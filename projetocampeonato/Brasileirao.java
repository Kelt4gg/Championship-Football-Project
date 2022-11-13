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
        this.setClubesColocados(new ArrayList<ClubeBrasileirao>(getClubes()));
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

        System.out.printf("Classificação da %d° rodada:\n\n", this.getRodada());
        int posicao = 1;
        for(ClubeBrasileirao clube : this.getClubesColocados()) {
            System.out.printf("%d°%20s %d\n",posicao++,clube.getNome(),clube.getPontuacao());
        }
    }

    public void organizaEscalacao(){
        for(int k = 0; k < this.getClubesColocados().size(); k++){
            for(int kk = k+1; kk < this.getClubesColocados().size(); kk++){
                if(this.getClubesColocados().get(k).getPontuacao() < this.getClubesColocados().get(kk).getPontuacao()){
                    this.getClubesColocados().add(k, getClubesColocados().get(kk));
                    this.getClubesColocados().remove(kk+1);
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
            this.setClubesDisponiveis(new ArrayList<ClubeBrasileirao>(this.getClubes()));
            this.setRodada(this.getRodada()+1);
            System.out.println("-".repeat(50));
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
            classificacao();
        }
    }


    @Override
    public void torneio() {
        // TODO Auto-generated method stub
        
    }

    public ArrayList<ClubeCopaBrasil> passarColocados() {
        ArrayList<ClubeCopaBrasil> clubes = new ArrayList<ClubeCopaBrasil>();
        return clubes;
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

    public ArrayList<ClubeBrasileirao> getClubesColocados() {
        return clubesAux;
    }

    public void setClubesColocados(ArrayList<ClubeBrasileirao> clubesColocados) {
        this.clubesAux = clubesColocados;
    }
}