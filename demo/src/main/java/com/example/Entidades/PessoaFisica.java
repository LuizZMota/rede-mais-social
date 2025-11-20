package com.example.Entidades;

import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class PessoaFisica extends Entidade {
    private String cpf;
    private Identidade identidade; 
    private List<Formacao> formacoes;
    private String profissao; // ✅ NOVO CAMPO

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

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    /**
     * Define a data de nascimento no objeto Identidade, convertendo de LocalDate para Date.
     * @param dataNascimento A data de nascimento como LocalDate.
     */
    public void setDataNascimento(LocalDate dataNascimento) {
        if (this.identidade != null && dataNascimento != null) {
            this.identidade.setDataNascimento(java.util.Date.from(dataNascimento.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
    }

    /**
     * Obtém a data de nascimento do objeto Identidade, convertendo de Date para LocalDate.
     * @return A data de nascimento como LocalDate, ou null se não existir.
     */
    public LocalDate getDataNascimento() {
        if (this.identidade != null && this.identidade.getDataNascimento() != null) {
            return this.identidade.getDataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }
}
