package com.example.Entidades;

public class ItemAceite extends Elemento{
    private Boolean exclusivo;
    private String descricao;
    private Integer valor;

    
    public ItemAceite(Integer ordem, Boolean exclusivo, String descricao, Integer valor) {
        super(ordem);
        this.exclusivo = exclusivo;
        this.descricao = descricao;
        this.valor = valor;
    }
    
    public Boolean getExclusivo() {
        return exclusivo;
    }
    public void setExclusivo(Boolean exclusivo) {
        this.exclusivo = exclusivo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Integer getValor() {
        return valor;
    }
    public void setValor(Integer valor) {
        this.valor = valor;
    }

    
    

    
}
