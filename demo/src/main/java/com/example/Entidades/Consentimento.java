package com.example.Entidades;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public class Consentimento {
    private String status;
    private LocalDateTime data;
    private Termo termo; 
    private List<ConsentimentoItem> consentimentoItens;

    
    public Consentimento(){
        
    }
    public Consentimento(String status, LocalDateTime data, Termo termo, List<ConsentimentoItem> consentimentoItens) {
        this.status = status;
        this.data = data;
        this.termo = termo;
        this.consentimentoItens = consentimentoItens;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
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
    public void adicionarConsentimentoItens(ConsentimentoItem consentimentoItem) {
        // Inicializa a lista se for nula
        if (this.consentimentoItens == null) {
            this.consentimentoItens = new java.util.ArrayList<>();
        }
        this.consentimentoItens.add(consentimentoItem);
    }
    
}
