package projetocampeonato;

public class Clube {
    private String nome; // Variável que armazena o nome 
    private int fundacao; // Variável que armazena o ano de fundação do clube
    private String local; // Variável que armazena o nome do estádio do clube
    private long torcida; //Variável que armazena a quantidade de torcedores do clube
    private int score; //Variável que armazena a avaliação do clube

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