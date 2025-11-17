package com.example.Entidades;
import java.util.Date;

public class Afiliacao {
    private Date data;
    private String motivoRejeicao;
    private String status;
    private Candidato candidato; // Relacionamento 1..1
    private Consentimento consentimento; // Relacionamento 1..1
    private Validacao validacao;

    
    public Afiliacao(Date data, String motivoRejeicao, String status, Candidato candidato, Consentimento consentimento,
            Validacao validacao) {
        this.data = data;
        this.motivoRejeicao = motivoRejeicao;
        this.status = status;
        this.candidato = candidato;
        this.consentimento = consentimento;
        this.validacao = validacao;
    }
    
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }
    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Candidato getCandidato() {
        return candidato;
    }
    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
    public Consentimento getConsentimento() {
        return consentimento;
    }
    public void setConsentimento(Consentimento consentimento) {
        this.consentimento = consentimento;
    }
    public Validacao getValidacao() {
        return validacao;
    }
    public void setValidacao(Validacao validacao) {
        this.validacao = validacao;
    }

    
}
