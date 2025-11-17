package com.example.Entidades;

public class Afiliacao {
    private int dataSolicitacao;
    private String motivoRejeicao;
    private String situacao;

    public Afiliacao(int dataSolicitacao, String motivoRejeicao, String situacao) {
        this.dataSolicitacao = dataSolicitacao;
        this.motivoRejeicao = motivoRejeicao;
        this.situacao = situacao;
    }

    public int getDataSolicitacao() {
        return dataSolicitacao;
    }
    public void setDataSolicitacao(int dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }
    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }
    public String getSituacao() {
        return situacao;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    
}
