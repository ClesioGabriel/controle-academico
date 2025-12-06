/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.unimontes.ccet.dcc.pg1.model.dao;

import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.util.List;

/**
 *
 * @author Avell
 */
public interface Dao<T> {
    int save(T entidade) throws DAOException;

    int update(T entidade) throws DAOException;

    int delete(T entidade) throws DAOException;

    List<T> findAll() throws DAOException;

    T findOne(T entidade) throws DAOException;
}
