package com.example.Entidades;

public class Formacao {
    public String tipo;
    public String profissao;
    public String curso;
    public String instituicao;

    public Formacao(){
        
    }
    public Formacao(String tipo, String profissao, String curso,  String instituicao) {
        this.tipo = tipo;
        this.profissao = profissao;
        this.curso = curso;
        this.instituicao = instituicao;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getProfissao() {
        return profissao;
    }
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
}
