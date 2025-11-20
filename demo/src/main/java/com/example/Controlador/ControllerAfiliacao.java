package com.example.Controlador;

import com.example.Conexão.DatabaseConnection;
import com.example.Entidades.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ControllerAfiliacao {

    private Entidade entidadeAtual;
    private PessoaFisica pessoaFisicaAtual;
    private PessoaJuridica pessoaJuridicaAtual;
    private Candidato candidatoAtual;

    private Perfil perfilAtual;
    private Consentimento consentimentoAtual;

    // -------------------------------------------------------------
    // 1) BUSCA INICIAL – CPF/CNPJ + EMAIL
    // -------------------------------------------------------------
    public boolean buscarCandidatoExistente(String cpfOuCnpj, String email) {
        String query = """
            SELECT e.id_entidade 
            FROM entidade e
            LEFT JOIN pessoa_fisica pf ON e.id_entidade = pf.id_entidade
            LEFT JOIN pessoa_juridica pj ON e.id_entidade = pj.id_entidade
            WHERE e.email = ? AND (pf.cpf = ? OR pj.cnpj = ?)
        """;

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, cpfOuCnpj);
            pstmt.setString(3, cpfOuCnpj);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                this.entidadeAtual = new Entidade();
                entidadeAtual.setId(rs.getInt("id_entidade"));
                entidadeAtual.setEmail(email);

                gerarEEnviarCodigoValidacao();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // -------------------------------------------------------------
    // 2) NÃO EXISTE – CRIA OBJETOS EM MEMÓRIA
    // -------------------------------------------------------------
    public void iniciarNovoCandidato(String cpfOuCnpj, String email) {

        this.entidadeAtual = new Entidade();
        entidadeAtual.setEmail(email);

        if (VerificadorCP.VerificationCpf(cpfOuCnpj)) {
            pessoaFisicaAtual = new PessoaFisica();
            pessoaFisicaAtual.setCpf(cpfOuCnpj);
            pessoaFisicaAtual.setIdentidade(new Identidade());
        } else if (VerificadorCP.verificationCnpj(cpfOuCnpj)) {
            pessoaJuridicaAtual = new PessoaJuridica();
            pessoaJuridicaAtual.setCnpj(cpfOuCnpj);
        }
    }

    // -------------------------------------------------------------
    // 3) DADOS PESSOAIS (IDENTIDADE + ENDEREÇOS + FORMAÇÃO)
    // -------------------------------------------------------------
    public void registrarDadosCompletos(
            String nome,
            String sexo,
            String dataNascimento,
            String nacionalidade,
            List<Endereco> endResidencial,
            Empresa endComercial,
            List<Formacao> formacoes) {

        entidadeAtual.setNome(nome);

        if (pessoaFisicaAtual != null) {
            pessoaFisicaAtual.getIdentidade().setSexo(sexo);
            pessoaFisicaAtual.getIdentidade().setNacionalidade(nacionalidade);

            LocalDate nasc = LocalDate.parse(dataNascimento);
            pessoaFisicaAtual.getIdentidade().setDataNascimento(nasc);

            pessoaFisicaAtual.setLocalizacoes(endResidencial);
            pessoaFisicaAtual.setFormacao(formacoes);
        }

        if (pessoaJuridicaAtual != null) {
            pessoaJuridicaAtual.setEmpresa(endComercial);
        }
    }

    // -------------------------------------------------------------
    // 4) PERFIL: HABILIDADES + INTERESSES
    // -------------------------------------------------------------
    public void registrarPerfil(List<Habilidades> habilidades, List<Interesses> interesses) {
        this.perfilAtual = new Perfil();
        perfilAtual.setHabilidades(habilidades);
        perfilAtual.setInteresses(interesses);
    }

    // -------------------------------------------------------------
    // 5) TERMO DE ACEITE
    // -------------------------------------------------------------
    public void registrarTermoAceite(List<ItemTermo> itensAceitos, Termo termo) {

        consentimentoAtual = new Consentimento();
        consentimentoAtual.setStatus("Aceito");
        consentimentoAtual.setData(LocalDateTime.now());
        consentimentoAtual.setTermo(termo);

        for (ItemTermo item : itensAceitos) {
            ConsentimentoItem ci = new ConsentimentoItem();
            ci.setItemTermo(item);
            ci.setAceite(true);
            consentimentoAtual.adicionarConsentimentoItens(ci);
        }

        // Situação conforme UC002 passo 11
        if (candidatoAtual != null) {
            candidatoAtual.setStatus("Aguardando Validação");
        }
    }

    // -------------------------------------------------------------
    // 6) FINALIZA A AFILIAÇÃO – CRIA TODAS ENTIDADES NO BANCO
    // -------------------------------------------------------------
    public void finalizarAfilicao() {

        try (Connection conn = DatabaseConnection.getConnect()) {
            conn.setAutoCommit(false);

            // -------------------------------------------------
            // ENTIDADE
            // -------------------------------------------------
            int idEntidade = inserirEntidade(conn);
            entidadeAtual.setId(idEntidade);

            // -------------------------------------------------
            // PESSOA FÍSICA / JURÍDICA
            // -------------------------------------------------
            if (pessoaFisicaAtual != null) inserirPessoaFisica(conn);
            if (pessoaJuridicaAtual != null) inserirPessoaJuridica(conn);

            // -------------------------------------------------
            // CANDIDATO
            // -------------------------------------------------
            int idCandidato = inserirCandidato(conn);
            candidatoAtual = new Candidato();
            candidatoAtual.setId(idCandidato);

            // -------------------------------------------------
            // PERFIL: habilidades + interesses
            // -------------------------------------------------
            inserirPerfilCompleto(conn, idCandidato);

            // -------------------------------------------------
            // CONSENTIMENTO + ITEM
            // -------------------------------------------------
            inserirConsentimento(conn);

            // -------------------------------------------------
            // GERA VALIDAÇÃO DE EMAIL
            // -------------------------------------------------
            gerarEEnviarCodigoValidacao();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------
    // IMPLEMENTAÇÃO DE CADA INSERÇÃO
    // -------------------------------------------------------------
    private int inserirEntidade(Connection conn) throws SQLException {
        String sql = """
            INSERT INTO entidade (email, nome, senha, telefone)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entidadeAtual.getEmail());
            ps.setString(2, entidadeAtual.getNome());
            ps.setString(3, "senha_provisoria");
            ps.setString(4, "00000000");
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private void inserirPessoaFisica(Connection conn) throws SQLException {

        // --- Identidade
        String sqlIdent = """
            INSERT INTO identidade (sexo, data_nasc, nacionalidade)
            VALUES (?, ?, ?)
        """;
        int idIdentidade;
        try (PreparedStatement ps = conn.prepareStatement(sqlIdent, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pessoaFisicaAtual.getIdentidade().getSexo());
            ps.setDate(2, Date.valueOf(pessoaFisicaAtual.getIdentidade().getDataNascimento()));
            ps.setString(3, pessoaFisicaAtual.getIdentidade().getNacionalidade());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            idIdentidade = rs.getInt(1);
        }

        // --- Pessoa Física
        String sqlPf = """
            INSERT INTO pessoa_fisica (cpf, id_entidade, id_identidade)
            VALUES (?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sqlPf)) {
            ps.setString(1, pessoaFisicaAtual.getCpf());
            ps.setInt(2, entidadeAtual.getId());
            ps.setInt(3, idIdentidade);
            ps.executeUpdate();
        }
    }

    private void inserirPessoaJuridica(Connection conn) throws SQLException {
        String sql = """
            INSERT INTO pessoa_juridica (cnpj, id_entidade)
            VALUES (?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pessoaJuridicaAtual.getCnpj());
            ps.setInt(2, entidadeAtual.getId());
            ps.executeUpdate();
        }
    }

    private int inserirCandidato(Connection conn) throws SQLException {
        String sql = """
            INSERT INTO candidato (id_entidade, status)
            VALUES (?, 'Aguardando Validação')
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entidadeAtual.getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private void inserirPerfilCompleto(Connection conn, int idCandidato) throws SQLException {

        // PERFIL
        String sqlPerfil = "INSERT INTO perfil (id_candidato) VALUES (?)";
        int idPerfil;

        try (PreparedStatement ps = conn.prepareStatement(sqlPerfil, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idCandidato);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            idPerfil = rs.getInt(1);
        }

        // HABILIDADES
        String sqlHab = """
            INSERT INTO habilidades (descricao, status, id_perfil)
            VALUES (?, ?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sqlHab)) {
            for (Habilidades h : perfilAtual.getHabilidades()) {
                ps.setString(1, h.getDescricao());
                ps.setString(2, h.getStatus());
                ps.setInt(3, idPerfil);
                ps.addBatch();
            }
            ps.executeBatch();
        }

        // INTERESSES
        String sqlInt = """
            INSERT INTO interesses (descricao, status, id_perfil)
            VALUES (?, ?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(sqlInt)) {
            for (Interesses i : perfilAtual.getInteresses()) {
                ps.setString(1, i.getDescricao());
                ps.setString(2, i.getStatus());
                ps.setInt(3, idPerfil);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void inserirConsentimento(Connection conn) throws SQLException {

        String sql = """
            INSERT INTO consentimento (id_entidade, status, data, id_termo)
            VALUES (?, ?, ?, ?)
        """;

        int idConsentimento;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entidadeAtual.getId());
            ps.setString(2, consentimentoAtual.getStatus());
            ps.setTimestamp(3, Timestamp.valueOf(consentimentoAtual.getData()));
            ps.setInt(4, consentimentoAtual.getTermo().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            idConsentimento = rs.getInt(1);
        }

        String sqlItem = """
            INSERT INTO consentimento_item (id_consentimento, id_item_termo, aceite)
            VALUES (?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sqlItem)) {
            for (ConsentimentoItem item : consentimentoAtual.getConsentimentoItens()) {
                ps.setInt(1, idConsentimento);
                ps.setInt(2, item.getItemTermo().getId());
                ps.setBoolean(3, true);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    // -------------------------------------------------------------
    // 7) VALIDAÇÃO DE EMAIL
    // -------------------------------------------------------------
    private void gerarEEnviarCodigoValidacao() {

        String codigo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        String sql = """
            INSERT INTO validacao (id_entidade, codigo, data_criacao, data_expiracao, status)
            VALUES (?, ?, ?, ?, 'Pendente')
        """;

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, entidadeAtual.getId());
            ps.setString(2, codigo);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now().plusHours(24)));
            ps.executeUpdate();

            System.out.println("CÓDIGO DE VALIDAÇÃO ENVIADO PARA: " + entidadeAtual.getEmail());
            System.out.println("CÓDIGO: " + codigo);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validarCodigoEmail(String codigo) {

        String sql = """
            SELECT id_validacao, id_entidade, data_expiracao
            FROM validacao 
            WHERE codigo = ? AND status = 'Pendente'
        """;

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                LocalDateTime expira = rs.getTimestamp("data_expiracao").toLocalDateTime();

                if (LocalDateTime.now().isAfter(expira)) {
                    atualizarStatusCodigo(rs.getInt("id_validacao"), "Expirado");
                    return false;
                }

                atualizarStatusCodigo(rs.getInt("id_validacao"), "Utilizado");

                atualizarStatusCandidato(rs.getInt("id_entidade"), "Aguardando Aprovação");

                return true;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void atualizarStatusCodigo(int id, String status) throws SQLException {

        String sql = """
            UPDATE validacao SET status = ? WHERE id_validacao = ?
        """;

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    private void atualizarStatusCandidato(int idEntidade, String novoStatus) throws SQLException {

        String sql = """
            UPDATE candidato SET status = ? WHERE id_entidade = ?
        """;

        try (Connection conn = DatabaseConnection.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novoStatus);
            ps.setInt(2, idEntidade);
            ps.executeUpdate();
        }
    }
}
