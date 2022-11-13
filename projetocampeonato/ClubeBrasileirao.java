package projetocampeonato;

import java.util.ArrayList;

public class ClubeBrasileirao extends Clube{
    private int pontuacao; // Varíavel que armazena a pontuação do clube
    private ArrayList<ClubeBrasileirao> confrontos; // Variável que armazena os clubes que o clube já enfrentou

    public ClubeBrasileirao(String nome, int fundacao, String local, double torcida, int score) {
        super(nome, fundacao, local, torcida, score);
        this.pontuacao = 0;
        confrontos = new ArrayList<ClubeBrasileirao>();
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public ArrayList<ClubeBrasileirao> getConfrontos() {
        return confrontos;
    }

    public void setConfrontos(ClubeBrasileirao confrontos) {
        this.confrontos.add(confrontos);
    }
    
    
    
}
