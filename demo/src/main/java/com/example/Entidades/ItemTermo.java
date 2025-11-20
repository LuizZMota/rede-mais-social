package com.example.Entidades;

import java.util.List;

public class ItemTermo {
    int id;

    String descricao;
    public ItemTermo() {
    }

    public ItemTermo(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
