package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.controller.AlunoController;
import br.unimontes.ccet.dcc.pg1.controller.CursoController;
import br.unimontes.ccet.dcc.pg1.controller.MatriculaController;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import java.awt.Component;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

public class TelaVincularAlunoCurso extends javax.swing.JFrame {

    public TelaVincularAlunoCurso() {
        initComponents();
        loadAlunos();
        loadCursos();
    }

    private void loadAlunos() {
        try {
            AlunoController ac = new AlunoController();
            List<Aluno> alunos = ac.findAll();
            DefaultComboBoxModel<Aluno> model = new DefaultComboBoxModel<>();
            model.addElement(null);
            for (Aluno a : alunos) model.addElement(a);
            cbAlunos.setModel(model);
            cbAlunos.setRenderer(new ListCellRenderer<Aluno>() {
                @Override
                public Component getListCellRendererComponent(JList<? extends Aluno> list, Aluno value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = new JLabel();
                    if (value == null) label.setText("-- selecione --");
                    else label.setText(value.getCpf() + " - " + value.getNome());
                    return label;
                }
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " + ex.getMessage());
        }
    }

    private void loadCursos() {
        try {
            CursoController cc = new CursoController();
            List<Curso> cursos = cc.findAll();
            DefaultComboBoxModel<Curso> model = new DefaultComboBoxModel<>();
            model.addElement(null);
            for (Curso c : cursos) model.addElement(c);
            cbCursos.setModel(model);
            cbCursos.setRenderer(new ListCellRenderer<Curso>() {
                @Override
                public Component getListCellRendererComponent(JList<? extends Curso> list, Curso value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = new JLabel();
                    if (value == null) label.setText("-- selecione --");
                    else label.setText(value.getId() + " - " + value.getNome());
                    return label;
                }
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cursos: " + ex.getMessage());
        }
    }

    private void initComponents() {
        panel = new br.unimontes.ccet.dcc.pg1.view.panels.PanelPG1();
        jlAluno = new javax.swing.JLabel();
        jlCurso = new javax.swing.JLabel();
        cbAlunos = new javax.swing.JComboBox<>();
        cbCursos = new javax.swing.JComboBox<>();
        jbVincular = new javax.swing.JButton();
        jbLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vincular Aluno a Curso");
        setResizable(false);

        jlAluno.setText("Aluno");
        jlCurso.setText("Curso");

        jbVincular.setText("Vincular");
        jbVincular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbVincularActionPerformed(evt);
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
                        .addComponent(jlAluno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbAlunos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jlCurso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jbVincular)
                        .addGap(10, 10, 10)
                        .addComponent(jbLimpar)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlAluno)
                    .addComponent(cbAlunos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCurso)
                    .addComponent(cbCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbVincular)
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

    private void jbVincularActionPerformed(java.awt.event.ActionEvent evt) {
        Aluno selecionado = (Aluno) cbAlunos.getSelectedItem();
        Curso selecionadoCurso = (Curso) cbCursos.getSelectedItem();
        if (selecionado == null || selecionadoCurso == null) {
            JOptionPane.showMessageDialog(this, "Selecione aluno e curso antes de vincular.");
            return;
        }

        try {
            MatriculaController controller = new MatriculaController();
            int res = controller.criar(selecionado.getCpf(), selecionadoCurso.getId());
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Matrícula realizada com sucesso!");
                loadAlunos();
                loadCursos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao realizar matrícula!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar matrícula: " + ex.getMessage());
        }
    }

    private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {
        cbAlunos.setSelectedIndex(0);
        cbCursos.setSelectedIndex(0);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TelaVincularAlunoCurso().setVisible(true));
    }

    private javax.swing.JButton jbLimpar;
    private javax.swing.JButton jbVincular;
    private javax.swing.JComboBox<br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno> cbAlunos;
    private javax.swing.JComboBox<br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso> cbCursos;
    private javax.swing.JLabel jlAluno;
    private javax.swing.JLabel jlCurso;
    private br.unimontes.ccet.dcc.pg1.view.panels.PanelPG1 panel;
}
