package com.example.Controlador;

import com.example.Conexão.DatabaseConnection;
import com.example.Entidades.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

                // ✅ CORREÇÃO: Recriar o objeto PF/PJ para manter o estado
                if (VerificadorCP.VerificationCpf(cpfOuCnpj)) {
                    this.pessoaFisicaAtual = new PessoaFisica();
                    this.pessoaFisicaAtual.setCpf(cpfOuCnpj);
                } else if (VerificadorCP.verificationCnpj(cpfOuCnpj)) {
                    this.pessoaJuridicaAtual = new PessoaJuridica();
                }
                
                gerarEEnviarCodigoValidacao(conn); // ✅ CORREÇÃO: Passar a conexão existente
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
        
        // Focando apenas no fluxo de Pessoa Física, como solicitado.
        if (VerificadorCP.VerificationCpf(cpfOuCnpj)) {
            this.pessoaFisicaAtual = new PessoaFisica();
            this.pessoaFisicaAtual.setCpf(cpfOuCnpj);
            this.pessoaFisicaAtual.setIdentidade(new Identidade()); // Inicializa o objeto Identidade
        }
    }

    // Called from "Dados Pessoais" screen
    public boolean registrarDadosCompletos(String nome, String sexo, String dataNascimento, String nacionalidade, String profissao) {
        if (this.entidadeAtual == null) return false;

        this.entidadeAtual.setNome(nome);
        this.entidadeAtual.setSenha("senha_padrao"); 
        this.entidadeAtual.setTelefone("000000000");

        if (this.pessoaFisicaAtual != null) {
            this.pessoaFisicaAtual.getIdentidade().setSexo(sexo);
            
            // Adicionar validação para o formato da data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(dataNascimento, formatter);
            this.pessoaFisicaAtual.setDataNascimento(localDate);
            
            this.pessoaFisicaAtual.getIdentidade().setNacionalidade(nacionalidade);
            this.pessoaFisicaAtual.setProfissao(profissao); // ✅ ARMAZENAR PROFISSÃO
        }
        return true; // Retorna verdadeiro se tudo ocorreu bem
    }

    // Called from "Habilidades" screen
    public void registrarPerfil(List<String> habilidades, List<String> interesses) {
        // Esta lógica depende do candidato já estar inserido no banco para obter o ID.
        // O fluxo ideal seria inserir a entidade/pessoa/candidato primeiro.
        System.out.println("Habilidades selecionadas: " + habilidades);
        System.out.println("Interesses selecionados: " + interesses);
    }

    // Called from "Termo de Aceite" screen
    public void registrarTermoAceite() {
        // Lógica para registrar o aceite do termo.
        System.out.println("Termo de aceite registrado.");
    }

    // ✅ CORREÇÃO: Este método agora deve ser chamado APENAS UMA VEZ
    public void finalizarAfilicao(String status, boolean receberAtualizacoes) {
        System.out.println("\n🔄 Iniciando finalização da afiliação...");
        System.out.println("Status inicial: " + status);
        
        try (Connection conn = DatabaseConnection.getConnect()) {
            conn.setAutoCommit(false); // Iniciar transação

            // 1. Inserir Entidade
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
                    System.out.println("✅ Entidade inserida com ID: " + entidadeAtual.getId());
                } else {
                    throw new SQLException("Falha ao obter ID da entidade.");
                }
            }

            // 2. Inserir PessoaFisica ou PessoaJuridica
            if (pessoaFisicaAtual != null) {
                // ✅ CORREÇÃO: Adicionado data_nascimento
                String sqlPf = "INSERT INTO pessoa_fisica (cpf, id_entidade, sexo, nacionalidade, data_nascimento, profissao) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sqlPf)) {
                    pstmt.setString(1, pessoaFisicaAtual.getCpf());
                    pstmt.setInt(2, entidadeAtual.getId());
                    // ✅ CORREÇÃO: Buscar os dados do objeto para inserir
                    pstmt.setString(3, pessoaFisicaAtual.getIdentidade().getSexo());
                    pstmt.setString(4, pessoaFisicaAtual.getIdentidade().getNacionalidade());
                    // ✅ CORREÇÃO: Converter e inserir a data de nascimento
                    pstmt.setDate(5, java.sql.Date.valueOf(pessoaFisicaAtual.getDataNascimento()));
                    pstmt.setString(6, pessoaFisicaAtual.getProfissao()); // ✅ INSERIR PROFISSÃO

                    pstmt.executeUpdate();
                    System.out.println("✅ Pessoa Física inserida com CPF: " + pessoaFisicaAtual.getCpf());
                }
            } else if (pessoaJuridicaAtual != null) {
                // Adicionar lógica para CNPJ
                System.out.println("⚠️ Lógica de Pessoa Jurídica ainda não implementada completamente.");
            }

            // 3. Inserir Candidato com status correto
            String sqlCandidato = "INSERT INTO candidato (id_entidade, status) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCandidato, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, entidadeAtual.getId());
                pstmt.setString(2, status); // ✅ Deve ser "Aguardando Validação"
                pstmt.executeUpdate();
                
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    this.candidatoAtual = new Candidato();
                    candidatoAtual.setId(generatedKeys.getInt(1));
                    System.out.println("✅ Candidato inserido com ID: " + candidatoAtual.getId() + " | Status: " + status);
                }
            }

            // 4. Gerar e enviar código de validação DENTRO da transação
            gerarEEnviarCodigoValidacao(conn);

            conn.commit(); // Finalizar transação
            System.out.println("✅ Transação finalizada com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao finalizar afiliação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ CORREÇÃO: Melhor logging para debug
    private void gerarEEnviarCodigoValidacao(Connection conn) {
        String codigo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        LocalDateTime dataExpiracao = LocalDateTime.now().plusHours(1);

        String sql = "INSERT INTO validacao (id_entidade, codigo, data_criacao, data_expiracao, status) VALUES (?, ?, ?, ?, 'Pendente')";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, entidadeAtual.getId());
            pstmt.setString(2, codigo);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setTimestamp(4, Timestamp.valueOf(dataExpiracao));
            pstmt.executeUpdate();

            // ✅ CORREÇÃO: Melhor formatação do código no console
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║    📧 CÓDIGO DE VALIDAÇÃO GERADO      ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ ID Entidade: " + String.format("%-24s", entidadeAtual.getId()) + "║");
            System.out.println("║ E-mail: " + String.format("%-30s", entidadeAtual.getEmail()) + "║");
            System.out.println("║                                        ║");
            System.out.println("║ ✨ CÓDIGO: [" + codigo + "]            ║");
            System.out.println("║                                        ║");
            System.out.println("║ Tamanho: " + codigo.length() + " caracteres                 ║");
            System.out.println("║ Expira em: " + dataExpiracao.toLocalTime() + "              ║");
            System.out.println("╚════════════════════════════════════════╝\n");
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao gerar código de validação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ CORREÇÃO: Validação melhorada com logs detalhados
    public boolean validarCodigoEmail(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            System.err.println("❌ Código vazio ou nulo");
            return false;
        }
        
        String codigoLimpo = codigo.trim().toUpperCase();
        System.out.println("\n🔍 Iniciando validação de código...");
        System.out.println("Código fornecido: [" + codigoLimpo + "]");
        System.out.println("Tamanho: " + codigoLimpo.length() + " caracteres");
        
        String sql = "SELECT id_validacao, id_entidade, status, data_expiracao FROM validacao WHERE codigo = ? AND status = 'Pendente'";
        
        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigoLimpo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idValidacao = rs.getInt("id_validacao");
                int idEntidade = rs.getInt("id_entidade");
                LocalDateTime dataExpiracao = rs.getTimestamp("data_expiracao").toLocalDateTime();

                System.out.println("✅ Código encontrado no banco!");
                System.out.println("ID Validação: " + idValidacao);
                System.out.println("ID Entidade: " + idEntidade);
                System.out.println("Data expiração: " + dataExpiracao);
                System.out.println("Data atual: " + LocalDateTime.now());

                if (LocalDateTime.now().isAfter(dataExpiracao)) {
                    System.err.println("❌ Código expirado!");
                    atualizarStatusCodigo(idValidacao, "Expirado");
                    return false;
                }

                // ✅ Código válido, atualiza status
                System.out.println("✅ Código válido! Atualizando status...");
                atualizarStatusCodigo(idValidacao, "Utilizado");
                atualizarStatusCandidatoPorEntidade(idEntidade, "Aguardando Aprovação");
                
                System.out.println("✅ Validação concluída com sucesso!");
                return true;
            } else {
                System.err.println("❌ Código não encontrado no banco ou status diferente de 'Pendente'");
                System.err.println("Código procurado: [" + codigoLimpo + "]");
                
                // Debug adicional: listar códigos pendentes
                String debugSql = "SELECT codigo, status FROM validacao WHERE status = 'Pendente' ORDER BY id_validacao DESC LIMIT 5";
                try (PreparedStatement debugStmt = conn.prepareStatement(debugSql);
                     ResultSet debugRs = debugStmt.executeQuery()) {
                    System.err.println("\n📋 Últimos 5 códigos pendentes no banco:");
                    while (debugRs.next()) {
                        System.err.println("   - [" + debugRs.getString("codigo") + "] | Status: " + debugRs.getString("status"));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro SQL na validação: " + e.getMessage());
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
            System.out.println("✅ Status do código atualizado para: " + novoStatus);
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar status do código: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void atualizarStatusCandidatoPorEntidade(int idEntidade, String novoStatus) {
        String sql = "UPDATE candidato SET status = ? WHERE id_entidade = ?";
        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoStatus);
            pstmt.setInt(2, idEntidade);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("✅ Status do candidato atualizado para: " + novoStatus + " (" + rowsAffected + " registro(s) afetado(s))");
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar status do candidato: " + e.getMessage());
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