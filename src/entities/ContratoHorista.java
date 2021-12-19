package entities;

import java.text.ParseException;
import java.util.Date;

public class ContratoHorista extends Contrato {

    private int horasMes;
    private double valorHora;

    public ContratoHorista(Date dataInicio, Colaborador colaborador, int horasMes, double valorHora) throws ParseException {
        super(dataInicio, colaborador);
        this.horasMes = horasMes;
        this.valorHora = valorHora;
    }

    public int getHorasMes() {
        return horasMes;
    }

    public double getValorHora() {
        return valorHora;
    }

    public double calcVencimento(){
        double vencimento = horasMes*valorHora;
        double seguro;
        if (vencimento<5000.00) seguro = vencimento*.02;
        else seguro = vencimento*.025;
        vencimento -= seguro;
        return vencimento;
    }
}