package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.model.dao.service.Utils;
import java.sql.SQLException;

/**
 * Controller para criar novo aluno.
 * Responsável por validar entrada e persistir via DAO.
 */
public class CadastrarAlunoController {

    public int executar(String cpf, String nome, String dataNascimentoBr, String telefone, String email, String situacao) throws DAOException, SQLException {
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

        // Converter data de br para ISO
        String dataISO = Utils.toISODate(dataNascimentoBr);
        if (dataISO == null) {
            throw new DAOException("Data de nascimento inválida. Use dd/MM/yyyy.");
        }

        // Normalizar telefone (opcional, apenas remover não-dígitos)
        String telNorm = (telefone != null) ? telefone.replaceAll("\\D", "") : "";

        // Validar email (simples regex)
        if (email != null && !email.isEmpty() && !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new DAOException("Email inválido.");
        }

        // Criar entidade e salvar
        Aluno aluno = new Aluno(cpfNorm, nomeVal, dataISO, telNorm, email != null ? email : "", situacao != null ? situacao : "ativo");
        AlunoDao dao = new AlunoDao();
        return dao.save(aluno);
    }
}
