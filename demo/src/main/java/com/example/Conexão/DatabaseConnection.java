package com.example.Conexão;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/redemaissocial?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "banco123";

    public static Connection getConnect() throws SQLException {
        try {
            // JDBC driver (opcional para Java 11+)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do MySQL não encontrado!", e);
        }

        // Se falhar, lança SQLException — nunca retorna null
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
