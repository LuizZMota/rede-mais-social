package com.example.Entidades;
import java.util.Date;
public class Certidoes {
    private String tipoCertidao; // Novo atributo para identificar qual certidão é (ex: "Negativa de Débito")
    private String verso;
    private Date dataEmissao;
    
    public Certidoes(String tipoCertidao, String verso, Date dataEmissao) {
        this.tipoCertidao = tipoCertidao;
        this.verso = verso;
        this.dataEmissao = dataEmissao;
    }

    public String getTipoCertidao() {
        return tipoCertidao;
    }
    public void setTipoCertidao(String tipoCertidao) {
        this.tipoCertidao = tipoCertidao;
    }
    public String getVerso() {
        return verso;
    }
    public void setVerso(String verso) {
        this.verso = verso;
    }
    public Date getDataEmissao() {
        return dataEmissao;
    }
    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    
}
