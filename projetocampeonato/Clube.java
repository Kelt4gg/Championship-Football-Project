package ProjetoCampeonato;

public class Clube {
    private String nome;
    private int fundacao;
    private String local;
    private double torcida;
    private int score;
    private int pontuação;

    public Clube(String nome, int fundacao, String local, double torcida, int score) {
        this.nome = nome;
        this.fundacao = fundacao;
        this.local = local;
        this.torcida = torcida;
        this.score = score;
        this.pontuação = 0;
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

    public int getPontuação() {
        return pontuação;
    }

    public void setPontuação(int pontuação) {
        this.pontuação = pontuação;
    }
    
}
