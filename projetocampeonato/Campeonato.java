package projetocampeonato;

import java.util.ArrayList;

public abstract class Campeonato {
    private static ArrayList<ClubeCopaBrasil> colocados; // Array que armazena clubes que passaram do brasileirão para a copa do brasil
    private int rodada; // Variável que armazena a rodada em questão

    public Campeonato() {
        this.setColocados(new ArrayList<ClubeCopaBrasil>());
    }

    public abstract void rodada(int qRodada);

    public abstract void torneio();

    public abstract void classificacao();

    public void setRodada(int rodada) {
        this.rodada = rodada;
    }
    
    public int getRodada() {
        return rodada;
    }

    public ArrayList<ClubeCopaBrasil> getColocados() {
        return colocados;
    }

    public void setColocados(ArrayList<ClubeCopaBrasil> colocados) {
        Campeonato.colocados = colocados;
    }

    

    

    
}
