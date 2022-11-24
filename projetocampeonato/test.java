package projetocampeonato;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws IOException {

        String word = "America";
        String word2 = "Afeganistao";
        System.out.println(word.charAt(0) > word2.charAt(0));
        File file = null;
        Scanner scan = null;
        FileWriter fw = null;
        try {
            file = new File("projetocampeonato/clubes.csv");
            scan = new Scanner(file);
            
        } catch (IOException e) {
            System.out.println(e);
        }
        ArrayList<String> allData = new ArrayList<String>();
        while(scan.hasNextLine()) {
            allData.add(scan.nextLine());
        }
        int counter = 0;
        fw = new FileWriter(file);
        for(String line : allData) {
            fw.write(allData.get(counter)+"\n");
            counter++;
        }
        fw.write("Vitoria de Feira,1902,barradão de feira,2000000,67");
        fw.flush();
        fw.close();
        scan.close();

        
    }
}
