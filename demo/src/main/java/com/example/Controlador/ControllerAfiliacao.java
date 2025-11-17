package com.example.Controlador;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import com.example.Entidades.*;

public class ControllerAfiliacao {

    // Simulação do repositório/Service
    private Candidato candidatoAtual;

    /**
     * Tenta encontrar um candidato. Se não encontrar, retorna falso.
     */
    public boolean buscarCandidatoExistente(String cpf, String email) {
        System.out.println("Controller: buscarCandidatoExistente(" + cpf + ", " + email + ")");
        // Lógica de Repositório/DAO: candidatoAtual = dao.find(cpf);
        
        // Simulação: Sempre retorna falso para forçar o fluxo de criação
        return false;
    }

    /**
     * Cria e inicializa a entidade Candidato.
     */
    public void iniciarNovoCandidato(String cpf, String email) {
        System.out.println("Controller: iniciarNovoCandidato(" + cpf + ", " + email + ")");
        candidatoAtual = new Candidato();
        candidatoAtual.setCpf(cpf);
        candidatoAtual.setEmail(email);
        candidatoAtual.setStatus("Pendente"); // Status inicial
    }

    /**
     * Atualiza dados pessoais (Identidade) e coleções (Formacao, Localizacao).
     * O método atualizaDados no diagrama é o ponto de entrada para todas estas criações.
     */
    public void registrarDadosCompletos(String nome, String sexo, String dataNascimento, String nacionalidade, boolean endResidencial, boolean endComercial, List<Formacao> formacoes) {
        System.out.println("Controller: registrarDadosCompletos - Formações recebidas: " + formacoes.size());

        candidatoAtual.setNome(nome);
        // Lógica para setar Identidade (sexo, dataNascimento, nacionalidade)
        // Lógica para processar Formacoes e adicioná-las ao candidatoAtual.getFormacoes()
        candidatoAtual.setFormacao(formacoes); // Simulação de set

        // <<create>> criaEndereco (Localizacao)
        if (endResidencial) {
            candidatoAtual.getLocalizacoes().add(new Localizacao()); 
        }
        // ... Lógica para endComercial
    }
    
    /**
     * Cria e associa o Perfil (Habilidades e Interesses) ao Candidato.
     */
    public void registrarPerfil(List<String> habilidades, List<String> interesses) {
        System.out.println("Controller: registrarPerfil - Criando Perfil e associando Habilidades/Interesses");
        
        List<Habilidades> habilidade = new ArrayList<>();
        List<Interesses> interesse = new ArrayList<>();
        
        // 1. Criação e Associação do Perfil
        Perfil perfil = new Perfil();
        perfil.setHabilidades(habilidade); // Associa lista vazia
        perfil.setInteresses(interesse);   // Associa lista vazia
        
        candidatoAtual.setPerfil(perfil);
    }

    /**
     * Processa o aceite do Termo e o registra como Consentimento.
     */
    public void registrarTermoAceite() {
        System.out.println("Controller: registrarTermoAceite - Registrando Consentimento legal.");
        
        // termoCompromisso() & registraAceite()
        Consentimento consentimento = new Consentimento();
        consentimento.setData(new Date());
        consentimento.setStatus(true); // Termo aceito
        
    }
    
    /**
     * Finaliza o processo, persiste o Candidato e envia a notificação.
     */
    public void finalizarAfilicao(String status) {
        System.out.println("Controller: finalizarAfilicao - Salvando e Notificando");

        candidatoAtual.setStatus(status);
        // Lógica de Persistência: dao.save(candidatoAtual);

        if ("Aguardando Validação".equals(status)) {
            // enviaMensagem(email, "Validacao")
            Notificador.enviarEmailValidacao(candidatoAtual.getEmail());
        }
    }
}

// Classe de Notificação (Melhor seria uma Interface/Service)
class Notificador {
    public static void enviarEmailValidacao(String email) {
        System.out.println("Notificador: Enviando e-mail de validação para: " + email);
    }
}