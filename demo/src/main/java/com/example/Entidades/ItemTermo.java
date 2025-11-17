package com.example.Entidades;

import java.util.List;

public class ItemTermo {
    private List<Elemento> elementos;

    public ItemTermo(List<Elemento> elementos) {
        this.elementos = elementos;
    }

    public List<Elemento> getElementos() {
        return elementos;
    }

    public void setElementos(List<Elemento> elementos) {
        this.elementos = elementos;
    }

    
}
