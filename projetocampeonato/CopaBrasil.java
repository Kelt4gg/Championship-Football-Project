package projetocampeonato;

import java.util.ArrayList;
import java.util.Random;

public class CopaBrasil extends Campeonato{
    private ArrayList<Clube> chave;

    public CopaBrasil() {
        super.cbf = new OrganizacaoFutebol();
        super.qTimes = cbf.getqTimes() / 2;
        this.chave = new ArrayList<Clube>();
        
    }

    public ArrayList<Integer> sorteio() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random randomizer = new Random();
        while(numbers.size() != super.getqTimes()) {
            int number = randomizer.nextInt(super.getqTimes());
            if(!numbers.contains(number)) {
                numbers.add(number);
            }
        }
        return numbers;
    }

    public void organiza() {
        super.cbf.organiza();
        ArrayList<Integer> numbers = sorteio();
        for(int k = 0; k < numbers.size(); k++) {
            this.chave.add(cbf.getClubes().get(numbers.get(k)));
        }
    }

    public void EscalacaoTimes() {
        if(this.chave.isEmpty()) {
            System.out.println("Tabela ainda não organizada!!z");
            return;
        }
        
        int counter = 1;
        System.out.println("---------------------------------");
        for(Clube time : this.chave) {
            System.out.printf("| Time[%d] %22s|\n",counter,time.getNome());
            counter++;
        }
        System.out.println("---------------------------------");
        System.out.println();
    }
    
    @Override
    public Clube match(Clube time1, Clube time2) {
        int probabilidade = 49;
        probabilidade += time1.getScore() - time2.getScore();
        Random randomizer = new Random();
        int number = randomizer.nextInt(100);
        if(number <= probabilidade) {
            return time2;
        } else {
            return time1;
        }
    }

    @Override
    public void rodada(int qRodada) {
        if(this.chave.isEmpty()) {
            System.out.println("Tabela ainda não organizada!!");
            return;
        }
        System.out.println(this.chave.size());

        for(int k = 0; k < qRodada; k++) {
            if(this.chave.size() <= 1) {
                System.out.printf("O %s é o vencedor da Copa do Brasil!!!\n", this.chave.get(0).getNome());
                return;
            }
            this.EscalacaoTimes();
            for(int kk = this.chave.size()/2-1; kk >= 0; kk--) {
                this.chave.remove(this.match(this.chave.get(kk*2), this.chave.get(kk*2+1)));
            }
        }
    }

    @Override
    public void torneio() {
        this.rodada(4);
        // TODO Auto-generated method stub
        return;
    }

}
