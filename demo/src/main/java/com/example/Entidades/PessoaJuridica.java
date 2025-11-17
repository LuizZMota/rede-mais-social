package com.example.Entidades;

import java.util.List;

public class PessoaJuridica extends Entidade{
    private String cnpj;
    private Empresa empresa; 
    private List<Certidoes> certidoes;

    public PessoaJuridica(String email, List<Localizacao> localizacoes, String cnpj, Empresa empresa,
            List<Certidoes> certidaos) {
        super(email, localizacoes);
        this.cnpj = cnpj;
        this.empresa = empresa;
        this.certidoes = certidaos;
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
    public void setCertidoes(List<Certidoes> certidaos) {
        this.certidoes = certidaos;
    }

    
}
