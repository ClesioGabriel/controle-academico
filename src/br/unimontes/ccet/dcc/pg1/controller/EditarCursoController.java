package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.CursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.model.dao.service.Utils;
import java.sql.SQLException;

/**
 * Controller para editar curso existente.
 */
public class EditarCursoController {

    public int executar(int id, String nome, String descricao, String cargaHorariaStr, String duracao, String modalidade, String turno) throws DAOException, SQLException {
        // Validar nome
        String nomeVal = Utils.validaNome(nome);
        if (nomeVal == null) {
            throw new DAOException("Nome do curso não pode estar vazio!");
        }

        // Parse carga horária
        int carga = 0;
        if (cargaHorariaStr != null && !cargaHorariaStr.trim().isEmpty()) {
            try {
                carga = Integer.parseInt(cargaHorariaStr.trim());
            } catch (NumberFormatException ex) {
                throw new DAOException("Carga horária deve ser um número inteiro.");
            }
        }

        // Validar campos obrigatórios
        if (duracao == null || duracao.trim().isEmpty()) {
            throw new DAOException("Duração não pode estar vazia.");
        }
        if (modalidade == null || modalidade.trim().isEmpty()) {
            throw new DAOException("Modalidade não pode estar vazia.");
        }
        if (turno == null || turno.trim().isEmpty()) {
            throw new DAOException("Turno não pode estar vazio.");
        }

        // Atualizar
        Curso curso = new Curso(id, nomeVal, descricao != null ? descricao : "", carga, duracao, modalidade, turno);
        CursoDao dao = new CursoDao();
        return dao.update(curso);
    }
}
