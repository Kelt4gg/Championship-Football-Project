package projetoCampeonato;
public abstract class Campeonato {
    OrganizacaoFutebol cbf;
    int qTimes;

    public abstract Clube match(Clube time1, Clube time2);

    public abstract void rodada(int qRodada);

    public abstract void torneio();

    public OrganizacaoFutebol getCbf() {
        return cbf;
    }

    public void setCbf(OrganizacaoFutebol cbf) {
        this.cbf = cbf;
    }

    public int getqTimes() {
        return qTimes;
    }

    public void setqTimes(int qTimes) {
        this.qTimes = qTimes;
    }

    

    
}
