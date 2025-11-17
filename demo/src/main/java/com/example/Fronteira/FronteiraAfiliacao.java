package com.example.Fronteira;

import javax.swing.*;
import java.util.List;
import java.util.Arrays;
import com.example.Controlador.ControllerAfiliacao;
import com.example.Entidades.Formacao;

public class FronteiraAfiliacao extends JFrame {

    private ControllerAfiliacao controller;

    // Elementos da interface
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JButton btnInfoCpfEmail;
    
    private JTextField txtNome;
    private JButton btnFinalizarAfilicao; // Novo botão para consolidar o processo
    
    public FronteiraAfiliacao() {
        super("Guia Afiliacao");
        controller = new ControllerAfiliacao();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        initComponents();
        addListeners();
        
        setVisible(true);
    }

    private void initComponents() {
        // ... (Mesmos componentes simplificados) ...
        add(new JLabel("--- ETAPA 1: Cadastro Inicial ---"));
        txtCpf = new JTextField(15);
        txtEmail = new JTextField(15);
        btnInfoCpfEmail = new JButton("Buscar/Criar Candidato");
        add(new JLabel("CPF:")); add(txtCpf);
        add(new JLabel("Email:")); add(txtEmail);
        add(btnInfoCpfEmail);
        
        add(new JLabel("--- ETAPA 2: Dados Pessoais e Perfil ---"));
        txtNome = new JTextField(15);
        btnFinalizarAfilicao = new JButton("Finalizar Afiliação e Aceitar Termo");
        add(new JLabel("Nome:")); add(txtNome);
        // Campos Formacao, Habilidades e Localizacao seriam adicionados aqui
        add(btnFinalizarAfilicao);
    }

    private void addListeners() {
        // Ação 1: Busca/Criação Inicial
        btnInfoCpfEmail.addActionListener(e -> {
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();
            
            if (controller.buscarCandidatoExistente(cpf, email)) {
                JOptionPane.showMessageDialog(this, "Candidato Encontrado! (Fluxo de atualização)");
                // Lógica para carregar dados para edição
            } else {
                JOptionPane.showMessageDialog(this, "Candidato Novo. Iniciando criação...");
                controller.iniciarNovoCandidato(cpf, email);
                // Habilita a próxima seção do formulário
            }
        });
        
        // Ação 2: Consolidar todas as informações e finalizar o processo
        btnFinalizarAfilicao.addActionListener(e -> {
            // **FLUXO COMPLETO DE AFILIAÇÃO**
            
            // 1. Coletar e Registrar Dados Pessoais (Nome, Formacao, Localizacao)
            String nome = txtNome.getText();
            List<Formacao> formacoes = Arrays.asList(new Formacao()); // Dados da tela
            controller.registrarDadosCompletos(nome, "M", "01/01/1990", "Brasileira", true, false, formacoes);
            
            // 2. Coletar e Registrar Perfil (Habilidades, Interesses)
            List<String> descHabilidades = Arrays.asList("Java", "SQL"); // Dados da tela
            List<String> descInteresses = Arrays.asList("Tecnologia");  // Dados da tela
            
            // Chamada ao Controller: o mapeamento para objetos Habilidade/Interesse ocorre DENTRO do Controller
            controller.registrarPerfil(descHabilidades, descInteresses);
            
            // 3. Registrar Termo de Aceite (Assumindo que o usuário clicou no botão)
            controller.registrarTermoAceite();
            
            // 4. Finalizar o processo e enviar notificação
            controller.finalizarAfilicao("Aguardando Validação");
            
            JOptionPane.showMessageDialog(this, "Afiliação concluída. Aguarde o e-mail de validação!");
            this.dispose(); 
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FronteiraAfiliacao());
    }
}