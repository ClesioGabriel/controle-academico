/*
 * AlunoCursoDao - vinculação entre aluno e curso
 */
package br.unimontes.ccet.dcc.pg1.model.dao;

import br.unimontes.ccet.dcc.pg1.model.dao.entity.AlunoCurso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoCursoDao implements Dao<AlunoCurso> {
    private Connection conexao;

    public AlunoCursoDao() throws SQLException {
        conexao = DB.getInstancia().getConnection();
    }

    @Override
    public int save(AlunoCurso entidade) throws DAOException {
        int linhas = 0;
        try{
            // prevent duplicate matrícula and insert using try-with-resources
            String check = "SELECT 1 FROM aluno_curso WHERE cpf_aluno = ? AND id_curso = ?";
            try (PreparedStatement chk = conexao.prepareStatement(check)) {
                chk.setString(1, entidade.getCpfAluno());
                chk.setInt(2, entidade.getIdCurso());
                try (ResultSet rchk = chk.executeQuery()) {
                    if (rchk.next()) {
                        throw new DAOException("Já existe uma matrícula para esse aluno neste curso.");
                    }
                }
            }

            String q = "INSERT INTO aluno_curso (cpf_aluno, id_curso, data_matricula) VALUES (?,?,CURDATE())";
            try (PreparedStatement st = conexao.prepareStatement(q)) {
                st.setString(1, entidade.getCpfAluno());
                st.setInt(2, entidade.getIdCurso());
                linhas = st.executeUpdate();
            }
        } catch(SQLException ex){
            throw new DAOException("Erro ao salvar vínculo aluno-curso: " + ex.getMessage() + " | CPF: " + entidade.getCpfAluno() + " | IdCurso: " + entidade.getIdCurso());
        }
        return linhas;
    }

    @Override
    public int update(AlunoCurso entidade) throws DAOException {
        int linhas = 0;
        try{
            String q = "UPDATE aluno_curso SET data_matricula = CURDATE() WHERE cpf_aluno = ? AND id_curso = ?";
            try (PreparedStatement st = conexao.prepareStatement(q)) {
                st.setString(1, entidade.getCpfAluno());
                st.setInt(2, entidade.getIdCurso());
                linhas = st.executeUpdate();
            }
        } catch(SQLException ex){
            throw new DAOException("Erro ao atualizar vínculo. SQLSTATE: "+ex.getSQLState());
        }
        return linhas;
    }

    @Override
    public int delete(AlunoCurso entidade) throws DAOException {
        int linhas = 0;
        try{
            String q = "DELETE FROM aluno_curso WHERE cpf_aluno = ? AND id_curso = ?";
            try (PreparedStatement st = conexao.prepareStatement(q)) {
                st.setString(1, entidade.getCpfAluno());
                st.setInt(2, entidade.getIdCurso());
                linhas = st.executeUpdate();
            }
        } catch(SQLException ex){
            throw new DAOException("Erro ao deletar vínculo. SQLSTATE: "+ex.getSQLState());
        }
        return linhas;
    }

    @Override
    public List<AlunoCurso> findAll() throws DAOException {
        List<AlunoCurso> lista = new ArrayList<>();
        try{
            String q = "SELECT cpf_aluno, id_curso, data_matricula FROM aluno_curso";
            try (PreparedStatement st = conexao.prepareStatement(q);
                 ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    AlunoCurso ac = new AlunoCurso(rs.getString("cpf_aluno"), rs.getInt("id_curso"), rs.getString("data_matricula"));
                    lista.add(ac);
                }
            }
        } catch(SQLException ex){
            throw new DAOException("Erro ao listar vínculos. SQLSTATE: "+ex.getSQLState());
        }
        return lista;
    }

    @Override
    public AlunoCurso findOne(AlunoCurso entidade) throws DAOException {
        try{
            String q = "SELECT cpf_aluno, id_curso, data_matricula FROM aluno_curso WHERE cpf_aluno = ? AND id_curso = ?";
            try (PreparedStatement st = conexao.prepareStatement(q)) {
                st.setString(1, entidade.getCpfAluno());
                st.setInt(2, entidade.getIdCurso());
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return new AlunoCurso(rs.getString("cpf_aluno"), rs.getInt("id_curso"), rs.getString("data_matricula"));
                    }
                }
            }
            return null;
        } catch(SQLException ex){
            throw new DAOException("Erro ao buscar vínculo. SQLSTATE: "+ex.getSQLState());
        }
    }
}
