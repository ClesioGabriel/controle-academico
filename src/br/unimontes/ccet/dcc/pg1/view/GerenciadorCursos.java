package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.controller.CursoController;
import br.unimontes.ccet.dcc.pg1.model.dao.CursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.view.ui.Theme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GerenciadorCursos extends javax.swing.JFrame {

    private DefaultTableModel tableModel;
    private JTable table;

    public GerenciadorCursos() {
        initComponents();
        carregarCursos();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciador de Cursos");
        setSize(900, 600);
        setResizable(true);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Painel Superior com Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.GAP_SM, Theme.GAP_SM));
        painelBotoes.setBackground(Theme.BACKGROUND);

        JButton btnNovo = new JButton("Novo Curso");
        Theme.stylePrimaryButton(btnNovo);
        btnNovo.addActionListener(evt -> abrirDialogoNovoCurso());
        painelBotoes.add(btnNovo);

        JTextField tfFiltro = new JTextField(28);
        tfFiltro.setFont(Theme.body(13));
        JButton btnBuscar = new JButton("Buscar");
        Theme.styleSecondaryButton(btnBuscar);
        btnBuscar.addActionListener(evt -> filtrarCursos(tfFiltro.getText().trim()));
        painelBotoes.add(tfFiltro);
        painelBotoes.add(btnBuscar);

        painelPrincipal.add(painelBotoes, BorderLayout.NORTH);

        // Painel Central com Tabela
        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Descrição", "Carga (h)", "Duração", "Modalidade", "Turno"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        Theme.styleTable(table);

        // Configurar largura das colunas
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(table);
        painelPrincipal.add(scrollPane, java.awt.BorderLayout.CENTER);

        // Painel Inferior com Botões de Ação
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.GAP_SM, Theme.GAP_SM));
        painelAcoes.setBackground(Theme.BACKGROUND);

        JButton btnVoltar = new JButton("Voltar");
        Theme.styleSecondaryButton(btnVoltar);
        btnVoltar.addActionListener(evt -> dispose());
        painelAcoes.add(btnVoltar);

        JButton btnEditar = new JButton("Editar");
        Theme.styleSecondaryButton(btnEditar);
        btnEditar.addActionListener(evt -> editarCursoSelecionado());
        painelAcoes.add(btnEditar);

        JButton btnDeletar = new JButton("Deletar");
        Theme.styleSecondaryButton(btnDeletar);
        btnDeletar.addActionListener(evt -> deletarCursoSelecionado());
        painelAcoes.add(btnDeletar);

        painelPrincipal.add(painelAcoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void carregarCursos() {
        tableModel.setRowCount(0);
        try {
            CursoDao dao = new CursoDao();
            List<Curso> cursos = dao.findAll();
            for (Curso curso : cursos) {
                tableModel.addRow(new Object[]{
                    curso.getId(),
                    curso.getNome(),
                    curso.getDescricao(),
                    curso.getCargaHoraria(),
                    curso.getDuracao(),
                    curso.getModalidade(),
                    curso.getTurno()
                });
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cursos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarCursos(String q) {
        tableModel.setRowCount(0);
        try {
            CursoDao dao = new CursoDao();
            List<Curso> cursos = dao.findAll();
            String ql = q.toLowerCase();
            for (Curso curso : cursos) {
                if (String.valueOf(curso.getId()).contains(q) || curso.getNome().toLowerCase().contains(ql)) {
                    tableModel.addRow(new Object[]{
                        curso.getId(),
                        curso.getNome(),
                        curso.getDescricao(),
                        curso.getCargaHoraria(),
                        curso.getDuracao(),
                        curso.getModalidade(),
                        curso.getTurno()
                    });
                }
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao filtrar cursos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirDialogoNovoCurso() {
        new DialogoNovoCurso(this, true).setVisible(true);
        carregarCursos();
    }

    private void editarCursoSelecionado() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um curso para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(linha, 0);
        try {
            CursoDao dao = new CursoDao();
            Curso curso = new Curso();
            curso.setId(id);
            curso = dao.findOne(curso);
            if (curso != null) {
                new DialogoEditarCurso(this, true, curso).setVisible(true);
                carregarCursos();
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar curso: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarCursoSelecionado() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um curso para deletar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(linha, 0);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este curso?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Delega para o controller
            CursoController controller = new CursoController();
            int resultado = controller.deletar(id);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Curso deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarCursos();
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar curso: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GerenciadorCursos().setVisible(true));
    }
}
