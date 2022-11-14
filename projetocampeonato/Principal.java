package projetocampeonato;

public class Principal{
    public static void main(String[] args) {
        //Brasileirao bra = new Brasileirao();
        //bra.torneio();
        //bra.classificacao();

        //CopaBrasil cp = new CopaBrasil();
        //cp.setColocados(bra.passarColocados());
        //cp.geraChave();
        //cp.torneio();

        //
        printTitle("BOAS VINDAS AO SIMULADOR DE CAMPEONATO BRASILEIRO");
        String[] options = {"1 Ir para o menu do Brasileirão", "2 Ir para o menu da Copa do brasil", "0 Para sair"};
        menu("MENU PRINCIPAL", options);
        int option = 

    }

    public static void menu(String titulo, String[] options) {
        int espaçosIniciais = 6;
        String title = "|"+" ".repeat(10)+titulo+" ".repeat(20)+"|";
        int length = title.length();
        printDiv(length);
        System.out.println(title);
        printDiv(length);
        if(options.length > 0) {
            for(String option : options) {
                String line = "|"+" ".repeat(espaçosIniciais)+option;
                line += " ".repeat(length - line.length() - 1)+"|";
                System.out.println(line);
            }
            printDiv(length);
        }
        
    }

    public static void printDiv(int length) {
        System.out.println("+"+"-".repeat(length - 2)+"+");
    }
    public static void printOption(String line) {
        System.out.println();
    }

    public static void printTitle(String line) {
        line = "|"+" ".repeat(6)+line+" ".repeat(15)+"|";
        printDiv(line.length());
        System.out.println(line);
        printDiv(line.length());
    }
}