package com.example.Entidades;
import java.util.Date;
import java.util.List;
public class Consentimento {
    private Boolean status;
    private Date data;
    private Termo termo; 
    private List<ConsentimentoItem> consentimentoItens;
    
    public Consentimento(){
        
    }
    public Consentimento(Boolean status, Date data, Termo termo, List<ConsentimentoItem> consentimentoItens) {
        this.status = status;
        this.data = data;
        this.termo = termo;
        this.consentimentoItens = consentimentoItens;
    }

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public Termo getTermo() {
        return termo;
    }
    public void setTermo(Termo termo) {
        this.termo = termo;
    }
    public List<ConsentimentoItem> getConsentimentoItens() {
        return consentimentoItens;
    }
    public void setConsentimentoItens(List<ConsentimentoItem> consentimentoItens) {
        this.consentimentoItens = consentimentoItens;
    }

    
}
