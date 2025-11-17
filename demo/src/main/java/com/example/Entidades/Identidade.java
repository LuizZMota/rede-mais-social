package com.example.Entidades;

public class Identidade {
    public String sexo;
    public int dataNasc;
    public String nacionalidade;

    public Identidade(String sexo, int dataNasc, String nacionalidade) {
        this.sexo = sexo;
        this.dataNasc = dataNasc;
        this.nacionalidade = nacionalidade;
    }

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public int getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(int dataNasc) {
        this.dataNasc = dataNasc;
    }
    public String getNacionalidade() {
        return nacionalidade;
    }
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    
}
