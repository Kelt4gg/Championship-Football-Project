package projetoCampeonato;

public class Principal{
    public static void main(String[] args) {
        CopaBrasil cB = new CopaBrasil();
        cB.organiza();
        cB.EscalacaoTimes();
        cB.rodada(1);

    }
}