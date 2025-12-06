package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.controller.CursoController;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.view.ui.Theme;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class DialogoEditarCurso extends javax.swing.JDialog {

    private JTextField tfNome;
    private JTextArea taDescricao;
    private JTextField tfCargaHoraria;
    private javax.swing.JComboBox<String> cbDuracao;
    private javax.swing.JComboBox<String> cbModalidade;
    private javax.swing.JComboBox<String> cbTurno;
    private JButton btnSalvar;
    private Curso cursoOriginal;

    public DialogoEditarCurso(java.awt.Frame parent, boolean modal, Curso curso) {
        super(parent, modal);
        this.cursoOriginal = curso;
        initComponents();
        preencherCampos();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Editar Curso");
        setSize(520, 420);
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridBagLayout());
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = java.awt.GridBagConstraints.WEST;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome do Curso:"), gbc);

        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tfNome = new JTextField(25);
        panel.add(tfNome, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        taDescricao = new JTextArea(6, 30);
        taDescricao.setLineWrap(true);
        taDescricao.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(taDescricao);
        panel.add(scrollPane, gbc);

        // Carga Horária
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Carga Horária (horas):"), gbc);

        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tfCargaHoraria = new JTextField(10);
        panel.add(tfCargaHoraria, gbc);

        // Duração
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Duração:"), gbc);

        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbDuracao = new javax.swing.JComboBox<>(new String[]{"1 ano","2 anos","3 anos","4 anos","5 anos","6 anos"});
        panel.add(cbDuracao, gbc);

        // Modalidade
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Modalidade:"), gbc);

        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbModalidade = new javax.swing.JComboBox<>(new String[]{"Presencial","EAD","Híbrido"});
        panel.add(cbModalidade, gbc);

        // Turno
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Turno:"), gbc);

        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbTurno = new javax.swing.JComboBox<>(new String[]{"Manhã","Tarde","Noite"});
        panel.add(cbTurno, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 5));
        painelBotoes.setOpaque(false);

        btnSalvar = new JButton("Salvar");
        Theme.stylePrimaryButton(btnSalvar);
        btnSalvar.addActionListener(evt -> salvarCurso());
        painelBotoes.add(btnSalvar);

        JButton btnCancelar = new JButton("Cancelar");
        Theme.styleSecondaryButton(btnCancelar);
        btnCancelar.addActionListener(evt -> dispose());
        painelBotoes.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(painelBotoes, gbc);

        add(panel);
    }

        public DialogoEditarCurso(java.awt.Frame parent, boolean modal, Curso curso, boolean readOnly) {
            super(parent, modal);
            this.cursoOriginal = curso;
            initComponents();
            preencherCampos();
            setReadOnly(readOnly);
            setLocationRelativeTo(parent);
        }

        private void setReadOnly(boolean ro) {
            tfNome.setEditable(!ro);
            taDescricao.setEditable(!ro);
            tfCargaHoraria.setEditable(!ro);
            cbDuracao.setEnabled(!ro);
            cbModalidade.setEnabled(!ro);
            cbTurno.setEnabled(!ro);
            btnSalvar.setVisible(!ro);
        }

    private void preencherCampos() {
        tfNome.setText(cursoOriginal.getNome());
        taDescricao.setText(cursoOriginal.getDescricao() != null ? cursoOriginal.getDescricao() : "");
        tfCargaHoraria.setText(String.valueOf(cursoOriginal.getCargaHoraria()));
        if (cursoOriginal.getDuracao() != null) cbDuracao.setSelectedItem(cursoOriginal.getDuracao());
        if (cursoOriginal.getModalidade() != null) cbModalidade.setSelectedItem(cursoOriginal.getModalidade());
        if (cursoOriginal.getTurno() != null) cbTurno.setSelectedItem(cursoOriginal.getTurno());
    }

    private void salvarCurso() {
        // Apenas coleta valores (UI apenas)
        String nome = tfNome.getText().trim();
        String descricao = taDescricao.getText().trim();
        String cargaText = tfCargaHoraria.getText().trim();
        String duracao = String.valueOf(cbDuracao.getSelectedItem());
        String modalidade = String.valueOf(cbModalidade.getSelectedItem());
        String turno = String.valueOf(cbTurno.getSelectedItem());

        try {
            // Delega validação e persistência para o controller
            CursoController controller = new CursoController();
            int resultado = controller.atualizar(cursoOriginal.getId(), nome, descricao, cargaText, duracao, modalidade, turno);

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Curso atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar curso", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + ex.getMessage(), "Erro BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}
