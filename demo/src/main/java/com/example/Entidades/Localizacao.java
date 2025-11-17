package com.example.Entidades;

public class Localizacao {
    private Telefone telefone;
    private Endereco endereco;

    public Localizacao(){
        
    }
    public Localizacao(Telefone telefone, Endereco endereco) {
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Telefone getTelefone() {
        return telefone;
    }
    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    
}
