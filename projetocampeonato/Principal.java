package projetocampeonato;

import java.util.Scanner;

public class Principal{
    public static void main(String[] args) {
        //
        //bra.torneio();
        //bra.classificacao();

        //cp.setColocados(bra.passarColocados());
        //cp.geraChave();
        //cp.torneio();
        
        //
        Scanner scan = new Scanner(System.in);
        printTitle("BOAS VINDAS AO SIMULADOR DE CAMPEONATO BRASILEIRO");
        
        boolean exitP = false;
        Brasileirao bra = new Brasileirao();
        CopaBrasil copa = new CopaBrasil();
        while(!exitP) {
            String[] optionsP = {"1 - Ir para o menu do Brasileirão", "2 - Ir para o menu da Copa do brasil", "0 - Para sair"};
            menu("MENU PRINCIPAL", optionsP);
            System.out.print("Escolha uma opção: ");

            String choiceP = scan.next();
            switch (choiceP) {
                case "1":
                    String[] optionsB = {"1 - Cadastrar novo clube","2 - Ver Classificação", "3 - Rodar rodadas", "4 - Para rodar o torneio inteiro","5 - Reiniciar o brasileirão", "0 - Para voltar ao menu anterior"};
                    boolean exitB = false;
                    while(!exitB) {
                        menu("MENU BRASILEIRÃO", optionsB);
                        System.out.print("Escolha uma opção: ");
                        String choiceB = scan.next();

                        switch (choiceB) {
                            case "1":
                            
                                String error = "Motivos: \n";  
                                int valid = 0;

                                System.out.print("Nome do clube: ");
                                String nomeClube = scan.next();
                                valid++;
                                
                                int fundacao = 0;
                                try {
                                    System.out.print("Ano de fundação do clube: ");
                                    fundacao = Integer.parseInt(scan.next());
                                    valid++;
                                } catch (Exception e) {
                                    error += "O ano de fundação deve ser um numero inteiro\n";
                                    scan.close();
                                    
                                    return;
                                }

                                System.out.print("Estadio do clube: ");
                                String estadio = scan.next();
                                valid++;

                                long torcida = 0;
                                try {
                                    System.out.print("Quantidade de torcedores do clube: ");
                                    torcida = Long.parseLong(scan.next());
                                    valid++;
                                } catch (Exception e) {
                                    System.out.println("A quantidade de torcedores deve ser um numero inteiro");
                                    scan.close();
                                    return;
                                }

                                int nota = 0;
                                try {
                                    System.out.print("Nota do clube de 1 a 100: ");
                                    nota = Integer.parseInt(scan.next());
                                    if(nota <= 0 || nota > 100) {
                                        error += "A nota do clube deve ser entre 1 e 100\n";
                                        valid--;
                                    }
                                    valid++;
                                } catch (Exception e) {
                                    error += "A nota do clube deve ser um numero inteiro\n";
                                    scan.close();
                                    return;
                                }

                                if(valid >= 4) {
                                    bra.cadastraClube(nomeClube, fundacao, estadio, torcida, nota);
                                } else {
                                    System.out.printf("Não foi possivel cadastrar o clube, %s", error);
                                }
                                break;
                            
                            case "2":
                                bra.classificacao();
                                break;
                            
                            case "3":
                                
                                boolean pass = false;
                                int rodadaB = 0;
                                while(!pass) {
                                    try {
                                        System.out.print("Quantas rodadas você quer: ");
                                        String rodadaAux = scan.next();
                                        rodadaB = Integer.parseInt(rodadaAux);
                                        System.out.println("bleh"+rodadaB);
                                        if(rodadaB >= 1 && rodadaB <=30) {
                                            pass = true;
                                        }
                                    } catch (Exception e) {

                                    }
                                    printTitle("!!O número de rodadas deve ser um inteiro entre 1 e 30");
                                }
                                bra.rodada(rodadaB);
                                break;
                            case "4":
                                bra.torneio();
                                break;
                            case "5":
                                bra = new Brasileirao();
                                break;
                            case "0":
                                exitB = true;
                                break;
                            default:
                                printTitle("!!Deve ser escolhido uma opção valida!!");
                                break;
                        }
                    }
                    break;
                case "2":
                    copa.setColocados(bra.passarColocados());
                    if(copa.getColocados() == null) {
                        printTitle("!!Para a copa do brasil começar, deve ter terminado o brasileirão!!");
                        break;
                    }
                    String[] optionsC = {"1 - Ver Classificação", "2 - Rodar rodadas", "3 - Para rodar o torneio inteiro","4 - Gerar uma nova chave de clubes", "0 - Para voltar ao menu anterior"};
                    boolean exitC = false;
                    while(!exitC) {
                        menu("MENU COPA DO BRASIL", optionsC);
                        System.out.print("Escolha uma opção: ");
                        String choiceC = scan.next();
                        
                        switch (choiceC) {
                            case "1":
                                copa.classificacao();
                                break;
                            
                            case "2":
                                
                                boolean pass = false;
                                int rodadaC = 0;
                                while(!pass) {
                                    try {
                                        System.out.print("Quantas rodadas você quer: ");
                                        String rodadaAux = scan.next();
                                        rodadaC = Integer.parseInt(rodadaAux);
                                        if(rodadaC >= 1 && rodadaC <=4) {
                                            pass = true;
                                        }
                                    } catch (Exception e) {}
                                    printTitle("!!O número de rodadas deve ser um inteiro entre 1 e 4");
                                }
                                copa.rodada(rodadaC);
                                break;
                            case "3":
                                copa.torneio();
                                break;
                            case "4":
                                copa.geraChave();
                                break;
                            case "0":
                                exitC = true;
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case "0":
                    exitP = true;
                    System.out.println("Tchau tchau");
                    break;
                default:
                    printTitle("!!Deve ser escolhido uma opção valida!!");
                    break;
            }
        }
        scan.close();
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
        line = "|"+" ".repeat(6)+line+" ".repeat(((int) (line.length() * 0.3)))+"|";
        printDiv(line.length());
        System.out.println(line);
        printDiv(line.length());
    }
}