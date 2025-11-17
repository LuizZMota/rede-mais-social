package com.example.Entidades;

public class Validacao {
    public String envio;
    public String link;
    public String status;

    public Validacao(String envio, String link, String status) {
        this.envio = envio;
        this.link = link;
        this.status = status;
    }

    public String getEnvio() {
        return envio;
    }
    public void setEnvio(String envio) {
        this.envio = envio;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
