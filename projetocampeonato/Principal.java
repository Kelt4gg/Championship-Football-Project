package projetocampeonato;

import java.io.IOException;
import java.util.Scanner;

public class Principal{
    public static void main(String[] args) throws IOException {
        //
        //bra.torneio();
        //bra.classificacao();

        //cp.setColocados(bra.passarColocados());
        //cp.geraChave();
        //cp.torneio();
        
        printTitle("BOAS VINDAS AO SIMULADOR DE CAMPEONATO BRASILEIRO");
        
        boolean exitP = false;
        Scanner scan = new Scanner(System.in);
        OrganizacaoFutebol cbf = new OrganizacaoFutebol();
        while(!exitP) {

            String[] optionsP = {"1 - Listar Clubes","2 - Cadastrar novo clube","3 - Remover Clube","4 - Ir para o menu do Brasileirão", "5 - Ir para o menu da Copa do brasil", "0 - Para sair"};
            menu("MENU PRINCIPAL", optionsP);
            System.out.print("Escolha uma opção: ");
            String choiceP = scan.nextLine();
            switch (choiceP) {
                case "1": // Lista clubes do csv
                    cbf.listarClubes();
                    break;

                case "2": // Cadastra novo clube
                    if(cbf.getBra().getRodada() > 0) {
                        System.out.println("Clubes só podem ser cadastrados antes da competição começar");
                        return;
                    }
                    
                    if(cbf.getqClubes() >= 16) {
                        System.out.println("A quantidade maxima de clubes cadastrados é 16");
                        return;
                    }
                    String error = "Motivos: \n";  
                    int valid = 0;

                    System.out.print("Nome do clube: ");
                    String nomeClube = scan.nextLine();
                    valid++;
                    
                    int fundacao = 0;
                    try {
                        System.out.print("Ano de fundação do clube: ");
                        fundacao = Integer.parseInt(scan.nextLine());
                        valid++;
                    } catch (Exception e) {
                        error += "O ano de fundação deve ser um numero inteiro\n";
                    }

                    System.out.print("Estadio do clube: ");
                    String estadio = scan.nextLine();
                    valid++;
                    
                    long torcida = 0;
                    try {
                        System.out.print("Quantidade de torcedores do clube: ");
                        torcida = Long.parseLong(scan.nextLine());
                        valid++;
                    } catch (Exception e) {
                        error += "A quantidade de torcedores deve ser um numero inteiro\n";
                    }

                    int nota = 0;
                    try {
                        System.out.print("Nota do clube de 1 a 100: ");
                        nota = Integer.parseInt(scan.nextLine());
                        if(nota <= 0 || nota > 100) {
                            error += "A nota do clube deve ser entre 1 e 100\n";
                            valid--;
                        }
                        valid++;
                    } catch (Exception e) {
                        error += "A nota do clube deve ser um numero inteiro\n";
                    }
                    if(valid == 5) {
                        cbf.cadastraClube(nomeClube,fundacao,estadio,torcida,nota);
                        System.out.println("O clube %s foi cadastrado com sucesso");
                    } else {
                        System.out.println("Não foi possive cadastrar o clube %s, " +error);
                    }
                    break;
                    
                case "3": //Remover clube
                    if(cbf.getqClubes() < 1) {
                        printTitle("!!Não há clubes para remover!!");
                        break;
                    }
                    System.out.print("Nome do clube que deseja remover: ");
                    String nome = "";
                    nome = scan.nextLine();
                    cbf.removerClube(nome);
                    break;
                    
                case "4": // Entrar no menu do brasileirão
                    String[] optionsB = {"1 - Ver Classificação", "2 - Rodar rodadas", "3 - Para rodar a competição inteira","4 - Reiniciar o brasileirão", "0 - Para voltar ao menu anterior"};
                    boolean exitB = false;
                    while(!exitB) {
                        menu("MENU BRASILEIRÃO", optionsB);
                        System.out.print("Escolha uma opção: ");
                        String choiceB = scan.next();
                        
                        switch (choiceB) {
                            case "1": // Ver classificação
                                cbf.getBra().classificacao();
                                break;
                            
                            case "2": // Roda um rodada                          
                                boolean pass = false;
                                int rodadaB = 0;
                                while(!pass) {
                                    try {
                                        System.out.print("Quantas rodadas você quer: ");
                                        String rodadaAux = scan.next();
                                        rodadaB = Integer.parseInt(rodadaAux);
                                        if(rodadaB >= 1 && rodadaB <=30) {
                                            break;
                                        }
                                    } catch (Exception e) {}
                                    printTitle("!!O número de rodadas deve ser um inteiro entre 1 e 30!!");
                                }
                                cbf.getBra().rodada(rodadaB);
                                break;

                            case "3": // Rodar competição inteira
                                cbf.getBra().torneio();
                                break;

                            case "4": // Reiniciar o brasileirão
                                cbf.setBra(new Brasileirao());
                                cbf.escalaClubes();
                                cbf.getBra().setClubesAux(cbf.getBra().getClubes());
                                break;

                            case "0": // Voltar ao menu principal
                                exitB = true;
                                break;
                            default:
                                printTitle("!!Deve ser escolhido uma opção valida!!");
                                break;
                            }
                    }
                    break;

                case "5": // Entrar no menu da Copa do Brasil
                    cbf.getCopa().setColocados(cbf.getBra().passarColocados());
                    if(cbf.getCopa().getColocados() == null) {
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
                            case "1": // Ver classificação
                                cbf.getCopa().classificacao();;
                                break;
                            
                            case "2": // Rodar rodadas
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
                                cbf.getCopa().rodada(rodadaC);
                                break;
                            case "3": // Rodas toda a compedição
                                cbf.getCopa().torneio();
                                break;
                            case "4":
                                cbf.getCopa().geraChave();
                                break;
                            case "0":
                                exitC = true;
                                break;
                            default:
                                printTitle("!!Deve ser escolhido uma opção valida!!");
                                break;
                            }
                    }
                    break;
                case "0": // Sair do programa
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