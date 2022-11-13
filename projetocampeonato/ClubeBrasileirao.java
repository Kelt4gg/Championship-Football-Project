package projetocampeonato;

import java.util.ArrayList;

public class ClubeBrasileirao extends Clube{
    private int pontuacao;
    private ArrayList<ClubeBrasileirao> confrontos;

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
