/*
 * CursoDao - CRUD para cursos
 */
package br.unimontes.ccet.dcc.pg1.model.dao;

import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDao implements Dao<Curso> {

    private final Connection conn;

    public CursoDao() throws SQLException {
        this.conn = DB.getInstancia().getConnection();
    }

    @Override
    public int save(Curso curso) throws DAOException {
        String sql = "INSERT INTO curso (nome, descricao, carga_horaria, duracao, modalidade, turno) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getDescricao());
            ps.setInt(3, curso.getCargaHoraria());
            ps.setString(4, curso.getDuracao());
            ps.setString(5, curso.getModalidade());
            ps.setString(6, curso.getTurno());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir curso: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(Curso curso) throws DAOException {
        String sql = "UPDATE curso SET nome = ?, descricao = ?, carga_horaria = ?, duracao = ?, modalidade = ?, turno = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getDescricao());
            ps.setInt(3, curso.getCargaHoraria());
            ps.setString(4, curso.getDuracao());
            ps.setString(5, curso.getModalidade());
            ps.setString(6, curso.getTurno());
            ps.setInt(7, curso.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar curso: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Curso curso) throws DAOException {
        String sql = "DELETE FROM curso WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, curso.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao excluir curso: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Curso> findAll() throws DAOException {
        List<Curso> lista = new ArrayList<>();

        String sql = "SELECT id, nome, descricao, carga_horaria, duracao, modalidade, turno FROM curso";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setDescricao(rs.getString("descricao"));
                curso.setCargaHoraria(rs.getInt("carga_horaria"));
                curso.setDuracao(rs.getString("duracao"));
                curso.setModalidade(rs.getString("modalidade"));
                curso.setTurno(rs.getString("turno"));

                lista.add(curso);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar cursos: " + e.getMessage(), e);
        }

        return lista;
    }

    @Override
    public Curso findOne(Curso curso) throws DAOException {
        String sql = "SELECT id, nome, descricao, carga_horaria, duracao, modalidade, turno FROM curso WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, curso.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                curso.setNome(rs.getString("nome"));
                curso.setDescricao(rs.getString("descricao"));
                curso.setCargaHoraria(rs.getInt("carga_horaria"));
                curso.setDuracao(rs.getString("duracao"));
                curso.setModalidade(rs.getString("modalidade"));
                curso.setTurno(rs.getString("turno"));
                return curso;
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar curso: " + e.getMessage(), e);
        }
    }
}
