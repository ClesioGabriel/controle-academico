package br.unimontes.ccet.dcc.pg1;

import br.unimontes.ccet.dcc.pg1.model.dao.DB;
import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {
        try (@SuppressWarnings("unused") Connection ignored = DB.getInstancia().getConnection()) {
            System.out.println("Conexão OK!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
