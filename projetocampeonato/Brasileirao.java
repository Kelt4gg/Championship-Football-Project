package projetocampeonato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Brasileirao extends Campeonato{
    private ArrayList<Clube> clubes;
    private ArrayList<String> naoDisponiveis;

    public Brasileirao() {
        clubes = new ArrayList<Clube>();
        naoDisponiveis = new ArrayList<String>();
        super.setCbf(new OrganizacaoFutebol(2));
        super.getCbf().organiza();
        super.setqTimes(getCbf().getqTimes());
    }

    public void inicializaClubes() {
        System.out.println(super.getCbf().getClubes().get(0));
        ArrayList<Clube> clubesCBF = super.getCbf().getClubes();
        for(Clube clube : clubesCBF) {
            this.clubes.add(clube);
        }

    }

    public void escalacao() {
        organizaEscalacao();
        for(Clube clube : this.clubes) {
            System.out.printf("%20s %d\n", clube.getNome(), clube.getPontuacao());
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

    @Override
    public Clube match(Clube time1, Clube time2) {
        int probabilidade = 49;
        probabilidade += time1.getScore() - time2.getScore();
        Random randomizer = new Random();
        int number = randomizer.nextInt(100);
        if(number <= probabilidade) {
            return time1;
        } else {
            return time2;
        }
    }

    @Override
    public void rodada(int qRodada) {
        // TODO Auto-generated method stub
        Clube team = null;
        for(int k = 0; k < qRodada; k++) {
            for(int kk = k+1; kk < clubes.size(); kk++) {
                
                this.clubes.get(this.match(k, kk)).setPontuacao();
            } 
        }
        this.escalacao();
        
    }

    @Override
    public void torneio() {
        // TODO Auto-generated method stub
        
    }
}
