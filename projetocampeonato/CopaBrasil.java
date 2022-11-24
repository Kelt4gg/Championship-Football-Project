package projetocampeonato;

import java.util.ArrayList;
import java.util.Random;

public class CopaBrasil extends Campeonato{
    private ArrayList<ClubeCopaBrasil> chave; //Array que armazenará os times que jogaram a copa do brasil

    public CopaBrasil() {

        this.chave = new ArrayList<ClubeCopaBrasil>();
        super.setRodada(0);
    }

    // Cria a chave de equipes que vão se infrentar 
    public void geraChave() {
        super.setRodada(0);
        //Verifica se o array de colocados não esta vazio
        if(super.getColocados().isEmpty()) { 
            System.out.println("Deve ser termindado o Brasileirão!!");
            return;
        }

        ArrayList<Integer> numbers = sorteio();

        //Pega o array gerado pelo sorteio e usa como indice para escolher aleatóriamente um clube e adiciona na chave
        this.getChave().clear();
        for(Integer number : numbers){
            chave.add(super.getColocados().get(number));
        }

        System.out.println("+"+"-".repeat(120)+"+");
        System.out.println("Chave:");

        classificacao(); // Printa a os clubes que vão se infrentar

    }


    public ArrayList<Integer> sorteio() { // retorna um array de números inteiros diferentes organizados aleatóriamente

        ArrayList<Integer> numbers = new ArrayList<Integer>(); // Instancia o array que armazenará os números

        while(numbers.size() != super.getColocados().size()) { // Repete até que o tamanho do array de numeros seja igual a o tamanho do array de colocados

            Random gerador = new Random();
            int number = gerador.nextInt(super.getColocados().size()); //Gera o número de 0 a o tamanho do array de números colocados

            if(!numbers.contains(number)) { //Verifica se o número gerado já existe no array
                numbers.add(number);
            }
        }
        return numbers;
    }

    @Override
    public void classificacao() {
        if(this.chave.isEmpty()) {
            System.out.println("Chave ainda não organizada!!");
            return;
        }
        if(this.getRodada() == 0) {
            for(int k = 0; k < this.getChave().size() / 2; k++) {
                System.out.printf("[%s  x  %s]\n", this.getChave().get(k * 2).getNome() ,this.getChave().get(k * 2 + 1).getNome());
            }
            System.out.println("+"+"-".repeat(120)+"+");;
            return;
        }

        if(this.getChave().size() == 1) {
            System.out.printf("A copa do brasil chegou ao fim, o %s é o vencedor da Copa do Brasil!!!\n", this.getChave().get(0).getNome());
            return;
        }

        int counter = 1;
        int espaçosInicial = 5 * (super.getRodada() * 3);
        int espaçosEntreTimesFora = 2 * (getRodada() * getRodada());
        System.out.printf("Tabela da %d° rodada:\n", super.getRodada());
        for(Clube time : this.chave) {
            if(counter == 1) {
                System.out.print(" ".repeat(espaçosInicial));
            }
            if(counter % 2 == 1) {
                System.out.printf("[%s",time.getNome());
                if(counter < this.chave.size()) {
                    System.out.printf("  x  ");
                }
            } else {
                System.out.printf("%s]",time.getNome());
                System.out.printf(" ".repeat(espaçosEntreTimesFora));
            }
            counter++;
        }
        System.out.println();
        System.out.println("+"+"-".repeat(120)+"+");
    }
    
    public void match(ClubeCopaBrasil time1, ClubeCopaBrasil time2) {
        int probabilidade = 49;
        probabilidade += time1.getScore() - time2.getScore();
        Random randomizer = new Random();
        int number = randomizer.nextInt(100);
        if(number <= probabilidade) {
            this.getChave().remove(time1);
            System.out.printf("O %s ganhou do %s\n", time2.getNome(), time1.getNome());
        } else {
            this.getChave().remove(time2);
            System.out.printf("O %s ganhou do %s\n", time1.getNome(), time2.getNome());
        }
    }

    @Override
    public void rodada(int qRodada) {
        if(this.chave.isEmpty()) {
            System.out.println("Chave ainda não organizada!!");
            return;
        }

        for(int k = 0; k < qRodada; k++) {
            super.setRodada(super.getRodada()+1);
            if(this.chave.size() <= 1) {
                System.out.printf("O %s é o vencedor da Copa do Brasil!!!\n", this.chave.get(0).getNome());
                return;
            }
            this.classificacao();
            System.out.printf("Na %d° rodada:\n", super.getRodada());
            for(int kk = this.chave.size()/2-1; kk >= 0; kk--) {
                match(this.chave.get(kk*2), this.chave.get(kk*2+1));
            }
            System.out.println("-".repeat(120));
        }
    }

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

    public ArrayList<ClubeCopaBrasil> getChave() {
        return chave;
    }

    public void setChave(ArrayList<ClubeCopaBrasil> chave) {
        this.chave = chave;
    }
    

}
