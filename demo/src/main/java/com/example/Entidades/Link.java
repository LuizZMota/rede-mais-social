package com.example.Entidades;

public class Link extends Elemento{
    private String titulo;
    private String url;
    public Link(Integer ordem, String titulo, String url) {
        super(ordem);
        this.titulo = titulo;
        this.url = url;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    
}
