package com.example.Entidades;

public class Interesses{
    public String status;
    public String descricao;

    public Interesses(String status, String descricao) {
        super();
        this.status = status;
        this.descricao = descricao;
    }

    public Interesses() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
