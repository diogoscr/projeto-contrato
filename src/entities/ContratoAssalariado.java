package entities;

import java.text.ParseException;
import java.util.Date;

public class ContratoAssalariado extends Contrato {
    private double salarioMensal;
    private double percInsalubridade;
    private double percPericulosidade;

    public ContratoAssalariado(Date dataInicio, Colaborador colaborador, double salarioMensal, double percInsalubridade, double percPericulosidade) throws ParseException {
        super(dataInicio, colaborador);
        this.salarioMensal = salarioMensal;
        this.percInsalubridade = percInsalubridade;
        this.percPericulosidade = percPericulosidade;
    }

    public double getSalarioMensal() {
        return salarioMensal;
    }

    public double getpercInsalubridade() {
        return percInsalubridade;
    }

    public double getpercPericulosidade() {
        return percPericulosidade;
    }

    public double calcVencimento() {
        double vencimento = salarioMensal + (salarioMensal*percInsalubridade*.01) +(salarioMensal*percPericulosidade*.01);
        double seguro = vencimento*.02;
        if (seguro < 25.00) {
            seguro = 25.00;
        } else if (seguro > 150.00) seguro = 150.00;
        vencimento -= seguro;
        return vencimento;
    }
}