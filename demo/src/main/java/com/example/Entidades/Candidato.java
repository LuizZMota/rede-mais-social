package com.example.Entidades;

import java.util.List;

public class Candidato extends PessoaFisica{
    private String nome;
    private String status;
    private Perfil perfil;


    public Candidato() {
        super();
    }

    public Candidato(String email, List<Localizacao> localizacoes, String cpf, Identidade identidade,
            List<Formacao> formacoes, String nome, String status, Perfil perfil) {
        super(email, localizacoes, cpf, identidade, formacoes);
        this.nome = nome;
        this.status = status;
        this.perfil = perfil;
    }



    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Perfil getPerfis() {
        return perfil;
    }
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    
    
    


}
