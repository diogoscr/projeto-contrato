package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Contrato {

    static private int sequencial = 0;
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private final int id;
    private final Date dataInicio;
    private Date dataEncerramento;
    private final Colaborador colaborador;
    private boolean ativo;
    protected double ultimoVencimento;

    public Contrato(Date dataInicio, Colaborador colaborador) throws ParseException {
        this.id = ++sequencial;
        this.dataInicio = dataInicio;
        this.dataEncerramento = sdf.parse("00/00/00");
        this.colaborador = colaborador;
        this.ativo = true;
    }

    public int getId() {
        return id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public double getUltimoVencimento() {
        return ultimoVencimento;
    }

    public void setUltimoVencimento(double ultimoVencimento) {
        this.ultimoVencimento = ultimoVencimento;
    }

    public void encerrarContrato(Date data) {
        dataEncerramento = data;
        ativo = false;
   }
}