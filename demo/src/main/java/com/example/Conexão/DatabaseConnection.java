package com.example.Conexão;

import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String url = ""; //endereço do banco de dados
    private static final String user = ""; // Usuario do banvco
    private static final String password = ""; // Senhaa pra acessar

    public static java.sql.Connection conn;

    public static java.sql.Connection getConnect(){//metodo usado para conectar no banco de dados
        try {

            Class.forName("com.mysql.cj.jdbc.Driver"); //Carrega o driver JDBC do MySQL

            return DriverManager.getConnection( //aqui cria conexão com o banco de dados
                    url,
                    user,
                    password
            );

        } catch (Exception error){ //erros: senha errada, banco offline, driver não encontrado
            error.printStackTrace();
            return null;
        }
    }
}
