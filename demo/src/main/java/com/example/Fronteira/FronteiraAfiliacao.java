package com.example.Fronteira;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import com.example.Controlador.ControllerAfiliacao;
import com.example.Entidades.Formacao;

public class FronteiraAfiliacao extends Base {

    private ControllerAfiliacao controller;
    private JPanel mainContent;
    private CardLayout cardLayout;

    // --- Componentes das Telas ---
    private JTextField txtEmail;
    private JRadioButton radioCpf;
    private JRadioButton radioCnpj;
    private JTextField txtCpf;
    private JTextField txtCnpj; // Campo para CNPJ
    private JTextField txtNome;
    private JTextField txtSexo;
    private JTextField txtDataNascimento;
    private JComboBox<String> comboNacionalidade;
    private JTextField txtProfissao;
    
    // --- Componentes de Endereço ---
    private JTextField txtCepResidencial, txtRuaResidencial, txtComplementoResidencial, txtNumeroResidencial;
    private JTextField txtCepComercial, txtRuaComercial, txtComplementoComercial, txtNumeroComercial;

    // --- Componentes de Perfil ---
    private JList<String> listInteresses;
    private JList<String> listHabilidades;

    // --- Componentes do Termo e Validação ---
    private JCheckBox checkReceberAtualizacoes;
    private JTextField txtCodigoValidacao;

    
    private JButton btnProximaEtapa;
    private JButton btnVoltar;

    // --- Constantes para o CardLayout ---
    private static final String TELA_EMAIL_TIPO = "TELA_EMAIL_TIPO";
    private static final String TELA_CPF = "TELA_CPF";
    private static final String TELA_CNPJ = "TELA_CNPJ"; // Tela para CNPJ
    private static final String TELA_DADOS_PESSOAIS = "TELA_DADOS_PESSOAIS";
    private static final String TELA_ENDERECO_RESIDENCIAL = "TELA_ENDERECO_RESIDENCIAL";
    private static final String TELA_ENDERECO_COMERCIAL = "TELA_ENDERECO_COMERCIAL";
    private static final String TELA_INTERESSES = "TELA_INTERESSES";
    private static final String TELA_HABILIDADES = "TELA_HABILIDADES";
    private static final String TELA_TERMO = "TELA_TERMO";
    private static final String TELA_VALIDACAO_EMAIL = "TELA_VALIDACAO_EMAIL";

    public FronteiraAfiliacao() {
        super("Afiliação de Voluntário - Etapa 1/4");
        controller = new ControllerAfiliacao();
        
        mainContent = getContentPanel();
        cardLayout = new CardLayout();
        mainContent.setLayout(cardLayout);
        mainContent.removeAll(); 
        
        // --- Adiciona todas as telas ao CardLayout ---
        mainContent.add(createEmailTipoPanel(), TELA_EMAIL_TIPO);
        mainContent.add(createCpfPanel(), TELA_CPF);
        mainContent.add(createCnpjPanel(), TELA_CNPJ); // Adiciona a tela de CNPJ
        mainContent.add(createDadosPessoaisPanel(), TELA_DADOS_PESSOAIS);
        mainContent.add(createEnderecoResidencialPanel(), TELA_ENDERECO_RESIDENCIAL);
        mainContent.add(createEnderecoComercialPanel(), TELA_ENDERECO_COMERCIAL);
        mainContent.add(createInteressesPanel(), TELA_INTERESSES);
        mainContent.add(createHabilidadesPanel(), TELA_HABILIDADES);
        mainContent.add(createTermoAceitePanel(), TELA_TERMO);
        mainContent.add(createValidacaoEmailPanel(), TELA_VALIDACAO_EMAIL);

        // --- Exibe a primeira tela ---
        cardLayout.show(mainContent, TELA_EMAIL_TIPO);
        
        setSize(400, 550); 
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // --- TELA 1: E-mail e Seleção de Tipo (CPF/CNPJ) ---
    private JPanel createEmailTipoPanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("1. Identificação Inicial"));
        addVerticalSpace(panel, 20);
        
