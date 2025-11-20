package com.example.Fronteira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends Base {

    public MainScreen() {
        super("Rede Mais Social");

        JPanel panel = getContentPanel();

        // Título
        JLabel title = createTitle("Rede Mais Social");
        panel.add(title);
        addVerticalSpace(40);

        // Botões
        JButton cadastroButton = createButton("Iniciar Cadastro", new Color(100, 149, 237));
        JButton loginButton = createButton("Realizar Login", new Color(60, 179, 113));
        JButton sairButton = createButton("Sair", new Color(220, 20, 60));

        // Ações dos botões
        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ✅ CORREÇÃO: Inicia a tela de cadastro de afiliação.
                new FronteiraAfiliacao();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a MainScreen
                new LoginScreen(); // chamar a tela de login estilizada
            }
        });

        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // fecha a janela
            }
        });

        // Adiciona os botões ao painel central com espaçamento
        panel.add(cadastroButton);
        addVerticalSpace(15);
        panel.add(loginButton);
        addVerticalSpace(15);
        panel.add(sairButton);

        panel.add(Box.createVerticalGlue()); // empurra tudo para o topo/centro

        setVisible(true);
    }
}
