package br.unimontes.ccet.dcc.pg1.view.components;

import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CursoTableModel extends AbstractTableModel {
    private final List<Curso> lista;
    private final String[] colNames = {"ID", "Nome", "Descrição", "Carga (h)", "Duração", "Modalidade", "Turno"};

    public CursoTableModel(List<Curso> lista) {
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
        Curso c = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return c.getId();
            case 1: return c.getNome();
            case 2: return c.getDescricao();
            case 3: return c.getCargaHoraria();
            case 4: return c.getDuracao();
            case 5: return c.getModalidade();
            case 6: return c.getTurno();
            default: return null;
        }
    }
}