        panel.add(createLabel("E-mail:", Component.LEFT_ALIGNMENT));
        txtEmail = createTextField();
        panel.add(txtEmail);
        addVerticalSpace(panel, 20);

        panel.add(createLabel("Tipo de cadastro:", Component.LEFT_ALIGNMENT));
        radioCpf = new JRadioButton("Pessoa Física (CPF)");
        radioCpf.setSelected(true);
        radioCnpj = new JRadioButton("Pessoa Jurídica (CNPJ)");
        ButtonGroup group = new ButtonGroup();
        group.add(radioCpf);
        group.add(radioCnpj);
        
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(radioCpf);
        radioPanel.add(radioCnpj);
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        radioPanel.setMaximumSize(new Dimension(300, 40));
        panel.add(radioPanel);
        addVerticalSpace(panel, 30);
        
        btnProximaEtapa = createButton("Continuar", new Color(100, 149, 237));
        btnProximaEtapa.addActionListener(e -> {
            if (radioCpf.isSelected()) {
                setTitle("Afiliação - Etapa 1/4 (CPF)");
                cardLayout.show(mainContent, TELA_CPF);
            } else {
                setTitle("Afiliação - Etapa 1/4 (CNPJ)");
                cardLayout.show(mainContent, TELA_CNPJ);
            }
        });
        panel.add(btnProximaEtapa);
        
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // --- TELA 2 (Opção A): Inserir CPF ---
    private JPanel createCpfPanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("1. Identificação (CPF)"));
        addVerticalSpace(panel, 20);

        panel.add(createLabel("CPF:", Component.LEFT_ALIGNMENT));
        txtCpf = createTextField();
        panel.add(txtCpf);
        addVerticalSpace(panel, 30);

        JButton btnContinuarCpf = createButton("Continuar", new Color(100, 149, 237));
        btnContinuarCpf.addActionListener(e -> {
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();
            
            if (controller.buscarCandidatoExistente(cpf, email)) {
                JOptionPane.showMessageDialog(this, "Candidato Encontrado! Verifique a caixa de e-mail.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Candidato Novo. Prossiga com o cadastro.");
                controller.iniciarNovoCandidato(cpf, email);
                setTitle("Afiliação - Etapa 2/4");
                cardLayout.show(mainContent, TELA_DADOS_PESSOAIS);
            }
        });

        btnVoltar = createButton("Voltar", Color.GRAY);
        btnVoltar.addActionListener(e -> cardLayout.show(mainContent, TELA_EMAIL_TIPO));
        
        panel.add(createButtonPanel(btnVoltar, btnContinuarCpf));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // --- TELA 2 (Opção B): Inserir CNPJ ---
    private JPanel createCnpjPanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("1. Identificação (CNPJ)"));
        addVerticalSpace(panel, 20);

        panel.add(createLabel("CNPJ:", Component.LEFT_ALIGNMENT));
        txtCnpj = createTextField();
        panel.add(txtCnpj);
        addVerticalSpace(panel, 30);

        JButton btnContinuarCnpj = createButton("Continuar", new Color(100, 149, 237));
        btnContinuarCnpj.addActionListener(e -> {
            String cnpj = txtCnpj.getText();
            String email = txtEmail.getText();
            
            if (controller.buscarCandidatoExistente(cnpj, email)) {
                JOptionPane.showMessageDialog(this, "Candidato Encontrado! Verifique a caixa de e-mail.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Candidato Novo. Prossiga com o cadastro.");
                controller.iniciarNovoCandidato(cnpj, email);
                setTitle("Afiliação - Etapa 2/4");
                cardLayout.show(mainContent, TELA_DADOS_PESSOAIS); // Futuramente, pode ir para uma tela de dados de PJ
            }
        });

        btnVoltar = createButton("Voltar", Color.GRAY);
        btnVoltar.addActionListener(e -> cardLayout.show(mainContent, TELA_EMAIL_TIPO));
        
        panel.add(createButtonPanel(btnVoltar, btnContinuarCnpj));
        panel.add(Box.createVerticalGlue());
        return panel;
    }
    
    // --- TELA 3: Formulário de Identificação ---
    private JPanel createDadosPessoaisPanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("2. Dados Pessoais"));
        addVerticalSpace(panel, 20);

        panel.add(createLabel("Nome Completo:", Component.LEFT_ALIGNMENT));
        txtNome = createTextField();
        panel.add(txtNome);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Sexo:", Component.LEFT_ALIGNMENT));
        txtSexo = createTextField();
        panel.add(txtSexo);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Data de Nascimento (DD/MM/AAAA):", Component.LEFT_ALIGNMENT));
        txtDataNascimento = createTextField();
        panel.add(txtDataNascimento);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Nacionalidade:", Component.LEFT_ALIGNMENT));
        String[] nacionalidades = { "Brasileira", "Americana", "Canadense", "Mexicana", "Argentina", "Outra" };
        comboNacionalidade = new JComboBox<>(nacionalidades);
        comboNacionalidade.setMaximumSize(new Dimension(300, 30));
        comboNacionalidade.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(comboNacionalidade);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Profissão:", Component.LEFT_ALIGNMENT));
        txtProfissao = createTextField();
        panel.add(txtProfissao);
        addVerticalSpace(panel, 20);
        
        btnProximaEtapa = createButton("Próxima Etapa", new Color(60, 179, 113));
        btnProximaEtapa.addActionListener(e -> {
            String nome = txtNome.getText();
            String sexo = txtSexo.getText();
            String dataNascimento = txtDataNascimento.getText();
            String nacionalidade = (String) comboNacionalidade.getSelectedItem();
            // A profissão ainda não está sendo usada no controller, mas coletamos o dado.
            String profissao = txtProfissao.getText(); 

            controller.registrarDadosCompletos(
                nome, sexo, dataNascimento, nacionalidade, true, false, 
                Arrays.asList(new Formacao("Superior", "Engenharia", "UFSC"))
            );
            setTitle("Afiliação - Etapa 3/4");
            cardLayout.show(mainContent, TELA_ENDERECO_RESIDENCIAL);
        });

        btnVoltar = createButton("Voltar", Color.GRAY);
        btnVoltar.addActionListener(e -> {
            if (radioCpf.isSelected()) {
                cardLayout.show(mainContent, TELA_CPF);
            } else {
                cardLayout.show(mainContent, TELA_CNPJ);
            }
        });
        
        panel.add(createButtonPanel(btnVoltar, btnProximaEtapa));
        panel.add(Box.createVerticalGlue());
        return panel;
    }
    
    // --- TELA 4: Endereço Residencial ---
    private JPanel createEnderecoResidencialPanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("3. Endereço Residencial"));
        addVerticalSpace(panel, 20);

        panel.add(createLabel("CEP:", Component.LEFT_ALIGNMENT));
        txtCepResidencial = createTextField();
        panel.add(txtCepResidencial);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Rua:", Component.LEFT_ALIGNMENT));
        txtRuaResidencial = createTextField();
        panel.add(txtRuaResidencial);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Número:", Component.LEFT_ALIGNMENT));
        txtNumeroResidencial = createTextField();
        panel.add(txtNumeroResidencial);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Complemento:", Component.LEFT_ALIGNMENT));
        txtComplementoResidencial = createTextField();
        panel.add(txtComplementoResidencial);
        addVerticalSpace(panel, 20);

        btnProximaEtapa = createButton("Próxima Etapa", new Color(60, 179, 113));
        btnProximaEtapa.addActionListener(e -> {
            // Aqui você pode registrar os dados de endereço no controller
            setTitle("Afiliação - Etapa 3/4");
            cardLayout.show(mainContent, TELA_ENDERECO_COMERCIAL);
        });

        btnVoltar = createButton("Voltar", Color.GRAY);
        btnVoltar.addActionListener(e -> cardLayout.show(mainContent, TELA_DADOS_PESSOAIS));
        
        panel.add(createButtonPanel(btnVoltar, btnProximaEtapa));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // --- TELA 5: Endereço Comercial ---
    private JPanel createEnderecoComercialPanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("3. Endereço Comercial"));
        addVerticalSpace(panel, 20);

        panel.add(createLabel("CEP:", Component.LEFT_ALIGNMENT));
        txtCepComercial = createTextField();
        panel.add(txtCepComercial);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Rua:", Component.LEFT_ALIGNMENT));
        txtRuaComercial = createTextField();
        panel.add(txtRuaComercial);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Número:", Component.LEFT_ALIGNMENT));
        txtNumeroComercial = createTextField();
        panel.add(txtNumeroComercial);
        addVerticalSpace(panel, 10);

        panel.add(createLabel("Complemento:", Component.LEFT_ALIGNMENT));
        txtComplementoComercial = createTextField();
        panel.add(txtComplementoComercial);
        addVerticalSpace(panel, 20);

        btnProximaEtapa = createButton("Próxima Etapa", new Color(60, 179, 113));
        btnProximaEtapa.addActionListener(e -> {
            // Registrar dados de endereço comercial
            setTitle("Afiliação - Etapa 3/4");
            cardLayout.show(mainContent, TELA_INTERESSES);
        });

        btnVoltar = createButton("Voltar", Color.GRAY);
        btnVoltar.addActionListener(e -> cardLayout.show(mainContent, TELA_ENDERECO_RESIDENCIAL));
        
        panel.add(createButtonPanel(btnVoltar, btnProximaEtapa));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // --- TELA 6: Interesses ---
    private JPanel createInteressesPanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("3. Interesses"));
        addVerticalSpace(panel, 20);

        String[] interesses = { "Tecnologia", "Meio Ambiente", "Saúde", "Educação", "Cultura" };
        listInteresses = new JList<>(interesses);
        listInteresses.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listInteresses);
        scrollPane.setMaximumSize(new Dimension(300, 150));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scrollPane);
        addVerticalSpace(panel, 20);

        btnProximaEtapa = createButton("Próxima Etapa", new Color(60, 179, 113));
        btnProximaEtapa.addActionListener(e -> {
            List<String> selectedInteresses = listInteresses.getSelectedValuesList();
            // Registrar interesses no controller
            setTitle("Afiliação - Etapa 3/4");
            cardLayout.show(mainContent, TELA_HABILIDADES);
        });

        btnVoltar = createButton("Voltar", Color.GRAY);
        btnVoltar.addActionListener(e -> cardLayout.show(mainContent, TELA_ENDERECO_COMERCIAL));
        
        panel.add(createButtonPanel(btnVoltar, btnProximaEtapa));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // --- TELA 7: Habilidades ---
    private JPanel createHabilidadesPanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("3. Habilidades"));
        addVerticalSpace(panel, 20);

        String[] habilidades = { "Java", "Python", "SQL", "Gestão de Projetos", "Comunicação" };
        listHabilidades = new JList<>(habilidades);
        listHabilidades.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listHabilidades);
        scrollPane.setMaximumSize(new Dimension(300, 150));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scrollPane);
        addVerticalSpace(panel, 20);

        btnProximaEtapa = createButton("Aceitar Termo", new Color(60, 179, 113));
        btnProximaEtapa.addActionListener(e -> {
            List<String> selectedHabilidades = listHabilidades.getSelectedValuesList();
            List<String> selectedInteresses = listInteresses.getSelectedValuesList();
            controller.registrarPerfil(selectedHabilidades, selectedInteresses);
            setTitle("Afiliação - Etapa 4/4");
            cardLayout.show(mainContent, TELA_TERMO);
        });
        
        btnVoltar = createButton("Voltar", Color.GRAY);
        btnVoltar.addActionListener(e -> cardLayout.show(mainContent, TELA_INTERESSES));
        
        panel.add(createButtonPanel(btnVoltar, btnProximaEtapa));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // --- TELA 8: Termo de Aceite ---
    private JPanel createTermoAceitePanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("4. Termo de Compromisso"));
        addVerticalSpace(panel, 20);
        
        JTextArea txtTermo = new JTextArea("Eu, o Candidato, aceito as diretrizes...");
        txtTermo.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtTermo);
        scrollPane.setMaximumSize(new Dimension(300, 200));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scrollPane);
        addVerticalSpace(panel, 20);
        
        checkReceberAtualizacoes = new JCheckBox("Receber atualizações por e-mail");
        checkReceberAtualizacoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(checkReceberAtualizacoes);
        addVerticalSpace(panel, 20);

        JButton btnAceitar = createButton("Finalizar Afiliação", new Color(220, 20, 60));
        btnAceitar.addActionListener(e -> {
            controller.registrarTermoAceite();
            boolean receberAtualizacoes = checkReceberAtualizacoes.isSelected();
            controller.finalizarAfilicao("Aguardando Validação", receberAtualizacoes);
            
            JOptionPane.showMessageDialog(this, "Afiliação quase concluída! Um código foi enviado para o seu e-mail. Por favor, valide-o a seguir.");
            
            setTitle("Validação de E-mail");
            cardLayout.show(mainContent, TELA_VALIDACAO_EMAIL);
        });
        
        btnVoltar = createButton("Voltar", Color.GRAY);
        btnVoltar.addActionListener(e -> cardLayout.show(mainContent, TELA_HABILIDADES));
        
        panel.add(createButtonPanel(btnVoltar, btnAceitar));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // --- TELA 9: Validação do Código de E-mail ---
    private JPanel createValidacaoEmailPanel() {
        JPanel panel = createDefaultPanel();
        panel.add(createTitle("Validação de E-mail"));
        addVerticalSpace(panel, 20);

        panel.add(createLabel("Insira o código recebido por e-mail:", Component.LEFT_ALIGNMENT));
        txtCodigoValidacao = createTextField();
        panel.add(txtCodigoValidacao);
        addVerticalSpace(panel, 30);

        JButton btnValidar = createButton("Validar", new Color(60, 179, 113));
        btnValidar.addActionListener(e -> {
            String codigo = txtCodigoValidacao.getText();
            if (controller.validarCodigoEmail(codigo)) {
                JOptionPane.showMessageDialog(this, "E-mail validado com sucesso! Cadastro concluído.");
                this.dispose();
                // new FronteiraLogin().setVisible(true); // Descomente e ajuste para abrir a tela de login
            } else {
                JOptionPane.showMessageDialog(this, "Código inválido. Tente novamente.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Não há botão de voltar nesta tela para forçar a validação
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(btnValidar);
        panel.add(buttonWrapper);
        panel.add(Box.createVerticalGlue());
        return panel;
    }
    
    // --- Métodos Utilitários ---
    private JPanel createDefaultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        return panel;
    }

    private void addVerticalSpace(JPanel panel, int height) {
        panel.add(Box.createRigidArea(new Dimension(0, height)));
    }

    public JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(300, 30));
        tf.setAlignmentX(Component.CENTER_ALIGNMENT);
        return tf;
    }
    
    public JLabel createLabel(String text, float alignment) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(alignment);
        // Ajusta o alinhamento do contêiner do label para corresponder ao campo de texto
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.setMaximumSize(new Dimension(300, 20));
        wrapper.setOpaque(false);
        wrapper.add(label);
        // Retornar o wrapper ou o label? Para consistência, vamos manter o label
        // mas a estrutura do Base pode precisar de ajuste se o alinhamento não bater.
        // Por enquanto, o alinhamento do próprio label é o mais importante.
        return label;
    }

    public JPanel createButtonPanel(JButton left, JButton right) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.setOpaque(false);
        panel.add(left);
        panel.add(right);
        panel.setMaximumSize(new Dimension(350, 40));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FronteiraAfiliacao());
    }
}