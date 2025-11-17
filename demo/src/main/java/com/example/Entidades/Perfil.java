package com.example.Entidades;

import java.util.List;

public class Perfil {
    private List<Interesses> interesses;
    private List<Habilidades> habilidades;

    public Perfil(){
        
    }
    public Perfil(List<Interesses> interesses, List<Habilidades> habilidades) {
        this.interesses = interesses;
        this.habilidades = habilidades;
    }

    public List<Interesses> getInteresses() {
        return interesses;
    }
    public void setInteresses(List<Interesses> interesses) {
        this.interesses = interesses;
    }
    public List<Habilidades> getHabilidades() {
        return habilidades;
    }
    public void setHabilidades(List<Habilidades> habilidades) {
        this.habilidades = habilidades;
    }
}
