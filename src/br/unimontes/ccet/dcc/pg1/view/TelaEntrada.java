package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.view.ui.Theme;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class TelaEntrada extends JFrame {

    public TelaEntrada() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Entrar - Sistema Acadêmico");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(420, 300));
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BACKGROUND);

        JLabel header = new JLabel("  Bem-vindo");
        Theme.styleHeader(header);
        root.add(header, BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        Theme.styleCard(card);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lbUser = new JLabel("Usuário:");
        lbUser.setFont(Theme.body(14));
        card.add(lbUser, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JTextField tfUser = new JTextField(18);
        tfUser.setFont(Theme.body(14));
        card.add(tfUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        JLabel lbPass = new JLabel("Senha:");
        lbPass.setFont(Theme.body(14));
        card.add(lbPass, gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JPasswordField pf = new JPasswordField(18);
        pf.setFont(Theme.body(14));
        card.add(pf, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12,8,0,8);
        JPanel buttons = new JPanel();
        JButton btnEntrar = new JButton("Entrar");
        Theme.stylePrimaryButton(btnEntrar);
        btnEntrar.addActionListener(e -> {
            // simples fluxo: abrir tela principal
            new TelaPrincipal().setVisible(true);
            dispose();
        });
        buttons.add(btnEntrar);

        JButton btnDemo = new JButton("Modo Demo");
        Theme.styleSecondaryButton(btnDemo);
        btnDemo.addActionListener(e -> {
            new TelaPrincipal().setVisible(true);
            dispose();
        });
        buttons.add(btnDemo);

        card.add(buttons, gbc);

        root.add(card, BorderLayout.CENTER);
        setContentPane(root);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaEntrada().setVisible(true));
    }
}
