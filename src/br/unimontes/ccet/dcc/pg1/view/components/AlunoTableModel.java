package br.unimontes.ccet.dcc.pg1.view.components;

import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AlunoTableModel extends AbstractTableModel {
    private final List<Aluno> lista;
    private final String[] colNames = {"CPF", "Nome", "Data Nascimento"};

    public AlunoTableModel(List<Aluno> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista == null ? 0 : lista.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Aluno a = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return a.getCpf();
            case 1: return a.getNome();
            case 2: return a.getDataNascimento();
            default: return null;
        }
    }
}
