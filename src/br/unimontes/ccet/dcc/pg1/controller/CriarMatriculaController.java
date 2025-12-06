package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoCursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.AlunoCurso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.sql.SQLException;

/**
 * Controller para criar nova matrícula.
 * Responsável por validar CPF/curso e persistir via DAO.
 */
public class CriarMatriculaController {

    public int executar(String cpfFormatado, int idCurso) throws DAOException, SQLException {
        // Normalizar CPF (remover máscara)
        String cpf = cpfFormatado.replaceAll("\\D", "");
        if (cpf.length() != 11) {
            throw new DAOException("CPF inválido selecionado.");
        }

        if (idCurso <= 0) {
            throw new DAOException("ID do curso inválido.");
        }

        // Criar e salvar
        AlunoCurso ac = new AlunoCurso(cpf, idCurso);
        AlunoCursoDao dao = new AlunoCursoDao();
        return dao.save(ac);
    }
}
