package projetocampeonato;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Campeonato {
    private int qTimes;
    private int rodada;
    private static ArrayList<ClubeCopaBrasil> clubesColocados;

    public Campeonato() {
        this.qTimes = contaTimes();
    }

    public abstract Clube match(Clube time1, Clube time2);

    public abstract void rodada(int qRodada);

    public abstract void torneio();

    public abstract void tabela();

    private int contaTimes() {
        String path = "projetocampeonato/clubes.csv";
        File file = null;
        Scanner scan = null;
        int counter = 0;
        try {
            file = new File(path);
            scan = new Scanner(file);
            scan.nextLine();
            while(scan.hasNext()) {
                scan.nextLine();
                counter++;
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
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

    public static ArrayList<ClubeCopaBrasil> getClubesColocados() {
        return clubesColocados;
    }

    public static void setClubesColocados(ArrayList<ClubeCopaBrasil> clubesColocados) {
        Campeonato.clubesColocados = clubesColocados;
    }

    

    
}
