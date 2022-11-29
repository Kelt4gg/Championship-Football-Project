package projetocampeonato;

import java.io.IOException;
import java.util.Scanner;

public class Principal{
    private Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        printTitle("BOAS VINDAS AO SIMULADOR DE CAMPEONATO");
        Principal p = new Principal();
        
        boolean saidaP = false;
        OrganizacaoFutebol cbf = new OrganizacaoFutebol();
        while(!saidaP) {

            String[] optionsP = {"1 - Menu do Brasileirão",
                                 "2 - Menu da Copa do brasil",
                                 "3 - Menu de configuração de clubes",
                                 "0 - Para sair"};
            menu("MENU PRINCIPAL", optionsP);
            System.out.print("Escolha uma opção: ");

            switch (p.entrada()) {
                case "1": // Entrar no menu do brasileirão
                    if(cbf.getqClubes() != 16) {
                        printTitle("Deve ter pelo menos 16 clubes para iniciar o brasileirão");
                        break;
                    }
                    String[] optionsB = {"1 - Ver Classificação", 
                                         "2 - Simular rodadas", 
                                         "3 - Simular a competição inteira",
                                         "4 - Reiniciar o brasileirão", 
                                         "0 - Para voltar ao menu principal"};
                    boolean saidaB = false;
                    while(!saidaB) {
                        menu("MENU BRASILEIRÃO ANIMA", optionsB);
                        System.out.print("Escolha uma opção: ");
                        
                        switch (p.entrada()) {
                            case "1": // Ver classificação
                                cbf.getBra().tabela();
                                break;
                            
                            case "2": // Simula rodadas                          
                                boolean pass = false;
                                int rodadaB = 0;
                                while(!pass) {
                                    try {
                                        System.out.print("Quantas rodadas você quer: ");
                                        rodadaB = Integer.parseInt(p.entrada());
                                        if(rodadaB >= 1 && rodadaB <=30) {
                                            break;
                                        }
                                    } catch (Exception e) {}
                                    printTitle("!!O número de rodadas deve ser um inteiro entre 1 e 30!!");
                                }
                                cbf.getBra().rodada(rodadaB);
                                break;

                            case "3": // Simula competição inteira
                                cbf.getBra().torneio();
                                break;

                            case "4": // Reiniciar o brasileirão
                                cbf.setBra(new Brasileirao());
                                cbf.escalaClubes();
                                break;

                            case "0": // Voltar ao menu principal
                                saidaB = true;
                                break;
                            default:
                                printTitle("!!Deve ser escolhido uma opção valida!!");
                                break;
                            }
                    }
                    break;
                    
                case "2": // Entrar no menu da Copa do Brasil
                    if(cbf.getCopa().getRodada() == 0) {
                        cbf.getCopa().setColocados(cbf.getBra().passarColocados());
                    }
                    if(cbf.getCopa().getColocados() == null) {
                        printTitle("!!Para a copa do brasil começar, deve ter terminado o brasileirão!!");
                        break;
                    }
                    String[] optionsC = {"1 - Ver Classificação",
                                         "2 - Simular rodadas",
                                         "3 - Simualar a competição inteira",
                                         "4 - Gerar uma nova chave de clubes",
                                         "0 - Para voltar ao menu principal"};
                    boolean saidaC = false;
                    while(!saidaC) {
                        menu("MENU COPA FACS", optionsC);
                        System.out.print("Escolha uma opção: ");
                        
                        switch (p.entrada()) {
                            case "1": // Ver classificação
                                cbf.getCopa().tabela();;
                                break;
                            
                            case "2": // Rodar rodadas
                                boolean pass = false;
                                int rodadaC = 0;
                                while(!pass) {
                                    try {
                                        System.out.print("Quantas rodadas você quer: ");
                                        rodadaC = Integer.parseInt(p.entrada());
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

                            case "4": // Gera uma nova chave para a copa do brasil
                                cbf.getCopa().geraChave();
                                break;

                            case "0": // Volta ao menu anterior
                                saidaC = true;
                                break;

                            default:
                                printTitle("!!Deve ser escolhido uma opção valida!!");
                                break;
                            }
                    }
                    break;

                case "3": // Menu de configuração de clube
                    boolean saidaM = false;
                    String[] optionsM = {"1 - Listar Clube",
                                         "2 - Cadastrar novo clube",
                                         "3 - Modificar clube",
                                         "4 - Remover clube",
                                         "0 - Retornar ao menu pricipal"};
                    while(!saidaM) {
                        menu("Menu de configuração de clubes",optionsM);
                        System.out.printf("Escolha um opção: ");
                        switch (p.entrada()) {

                            case "1": // Listar clube
                                System.out.println("Digite o nome do clube que quer ver os atributos\n!Se quiser ver de todos os clubes, deixe em branco!");
                                System.out.print("Nome do clube: ");
                                String nome1 = p.entrada();
                                cbf.listarClubes(nome1);
                                break;

                            case "2": // Cadastrar novo clube
                                if(cbf.getBra().getRodada() != 0) {
                                    System.out.println("Os clubes só podem ser cadastrados antes da competição começar");
                                    break;
                                }
                                if(cbf.getqClubes() >= 16) {
                                    printTitle("A quantidade maxima de clubes cadastrados é 16");
                                    break;
                                }
                                cbf.cadastraClubes(null);;
                                break;
                        
                            case "3": // Modifica clube
                                if(cbf.getBra().getRodada() != 0) {
                                    System.out.println(" Os clubes só podem ser modificados antes da competição começar");
                                    break;
                                }
                                if(cbf.getqClubes() < 1) {
                                    printTitle("Não há clubes para serem modificados");
                                    break;
                                }
                                System.out.print("Nome do clube que deseja modificar: ");
                                String nome3 = p.entrada();
                                if(cbf.clubeCadastrado(nome3)) {
                                    cbf.modificaClube(nome3);
                                } else {
                                    printTitle("O clube "+nome3+" não foi encontrado");
                                }
                                break;

                            case "4": // remover clube
                                if(cbf.getqClubes() < 1) {
                                    printTitle("!!Não há clubes para remover!!");
                                    break;
                                }
            
                                System.out.print("Nome do clube que deseja remover: ");
                                String nome4 = p.entrada();
            
                                if(cbf.clubeCadastrado(nome4)) {
                                    System.out.printf("Deseja mesmo remover o clube "+nome4+"? Digite 's' para sim e 'n' para não: ");
                                    String confirmacao = p.entrada(); // Armazena a confirmação da remoção do clube

                                    if(confirmacao.equals("s")) {
                                        cbf.removerClube(nome4);
                                        printTitle("O clube "+nome4+" foi removido");

                                    } else if(confirmacao.equals("n")) {
                                        printTitle("O clube "+nome4+" não foi removido");
                                    System.out.printf("Deseja mesmo remover o clube "+nome4+"? Digite 's' para sim e 'n' para não: ");

                                    } else {
                                        printTitle("Não foi possivel dar continuidade a remoção do clube, por falha da confirmação");                        
                                    }
                                } else {
                                    printTitle("O clube "+nome4+" não foi encontrado");

                                }
                                break;

                            case "0": // Retornar ao menu principal
                                saidaM = true;
                                break;

                            default:
                                printTitle("!!Deve ser escolhido uma opção valida!!");
                                break;
                        }
                        
                    }
                    break;
  
                case "0": // Sair do programa
                    saidaP = true;
                    System.out.println("Tchau tchau");
                    break;
                default:
                    printTitle("!!Deve ser escolhido uma opção valida!!");
                    break;
                }
        }
    }
    

    /***********************************************************************************************
     * Printa um menu com um titulo e N opções
    ***********************************************************************************************/
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

    /***********************************************************************************************
     * Printa uma divisoria
    ***********************************************************************************************/
    public static void printDiv(int length) {
        System.out.println("+"+"-".repeat(length - 2)+"+");
    }

    /***********************************************************************************************
     * Printa um titulo com divisorias e uma frase no meio
    ***********************************************************************************************/
    public static void printTitle(String frase) {
        frase = "|"+" ".repeat(6)+frase+" ".repeat(((int) (frase.length() * 0.3)))+"|";
        printDiv(frase.length());
        System.out.println(frase);
        printDiv(frase.length());
    }


    /***********************************************************************************************
     * Recebe uma entrada do usuário
    ***********************************************************************************************/
    public String entrada() {
        Scanner in = this.getScan();
        return in.nextLine();
    }

    public Scanner getScan() {
        return scan;
    }
    
}