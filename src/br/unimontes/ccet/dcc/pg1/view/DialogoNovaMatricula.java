package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.CursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.controller.MatriculaController;

import javax.swing.*;
import br.unimontes.ccet.dcc.pg1.view.ui.Theme;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DialogoNovaMatricula extends JDialog {

    private JList<String> listAlunos;
    private DefaultListModel<String> modelAlunos;
    private JList<String> listCursos;
    private DefaultListModel<String> modelCursos;

    public DialogoNovaMatricula(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadAlunos();
        loadCursos();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Nova Matrícula");
        setSize(760, 360);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        // instruction
        JLabel instr = new JLabel("Pesquise: à esquerda — Aluno (por CPF ou nome). à direita — Curso (por nome). Selecionar e clicar em Salvar.");
        instr.setFont(Theme.body(12));
        instr.setForeground(Theme.TEXT_SECONDARY);
        instr.setBorder(BorderFactory.createEmptyBorder(6,6,10,6));
        root.add(instr, BorderLayout.NORTH);

        // Split pane: alunos | cursos
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.5);
        split.setDividerSize(6);

        // Alunos panel
        JPanel pAlunos = new JPanel(new BorderLayout(6,6));
        pAlunos.setBorder(BorderFactory.createEmptyBorder(0,0,0,6));
        JPanel topA = new JPanel(new BorderLayout(6,6));
        JLabel lbA = new JLabel("Buscar Aluno (CPF/nome):"); lbA.setFont(Theme.body(13));
        JTextField tfBuscaAluno = new JTextField(); tfBuscaAluno.setColumns(20); tfBuscaAluno.setFont(Theme.body(13));
        topA.add(lbA, BorderLayout.WEST); topA.add(tfBuscaAluno, BorderLayout.CENTER);
        pAlunos.add(topA, BorderLayout.NORTH);

        modelAlunos = new DefaultListModel<>();
        listAlunos = new JList<>(modelAlunos);
        listAlunos.setVisibleRowCount(6);
        listAlunos.setFont(Theme.body(13));
        JScrollPane scA = new JScrollPane(listAlunos);
        scA.setPreferredSize(new Dimension(340, 220));
        scA.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pAlunos.add(scA, BorderLayout.CENTER);

        // Cursos panel
        JPanel pCursos = new JPanel(new BorderLayout(6,6));
        pCursos.setBorder(BorderFactory.createEmptyBorder(0,6,0,0));
        JPanel topC = new JPanel(new BorderLayout(6,6));
        JLabel lbC = new JLabel("Buscar Curso (nome):"); lbC.setFont(Theme.body(13));
        JTextField tfBuscaCurso = new JTextField(); tfBuscaCurso.setColumns(20); tfBuscaCurso.setFont(Theme.body(13));
        topC.add(lbC, BorderLayout.WEST); topC.add(tfBuscaCurso, BorderLayout.CENTER);
        pCursos.add(topC, BorderLayout.NORTH);

        modelCursos = new DefaultListModel<>();
        listCursos = new JList<>(modelCursos);
        listCursos.setVisibleRowCount(6);
        listCursos.setFont(Theme.body(13));
        JScrollPane scC = new JScrollPane(listCursos);
        scC.setPreferredSize(new Dimension(340, 220));
        scC.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pCursos.add(scC, BorderLayout.CENTER);

        split.setLeftComponent(pAlunos);
        split.setRightComponent(pCursos);

        root.add(split, BorderLayout.CENTER);

        // buttons
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.GAP_SM, Theme.GAP_SM));
        painelBotoes.setOpaque(false);
        JButton btnSalvar = new JButton("Salvar"); Theme.stylePrimaryButton(btnSalvar); btnSalvar.addActionListener(evt -> salvar()); painelBotoes.add(btnSalvar);
        JButton btnCancelar = new JButton("Cancelar"); Theme.styleSecondaryButton(btnCancelar); btnCancelar.addActionListener(evt -> dispose()); painelBotoes.add(btnCancelar);
        root.add(painelBotoes, BorderLayout.SOUTH);

        // attach live filters
        attachFilters(tfBuscaAluno, tfBuscaCurso);

        add(root);
    }

    // filter helpers: attach to textfields after component creation
    private void attachFilters(JTextField tfAluno, JTextField tfCurso) {
        tfAluno.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterAlunos(tfAluno.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterAlunos(tfAluno.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterAlunos(tfAluno.getText()); }
        });

        tfCurso.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterCursos(tfCurso.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterCursos(tfCurso.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterCursos(tfCurso.getText()); }
        });
    }

    private void filterAlunos(String q) {
        try {
            AlunoDao dao = new AlunoDao();
            List<Aluno> alunos = dao.findAll();
            modelAlunos.clear();
            String ql = q.toLowerCase();
            for (Aluno a : alunos) {
                String cleanCpf = br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.validaCPF(a.getCpf());
                if (cleanCpf == null) continue; // skip invalid cpf entries
                String item = br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.formatCPF(cleanCpf) + " - " + a.getNome();
                if (q.isEmpty() || item.toLowerCase().contains(ql)) modelAlunos.addElement(item);
            }
        } catch (SQLException | DAOException ex) {
            // ignore filter errors silently or show minimal message
        }
    }

    private void filterCursos(String q) {
        try {
            CursoDao dao = new CursoDao();
            List<Curso> cursos = dao.findAll();
            modelCursos.clear();
            String ql = q.toLowerCase();
            for (Curso c : cursos) {
                String item = c.getId() + " - " + c.getNome();
                if (q.isEmpty() || item.toLowerCase().contains(ql)) modelCursos.addElement(item);
            }
        } catch (SQLException | DAOException ex) {
        }
    }

    private void loadAlunos() {
        try {
            AlunoDao dao = new AlunoDao();
            List<Aluno> alunos = dao.findAll();
            modelAlunos.clear();
            for (Aluno a : alunos) {
                String cleanCpf = br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.validaCPF(a.getCpf());
                if (cleanCpf == null) continue;
                modelAlunos.addElement(br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.formatCPF(cleanCpf) + " - " + a.getNome());
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCursos() {
        try {
            CursoDao dao = new CursoDao();
            List<Curso> cursos = dao.findAll();
            modelCursos.clear();
            for (Curso c : cursos) {
                modelCursos.addElement(c.getId() + " - " + c.getNome());
            }
        } catch (SQLException | DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar cursos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvar() {
        // Coleta seleções (UI apenas)
        String alunoSel = listAlunos.getSelectedValue();
        String cursoSel = listCursos.getSelectedValue();
        if (alunoSel == null || cursoSel == null) {
            JOptionPane.showMessageDialog(this, "Selecione aluno e curso (use as listas)", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Extrai CPF e ID do curso do texto exibido
        String cpf = alunoSel.split(" - ")[0].trim();
        String cursoIdStr = cursoSel.split(" - ")[0].trim();

        try {
            // Parse ID curso
            int idCurso = Integer.parseInt(cursoIdStr);
            
            // Delega validação e persistência para o controller
            MatriculaController controller = new MatriculaController();
            int res = controller.criar(cpf, idCurso);
            
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Matrícula realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível criar a matrícula.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID do curso inválido.", "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Validação", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + ex.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}
