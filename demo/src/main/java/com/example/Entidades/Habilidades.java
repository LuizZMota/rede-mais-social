package com.example.Entidades;



public class Habilidades{
    public String descricao;
    public String status;

    public Habilidades() {
    }

    public Habilidades(String descricao, String status) {
        this.descricao = descricao;
        this.status = status;
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
