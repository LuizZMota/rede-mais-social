package com.example.Conexão;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/redemaissocial"; // Endereço do banco de dados
    private static final String USER = "root"; // Altere para o seu usuário do banco
    private static final String PASSWORD = "banco123"; // Altere para a sua senha

    private static Connection conn = null; // Instância única da conexão

    public static Connection getConnect() {
        try {
            if (conn == null || conn.isClosed()) {
                // Bloco sincronizado para garantir que apenas uma thread crie a conexão
                synchronized (DatabaseConnection.class) {
                    // Verifica novamente para o caso de outra thread ter criado a conexão enquanto a primeira esperava
                    if (conn == null || conn.isClosed()) {
                        try {
                            // Carrega o driver JDBC do MySQL (opcional para JDBC 4.0+)
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            
                            // Cria a conexão
                            conn = DriverManager.getConnection(URL, USER, PASSWORD);
                            System.out.println("Conexão com o banco de dados estabelecida.");
                        } catch (ClassNotFoundException e) {
                            System.err.println("Driver JDBC do MySQL não encontrado.");
                            e.printStackTrace();
                        } catch (SQLException e) {
                            System.err.println("Falha ao conectar ao banco de dados.");
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar o estado da conexão.");
            e.printStackTrace();
            conn = null; // Reseta a conexão em caso de erro
        }
        return conn;
    }

    /**
     * Fecha a conexão com o banco de dados, se estiver aberta.
     */
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexão com o banco de dados fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão com o banco de dados.");
            e.printStackTrace();
        }
    }
}
