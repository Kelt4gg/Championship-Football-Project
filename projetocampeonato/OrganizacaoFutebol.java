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
        this.getBra().setClubesAux(this.getBra().getClubes());
        this.setCopa(new CopaBrasil());
    }

    public void escalaClubes() { // Pega cada time no csv e adiciona no array clubes
        this.setqClubes(0);
        String path = "projetocampeonato/clubes.csv";
        File file = null;
        Scanner scan = null;
        try { // Faz uma verificação se o arquivo de clubes existe
            file = new File(path);
            scan = new Scanner(file);
            scan.nextLine(); //Pula a primeira linha de cabeçalho
            ArrayList<ClubeBrasileirao> clubes = new ArrayList<ClubeBrasileirao>();
            while(scan.hasNextLine()) { // Adiciona cada clube no csv dentro do array de clubes enquanto existir algum clube no csv
                String line = scan.nextLine(); // Pega a linha do csv
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

    public ArrayList<ClubeBrasileirao> organizaClubes(ArrayList<ClubeBrasileirao> clubes) {
        for(int k = 0; k < clubes.size(); k++) {
            for(int kk = k+1; kk< clubes.size(); kk++) {
                if(ordemAlfabetica(clubes.get(k).getNome().toUpperCase(), clubes.get(kk).getNome().toUpperCase(), 0)) {
                    ClubeBrasileirao aux = clubes.get(k);
                    clubes.set(k, clubes.get(kk));
                    clubes.set(kk, aux);
                }
            }
        }
        return clubes;
    }

    private boolean ordemAlfabetica(String nome1, String nome2, int index) {
        while(true) {
            if(nome1.length() == index) {
                return false;
            } else if(nome2.length() == index) {
                return true;
            }

            if(nome1.charAt(index) > nome2.charAt(index)) {
                return true;
            } else if(nome1.charAt(index) < nome2.charAt(index)) {
                return false;
            } else {
                ordemAlfabetica(nome1,nome2,index+=1);
            }
        }
    }

    private Clube entradaDadosClube() {
        Principal p = new Principal();
        String error = "Motivos: \n";  
        int valid = 0;

        System.out.print("Nome do clube: ");
        String nomeClube = p.entrada();
        valid++;
        
        System.out.print("Estadio do clube: ");
        String estadio = p.entrada();
        valid++;

        long[] attributes = new long[3];
        String[] attributesNames = {"Ano de fundação", "Quantidade de torcedores", "Nota do clube"};
        for(int k = 0; k < attributes.length; k++) {
            try {
                System.out.print(attributesNames[k] +": ");
                attributes[k] = Long.parseLong(p.entrada());
                if(k == 2) {
                    if(attributes[k] == -1) {
                        attributes[k] = 1;
                    }
                    else if(attributes[k] < 1|| attributes[k] > 100) {
                        error += "A nota do clube deve ser entre 1 e 100\n";
                        valid--;
                    }
                }
                valid++;
            } catch (Exception e) {
                error += attributesNames[k]+ " deve ser um numero inteiro\n";
            }
        }

        if(valid != 5) {
            System.out.println("Não foi possivel cadastrar o clube "+nomeClube+" ," + error);
            return null;
        } else {
            return new Clube(nomeClube,(int)attributes[0], estadio, (int)attributes[1], (int)attributes[2]);
        }

    }

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
        ArrayList<String> alldata = new ArrayList<String>();
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

    public void modificaClube(String nomeClube) throws IOException {
        System.out.println("Atributos atuais do clube:");
        this.listarClube(nomeClube);
        System.out.println("Atributos que não queira modificar, coloque -1");

        Clube novoClube = this.entradaDadosClube();
        if(novoClube == null) {
            return;
        }

        Clube atualClube = null;
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

    public void removerClube(String nomeClube) throws IOException {
        if(bra.getRodada() > 0) {
            System.out.println("Clubes só podem ser removidos antes da competição começar");
            return;
        }

        File file = new File("projetocampeonato/clubes.csv");
        Scanner scan = new Scanner(file);

        ArrayList<String> allData = new ArrayList<String>();
        while(scan.hasNextLine()) {
            allData.add(scan.nextLine());
        }
        
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