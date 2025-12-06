package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.controller.CursoController;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class TelaCadastroCurso extends javax.swing.JFrame {

    public TelaCadastroCurso() {
        initComponents();
    }

    private void initComponents() {
        panel = new br.unimontes.ccet.dcc.pg1.view.panels.PanelPG1();
        jlNome = new javax.swing.JLabel();
        jlObservacoes = new javax.swing.JLabel();
        tfNome = new br.unimontes.ccet.dcc.pg1.view.components.TextFieldPG1();
        tfObservacoes = new javax.swing.JTextArea();
        jbCadastrar = new javax.swing.JButton();
        jbLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro Curso");
        setResizable(false);

        jlNome.setText("Nome");
        jlObservacoes.setText("Observações");

        tfNome.setName("tfNome");
        tfObservacoes.setName("tfObservacoes");
        tfObservacoes.setRows(5);
        tfObservacoes.setColumns(20);

        jbCadastrar.setText("Cadastrar");
        jbCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCadastrarActionPerformed(evt);
            }
        });

        jbLimpar.setText("Limpar");
        jbLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jlNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jlObservacoes)
                        .addGap(18, 18, 18)
                        .addComponent(tfObservacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jbCadastrar)
                        .addGap(10, 10, 10)
                        .addComponent(jbLimpar)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNome)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlObservacoes)
                    .addComponent(tfObservacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCadastrar)
                    .addComponent(jbLimpar))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void jbCadastrarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            CursoController controller = new CursoController();
            // Tela fornece apenas nome e observações; preencher valores padrão para os demais campos
            int res = controller.criar(tfNome.getText(), tfObservacoes.getText(), "0", "N/A", "N/A", "N/A");
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Curso cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                tfNome.setText("");
                tfObservacoes.setText("");
                tfNome.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao cadastrar curso.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro de Dados", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + ex.getMessage(), "Erro de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {
        tfNome.setText("");
        tfObservacoes.setText("");
        tfNome.requestFocus();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TelaCadastroCurso().setVisible(true));
    }

    private javax.swing.JButton jbCadastrar;
    private javax.swing.JButton jbLimpar;
    private javax.swing.JLabel jlObservacoes;
    private javax.swing.JLabel jlNome;
    private br.unimontes.ccet.dcc.pg1.view.panels.PanelPG1 panel;
    private javax.swing.JTextArea tfObservacoes;
    private br.unimontes.ccet.dcc.pg1.view.components.TextFieldPG1 tfNome;
}
