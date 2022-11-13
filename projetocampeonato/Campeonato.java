package projetocampeonato;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Campeonato {
    private static ArrayList<ClubeCopaBrasil> colocados;
    private int qTimes;
    private int rodada;

    public Campeonato() {
        this.qTimes = contaTimes();
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
        try { // Instancia 
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

    public ArrayList<ClubeCopaBrasil> getColocados() {
        return colocados;
    }

    public void setColocados(ArrayList<ClubeCopaBrasil> colocados) {
        this.colocados = colocados;
    }

    

    

    
}
