package projetocampeonato;

import java.io.IOException;
import java.util.Scanner;

public class Principal{
    public static void main(String[] args) throws IOException {
        printTitle("BOAS VINDAS AO SIMULADOR DE CAMPEONATO BRASILEIRO");
        
        boolean exitP = false;
        Scanner scan = new Scanner(System.in);
        OrganizacaoFutebol cbf = new OrganizacaoFutebol();
        while(!exitP) {

            String[] optionsP = {"1 - Menu do Brasileirão","2 - Menu da Copa do brasil","3 - Menu de configuração de clubes", "0 - Para sair"};
            menu("MENU PRINCIPAL", optionsP);
            System.out.print("Escolha uma opção: ");
            String choiceP = scan.nextLine();

            switch (choiceP) {
                case "1": // Entrar no menu do brasileirão
                    String[] optionsB = {"1 - Ver Classificação", "2 - Simula rodadas", "3 - Para simular a competição inteira","4 - Reiniciar o brasileirão", "0 - Para voltar ao menu principal"};
                    boolean exitB = false;
                    while(!exitB) {
                        menu("MENU BRASILEIRÃO", optionsB);
                        System.out.print("Escolha uma opção: ");
                        String choiceB = scan.next();
                        
                        switch (choiceB) {
                            case "1": // Ver classificação
                                cbf.getBra().classificacao();
                                break;
                            
                            case "2": // Simula rodadas                          
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

                            case "3": // Simula competição inteira
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
                    
                case "2": // Entrar no menu da Copa do Brasil
                    cbf.getCopa().setColocados(cbf.getBra().passarColocados());
                    if(cbf.getCopa().getColocados() == null) {
                        printTitle("!!Para a copa do brasil começar, deve ter terminado o brasileirão!!");
                        break;
                    }
                    cbf.getCopa().geraChave();
                    String[] optionsC = {"1 - Ver Classificação", "2 - Rodar rodadas", "3 - Para rodar o torneio inteiro","4 - Gerar uma nova chave de clubes", "0 - Para voltar ao menu principal"};
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

                            case "4": // Gera uma nova chave para a copa do brasil
                                cbf.getCopa().geraChave();
                                break;

                            case "0": // Volta ao menu anterior
                                exitC = true;
                                break;

                            default:
                                printTitle("!!Deve ser escolhido uma opção valida!!");
                                break;
                            }
                    }
                    break;

                case "3": // Menu de configuração de clube
                    boolean exitM = false;
                    String[] optionsM = {"1 - Listar Clube","2 - Cadastrar novo clube","3 - Modificar clube","4 - Remover clube","0 - Retornar ao menu pricipal"};
                    while(!exitM) {
                        menu("Menu de configuração de clubes",optionsM);
                        System.out.printf("Escolha um opção: ");
                        String choiceM = scan.nextLine();
                        switch (choiceM) {
                            case "1": // Listar clube
                                System.out.println("Digite o nome do clube que quer ver os atributos, se quiser ver de todos os ge em branco");
                                System.out.print("Nome do clube: ");
                                cbf.listarClubes(scan.nextLine());
                                break;

                            case "2": // Cadastrar novo clube
                                if(verificaRodada(cbf.getBra().getRodada())) {
                                    System.out.println("Clubes só podem ser cadastrados antes da competição começar");
                                    break;
                                }
                                if(verificaQuantidadeClubes(cbf.getqClubes())) {
                                    System.out.println("A quantidade maxima de clubes cadastrados é 16");
                                    break;
                                }
            
                                String error = "Motivos: \n";  
                                int valid = 0;
            
                                System.out.print("Nome do clube: ");
                                String nomeClube = scan.nextLine();
                                valid++;
                                
                                System.out.print("Estadio do clube: ");
                                String estadio = scan.nextLine();
                                valid++;

                                long[] variables = new long[3];
                                String[] variableNames = {"Ano de fundação", "Quantidade de torcedores", "Nota do clube"};
                                for(int k = 0; k < variables.length; k++) {
                                    try {
                                        System.out.print(variableNames[k] +": ");
                                        variables[k] = Long.parseLong(scan.nextLine());
                                        if(k == 2) {
                                            if(variables[k] <= 0 || variables[k] > 100) {
                                                error += "A nota do clube deve ser entre 1 e 100\n";
                                                valid--;
                                            }
                                        }
                                        valid++;
                                    } catch (Exception e) {
                                        error += variableNames[k]+ "deve ser um numero inteiro\n";
                                    }
                                }

                                if(valid == 5) {
                                    cbf.cadastraClube(nomeClube,(int) variables[0],estadio,variables[1],(int)variables[2]);
                                    System.out.printf("O clube %s foi cadastrado com sucesso\n", nomeClube);
                                } else {
                                    System.out.println("Não foi possive cadastrar o clube, " +error);
                                }
                                break;
                        
                            case "3": // Modificar clube
                                if(cbf.getqClubes() < 1) {
                                    printTitle("!!Não há clubes para modificar!!");
                                    break;
                                }
                                if(verificaRodada(cbf.getBra().getRodada())) {
                                    System.out.println("Clubes só podem ser modificador antes da competição começar");
                                    break;
                                }
            
                                System.out.print("Nome do clube que deseja modificar: ");
                                String nomeM = "";
                                nomeM = scan.nextLine();
                                
                                Clube clubeNovo = null;
                                boolean foundM = false;
                                for(ClubeBrasileirao clube : cbf.getBra().getClubes()) {
                                    if(clube.getNome().toUpperCase().equals(nomeM.toUpperCase())) {
                                        clubeNovo = clube;
                                        foundM = true;
                                    }
                                }
                                
                                long[] attributes = new long[3];
                                attributes[0] = clubeNovo.getFundacao();
                                attributes[1] = clubeNovo.getTorcida();
                                attributes[2] = clubeNovo.getScore();
                                if(foundM) {
                                    
                
                                    String errorM = "Motivos: \n";  
                                    int validM = 0;
                                    
                                    printTitle("Se não quiser modificar algum atributo, coloque 0");
                                    System.out.printf("Atual nome do clube: %s\nNovo nome do clube: ",clubeNovo.getNome());
                                    String nomeNovo = scan.nextLine();
                                    if(!nomeNovo.equals("0")) {
                                        clubeNovo.setNome(nomeNovo);;
                                    }
                                    validM++;
                                    
                                    System.out.printf("Atual estadio do clube: %s\nNovo estadio do clube: ", clubeNovo.getLocal());
                                    String estadioNovo = scan.nextLine();
                                    if(!estadioNovo.equals("0")) {
                                        clubeNovo.setLocal(estadioNovo);
                                    }
                                    validM++;
    
                                    long[] variablesM = new long[3];
                                    String[] variableNamesM = {"Ano de fundação", "Quantidade de torcedores", "Nota do clube"};
                                    for(int k = 0; k < variablesM.length; k++) {
                                        try {
                                            System.out.printf("Atual %s do clube: %d\nNovo %s do clube: ", variableNamesM[k],attributes[k], variableNamesM[k]);
                                            variablesM[k] = Long.parseLong(scan.nextLine());
                                            if(k == 2) {
                                                if(variablesM[k] < 0 || variablesM[k] > 100) {
                                                    errorM += "A nota do clube deve ser entre 0 e 100\n";
                                                    validM--;
                                                }
                                            }
                                            validM++;
                                        } catch (Exception e) {
                                            errorM += variableNamesM[k]+ " deve ser um numero inteiro\n";
                                        }
                                    }
                                    if(!(variablesM[0] == 0)) {
                                        clubeNovo.setFundacao((int)variablesM[0]);
                                    }
                                    if(!(variablesM[1] == 0)) {
                                        clubeNovo.setTorcida(variablesM[1]);
                                    }
                                    if(!(variablesM[2] == 0)) {
                                        clubeNovo.setScore((int)variablesM[2]);
                                    }
    
                                    if(validM == 5) {
                                        cbf.removerClube(nomeNovo);
                                        cbf.cadastraClube(clubeNovo);
                                        System.out.printf("O clube %s foi modificado com sucesso\n", nomeNovo);
                                    } else {
                                        System.out.println("Não foi possive cadastrar o clube, " +errorM);
                                    }
                                } else {
                                    printTitle("O clube "+nomeM+" não foi encontrado");
                                }
                                break;

                            case "4": // remover clube
                                if(cbf.getqClubes() < 1) {
                                    printTitle("!!Não há clubes para remover!!");
                                    break;
                                }
            
                                System.out.print("Nome do clube que deseja remover: ");
                                String nomeR = "";
                                nomeR = scan.nextLine();
            
                                boolean foundR = false;
                                for(ClubeBrasileirao clube : cbf.getBra().getClubes()) {
                                    if(clube.getNome().toUpperCase().equals(nomeR.toUpperCase())) {
                                        foundR = true;
                                    }
                                }
            
                                if(foundR) {
                                    System.out.printf("Deseja mesmo remover o clube "+nomeR+"? Digite 's' para sim e 'n' para não: ");
                                    String confirm = scan.nextLine();
                                    if(confirm.equals("s")) {
                                        cbf.removerClube(nomeR);
                                        printTitle("O clube "+nomeR+" foi removido");
                                    } else if(confirm.equals("n")) {
                                        printTitle("O clube "+nomeR+" não foi removido");
                                    } else {
                                        printTitle("Não foi possivel dar continuaidade a remoção do clube, por falha da confirmação");                        
                                    }
                                } else {
                                    printTitle("O clube "+nomeR+" não foi encontrado");
                                }
                                break;

                            case "0": // Retornar ao menu principal
                                exitM = true;
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

    public static boolean verificaRodada(int rodada) {
        if(rodada > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean verificaQuantidadeClubes(int qClubes) {
        if(qClubes >= 16) {
            return true;
        } else {
            return false;
        }
    }
}