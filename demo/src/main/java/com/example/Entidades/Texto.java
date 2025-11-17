package com.example.Entidades;

public class Texto extends Elemento{
    private String descricao;

    public Texto(Integer ordem, String descricao) {
        super(ordem);
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
}
