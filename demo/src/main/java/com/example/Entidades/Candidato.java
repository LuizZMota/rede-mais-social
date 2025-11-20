package com.example.Entidades;

public class Candidato {
    private int id;
    private Entidade entidade;
    private String status;
    private Perfil perfil;

    public Candidato() {
    }

    public Candidato(Entidade entidade, String status, Perfil perfil) {
        this.entidade = entidade;
        this.status = status;
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}