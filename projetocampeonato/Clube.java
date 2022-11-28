package projetocampeonato;

public class Clube {
    private String nome;
    private int fundacao;
    private String local;
    private long torcida;
    private int score;

    public Clube(String nome, int fundacao, String local, long torcida, int score) {
        this.nome = nome;
        this.fundacao = fundacao;
        this.local = local;
        this.torcida = torcida;
        this.score = score;
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
    
    public long getTorcida() {
        return torcida;
    }
    
    public void setTorcida(long torcida) {
        this.torcida = torcida;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}