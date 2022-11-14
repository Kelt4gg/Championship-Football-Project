package projetocampeonato;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Campeonato {
    private static ArrayList<ClubeCopaBrasil> colocados; // Array que armazena clubes que passaram do brasileirão para a copa do brasil
    private int qTimes; // Variável que armazena a quantidade de times no campeonato
    private int rodada; // Variável que armazena a rodada em questão

    public Campeonato() {
        setqTimes(contaTimes()); //Usa o metodo conta times para colocar a quantidade de times no campeonato
        this.setColocados(new ArrayList<ClubeCopaBrasil>());
    }

    public abstract void rodada(int qRodada);

    public abstract void torneio();

    public abstract void classificacao();

    //Conta a quantidade de clubes armazenados no clubes.csv
    private int contaTimes() {
        String path = "projetocampeonato/clubes.csv";
        File file = null;
        Scanner scan = null;
        int counter = 0;
        try { // Instancia o arquivo se existir e passa para o scanner para pegar o arquivoe ler
            file = new File(path);
            scan = new Scanner(file);
            scan.nextLine();
            while(scan.hasNext()) {
                scan.nextLine();
                counter++;
            }
        } catch (IOException e) {// se o arquivo não existir printa um erro
            System.out.println(e);
        } finally {// e finalmente fecha o scanner
            scan.close();
        }
        return counter;
    }

    public int getqTimes() {
        return qTimes;
    }

    public void setqTimes(int qTimes) {
        this.qTimes = qTimes;
    }

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
