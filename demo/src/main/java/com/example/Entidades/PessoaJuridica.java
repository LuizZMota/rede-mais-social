package com.example.Entidades;

import java.util.List;
import java.util.ArrayList;

public class PessoaJuridica extends Entidade {
    private String cnpj;
    private Empresa empresa; 
    private List<Certidoes> certidoes;

    public PessoaJuridica() {
        super();
        this.certidoes = new ArrayList<>();
    }

    public PessoaJuridica(String nome, String email, String senha, String telefone, String cnpj, Empresa empresa) {
        super(nome, email, senha, telefone);
        this.cnpj = cnpj;
        this.empresa = empresa;
        this.certidoes = new ArrayList<>();
    }

    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    public List<Certidoes> getCertidoes() {
        return certidoes;
    }
    public void setCertidoes(List<Certidoes> certidoes) {
        this.certidoes = certidoes;
    }
}
