package projetocampeonato;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Brasileirao extends Campeonato{
    private ArrayList<ClubeBrasileirao> clubes;

    public Brasileirao() {
        clubes = new ArrayList<ClubeBrasileirao>();
        super.setRodada(1);
        EscalaTimes();
    }

    public void EscalaTimes() {
        String path = "projetocampeonato/clubes.csv";
        File file = null;
        Scanner scan = null;
        try {
            file = new File(path);
            scan = new Scanner(file);
            scan.nextLine();
            int k = 0;
            while(scan.hasNext()) {
                String line = scan.nextLine();
                String[] separeted = line.split(",");
                this.clubes.add(new Clube(separeted[0], Integer.parseInt(separeted[1]), separeted[2], Double.parseDouble(separeted[3]), Integer.parseInt(separeted[4])));
                k++;
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            scan.close();
        }
    }
    
    @Override
    public void tabela() {
        organizaEscalacao();
        for(Clube time : clubes) {
            System.out.println(time.getNome());
        }
    }

    public void organizaEscalacao(){
        for(int k = 0; k < this.clubes.size(); k++){
            for(int kk = k+1; kk < this.clubes.size(); kk++){
                if(clubes.get(k).getPontuacao() < clubes.get(kk).getPontuacao()){
                    clubes.add(k, clubes.get(kk));
                    clubes.remove(kk+1);
                } 
            }
        }
    }

    @Override
    public Clube match(Clube time1, Clube time2) {
        int probabilidade = 49;
        probabilidade += time1.getScore() - time2.getScore();
        Random randomizer = new Random();
        int number = randomizer.nextInt(100);
        if(number <= probabilidade) {
            return time1;
        } else {
            return time2;
        }
    }

    @Override
    public void rodada(int qRodada) {
        // TODO Auto-generated method stub
        Clube team = null;
        for(int k = 0; k < qRodada; k++) {
            for(int kk = k+1; kk < clubes.size(); kk++) {
                
                this.clubes.get(this.match(k, kk)).setPontuacao();
            } 
        }
        this.escalacao();
        
    }

    @Override
    public void torneio() {
        // TODO Auto-generated method stub
        
    }

    public void passarColocados() {

    }

}
