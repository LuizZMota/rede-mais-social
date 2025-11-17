package com.example.Entidades;
import java.util.Date;

public class Identidade {
    private String tipo;
    private String numero;
    private Date dataEmissao;
    private String nacionalidade;

    
    public Identidade(String tipo, String numero, Date dataEmissao, String nacionalidade) {
        this.tipo = tipo;
        this.numero = numero;
        this.dataEmissao = dataEmissao;
        this.nacionalidade = nacionalidade;
    }
    
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Date getDataEmissao() {
        return dataEmissao;
    }
    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
    public String getNacionalidade() {
        return nacionalidade;
    }
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    
}
