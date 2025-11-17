package com.example.Entidades;
import java.util.Date;

public class Termo {
    private Date dataCriacao;
    private ItemTermo itemTermo; 
    private TemplateTermo templateTermo;

    public Termo(Date dataCriacao, ItemTermo itemTermo, TemplateTermo templateTermo) {
        this.dataCriacao = dataCriacao;
        this.itemTermo = itemTermo;
        this.templateTermo = templateTermo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public ItemTermo getItemTermo() {
        return itemTermo;
    }
    public void setItemTermo(ItemTermo itemTermo) {
        this.itemTermo = itemTermo;
    }
    public TemplateTermo getTemplateTermo() {
        return templateTermo;
    }
    public void setTemplateTermo(TemplateTermo templateTermo) {
        this.templateTermo = templateTermo;
    }   
}
