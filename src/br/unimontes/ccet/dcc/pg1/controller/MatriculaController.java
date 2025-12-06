package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoCursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.AlunoCurso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.sql.SQLException;

/**
 * Controller centralizado para operações de Matrícula (AlunoCurso).
 */
public class MatriculaController {

    private final AlunoCursoDao dao;

    public MatriculaController() throws SQLException {
        this.dao = new AlunoCursoDao();
    }

    /**
     * Criar nova matrícula com validação de CPF/ID.
     */
    public int criar(String cpfFormatado, int idCurso) throws DAOException, SQLException {
        // Normalizar CPF (remover máscara)
        String cpf = cpfFormatado.replaceAll("\\D", "");
        if (cpf.length() != 11) {
            throw new DAOException("CPF inválido selecionado.");
        }

        if (idCurso <= 0) {
            throw new DAOException("ID do curso inválido.");
        }

        // Criar e persistir
        AlunoCurso ac = new AlunoCurso(cpf, idCurso);
        return dao.save(ac);
    }

    /**
     * Atualizar matrícula.
     */
    public int atualizar(String cpfFormatado, int idCurso) throws DAOException, SQLException {
        String cpf = cpfFormatado.replaceAll("\\D", "");
        if (cpf.length() != 11) {
            throw new DAOException("CPF inválido.");
        }

        AlunoCurso ac = new AlunoCurso(cpf, idCurso);
        return dao.update(ac);
    }

    /**
     * Deletar matrícula.
     */
    public int deletar(String cpfFormatado, int idCurso) throws DAOException, SQLException {
        String cpf = cpfFormatado.replaceAll("\\D", "");
        if (cpf.length() != 11) {
            throw new DAOException("CPF inválido.");
        }

        AlunoCurso ac = new AlunoCurso(cpf, idCurso);
        return dao.delete(ac);
    }
}
