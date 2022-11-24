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
                if(maior(clubes.get(k).getNome().toUpperCase(), clubes.get(kk).getNome().toUpperCase(), 0)) {
                    ClubeBrasileirao aux = clubes.get(k);
                    clubes.set(k, clubes.get(kk));
                    clubes.set(kk, aux);
                }
            }
        }
        return clubes;
    }

    private boolean maior(String nome1, String nome2, int index) {
        while(true) {
            if(nome1.charAt(index) > nome2.charAt(index)) {
                return true;
            } else if(nome1.charAt(index) < nome2.charAt(index)) {
                return false;
            } else {
                maior(nome1,nome2,index+=1);
            }
        }
    }

    public void cadastraClube(String nomeClube, int fundacao, String estadio, long torcida, int nota) throws IOException { //Cadastra um novo clube
        File file = new File("projetocampeonato/clubes.csv");
        Scanner scan = new Scanner(file);
        ArrayList<String> alldata = new ArrayList<String>();
        while(scan.hasNextLine()) {
            alldata.add(scan.nextLine());
        }

        String novoClube = nomeClube+","+fundacao+","+estadio+","+torcida+","+nota;
        
        boolean exist = false;
        FileWriter fw = new FileWriter(file);
        for(String line : alldata) {
            if(line.split(",")[0] == nomeClube) {
                exist = true;
            }
            fw.write(line+"\n");
        }
        if(!exist) {
            fw.write(novoClube);
        } else {
            System.out.printf("O clube %s já está cadastrado", nomeClube);
        }
        fw.flush();
        fw.close();
        scan.close();
        this.escalaClubes();
        
    }

    public void removerClube(String nomeClube) throws IOException {
        boolean found = false;
        if(bra.getRodada() > 0) {
            System.out.println("Clubes só podem ser cadastrados antes da competição começar");
            return;
        }
        File file = new File("projetocampeonato/clubes.csv");
        Scanner scan = new Scanner(file);
        ArrayList<String> alldata = new ArrayList<String>();
        while(scan.hasNextLine()) {
            alldata.add(scan.nextLine());
        }
        
        FileWriter fw = new FileWriter(file);
        for(int k = 0; k < alldata.size(); k++) {
            String nome = alldata.get(k).split(",")[0];
            if(!nome.toUpperCase().equals(nomeClube.toUpperCase())) {
                fw.write(alldata.get(k));
                if(k < alldata.size() - 1) {
                    fw.write("\n");
                }
            } else {
                found = true;
            }

        }
        fw.flush();
        fw.close();
        scan.close();
        this.escalaClubes();
        if(found) {
            System.out.printf("O clube %s foi removido\n", nomeClube);
        } else {
            System.out.printf("O clube %s não foi achado\n", nomeClube);
        }
    }

    public void listarClubes() {
        this.escalaClubes();
        int counter = 1;
        for(ClubeBrasileirao clube : this.getBra().getClubes()) {
            String nClube = "Clube " + counter++ +":";
            String nome = "Nome do clube: " + clube.getNome();
            String fundacao = "Ano de fundacação: " + clube.getFundacao();
            String estadio = "Estádio: " + clube.getLocal();
            String torcida ="Quantidade de torcedores: "+ String.format("%,d",clube.getTorcida());
            String score = "Classificação: "+clube.getScore();
            System.out.println("+"+"-".repeat(52)+"+");
            System.out.printf("| %s "+" ".repeat(50-nClube.length())+"|\n"+
                              "| %s "+" ".repeat(50-nome.length())+"|\n"+
                              "| %s "+" ".repeat(50-fundacao.length())+"|\n"+
                              "| %s "+" ".repeat(50-estadio.length())+"|\n"+
                              "| %s "+" ".repeat(50-torcida.length())+"|\n"+
                              "| %s "+" ".repeat(50-score.length())+"|\n", nClube,nome,fundacao,estadio,torcida,score);
        }
        System.out.println("+"+"-".repeat(52)+"+");
        
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


