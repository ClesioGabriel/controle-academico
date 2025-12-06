package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.CursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.model.dao.service.Utils;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller centralizado para operações de Curso.
 */
public class CursoController {

    private final CursoDao dao;

    public CursoController() throws SQLException {
        this.dao = new CursoDao();
    }

    /**
     * Criar novo curso com validação.
     */
    public int criar(String nome, String descricao, String cargaHorariaStr, String duracao, String modalidade, String turno)
            throws DAOException, SQLException {
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

        // Preencher valores padrão se campos não estiverem presentes na UI
        duracao = (duracao == null || duracao.trim().isEmpty()) ? "N/A" : duracao;
        modalidade = (modalidade == null || modalidade.trim().isEmpty()) ? "N/A" : modalidade;
        turno = (turno == null || turno.trim().isEmpty()) ? "N/A" : turno;

        // Criar entidade e persistir
        Curso curso = new Curso(nomeVal, descricao != null ? descricao : "", carga, duracao, modalidade, turno);
        return dao.save(curso);
    }

    /**
     * Atualizar curso existente com validação.
     */
    public int atualizar(int id, String nome, String descricao, String cargaHorariaStr, String duracao, String modalidade, String turno)
            throws DAOException, SQLException {
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

        // Preencher valores padrão se campos não estiverem presentes na UI
        duracao = (duracao == null || duracao.trim().isEmpty()) ? "N/A" : duracao;
        modalidade = (modalidade == null || modalidade.trim().isEmpty()) ? "N/A" : modalidade;
        turno = (turno == null || turno.trim().isEmpty()) ? "N/A" : turno;

        // Atualizar
        Curso curso = new Curso(id, nomeVal, descricao != null ? descricao : "", carga, duracao, modalidade, turno);
        return dao.update(curso);
    }

    /**
     * Deletar curso.
     */
    public int deletar(int id) throws DAOException, SQLException {
        Curso curso = new Curso();
        curso.setId(id);
        return dao.delete(curso);
    }

    /**
     * Retorna todos os cursos (para popular combos nas views).
     */
    public List<Curso> findAll() throws DAOException, SQLException {
        return dao.findAll();
    }

    /**
     * Busca um curso por id.
     */
    public Curso findOne(int id) throws DAOException, SQLException {
        Curso c = new Curso();
        c.setId(id);
        return dao.findOne(c);
    }
}
