package projetocampeonato;

public class Principal{
    public static void main(String[] args) {
        Brasileirao bra = new Brasileirao();
        bra.torneio();
        bra.classificacao();

        CopaBrasil cp = new CopaBrasil();
        cp.setColocados(bra.passarColocados());
        cp.geraChave();
        cp.torneio();

    }
}