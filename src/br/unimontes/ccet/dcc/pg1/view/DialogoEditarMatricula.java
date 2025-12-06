package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.controller.MatriculaController;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;

import javax.swing.*;
import br.unimontes.ccet.dcc.pg1.view.ui.Theme;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DialogoEditarMatricula extends JDialog {

    private JLabel jlInfo;
    private String cpf;
    private int idCurso;

    public DialogoEditarMatricula(Frame parent, boolean modal, String cpf, int idCurso) {
        super(parent, modal);
        this.cpf = cpf;
        this.idCurso = idCurso;
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Atualizar Matrícula");
        setSize(360, 160);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String hoje = LocalDate.now().format(fmt);

        jlInfo = new JLabel("Ao confirmar, data da matrícula será atualizada para: " + hoje);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(jlInfo, gbc);

        JButton btnSalvar = new JButton("Confirmar"); Theme.stylePrimaryButton(btnSalvar); btnSalvar.addActionListener(evt -> atualizar());

        JButton btnCancelar = new JButton("Cancelar"); Theme.styleSecondaryButton(btnCancelar); btnCancelar.addActionListener(evt -> dispose());

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.GAP_SM, Theme.GAP_SM));
        botoes.setOpaque(false);
        botoes.add(btnSalvar); botoes.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.SOUTHEAST; gbc.fill = GridBagConstraints.NONE; gbc.weighty = 0.0;
        panel.add(botoes, gbc);

        add(panel);
    }

    private void atualizar() {
        try {
            // Delega persistência para o controller
            MatriculaController controller = new MatriculaController();
            int res = controller.atualizar(cpf, idCurso);
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Data atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma alteração realizada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar matrícula: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
