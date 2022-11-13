//Classe sem função por enquanto
package projetocampeonato;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ExportaClubesF {
    
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\Kevin S\\Documents\\GitHub\\Championship-Football-Project\\projetocampeonato\\clubes.csv";
        File file = new File(path);
        Scanner scanfile = null;
        FileWriter fw = null;

        try {
            scanfile = new Scanner(file);
            fw = new FileWriter(path);
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        printMenu();
        Scanner scan = new Scanner(System.in);
        int option = scan.nextInt();

        //Menu de switchs
        while(option != 0) {
            switch (option) {
                case 1:
                    fw.write(criaClube());
                    break;
                case 2:
                    while(scanfile.hasNext()) {
                        System.out.println(scanfile.nextLine());
                    }
                    break;
                default:
                    System.out.println("!!Opção Invalida!! Escolha uma opção valida!!");
                    break;
            }
            printMenu();
            option = scan.nextInt();
        }
        
        //Fechando todos os scanners
        scan.close();
        scanfile.close();
        fw.close();
    }

    //Printa o menu
    public static void printMenu() {
        System.out.println("Criador de clubes, escolha uma opção:");
        System.out.println("Opção 1: Cria time\n" +
                           "Opção 2: Mostra times\n" +
                           "Opção 0: Sair do programa");
        System.out.print("Escolha: ");
    }

    public static String criaClube(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Criador de time: ");
        String nome = scan.nextLine();
        int fundacao = scan.nextInt();
        String local = scan.next();
        double torcida = scan.nextDouble();
        String clube = String.format("%s,%d,%s,%f",nome,fundacao,local,torcida);
        scan.close();
        return clube;
    };
    
}
