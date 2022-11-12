package projetocampeonato;

public class ClubeBrasileirao extends Clube{
    private int pontuacao;

    public ClubeBrasileirao(String nome, int fundacao, String local, double torcida, int score) {
        super(nome, fundacao, local, torcida, score);
        this.pontuacao = 0;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
    
    
}
