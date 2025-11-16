package com.example.Fronteira;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Base extends JFrame {

    private JPanel content;

    public Base(String title) {
        // Configurações gerais da janela
        setTitle(title);
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // não permitir redimensionamento
        setLayout(new BorderLayout());

        // Painel principal com padding e cor agradável
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(245, 245, 245)); // cor de fundo suave
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Adiciona sombra ou borda arredondada usando JPanel interno
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(content, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);

        // Centraliza os componentes verticalmente
        content.add(Box.createVerticalGlue());
    }

    // Retorna o painel principal para adicionar componentes
    public JPanel getContentPanel() {
        return content;
    }

    // Método utilitário para criar espaçamento vertical
    public void addVerticalSpace(int pixels) {
        content.add(Box.createVerticalStrut(pixels));
    }

    // Método utilitário para criar título centralizado
    public JLabel createTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // Método utilitário para criar botões padronizados
    public JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(150, 35));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
}
