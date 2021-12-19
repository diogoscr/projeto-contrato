package entities;

import java.util.Date;

public class Colaborador {

    private final int matricula;
    private final String CPF;
    private String nome;
    private final Date dataNascimento;
    private boolean situacao;

    public Colaborador(int matricula, String CPF, String nome, Date dataNascimento) {
        this.matricula = matricula;
        this.CPF = CPF;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.situacao = false;

    }

    public int getMatricula() {
        return matricula;
    }

    public String getCpf() {
        return CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public static boolean validarCpf(String CPF){
        return ValidaCPF.isCPF(CPF);
    }

    public void ativar(){
        situacao = true;
    }

    public void desativar(){
        situacao = false;
    }
}