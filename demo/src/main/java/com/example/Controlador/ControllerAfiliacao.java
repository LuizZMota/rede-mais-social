package com.example.Controlador;

import com.example.Conexão.DatabaseConnection;
import com.example.Entidades.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ControllerAfiliacao {

    private Entidade entidadeAtual;
    private PessoaFisica pessoaFisicaAtual;
    private PessoaJuridica pessoaJuridicaAtual;
    private Candidato candidatoAtual;

    // Called from FronteiraAfiliacao when user provides CPF/CNPJ and email
    public boolean buscarCandidatoExistente(String cpfOuCnpj, String email) {
        String query = "SELECT e.id_entidade FROM entidade e " +
                       "LEFT JOIN pessoa_fisica pf ON e.id_entidade = pf.id_entidade " +
                       "LEFT JOIN pessoa_juridica pj ON e.id_entidade = pj.id_entidade " +
                       "WHERE e.email = ? AND (pf.cpf = ? OR pj.cnpj = ?)";

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, cpfOuCnpj);
            pstmt.setString(3, cpfOuCnpj);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Candidato existe, podemos enviar um código de validação para login ou recuperação
                int idEntidade = rs.getInt("id_entidade");
                this.entidadeAtual = new Entidade();
                this.entidadeAtual.setId(idEntidade);
                this.entidadeAtual.setEmail(email);
                
                gerarEEnviarCodigoValidacao(); // Re-enviar código para usuário existente
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Called if buscarCandidatoExistente is false
    public void iniciarNovoCandidato(String cpfOuCnpj, String email) {
        this.entidadeAtual = new Entidade();
        this.entidadeAtual.setEmail(email);

        if (VerificadorCP.isCPF(cpfOuCnpj)) {
            this.pessoaFisicaAtual = new PessoaFisica();
            this.pessoaFisicaAtual.setCpf(cpfOuCnpj);
        } else if (VerificadorCP.isCNPJ(cpfOuCnpj)) {
            this.pessoaJuridicaAtual = new PessoaJuridica();
            this.pessoaJuridicaAtual.setCnpj(cpfOuCnpj);
        }
    }

    // Called from "Dados Pessoais" screen
    public void registrarDadosCompletos(String nome, String sexo, String dataNascimento, String nacionalidade, boolean isPf, boolean isPj, List<Formacao> formacoes) {
        if (this.entidadeAtual == null) return;

        this.entidadeAtual.setNome(nome);
        // A senha e o telefone não estão na tela, precisaria adicioná-los.
        // Por enquanto, vou usar valores padrão.
        this.entidadeAtual.setSenha("senha_padrao"); 
        this.entidadeAtual.setTelefone("000000000");

        if (isPf && this.pessoaFisicaAtual != null) {
            this.pessoaFisicaAtual.setSexo(sexo);
            // A data de nascimento precisa ser convertida de String para Date
            // this.pessoaFisicaAtual.setDataNascimento(dataNascimento);
            this.pessoaFisicaAtual.setNacionalidade(nacionalidade);
        }
        // Lógica para Pessoa Jurídica seria similar
    }

    // Called from "Habilidades" screen
    public void registrarPerfil(List<String> habilidades, List<String> interesses) {
        // Esta lógica depende do candidato já estar inserido no banco para obter o ID.
        // O fluxo ideal seria inserir a entidade/pessoa/candidato primeiro.
        // Vou adiar a implementação detalhada até que o fluxo de inserção esteja claro.
    }

    // Called from "Termo de Aceite" screen
    public void registrarTermoAceite() {
        // Lógica para registrar o aceite do termo.
        // Também depende do candidato já existir no banco.
    }

    // Called after accepting the term
    public void finalizarAfilicao(String status, boolean receberAtualizacoes) {
        // 1. Inserir Entidade e obter ID
        // 2. Inserir PessoaFisica/Juridica com o ID da entidade
        // 3. Inserir Candidato com o ID da entidade
        // 4. Gerar e enviar código de validação
        
        try (Connection conn = DatabaseConnection.getConnect()) {
            conn.setAutoCommit(false); // Iniciar transação

            // Inserir Entidade
            String sqlEntidade = "INSERT INTO entidade (email, senha, nome, telefone) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlEntidade, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, entidadeAtual.getEmail());
                pstmt.setString(2, entidadeAtual.getSenha());
                pstmt.setString(3, entidadeAtual.getNome());
                pstmt.setString(4, entidadeAtual.getTelefone());
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    entidadeAtual.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter ID da entidade.");
                }
            }

            // Inserir PessoaFisica ou PessoaJuridica
            if (pessoaFisicaAtual != null) {
                String sqlPf = "INSERT INTO pessoa_fisica (cpf, id_entidade, sexo, nacionalidade) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sqlPf)) {
                    pstmt.setString(1, pessoaFisicaAtual.getCpf());
                    pstmt.setInt(2, entidadeAtual.getId());
                    pstmt.setString(3, pessoaFisicaAtual.getSexo());
                    pstmt.setString(4, pessoaFisicaAtual.getNacionalidade());
                    // Faltando data de nascimento
                    pstmt.executeUpdate();
                }
            } // Adicionar else if para pessoaJuridicaAtual

            // Inserir Candidato
            String sqlCandidato = "INSERT INTO candidato (id_entidade, status) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCandidato, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, entidadeAtual.getId());
                pstmt.setString(2, status);
                pstmt.executeUpdate();
                
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    this.candidatoAtual = new Candidato();
                    candidatoAtual.setId(generatedKeys.getInt(1));
                }
            }

            conn.commit(); // Finalizar transação
            
            gerarEEnviarCodigoValidacao();

        } catch (SQLException e) {
            e.printStackTrace();
            // Rollback em caso de erro
        }
    }

    private void gerarEEnviarCodigoValidacao() {
        String codigo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LocalDateTime dataExpiracao = LocalDateTime.now().plusHours(1);

        String sql = "INSERT INTO validacao (id_entidade, codigo, data_criacao, data_expiracao, status) VALUES (?, ?, ?, ?, 'Pendente')";
        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, entidadeAtual.getId());
            pstmt.setString(2, codigo);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setTimestamp(4, Timestamp.valueOf(dataExpiracao));
            pstmt.executeUpdate();

            // Simular envio de e-mail
            System.out.println("--- CÓDIGO DE VALIDAÇÃO ---");
            System.out.println("Para o e-mail: " + entidadeAtual.getEmail());
            System.out.println("Código: " + codigo);
            System.out.println("---------------------------");
            
            // EnvioEmail.enviarEmail(entidadeAtual.getEmail(), "Seu código de validação", "Seu código é: " + codigo);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This is the method the user is having trouble with
    public boolean validarCodigoEmail(String codigo) {
        // This method is now stateless and finds the user by the validation code.
        String sql = "SELECT id_validacao, id_entidade, status, data_expiracao FROM validacao WHERE codigo = ? AND status = 'Pendente'";
        
        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo.trim());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idValidacao = rs.getInt("id_validacao");
                LocalDateTime dataExpiracao = rs.getTimestamp("data_expiracao").toLocalDateTime();

                if (LocalDateTime.now().isAfter(dataExpiracao)) {
                    // Código expirou, atualiza o status
                    atualizarStatusCodigo(idValidacao, "Expirado");
                    return false;
                }

                // Código válido, atualiza o status para 'Utilizado'
                atualizarStatusCodigo(idValidacao, "Utilizado");
                
                // Atualiza o status do candidato para 'Ativo'
                int idEntidade = rs.getInt("id_entidade");
                atualizarStatusCandidatoPorEntidade(idEntidade, "Ativo");
                
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void atualizarStatusCodigo(int idValidacao, String novoStatus) {
        String sql = "UPDATE validacao SET status = ? WHERE id_validacao = ?";
        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoStatus);
            pstmt.setInt(2, idValidacao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Original methods from the file
    public boolean solicitarAfiliacao(Afiliacao afiliacao) {
        Integer idCandidato = buscarIdCandidatoPorIdEntidade(afiliacao.getCandidato().getId());

        if (idCandidato == null) {
            System.err.println("Não foi possível encontrar o candidato com o ID da entidade fornecido.");
            return false;
        }

        String sql = "INSERT INTO afiliacao (id_candidato, data_solicitacao, status) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCandidato);
            pstmt.setDate(2, new java.sql.Date(afiliacao.getData().getTime()));
            pstmt.setString(3, afiliacao.getStatus());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Integer buscarIdCandidatoPorIdEntidade(int idEntidade) {
        String sql = "SELECT id_candidato FROM candidato WHERE id_entidade = ?";
        Integer idCandidato = null;

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEntidade);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                idCandidato = rs.getInt("id_candidato");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCandidato;
    }
}
