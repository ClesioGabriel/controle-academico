package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.model.dao.service.Utils;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller centralizado para operações de Aluno.
 * Responsável por validar entrada, normalizar dados e delegar persistência ao DAO.
 */
public class AlunoController {

    private final AlunoDao dao;

    public AlunoController() throws SQLException {
        this.dao = new AlunoDao();
    }

    /**
     * Criar novo aluno com validação e normalização de dados.
     */
    public int criar(String cpf, String nome, String dataNascimentoBr, String telefone, String email, String situacao) 
            throws DAOException, SQLException {
        // Validar e normalizar CPF
        String cpfNorm = Utils.validaCPF(cpf);
        if (cpfNorm == null) {
            throw new DAOException("CPF inválido. Deve ter 11 dígitos.");
        }

        // Validar nome
        String nomeVal = Utils.validaNome(nome);
        if (nomeVal == null) {
            throw new DAOException("Nome inválido ou vazio.");
        }

        // Converter data de BR para ISO
        String dataISO = Utils.toISODate(dataNascimentoBr);
        if (dataISO == null) {
            throw new DAOException("Data de nascimento inválida. Use dd/MM/yyyy.");
        }

        // Normalizar telefone
        String telNorm = (telefone != null) ? telefone.replaceAll("\\D", "") : "";

        // Validar email
        if (email != null && !email.isEmpty() && !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new DAOException("Email inválido.");
        }

        // Criar entidade e persistir
        Aluno aluno = new Aluno(cpfNorm, nomeVal, dataISO, telNorm, email != null ? email : "", situacao != null ? situacao : "ativo");
        return dao.save(aluno);
    }

    /**
     * Atualizar aluno existente com validação.
     */
    public int atualizar(String cpf, String nome, String dataNascimentoBr, String telefone, String email, String situacao)
            throws DAOException, SQLException {
        // Validar nome
        String nomeVal = Utils.validaNome(nome);
        if (nomeVal == null) {
            throw new DAOException("Nome inválido ou vazio.");
        }

        // Converter data de BR para ISO
        String dataISO = Utils.toISODate(dataNascimentoBr);
        if (dataISO == null) {
            throw new DAOException("Data de nascimento inválida. Use dd/MM/yyyy.");
        }

        // Normalizar telefone
        String telNorm = (telefone != null) ? telefone.replaceAll("\\D", "") : "";

        // Validar email
        if (email != null && !email.isEmpty() && !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new DAOException("Email inválido.");
        }

        // Atualizar
        Aluno aluno = new Aluno(cpf, nomeVal, dataISO, telNorm, email != null ? email : "", situacao != null ? situacao : "ativo");
        return dao.update(aluno);
    }

    /**
     * Deletar aluno.
     */
    public int deletar(String cpf) throws DAOException, SQLException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new DAOException("CPF inválido.");
        }
        return dao.delete(new Aluno(cpf));
    }

    /**
     * Retorna todos os alunos (usado pelas views para popular modelos).
     */
    public List<Aluno> findAll() throws DAOException, SQLException {
        return dao.findAll();
    }

    /**
     * Busca um aluno pelo CPF.
     */
    public Aluno findOne(String cpf) throws DAOException, SQLException {
        return dao.findOne(new Aluno(cpf));
    }
}
