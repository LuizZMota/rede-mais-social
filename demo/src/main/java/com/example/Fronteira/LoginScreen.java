package com.example.Fronteira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends Base{

    public LoginScreen() {
        super("Login - Rede Mais Social");

        // Painel central com layout vertical
        JPanel panel = getContentPanel();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBackground(new Color(245, 245, 245)); // fundo claro

        // Espaçamento superior
        panel.add(Box.createVerticalStrut(40));

        // Título
        JLabel title = new JLabel("Bem-vindo!");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(30));

        // CPF / CNPJ
        JLabel labelCP = new JLabel("CPF / CNPJ:");
        labelCP.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelCP);

        JTextField txtCP = new JTextField();
        txtCP.setMaximumSize(new Dimension(250, 30)); // campo pequeno
        txtCP.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(txtCP);
        panel.add(Box.createVerticalStrut(20));

        // Senha
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelSenha);

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setMaximumSize(new Dimension(250, 30));
        txtSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(txtSenha);
        panel.add(Box.createVerticalStrut(30));

        // Botão Entrar
        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBackground(new Color(100, 149, 237)); // azul agradável
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setMaximumSize(new Dimension(150, 35));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cp = txtCP.getText();
                String senha = new String(txtSenha.getPassword());

                // Aqui você chamaria seu controle para validar login
                JOptionPane.showMessageDialog(null,
                        "CPF/CNPJ: " + cp + "\nSenha: " + senha);
            }
        });

        // Espaçamento inferior
        panel.add(Box.createVerticalGlue());

        setVisible(true);
    }
}
