package com.example.Entidades;
import java.util.Date;

public class Identidade {
    private String sexo;
    private Date dataNascimento;
    private String nacionalidade;

    public Identidade() {
    }
    
    public Identidade(String sexo, Date dataNascimento, String nacionalidade) {
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
    }
    
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public Date getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getNacionalidade() {
        return nacionalidade;
    }
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
}
