package projetocampeonato;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class OrganizacaoFutebol {
    private ArrayList<Clube> clubes; 
    private int qTimes;


    public OrganizacaoFutebol(int option) {
        this.clubes = new ArrayList<Clube>();
        this.qTimes = contaTimes();
    }

    public void organiza() {
        String path = "projetocampeonato/clubes.csv";
        File file = null;
        Scanner scan = null;
        ArrayList<Integer> numbers = sorteio();
        try {
            file = new File(path);
            scan = new Scanner(file);
            scan.nextLine();
            int k = 0;
            while(scan.hasNext()) {
                String line = scan.nextLine();
                String[] separeted = line.split(",");
                if(numbers.contains(k)) {
                    this.clubes.add(new Clube(separeted[0], Integer.parseInt(separeted[1]), separeted[2], Double.parseDouble(separeted[3]), Integer.parseInt(separeted[4])));
                }
                k++;
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            scan.close();
        }
    }

    public ArrayList<Integer> sorteio() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        while(numbers.size() != qTimes) {
            Random gerador = new Random();
            int number = gerador.nextInt(qTimes);
            if(!numbers.contains(number)) {
                numbers.add(number);
            }
        }
        return numbers;
    }

    public void EscalacaoTimes() {
        for(Clube time : clubes) {
            System.out.println(time.getNome());
        }
    }

    public int contaTimes() {
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

    public ArrayList<Clube> getClubes() {
        return clubes;
    }

    public void setClubes(ArrayList<Clube> clubes) {
        this.clubes = clubes;
    }

    public int getqTimes() {
        return qTimes;
    }

    public void setqTimes(int qTimes) {
        this.qTimes = qTimes;
    }

    
    
}


