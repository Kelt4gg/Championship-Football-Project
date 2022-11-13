package projetocampeonato;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Brasileirao extends Campeonato{
    private ArrayList<ClubeBrasileirao> clubes;
    private ArrayList<ClubeBrasileirao> clubesDisponiveis;

    public Brasileirao() {
        clubes = new ArrayList<ClubeBrasileirao>();
        super.setRodada(1);
        EscalaTimes();
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
    public void tabela() {
        organizaEscalacao();
        for(ClubeBrasileirao time : clubes) {
            System.out.printf("%20s %d\n",time.getNome(),time.getPontuacao());
        }
    }

    public void organizaEscalacao(){
        for(int k = 0; k < this.clubes.size(); k++){
            for(int kk = k+1; kk < this.clubes.size(); kk++){
                if(clubes.get(k).getPontuacao() < clubes.get(kk).getPontuacao()){
                    clubes.add(k, clubes.get(kk));
                    clubes.remove(kk+1);
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
        Random randomizer = new Random();
        while(clube1 == clube2 || this.getClubes().get(this.getClubes().indexOf(clube1)).getConfrontos().contains(clube2)) {
            clube2 = this.getClubesDisponiveis().get(randomizer.nextInt(this.getClubesDisponiveis().size()));
        }
        return clube2;
        
    }

    @Override
    public void rodada(int qRodada) {
        for(int k = 0; k < qRodada; k++) {
            this.setClubesDisponiveis(new ArrayList<ClubeBrasileirao>(this.getClubes()));
            while(!this.getClubesDisponiveis().isEmpty()) {
                Random randomizer = new Random();
                ClubeBrasileirao clube1 = this.getClubesDisponiveis().get(randomizer.nextInt(this.getClubesDisponiveis().size()));
                ClubeBrasileirao clube2 = this.getClubesDisponiveis().get(randomizer.nextInt(this.getClubesDisponiveis().size()));
                clube2 = verificaDisponiveis(clube1, clube2);
                this.getClubesDisponiveis().remove(getClubes().get(this.getClubes().indexOf(clube1)));
                this.getClubesDisponiveis().remove(getClubes().get(this.getClubes().indexOf(clube2)));
                match(clube1, clube2);
                this.getClubes().get(getClubes().indexOf(clube1)).setConfrontos(clube2);
                this.getClubes().get(getClubes().indexOf(clube2)).setConfrontos(clube1);
            }
            tabela();
        }
    }


    @Override
    public void torneio() {
        // TODO Auto-generated method stub
        
    }

    public void passarColocados() {

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
    

}
