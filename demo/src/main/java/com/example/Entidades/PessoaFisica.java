package com.example.Entidades;

import java.util.List;
import java.util.ArrayList;

public class PessoaFisica extends Entidade {
    private String cpf;
    private Identidade identidade; 
    private List<Formacao> formacoes;

    public PessoaFisica(){
        super();
        this.formacoes = new ArrayList<>();
    }
    
    public PessoaFisica(String nome, String email, String senha, String telefone, String cpf, Identidade identidade) {
        super(nome, email, senha, telefone);
        this.cpf = cpf;
        this.identidade = identidade;
        this.formacoes = new ArrayList<>();
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public Identidade getIdentidade() {
        return identidade;
    }
    public void setIdentidade(Identidade identidade) {
        this.identidade = identidade;
    }
    public List<Formacao> getFormacao() {
        return formacoes;
    }
    public void setFormacao(List<Formacao> formacoes) {
        this.formacoes = formacoes;
    }
}
