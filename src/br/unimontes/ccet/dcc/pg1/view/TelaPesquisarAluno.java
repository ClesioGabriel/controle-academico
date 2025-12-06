package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.view.components.AlunoTableModel;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class TelaPesquisarAluno extends javax.swing.JFrame {

    public TelaPesquisarAluno() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jbFechar = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar Alunos");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CPF", "Nome", "Ano Nascimento"
            }
        ));
        jScrollPane1.setViewportView(table);

        jbFechar.setText("Fechar");
        jbFechar.addActionListener(evt -> dispose());

        jbEditar.setText("Editar");
        jbEditar.addActionListener(evt -> jbEditarActionPerformed(evt));

        jbExcluir.setText("Excluir");
        jbExcluir.addActionListener(evt -> jbExcluirActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbFechar)) )
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbEditar)
                    .addComponent(jbExcluir)
                    .addComponent(jbFechar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void loadData() {
        try {
            AlunoDao dao = new AlunoDao();
            List<Aluno> lista = dao.findAll();
            table.setModel(new AlunoTableModel(lista));
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jbEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpf = String.valueOf(table.getValueAt(row, 0));
        String nome = String.valueOf(table.getValueAt(row, 1));
        String ano = String.valueOf(table.getValueAt(row, 2));
        TelaCadastroAluno tela = new TelaCadastroAluno();
        tela.setAlunoFields(cpf, nome, ano);
        tela.setCpfEditable(false);
        tela.setVisible(true);
    }

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String cpf = String.valueOf(table.getValueAt(row, 0));
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do aluno CPF: " + cpf + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            AlunoDao dao = new AlunoDao();
            Aluno a = new Aluno(cpf);
            int r = dao.delete(a);
            if (r > 0) {
                JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma linha afetada.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JButton jbFechar;
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbExcluir;
}
