package com.example.Controlador;

import com.example.Entidades.Entidade;
import com.example.Conexão.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntidadeDAO {

    /**
     * Insere uma nova entidade no banco de dados.
     * @param entidade O objeto Entidade a ser inserido.
     * @return O ID gerado para a entidade inserida, ou -1 em caso de falha.
     */
    public int inserirEntidade(Entidade entidade) {
        String sql = "INSERT INTO entidade (email, senha, nome, telefone) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        // Usa try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidade.getEmail());
            ps.setString(2, entidade.getSenha());
            ps.setString(3, entidade.getNome());
            ps.setString(4, entidade.getTelefone());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        entidade.setId(generatedId); // Atualiza o objeto com o ID gerado
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Em uma aplicação real, seria melhor lançar uma exceção customizada aqui
        }
        return generatedId;
    }

    /**
     * Consulta uma entidade pelo seu ID.
     * @param id O ID da entidade a ser consultada.
     * @return O objeto Entidade se encontrado, caso contrário null.
     */
    public Entidade consultarEntidadePorId(int id) {
        String sql = "SELECT * FROM entidade WHERE id_entidade = ?";
        Entidade entidade = null;

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entidade = new Entidade();
                    entidade.setId(rs.getInt("id_entidade"));
                    entidade.setEmail(rs.getString("email"));
                    entidade.setSenha(rs.getString("senha")); // Cuidado: em produção, senhas devem ser tratadas com hash
                    entidade.setNome(rs.getString("nome"));
                    entidade.setTelefone(rs.getString("telefone"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entidade;
    }

    /**
     * Consulta uma entidade pelo seu email.
     * @param email O email da entidade a ser consultada.
     * @return O objeto Entidade se encontrado, caso contrário null.
     */
    public Entidade consultarEntidadePorEmail(String email) {
        String sql = "SELECT * FROM entidade WHERE email = ?";
        Entidade entidade = null;

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entidade = new Entidade();
                    entidade.setId(rs.getInt("id_entidade"));
                    entidade.setEmail(rs.getString("email"));
                    entidade.setSenha(rs.getString("senha"));
                    entidade.setNome(rs.getString("nome"));
                    entidade.setTelefone(rs.getString("telefone"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entidade;
    }
}