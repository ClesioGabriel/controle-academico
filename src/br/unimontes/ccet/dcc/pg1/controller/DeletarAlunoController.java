package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.sql.SQLException;

/**
 * Controller para deletar aluno.
 */
public class DeletarAlunoController {

    public int executar(String cpf) throws DAOException, SQLException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new DAOException("CPF inválido.");
        }
        AlunoDao dao = new AlunoDao();
        return dao.delete(new Aluno(cpf));
    }
}
