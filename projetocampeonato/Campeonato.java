package projetocampeonato;

public abstract class Campeonato {
    private int rodada; // Variável que armazena a rodada em questão

    public abstract void rodada(int qRodada);

    public abstract void torneio();

    public abstract void tabela();

    public void setRodada(int rodada) {
        this.rodada = rodada;
    }
    
    public int getRodada() {
        return rodada;
    }
   
}