package projetocampeonato;

import java.util.ArrayList;
import java.util.Random;

public class CopaBrasil extends Campeonato{
    private ArrayList<Clube> colocados; // Array que armazena clubes que passaram do brasileirão para a copa do brasil
    private ArrayList<Clube> chave; //Array que armazenará os times que vão se confrontar na copa do brasil

    public CopaBrasil() {
        this.setColocados(new ArrayList<Clube>());
        this.chave = new ArrayList<Clube>();
        super.setRodada(0);
    }

    /***********************************************************************************************
     * Função que gera uma nova chave de confrontos aleatoria
        * Reinicia a copa do Brasil
    ***********************************************************************************************/
    public void geraChave() {
        super.setRodada(0);
        //Verifica se o array de colocados está vazio, se estiver vazio é porque o brasileirão não acabou ainda
        if(this.getColocados().isEmpty()) { 
            System.out.println("Deve ser termindado o Brasileirão Anima!!");
            return;
        }

        this.getChave().clear();
        
        //Pega o array gerado pelo sorteio e usa como indice para escolher aleatóriamente um clube e adiciona na chave
        for(Integer numerosAleatorios : sorteio()){
            chave.add(this.getColocados().get(numerosAleatorios));
        }

        System.out.println("+"+"-".repeat(120)+"+"); // Printa uma divisoria 

    }


    /***********************************************************************************************
     * Função cria um array de 8 números diferentes gerados aleatoriamente, e retorna esse array
    ***********************************************************************************************/
    private ArrayList<Integer> sorteio() {

        ArrayList<Integer> numeros = new ArrayList<Integer>(); // Instancia o array que armazenará os números

        while(numeros.size() != this.getColocados().size()) { // Repete até que o tamanho do array de numeros seja igual a o tamanho do array de colocados

            Random gerador = new Random();
            int number = gerador.nextInt(this.getColocados().size()); //Gera o número de 0 ao N (tamanho do array de colcocados)

            if(!numeros.contains(number)) { //Verifica se o número gerado já existe no array
                numeros.add(number);
            }
        }
        return numeros;
    }

    /***********************************************************************************************
     * Printa uma tabela com os confrontos de cada equipe na rodada
     *  Se for a rodada 0, printa uma tabela mais compacta
    ***********************************************************************************************/
    @Override
    public void tabela() {
        if(this.chave.isEmpty()) { // Verifica se a tebela esta organizada
            System.out.println("Chave ainda não organizada!!");
            return;
        }

        if(this.getRodada() == 0) { // Se estiver na rodada 0, printa uma tabela compacta
            System.out.println("Chave: ");
            for(int k = 0; k < this.getChave().size() / 2; k++) {
                System.out.printf("[%s  x  %s]\n", this.getChave().get(k * 2).getNome() ,this.getChave().get(k * 2 + 1).getNome());
            }
            System.out.println("+"+"-".repeat(120)+"+");
            return;
        }

        if(this.getChave().size() == 1) { //Retorna se só tiver uma equipe na chave, pois a copa do brasil chegou ao fim
            System.out.printf("A copa do brasil chegou ao fim, o %s é o vencedor da Copa Facs!!!\n", this.getChave().get(0).getNome());
            return;
        }

        int counter = 1;
        int espaçosInicial = 5 * (super.getRodada() * 3); //Variavel que armazena a quantidade de espaços serão printados antes do nome dos clubes
        int espaçosEntreTimes = 2 * (getRodada() * getRodada()); //Variavel que armazena a quantidade de espaços serão printados entre o nome de dois clubes
        System.out.printf("Tabela da %d° rodada:\n", super.getRodada());
        for(Clube time : this.chave) {
            if(counter == 1) {
                System.out.print(" ".repeat(espaçosInicial));
            }

            // Se o contador for impar printa "[NomeClube  x  "
            if(counter % 2 == 1) {
                System.out.printf("[%s",time.getNome());
                if(counter < this.chave.size()) {
                    System.out.printf("  x  ");
                }
            // Se o contador for par printa "NomeClube]   "
            } else {
                System.out.printf("%s]",time.getNome());
                System.out.printf(" ".repeat(espaçosEntreTimes));
            }
            counter++;
        }
        System.out.println();
        System.out.println("+"+"-".repeat(120)+"+"); // printa divisoria
    }
    
    /***********************************************************************************************
     * Simula uma partida entre dois clubes
        * Cada time tem por padrão a probabilidade de 50% de chance de ganhar
        * O clube que perder é removido do array da Chave
    ***********************************************************************************************/
    private void partida(Clube clube1, Clube clube2) {
        int probabilidade = 49; //Variavel que armazena a probalidade de cada clube ganhar
        probabilidade += (clube1.getScore() - clube2.getScore()) / 2; // Adiciona a subtração dos score dos dois clubes

        Random randomificador = new Random();
        int numeroAleatorio = randomificador.nextInt(100);
        if(numeroAleatorio <= probabilidade) {// Se o valor de numero aleoatorio for menor ou igual ao valor da probabilidade, o clube2 é removido da chave
            this.getChave().remove(clube2);
            System.out.printf("O %s ganhou do %s\n", clube1.getNome(), clube2.getNome());
        } else {//Se o contrario, o clube1 é removido da chave
            this.getChave().remove(clube1);
            System.out.printf("O %s ganhou do %s\n", clube2.getNome(), clube1.getNome());
        }
    }

    /***********************************************************************************************
     * Simula uma quantidade N de rodadas
        * Cada rodada tendo uma quantidade N de partidas
     * Parâmetros:
     * Recebe um número entre 1 e 4 que será a quantidade de rodadas desejadas a serem simuladas
    ***********************************************************************************************/
    @Override
    public void rodada(int qRodada) {
        if(this.chave.isEmpty()) {
            System.out.println("Chave ainda não organizada!!");
            return;
        }

        for(int k = 0; k < qRodada; k++) {
            super.setRodada(super.getRodada()+1);
            if(this.chave.size() <= 1) {
                System.out.printf("O %s é o vencedor da Copa Facs!!!\n", this.chave.get(0).getNome());
                return;
            }
            this.tabela();
            System.out.printf("Na %d° rodada:\n", super.getRodada());
            for(int kk = this.chave.size()/2-1; kk >= 0; kk--) {
                this.partida(this.chave.get(kk*2), this.chave.get(kk*2+1));
            }
            System.out.println("-".repeat(120)); //Printa uma divisoria
        }
    }

    /***********************************************************************************************
     * Simula o torneio inteiro do começo ao fim
    ***********************************************************************************************/
    @Override
    public void torneio() {
        if(this.chave.isEmpty()) {
            System.out.println("Chave ainda não organizada!!");
            return;
        }
        while(this.getRodada() != 4) {
            rodada(1);
        }
        return;
    }

    public ArrayList<Clube> getChave() {
        return chave;
    }

    public void setChave(ArrayList<Clube> chave) {
        this.chave = chave;
    }

    public ArrayList<Clube> getColocados() {
        return colocados;
    }

    public void setColocados(ArrayList<Clube> colocados) {
        this.colocados = colocados;
    }
    
    
}
