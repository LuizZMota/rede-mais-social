package com.example.Entidades;

import java.util.List;

public class Entidade {
    private String email;
    private List<Localizacao> localizacoes;

    public Entidade(){
        
    }
    public Entidade(String email, List<Localizacao> localizacoes) {
        this.email = email;
        this.localizacoes = localizacoes;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Localizacao> getLocalizacoes() {
        return localizacoes;
    }
    public void setLocalizacoes(List<Localizacao> localizacoes) {
        this.localizacoes = localizacoes;
    }  
}
