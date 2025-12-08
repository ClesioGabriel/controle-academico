package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.controller.MatriculaController;
import br.unimontes.ccet.dcc.pg1.model.dao.AlunoCursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.AlunoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.CursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.AlunoCurso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.view.ui.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GerenciadorMatriculas extends javax.swing.JFrame {

    private DefaultTableModel tableModel;
    private JTable table;

    public GerenciadorMatriculas() {
        initComponents();
        carregarMatriculas();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciador de Matrículas");
        setSize(1000, 600);
        setResizable(true);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Painel Superior com Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.GAP_SM, Theme.GAP_SM));
        painelBotoes.setBackground(Theme.BACKGROUND);

        JButton btnNovo = new JButton("Nova Matrícula");
        Theme.stylePrimaryButton(btnNovo);
        btnNovo.addActionListener(evt -> abrirDialogoNovaMatricula());
        painelBotoes.add(btnNovo);

        painelPrincipal.add(painelBotoes, BorderLayout.NORTH);

        // Painel Central com Tabela
        tableModel = new DefaultTableModel(new String[]{"CPF Aluno", "Nome Aluno", "ID Curso", "Nome Curso", "Data Matrícula"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        Theme.styleTable(table);

        // Ajustar larguras
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(3).setPreferredWidth(300);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        painelPrincipal.add(scrollPane, java.awt.BorderLayout.CENTER);

        // Painel Inferior com Botões de Ação
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.GAP_SM, Theme.GAP_SM));
        painelAcoes.setBackground(Theme.BACKGROUND);

        JButton btnVoltar = new JButton("Voltar");
        Theme.styleSecondaryButton(btnVoltar);
        btnVoltar.addActionListener(evt -> dispose());
        painelAcoes.add(btnVoltar);

        JButton btnEditar = new JButton("Atualizar Data");
        Theme.styleSecondaryButton(btnEditar);
        btnEditar.addActionListener(evt -> editarMatriculaSelecionada());
        painelAcoes.add(btnEditar);

        JButton btnDeletar = new JButton("Deletar");
        Theme.styleSecondaryButton(btnDeletar);
        btnDeletar.addActionListener(evt -> deletarMatriculaSelecionada());
        painelAcoes.add(btnDeletar);

        painelPrincipal.add(painelAcoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void carregarMatriculas() {
        tableModel.setRowCount(0);
        try {
            AlunoCursoDao acDao = new AlunoCursoDao();
            List<AlunoCurso> lista = acDao.findAll();
            AlunoDao alunoDao = new AlunoDao();
            CursoDao cursoDao = new CursoDao();

            for (AlunoCurso ac : lista) {
                String cpf = ac.getCpfAluno();
                int idCurso = ac.getIdCurso();
                String dataMatricula = ac.getDataMatricula();

                Aluno aluno = alunoDao.findOne(new Aluno(cpf));
                Curso curso = new Curso();
                curso.setId(idCurso);
                curso = cursoDao.findOne(curso);

                String nomeAluno = (aluno != null) ? aluno.getNome() : "(não encontrado)";
                String nomeCurso = (curso != null) ? curso.getNome() : "(não encontrado)";

                tableModel.addRow(new Object[]{
                    br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.formatCPF(cpf),
                    nomeAluno,
                    idCurso,
                    nomeCurso,
                    br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.toDisplayDate(dataMatricula)
                });
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar matrículas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirDialogoNovaMatricula() {
        DialogoNovaMatricula dialog = new DialogoNovaMatricula(this, true);
        dialog.setVisible(true);
        carregarMatriculas();
    }

    private void editarMatriculaSelecionada() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula para atualizar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = (String) tableModel.getValueAt(linha, 0);
        cpf = cpf.replaceAll("\\D", "");
        int idCurso = (int) tableModel.getValueAt(linha, 2);

        try {
            AlunoCursoDao dao = new AlunoCursoDao();
            AlunoCurso ac = new AlunoCurso(cpf, idCurso);
            int res = dao.update(ac);
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Data de matrícula atualizada para hoje.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarMatriculas();
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma alteração feita.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar matrícula: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarMatriculaSelecionada() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula para deletar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = (String) tableModel.getValueAt(linha, 0);
        cpf = cpf.replaceAll("\\D", "");
        int idCurso = (int) tableModel.getValueAt(linha, 2);

        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover essa matrícula?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) return;

        try {
            // Delega para o controller
            MatriculaController controller = new MatriculaController();
            int res = controller.deletar(cpf, idCurso);
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Matrícula removida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarMatriculas();
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar matrícula: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GerenciadorMatriculas().setVisible(true));
    }
}
