/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unimontes.ccet.dcc.pg1.model.dao;

import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;


/**
 *
 * @author Avell
 */
public class AlunoDao implements Dao<Aluno> {
    private Connection conexao;
    
    public AlunoDao() throws SQLException {
        conexao = DB.getInstancia().getConnection();
    }
    
    @Override
    public int save(Aluno entidade) throws DAOException {
        String iQuery = "INSERT INTO alunos (cpf, nome, data_nascimento, telefone, email, situacao) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement st = conexao.prepareStatement(iQuery)) {
            st.setString(1, entidade.getCpf());
            st.setString(2, entidade.getNome());
            st.setString(3, entidade.getDataNascimento());
            st.setString(4, entidade.getTelefone());
            st.setString(5, entidade.getEmail());
            st.setString(6, entidade.getSituacao());
            return st.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Erro ao inserir aluno: " + ex.getMessage(), ex);
        }
    }

    @Override
    public int update(Aluno entidade) throws DAOException {
        int linhasAfetadas = 0;
        
        try{
            String uQuery = "UPDATE alunos SET nome = ?, data_nascimento = ?, telefone = ?, email = ?, situacao = ? WHERE cpf = ?";
            try (PreparedStatement st = conexao.prepareStatement(uQuery)) {
                st.setString(1, entidade.getNome());
                st.setString(2, entidade.getDataNascimento());
                st.setString(3, entidade.getTelefone());
                st.setString(4, entidade.getEmail());
                st.setString(5, entidade.getSituacao());
                st.setString(6, entidade.getCpf());
                linhasAfetadas = st.executeUpdate();
            }
        } catch(SQLException ex){
            throw new DAOException("Erro ao tentar atualizar entidade Aluno. SQLSTATE: "+ex.getSQLState(), ex);
        }
        
        return linhasAfetadas;
    }

    @Override
    public int delete(Aluno entidade) throws DAOException {
        int linhasAfetadas = 0;
        try{
            String dQuery = "DELETE FROM alunos WHERE cpf = ?";
            try (PreparedStatement st = conexao.prepareStatement(dQuery)) {
                st.setString(1, entidade.getCpf());
                linhasAfetadas = st.executeUpdate();
            }
        } catch(SQLException ex){
            throw new DAOException("Erro ao tentar deletar Aluno. SQLSTATE: "+ex.getSQLState());
        }
        return linhasAfetadas;
    }

    @Override
    public List<Aluno> findAll() throws DAOException {
        List<Aluno> lista = new ArrayList<>();
        try{
            String q = "SELECT cpf, nome, data_nascimento, telefone, email, situacao FROM alunos";
            try (PreparedStatement st = conexao.prepareStatement(q);
                 ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String cpf = rs.getString("cpf");
                    String nome = rs.getString("nome");
                    String data = rs.getString("data_nascimento");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String situacao = rs.getString("situacao");
                    lista.add(new Aluno(cpf, nome, data, telefone, email, situacao));
                }
            }
        } catch(SQLException ex){
            throw new DAOException("Erro ao listar Alunos. SQLSTATE: "+ex.getSQLState(), ex);
        }
        return lista;
    }

    @Override
    public Aluno findOne(Aluno entidade) throws DAOException {
        try{
            String q = "SELECT cpf, nome, data_nascimento, telefone, email, situacao FROM alunos WHERE cpf = ?";
            try (PreparedStatement st = conexao.prepareStatement(q)) {
                st.setString(1, entidade.getCpf());
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return new Aluno(rs.getString("cpf"), rs.getString("nome"), rs.getString("data_nascimento"), rs.getString("telefone"), rs.getString("email"), rs.getString("situacao"));
                    }
                }
            }
            return null;
        } catch(SQLException ex){
            throw new DAOException("Erro ao buscar Aluno. SQLSTATE: "+ex.getSQLState(), ex);
        }
    }
    
    
}
