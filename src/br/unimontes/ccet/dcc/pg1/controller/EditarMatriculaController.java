package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoCursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.AlunoCurso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.sql.SQLException;

/**
 * Controller para atualizar matrícula.
 */
public class EditarMatriculaController {

    public int executar(String cpfFormatado, int idCurso) throws DAOException, SQLException {
        String cpf = cpfFormatado.replaceAll("\\D", "");
        if (cpf.length() != 11) {
            throw new DAOException("CPF inválido.");
        }

        AlunoCurso ac = new AlunoCurso(cpf, idCurso);
        AlunoCursoDao dao = new AlunoCursoDao();
        return dao.update(ac);
    }
}
