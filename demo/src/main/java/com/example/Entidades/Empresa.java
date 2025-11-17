package com.example.Entidades;

public class Empresa {
    public String registro;
    public String nomeLegal;
    public String nomeFantasia;
    public String pais;
  
    public Empresa(String registro, String nomeLegal, String nomeFantasia, String pais) {
        this.registro = registro;
        this.nomeLegal = nomeLegal;
        this.nomeFantasia = nomeFantasia;
        this.pais = pais;
    }

    public String getRegistro() {
        return registro;
    }
    public void setRegistro(String registro) {
        this.registro = registro;
    }
    public String getNomeLegal() {
        return nomeLegal;
    }
    public void setNomeLegal(String nomeLegal) {
        this.nomeLegal = nomeLegal;
    }
    public String getNomeFantasia() {
        return nomeFantasia;
    }
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    
}
