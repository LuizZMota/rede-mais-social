package com.example.Controlador;

import com.example.Entidades.Entidade;
import com.example.Conexão.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntidadeDAO {

    public void inserirEntidade(Entidade enti){
        String sqlScript = "INSERT INTO ENTIDADE (IDENTIDADE, EMAIL, SENHA)  VALUES (?, ?, ?)";

        PreparedStatement ps = null;

        try {
            ps = DatabaseConnection.getConnect().prepareStatement(sqlScript);
            ps.setInt(1, enti.getId());
            ps.setString(2, enti.getEmail());
            ps.setObject(3, enti.getSenha());

            ps.execute();
            ps.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void consultarUEntidade(int id) {
        String sqlScript = "SELECT * FROM ENTIDADE WHERE Id = ?";

        PreparedStatement ps = null;

        try{
            ps = DatabaseConnection.getConnect().prepareStatement(sqlScript);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                System.out.println("Usuario Encontrado: \n");
                System.out.println("ID: " + rs.getInt("idEntidade"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Contato ID: " + rs.getString("contato_id"));
            }else{
                System.out.println("Usuário não encontrado!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}