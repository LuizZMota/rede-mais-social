package com.example.Entidades;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Identidade {
    private String sexo;
    private LocalDate dataNascimento;
    private String nacionalidade;

    public Identidade() {
    }
    
    public Identidade(String sexo, LocalDate dataNascimento, String nacionalidade) {
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
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getNacionalidade() {
        return nacionalidade;
    }
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
}
