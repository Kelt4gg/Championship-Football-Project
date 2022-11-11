package projetocampeonato;

public class Clube {
    private String nome;
    private int fundacao;
    private String local;
    private double torcida;
    private int score;
    private int pontuacao;

    public Clube(String nome, int fundacao, String local, double torcida, int score) {
        this.nome = nome;
        this.fundacao = fundacao;
        this.local = local;
        this.torcida = torcida;
        this.score = score;
        this.pontuacao = 0;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getFundacao() {
        return fundacao;
    }
    
    public void setFundacao(int fundacao) {
        this.fundacao = fundacao;
    }
    
    public String getLocal() {
        return local;
    }
    
    public void setLocal(String local) {
        this.local = local;
    }
    
    public double getTorcida() {
        return torcida;
    }
    
    public void setTorcida(double torcida) {
        this.torcida = torcida;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPontuacao() {
        return pontuacao;
    }
    
    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    
}
