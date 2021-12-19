package entities;

public class VendaComissionada {

    int sequencial = 0;

    private int id;
    private final int mes;
    private final int ano;
    private final double valor;
    private ContratoComissionado contratoComissionado;

    public VendaComissionada(int mes, int ano, double valor, ContratoComissionado contratoComissionado) {
        this.id = ++sequencial;
        this.mes = mes;
        this.ano = ano;
        this.valor = valor;
        this.contratoComissionado = contratoComissionado;
    }

    public int getId() {
        return id;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public double getValor() {
        return valor;
    }

    public ContratoComissionado getContratoComissionado() {
        return contratoComissionado;
    }
}