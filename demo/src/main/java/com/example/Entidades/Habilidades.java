package com.example.Entidades;

public class Habilidades{
    public String habilidade;
    public String status;

    public Habilidades(String habilidade, String status) {
        this.habilidade = habilidade;
        this.status = status;
    }

    public String getHabilidade() {
        return habilidade;
    }
    public void setHabilidade(String habilidade) {
        this.habilidade = habilidade;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
