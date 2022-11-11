package projetocampeonato;

public class Principal{
    public static void main(String[] args) {
        Brasileirao braseileirao = new Brasileirao();
        braseileirao.inicializaClubes();
        braseileirao.rodada(1);
    }
}