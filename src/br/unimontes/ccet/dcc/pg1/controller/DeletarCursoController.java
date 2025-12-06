package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.CursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.sql.SQLException;

/**
 * Controller para deletar curso.
 */
public class DeletarCursoController {

    public int executar(int id) throws DAOException, SQLException {
        Curso curso = new Curso();
        curso.setId(id);
        CursoDao dao = new CursoDao();
        return dao.delete(curso);
    }
}
