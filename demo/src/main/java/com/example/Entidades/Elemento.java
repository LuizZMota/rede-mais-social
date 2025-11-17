package com.example.Entidades;

public abstract class Elemento {
    private Integer ordem;

    public Elemento(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    
}
