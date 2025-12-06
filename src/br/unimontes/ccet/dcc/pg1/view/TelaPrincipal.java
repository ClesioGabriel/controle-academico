package br.unimontes.ccet.dcc.pg1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TelaPrincipal extends javax.swing.JFrame {

    private JButton btnAlunos;
    private JButton btnCursos;
    private JButton btnMatriculas;
    private JButton btnSair;
    private JLabel statusBar;

    public TelaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Acadêmico");
        setPreferredSize(new Dimension(780, 420));

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(248, 250, 252));

        // Header
        JLabel header = new JLabel("  Sistema Acadêmico");
        header.setOpaque(true);
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        root.add(header, BorderLayout.NORTH);

        // Center: big buttons
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        btnAlunos = makeMainButton("Gerenciador de Alunos", new Color(255, 255, 255), new Color(52, 73, 94));
        btnAlunos.addActionListener((ActionEvent e) -> new GerenciadorAlunos().setVisible(true));
        btnAlunos.setToolTipText("Abrir gerenciador de alunos");
        gbc.gridy = 0;
        center.add(btnAlunos, gbc);

        btnCursos = makeMainButton("Gerenciador de Cursos", new Color(255, 255, 255), new Color(52, 73, 94));
        btnCursos.addActionListener((ActionEvent e) -> new GerenciadorCursos().setVisible(true));
        btnCursos.setToolTipText("Abrir gerenciador de cursos");
        gbc.gridy = 1;
        center.add(btnCursos, gbc);

        btnMatriculas = makeMainButton("Gerenciador de Matrículas", new Color(255, 255, 255), new Color(52, 73, 94));
        btnMatriculas.addActionListener((ActionEvent e) -> new GerenciadorMatriculas().setVisible(true));
        btnMatriculas.setToolTipText("Abrir gerenciador de matrículas");
        gbc.gridy = 2;
        center.add(btnMatriculas, gbc);

        gbc.gridy = 3;
        // espaço reservado (layout equilibrado)
        center.add(new JPanel(), gbc);

        root.add(center, BorderLayout.CENTER);

        // Footer / status bar
        statusBar = new JLabel();
        statusBar.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        statusBar.setBackground(new Color(236, 240, 241));
        statusBar.setOpaque(true);
        updateStatusTime();
        // Timer para atualizar a cada 1s
        Timer timer = new Timer(1000, e -> updateStatusTime());
        timer.start();

        // Right panel with exit button
        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);
        btnSair = new JButton("Sair");
        btnSair.setBackground(new Color(231, 76, 60));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setPreferredSize(new Dimension(100, 40));
        btnSair.addActionListener(evt -> System.exit(0));
        right.add(btnSair, BorderLayout.NORTH);

        JPanel bottomWrapper = new JPanel(new BorderLayout());
        bottomWrapper.add(statusBar, BorderLayout.CENTER);
        bottomWrapper.add(right, BorderLayout.EAST);
        root.add(bottomWrapper, BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    private JButton makeMainButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(420, 60));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("SansSerif", Font.PLAIN, 15));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        // hover: subtile background change
        b.addMouseListener(new MouseAdapter() {
            Color orig = b.getBackground();
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(236,240,241)); }
            public void mouseExited(MouseEvent e) { b.setBackground(orig); }
        });
        return b;
    }

    private void updateStatusTime() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        statusBar.setText("Usuário: admin | " + LocalDateTime.now().format(fmt));
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
