package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.controller.AlunoController;
import br.unimontes.ccet.dcc.pg1.model.dao.AlunoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.view.ui.Theme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GerenciadorAlunos extends javax.swing.JFrame {

    private DefaultTableModel tableModel;
    private JTable table;

    public GerenciadorAlunos() {
        initComponents();
        carregarAlunos();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciador de Alunos");
        setSize(900, 600);
        setResizable(true);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Painel Superior com Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.GAP_SM, Theme.GAP_SM));
        painelBotoes.setBackground(Theme.BACKGROUND);

        JButton btnNovo = new JButton("Novo Aluno");
        Theme.stylePrimaryButton(btnNovo);
        btnNovo.addActionListener(evt -> abrirDialogoNovoAluno());
        painelBotoes.add(btnNovo);

        painelPrincipal.add(painelBotoes, BorderLayout.NORTH);

        // Painel Central com Tabela
        tableModel = new DefaultTableModel(new String[]{"CPF", "Nome", "Data Nascimento", "Telefone", "Email", "Situação"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        Theme.styleTable(table);
        // Ajuste de larguras para melhor legibilidade
        if (table.getColumnModel().getColumnCount() >= 3) {
            table.getColumnModel().getColumn(0).setPreferredWidth(140); // CPF
            table.getColumnModel().getColumn(1).setPreferredWidth(300); // Nome
            table.getColumnModel().getColumn(2).setPreferredWidth(120); // Data
            table.getColumnModel().getColumn(3).setPreferredWidth(120); // Telefone
            table.getColumnModel().getColumn(4).setPreferredWidth(200); // Email
            table.getColumnModel().getColumn(5).setPreferredWidth(100); // Situação
        }

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
        btnEditar.addActionListener(evt -> editarAlunoSelecionado());
        painelAcoes.add(btnEditar);

        JButton btnDeletar = new JButton("Deletar");
        Theme.styleSecondaryButton(btnDeletar);
        btnDeletar.addActionListener(evt -> deletarAlunoSelecionado());
        painelAcoes.add(btnDeletar);

        painelPrincipal.add(painelAcoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void carregarAlunos() {
        tableModel.setRowCount(0);
        try {
            AlunoDao dao = new AlunoDao();
            List<Aluno> alunos = dao.findAll();
            for (Aluno aluno : alunos) {
                tableModel.addRow(new Object[]{
                    br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.formatCPF(aluno.getCpf()),
                    aluno.getNome(),
                    br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.toDisplayDate(aluno.getDataNascimento()),
                    aluno.getTelefone(),
                    aluno.getEmail(),
                    aluno.getSituacao()
                });
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirDialogoNovoAluno() {
        new DialogoNovoAluno(this, true).setVisible(true);
        carregarAlunos();
    }

    private void editarAlunoSelecionado() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = (String) tableModel.getValueAt(linha, 0);
        cpf = cpf.replaceAll("\\D", "");
        try {
            AlunoDao dao = new AlunoDao();
            Aluno aluno = dao.findOne(new Aluno(cpf));
            if (aluno != null) {
                new DialogoEditarAluno(this, true, aluno).setVisible(true);
                carregarAlunos();
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarAlunoSelecionado() {
        int linha = table.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para deletar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cpf = (String) tableModel.getValueAt(linha, 0);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este aluno?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Delega para o controller
            AlunoController controller = new AlunoController();
            int resultado = controller.deletar(cpf);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Aluno deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarAlunos();
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GerenciadorAlunos().setVisible(true));
    }
}
