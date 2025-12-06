package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.controller.CursoController;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.view.ui.Theme;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class DialogoNovoCurso extends javax.swing.JDialog {

    private JTextField tfNome;
    private JTextArea taDescricao;
    private JTextField tfCargaHoraria;
    private javax.swing.JComboBox<String> cbDuracao;
    private javax.swing.JComboBox<String> cbModalidade;
    private javax.swing.JComboBox<String> cbTurno;

    public DialogoNovoCurso(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Novo Curso");
        setSize(520, 420);
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        Theme.styleCard(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lbNome = new JLabel("Nome do Curso:");
        lbNome.setFont(Theme.body(13));
        panel.add(lbNome, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tfNome = new JTextField(25);
        tfNome.setFont(Theme.body(13));
        panel.add(tfNome, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbDesc = new JLabel("Descrição:");
        lbDesc.setFont(Theme.body(13));
        panel.add(lbDesc, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        taDescricao = new JTextArea(6, 30);
        taDescricao.setLineWrap(true);
        taDescricao.setWrapStyleWord(true);
        taDescricao.setFont(Theme.body(13));
        JScrollPane scrollPane = new JScrollPane(taDescricao);
        panel.add(scrollPane, gbc);

        // Carga Horária
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbCarga = new JLabel("Carga Horária (horas):");
        lbCarga.setFont(Theme.body(13));
        panel.add(lbCarga, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tfCargaHoraria = new JTextField(10);
        tfCargaHoraria.setFont(Theme.body(13));
        panel.add(tfCargaHoraria, gbc);

        // Duração
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbDur = new JLabel("Duração:");
        lbDur.setFont(Theme.body(13));
        panel.add(lbDur, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbDuracao = new javax.swing.JComboBox<>(new String[]{"1 ano","2 anos","3 anos","4 anos","5 anos","6 anos"});
        cbDuracao.setFont(Theme.body(13));
        panel.add(cbDuracao, gbc);

        // Modalidade
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbMod = new JLabel("Modalidade:");
        lbMod.setFont(Theme.body(13));
        panel.add(lbMod, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbModalidade = new javax.swing.JComboBox<>(new String[]{"Presencial","EAD","Híbrido"});
        cbModalidade.setFont(Theme.body(13));
        panel.add(cbModalidade, gbc);

        // Turno
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbTurno = new JLabel("Turno:");
        lbTurno.setFont(Theme.body(13));
        panel.add(lbTurno, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbTurno = new javax.swing.JComboBox<>(new String[]{"Manhã","Tarde","Noite"});
        cbTurno.setFont(Theme.body(13));
        panel.add(cbTurno, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.GAP_SM, Theme.GAP_SM));
        painelBotoes.setOpaque(false);

        JButton btnSalvar = new JButton("Salvar");
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
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(12, 6, 6, 6);
        panel.add(painelBotoes, gbc);

        add(panel);
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
            int resultado = controller.criar(nome, descricao, cargaText, duracao, modalidade, turno);

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Curso cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar curso", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + ex.getMessage(), "Erro BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}

