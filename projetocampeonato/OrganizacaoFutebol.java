//Classe sem função por enquanto
package projetocampeonato;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;

public class OrganizacaoFutebol {
    private Brasileirao bra;
    private CopaBrasil copa;
    private int qClubes;


    public OrganizacaoFutebol() {
        this.setBra(new Brasileirao());
        this.escalaClubes();
        this.setCopa(new CopaBrasil());
    }

    /***********************************************************************************************
     * A função lê a tabela csv de clubes e adiciona no array de clubes da classe brasileirão
    ***********************************************************************************************/
    public void escalaClubes() { // Pega cada time no csv e adiciona no array clubes
        this.setqClubes(0);
        String path = "projetocampeonato/clubes.csv";
        File file = null;
        Scanner scan = null;

        try { // Faz uma verificação se o arquivo de clubes existe
            file = new File(path);
            scan = new Scanner(file);
            scan.nextLine();
            ArrayList<ClubeBrasileirao> clubes = new ArrayList<ClubeBrasileirao>();
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] atributosClube = line.split(","); // Separa a linha do csv em virgulas e coloca dentro do array de atributos
                
                ;//Instancia cada time colocando cada atributo separado na matriz atributosClube
                clubes.add(new ClubeBrasileirao(atributosClube[0], // Nome do Clube
                                         Integer.parseInt(atributosClube[1]), // Ano de fundação do Clube
                                                          atributosClube[2], // Estadio do Clube
                                        Long.parseLong(atributosClube[3]), // Quantidade da torcida do Clube
                                         Integer.parseInt(atributosClube[4]))); // Pontuação do Clube
                this.setqClubes(this.getqClubes()+1);
            }
            this.getBra().setClubes(organizaClubes(clubes));
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            scan.close();
        }
    }

    /***********************************************************************************************
     * A função recebe um array de clubes e organiza por ordem alfabetica
    ***********************************************************************************************/
    public ArrayList<ClubeBrasileirao> organizaClubes(ArrayList<ClubeBrasileirao> clubes) {
        for(int k = 0; k < clubes.size(); k++) {
            for(int kk = k+1; kk< clubes.size(); kk++) {
                if(ordemAlfabetica(clubes.get(k).getNome().toUpperCase(), clubes.get(kk).getNome().toUpperCase(), 0)) {
                    // Troca o indexes 
                    ClubeBrasileirao aux = clubes.get(k);
                    clubes.set(k, clubes.get(kk));
                    clubes.set(kk, aux);
                }
            }
        }
        return clubes;
    }

    /***********************************************************************************************
     * Compara os caracteres do nome1 e nome2
        * Se o caractere N do nome1 for menor que o caracter N do nome2 retorna true
        * Se o contrario, retorna false
        * Se os caracteres forem iguais, verifica o proximo caractere
    ***********************************************************************************************/
    private boolean ordemAlfabetica(String nome1, String nome2, int index) {
        while(true) {
            if(nome1.length() == index) {
                return false;
            } else if(nome2.length() == index) {
                return true;
            }

            if(nome1.charAt(index) > nome2.charAt(index)) { // Verifica o maior caractere dos dois nomes
                return true;
            } else if(nome1.charAt(index) < nome2.charAt(index)) {
                return false;
            } else {
                ordemAlfabetica(nome1,nome2,index+=1);
            }
        }
    }

    /***********************************************************************************************
     * A função pega os dados do usuário e instancia um clube
        * Para dado que entrar e não der erro, é adicionado +1 na variavel valido
        * Se valido é igual 5, retorna o clube instanciado com os dados do usuário
        * Se valido não é igual a 5, retorna null
    ***********************************************************************************************/
    private Clube entradaDadosClube() {
        Principal p = new Principal();
        String erro = "Motivos: \n";  
        int valido = 0;

        System.out.print("Nome do clube: ");
        String nomeClube = p.entrada();
        valido++;
        
        System.out.print("Estadio do clube: ");
        String estadio = p.entrada();
        valido++;

        long[] atributos = new long[3];
        String[] nomesAtributos = {"Ano de fundação", "Quantidade de torcedores", "Nota do clube"};
        for(int k = 0; k < atributos.length; k++) {
            try {
                System.out.print(nomesAtributos[k] +": ");
                atributos[k] = Long.parseLong(p.entrada());
                if(k == 2) {
                    if(atributos[k] == -1) {
                        atributos[k] = 1;
                    }
                    else if(atributos[k] < 1|| atributos[k] > 100) {
                        erro += "A nota do clube deve ser entre 1 e 100\n";
                        valido--;
                    }
                }
                valido++;
            } catch (Exception e) {
                erro += nomesAtributos[k]+ " deve ser um numero inteiro\n";
                valido--;
            }
        }

        if(valido != 5) {
            System.out.println("Não foi possivel cadastrar o clube "+nomeClube+", " + erro);
            return null;
        } else {
            return new Clube(nomeClube,(int)atributos[0], estadio, (int)atributos[1], (int)atributos[2]);
        }

    }


    /***********************************************************************************************
     * Recebe um clube como parametro e o escreve na lista de clubes do csv
    ***********************************************************************************************/
    public void cadastraClubes(Clube clube) throws IOException {
        this.escalaClubes();

        Clube novoClube = null;
        if(clube == null) {
            novoClube = this.entradaDadosClube();
        } else {
            novoClube = clube;
        }

        if(novoClube == null) {
            return;
        }

        if(clubeCadastrado(novoClube.getNome())) {
            System.out.printf("O clube %s já está cadastrado", novoClube.getNome());
            return;
        }
  
        File file = new File("projetocampeonato/clubes.csv");
        Scanner scan = new Scanner(file);
        ArrayList<String> alldata = new ArrayList<String>(); // Array que armazenará todas as linhas do csv
        while(scan.hasNextLine()) {
            alldata.add(scan.nextLine());
        }

        FileWriter fw = new FileWriter(file);
        for(String line : alldata) {

            fw.write(line+"\n");
        }
        String line = String.format("%s,%d,%s,%d,%d",novoClube.getNome(), novoClube.getFundacao(), novoClube.getLocal(),novoClube.getTorcida(), novoClube.getScore());
        fw.write(line);
        System.out.println("O clube "+novoClube.getNome()+" foi cadastrado com sucesso");


        fw.flush();
        fw.close();
        scan.close();
        this.escalaClubes();
    }

    /***********************************************************************************************
     * A função recebe um nome de um clube que deseja modificar
        * Se uma das entradas de atributo forem "-1", o atributo continuará o mesmo
        * Pega todos os atributos cria um clube, remove o antigo e cadastra o novo
    ***********************************************************************************************/
    public void modificaClube(String nomeClube) throws IOException {
        System.out.println("Atributos atuais do clube:");
        this.listarClube(nomeClube);
        System.out.println("Atributos que não queira modificar, coloque -1");

        Clube novoClube = this.entradaDadosClube();
        if(novoClube == null) {
            return;
        }

        Clube atualClube = null;
        // Pega o objeto que tem o nome igual a variavel nomeClube
        for(ClubeBrasileirao clube : this.getBra().getClubes()) {
            if(clube.getNome().toUpperCase().equals(nomeClube.toUpperCase())) {
                atualClube = clube;
            }
        }

        if(novoClube.getNome().equals("-1")) {
            novoClube.setNome(atualClube.getNome());
        }

        if(novoClube.getFundacao() == -1) {
            novoClube.setFundacao(atualClube.getFundacao());
        }
        
        if(novoClube.getLocal().equals("-1")) {
            novoClube.setLocal(atualClube.getLocal());
        }

        if(novoClube.getTorcida() == -1) {
            novoClube.setTorcida(atualClube.getTorcida());
        }

        if(novoClube.getScore() == -1) {
            novoClube.setScore(atualClube.getScore());
        }
        

        this.removerClube(nomeClube);
        this.cadastraClubes(novoClube);
        System.out.println("Clube apos mudanças:");
        this.listarClube(novoClube.getNome());
    }

    /***********************************************************************************************
     * Recebe um nome de um clube e o remove da tabela csv de clubes
    ***********************************************************************************************/
    public void removerClube(String nomeClube) throws IOException {
        if(bra.getRodada() > 0) {
            System.out.println("Clubes só podem ser removidos antes da competição começar");
            return;
        }

        File file = new File("projetocampeonato/clubes.csv");
        Scanner scan = new Scanner(file);

        ArrayList<String> allData = new ArrayList<String>(); // Array que armazenará todas as linhas do arquivo clubes.csv
        while(scan.hasNextLine()) {
            allData.add(scan.nextLine());
        }
        
        //Escreve linha por linha de cada clube, mas deixa o clube que tem o nome igual a variavel nomeClube de fora
        FileWriter fw = new FileWriter(file);
        for(int k = 0; k < allData.size(); k++) {
            String nome = allData.get(k).split(",")[0];
            if(!nome.toUpperCase().equals(nomeClube.toUpperCase())) {
                fw.write(allData.get(k));
                if(k < allData.size() - 1) {
                    fw.write("\n");
                }
            }
        }
        fw.flush();
        fw.close();
        scan.close();
        this.escalaClubes();
    }

    /***********************************************************************************************
     * Recebe um nome de clube e printa 5 atributos do clube
    ***********************************************************************************************/
    private void listarClube(String nomeVerifica) {
        if(!this.clubeCadastrado(nomeVerifica)) {
            System.out.println("Clube não cadastrado");
            return;
        }
        for(ClubeBrasileirao clube : this.getBra().getClubes()) {
            if(nomeVerifica.toUpperCase().equals(clube.getNome().toUpperCase())) {

                String nome = "Nome do clube: " + clube.getNome();
                String fundacao = "Ano de fundacação: " + clube.getFundacao();
                String estadio = "Estádio: " + clube.getLocal();
                String torcida ="Quantidade de torcedores: "+ String.format("%,d",clube.getTorcida());
                String score = "Classificação: "+clube.getScore();

                System.out.println("+"+"-".repeat(52)+"+");
                System.out.printf("| %s "+" ".repeat(50-nome.length())+"|\n"+
                                  "| %s "+" ".repeat(50-fundacao.length())+"|\n"+
                                  "| %s "+" ".repeat(50-estadio.length())+"|\n"+
                                  "| %s "+" ".repeat(50-torcida.length())+"|\n"+
                                  "| %s "+" ".repeat(50-score.length())+"|\n",nome,fundacao,estadio,torcida,score);
                System.out.println("+"+"-".repeat(52)+"+");
            }
        }
    }

    /***********************************************************************************************
     * Recebe uma String e a string estiver vazia printa 3 atributos de todos os clubes cadastrados
        * Se não for uma string vazia para para listarClube
    ***********************************************************************************************/
    public void listarClubes(String nomeVerifica) {
        if(!nomeVerifica.isBlank()) {
            listarClube(nomeVerifica);
            return;
        }

        int counter = 1;
        for(ClubeBrasileirao clube : this.getBra().getClubes()) {
            String nClube = "Clube " + counter++ +":";
            String nome = "Nome do clube: " + clube.getNome();
            String fundacao = "Ano de fundacação: " + clube.getFundacao();
            String estadio = "Estádio: " + clube.getLocal();

            System.out.println("+"+"-".repeat(52)+"+");
            System.out.printf("| %s "+" ".repeat(50-nClube.length())+"|\n"+
                              "| %s "+" ".repeat(50-nome.length())+"|\n"+
                              "| %s "+" ".repeat(50-fundacao.length())+"|\n"+
                              "| %s "+" ".repeat(50-estadio.length())+"|\n", nClube,nome,fundacao,estadio);
        }
        System.out.println("+"+"-".repeat(52)+"+");
        
    }

    /***********************************************************************************************
     * Verifica se o clube já esta cadastrado
        * Se sim, returna true
        * Se não, retorna false
    ***********************************************************************************************/
    public boolean clubeCadastrado(String nomeClube) {
        this.escalaClubes();
        boolean exist = false;
        for(Clube clube : this.getBra().getClubes()) {
            if(nomeClube.toUpperCase().equals(clube.getNome().toUpperCase())) {
                exist = true;
            }
        }
        return exist;
    }

    public Brasileirao getBra() {
        return bra;
    }

    public void setBra(Brasileirao bra) {
        this.bra = bra;
    }

    public CopaBrasil getCopa() {
        return copa;
    }

    public void setCopa(CopaBrasil copa) {
        this.copa = copa;
    }

    public int getqClubes() {
        return qClubes;
    }

    public void setqClubes(int qClubes) {
        this.qClubes = qClubes;
    }

}