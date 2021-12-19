package entities;

import java.text.ParseException;
import java.util.Date;

public class ContratoComissionado extends Contrato {

    private double percComissao;
    private double ajudaCusto;

    public ContratoComissionado(Date dataInicio, Colaborador colaborador, double percComissao, double ajudaCusto) throws ParseException {
        super(dataInicio, colaborador);
        this.percComissao = percComissao;
        this.ajudaCusto = ajudaCusto;
    }

    public double getPercComissao() {
        return percComissao;
    }

    public double getAjudaCusto() {
        return ajudaCusto;
    }

    public double calcVencimento (double vlFaturamento){
        double vencimento = (vlFaturamento*percComissao*.01)+ajudaCusto;
        double seguro = ajudaCusto*.005 + (vencimento-ajudaCusto)*.01;
        if (seguro<25.00) seguro = 25.00;
        vencimento -= seguro;
        return vencimento;
    }
}