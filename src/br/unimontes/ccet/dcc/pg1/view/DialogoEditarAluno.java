package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.controller.AlunoController;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.view.ui.Theme;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.awt.*;
import java.sql.SQLException;

public class DialogoEditarAluno extends javax.swing.JDialog {

    private JTextField tfCpf;
    private JTextField tfNome;
    private JTextField tfDataNascimento;
    private JTextField tfTelefone;
    private JTextField tfEmail;
    private JComboBox<String> cbSituacao;
    private JButton btnSalvar;
    private Aluno alunoOriginal;

    public DialogoEditarAluno(java.awt.Frame parent, boolean modal, Aluno aluno) {
        super(parent, modal);
        this.alunoOriginal = aluno;
        initComponents();
        preencherCampos();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Editar Aluno");
        setSize(460, 360);
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        Theme.styleCard(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        // CPF (somente leitura)
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lbCpf = new JLabel("CPF:");
        lbCpf.setFont(Theme.body(13));
        panel.add(lbCpf, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
            tfCpf = new JFormattedTextField(cpfMask);
        } catch (ParseException ex) {
            tfCpf = new JFormattedTextField();
        }
        tfCpf.setEditable(false);
        tfCpf.setFont(Theme.body(13));
        panel.add(tfCpf, gbc);

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbNome = new JLabel("Nome:");
        lbNome.setFont(Theme.body(13));
        panel.add(lbNome, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tfNome = new JTextField(20);
        tfNome.setFont(Theme.body(13));
        panel.add(tfNome, gbc);

        // Data de Nascimento
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbData = new JLabel("Data Nascimento (DD/MM/YYYY):");
        lbData.setFont(Theme.body(13));
        panel.add(lbData, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            tfDataNascimento = new JFormattedTextField(dateMask);
        } catch (ParseException ex) {
            tfDataNascimento = new JFormattedTextField();
        }
        tfDataNascimento.setFont(Theme.body(13));
        panel.add(tfDataNascimento, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbTel = new JLabel("Telefone:");
        lbTel.setFont(Theme.body(13));
        panel.add(lbTel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        try {
            MaskFormatter phoneMask = new MaskFormatter("(##) #####-####");
            phoneMask.setPlaceholderCharacter('_');
            tfTelefone = new JFormattedTextField(phoneMask);
        } catch (ParseException ex) {
            tfTelefone = new JFormattedTextField();
        }
        tfTelefone.setFont(Theme.body(13));
        panel.add(tfTelefone, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbEmail = new JLabel("Email:");
        lbEmail.setFont(Theme.body(13));
        panel.add(lbEmail, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tfEmail = new JTextField(20);
        tfEmail.setFont(Theme.body(13));
        panel.add(tfEmail, gbc);

        // Situação
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        JLabel lbSitu = new JLabel("Situação:");
        lbSitu.setFont(Theme.body(13));
        panel.add(lbSitu, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cbSituacao = new JComboBox<>(new String[]{"ativo","trancado","concluido","desligado"});
        cbSituacao.setFont(Theme.body(13));
        panel.add(cbSituacao, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.GAP_SM, Theme.GAP_SM));
        painelBotoes.setOpaque(false);

        // Save button is a field so we can toggle read-only mode
        btnSalvar = new JButton("Salvar");
        Theme.stylePrimaryButton(btnSalvar);
        btnSalvar.addActionListener(evt -> salvarAluno());
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

        public DialogoEditarAluno(java.awt.Frame parent, boolean modal, Aluno aluno, boolean readOnly) {
            super(parent, modal);
            this.alunoOriginal = aluno;
            initComponents();
            preencherCampos();
            setReadOnly(readOnly);
            setLocationRelativeTo(parent);
        }

        private void setReadOnly(boolean ro) {
            tfNome.setEditable(!ro);
            tfDataNascimento.setEditable(!ro);
            tfTelefone.setEditable(!ro);
            tfEmail.setEditable(!ro);
            cbSituacao.setEnabled(!ro);
            btnSalvar.setVisible(!ro);
        }

    private void preencherCampos() {
        tfCpf.setText(br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.formatCPF(alunoOriginal.getCpf()));
        tfNome.setText(alunoOriginal.getNome());
        tfDataNascimento.setText(br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.toDisplayDate(alunoOriginal.getDataNascimento()));
        tfTelefone.setText(alunoOriginal.getTelefone());
        tfEmail.setText(alunoOriginal.getEmail());
        if (alunoOriginal.getSituacao() != null) cbSituacao.setSelectedItem(alunoOriginal.getSituacao());
    }

    private void salvarAluno() {
        // Apenas coleta valores (UI apenas)
        String nome = tfNome.getText().trim();
        String dataBr = tfDataNascimento.getText().trim();
        String telefone = tfTelefone.getText().trim();
        String email = tfEmail.getText().trim();
        String situacao = (String) cbSituacao.getSelectedItem();

        try {
            // Delega validação e persistência para o controller
            AlunoController controller = new AlunoController();
            int resultado = controller.atualizar(alunoOriginal.getCpf(), nome, dataBr, telefone, email, situacao);

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Aluno atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar aluno", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + ex.getMessage(), "Erro BD", JOptionPane.ERROR_MESSAGE);
        }
    }
}

