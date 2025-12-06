package br.unimontes.ccet.dcc.pg1.view;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.CursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Aluno;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.Curso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import br.unimontes.ccet.dcc.pg1.view.ui.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TelaPesquisa extends JFrame {

    private DefaultTableModel alunosModel;
    private DefaultTableModel cursosModel;
    private JTable tabelaAlunos;
    private JTable tabelaCursos;

    public TelaPesquisa() {
        initComponents();
        setLocationRelativeTo(null);
        carregarDados();
    }

    private void initComponents() {
        setTitle("Pesquisar - Sistema Acadêmico");
        setSize(1000, 600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BACKGROUND);

        JLabel header = new JLabel("  Pesquisa");
        Theme.styleHeader(header);
        root.add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // Alunos tab
        JPanel pAlunos = new JPanel(new BorderLayout());
        JPanel topA = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.GAP_SM, Theme.GAP_SM));
        JTextField tfBuscaAluno = new JTextField(30);
        tfBuscaAluno.setFont(Theme.body(14));
        JButton btnBuscarAluno = new JButton("Buscar");
        Theme.styleSecondaryButton(btnBuscarAluno);
        topA.add(new JLabel("Buscar Aluno:"));
        topA.add(tfBuscaAluno);
        topA.add(btnBuscarAluno);
        pAlunos.add(topA, BorderLayout.NORTH);

        alunosModel = new DefaultTableModel(new String[]{"CPF","Nome","Data Nascimento"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tabelaAlunos = new JTable(alunosModel);
        Theme.styleTable(tabelaAlunos);
        tabelaAlunos.getColumnModel().getColumn(0).setPreferredWidth(140);
        tabelaAlunos.getColumnModel().getColumn(1).setPreferredWidth(420);
        tabelaAlunos.getColumnModel().getColumn(2).setPreferredWidth(140);
        pAlunos.add(new JScrollPane(tabelaAlunos), BorderLayout.CENTER);

        JPanel aActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.GAP_SM, Theme.GAP_SM));
        JButton btnVisualizarAluno = new JButton("Visualizar"); Theme.styleSecondaryButton(btnVisualizarAluno); aActions.add(btnVisualizarAluno);
        JButton btnEditarAluno = new JButton("Editar"); Theme.styleSecondaryButton(btnEditarAluno); aActions.add(btnEditarAluno);
        JButton btnDelAluno = new JButton("Deletar"); Theme.styleSecondaryButton(btnDelAluno); aActions.add(btnDelAluno);
        pAlunos.add(aActions, BorderLayout.SOUTH);

        // Cursos tab
        JPanel pCursos = new JPanel(new BorderLayout());
        JPanel topC = new JPanel(new FlowLayout(FlowLayout.LEFT, Theme.GAP_SM, Theme.GAP_SM));
        JTextField tfBuscaCurso = new JTextField(30);
        tfBuscaCurso.setFont(Theme.body(14));
        JButton btnBuscarCurso = new JButton("Buscar"); Theme.styleSecondaryButton(btnBuscarCurso);
        topC.add(new JLabel("Buscar Curso:")); topC.add(tfBuscaCurso); topC.add(btnBuscarCurso);
        pCursos.add(topC, BorderLayout.NORTH);

        cursosModel = new DefaultTableModel(new String[]{"ID","Nome","Descrição","Carga (h)","Duração","Modalidade","Turno"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tabelaCursos = new JTable(cursosModel);
        Theme.styleTable(tabelaCursos);
        tabelaCursos.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabelaCursos.getColumnModel().getColumn(1).setPreferredWidth(380);
        tabelaCursos.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabelaCursos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabelaCursos.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabelaCursos.getColumnModel().getColumn(5).setPreferredWidth(120);
        tabelaCursos.getColumnModel().getColumn(6).setPreferredWidth(80);
        pCursos.add(new JScrollPane(tabelaCursos), BorderLayout.CENTER);

        JPanel cActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.GAP_SM, Theme.GAP_SM));
        JButton btnVisualizarCurso = new JButton("Visualizar"); Theme.styleSecondaryButton(btnVisualizarCurso); cActions.add(btnVisualizarCurso);
        JButton btnEditarCurso = new JButton("Editar"); Theme.styleSecondaryButton(btnEditarCurso); cActions.add(btnEditarCurso);
        JButton btnDelCurso = new JButton("Deletar"); Theme.styleSecondaryButton(btnDelCurso); cActions.add(btnDelCurso);
        pCursos.add(cActions, BorderLayout.SOUTH);

        tabs.addTab("Alunos", pAlunos);
        tabs.addTab("Cursos", pCursos);

        root.add(tabs, BorderLayout.CENTER);
        setContentPane(root);

        // Actions
        btnBuscarAluno.addActionListener(e -> filtrarAlunos(tfBuscaAluno.getText().trim()));
        btnBuscarCurso.addActionListener(e -> filtrarCursos(tfBuscaCurso.getText().trim()));

        btnEditarAluno.addActionListener(e -> {
            int r = tabelaAlunos.getSelectedRow(); if (r<0) return; String cpf = (String)alunosModel.getValueAt(r,0);
            cpf = cpf.replaceAll("\\D","");
            try{Aluno a = new Aluno(cpf); a = new AlunoDao().findOne(a); if (a!=null) new DialogoEditarAluno(this,true,a).setVisible(true); carregarDados();}catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage());}
        });

        btnVisualizarAluno.addActionListener(e -> {
            int r = tabelaAlunos.getSelectedRow(); if (r<0) return; String cpf = (String)alunosModel.getValueAt(r,0);
            cpf = cpf.replaceAll("\\D","");
            try{Aluno a = new Aluno(cpf); a = new AlunoDao().findOne(a); if (a!=null) new DialogoEditarAluno(this,true,a,true).setVisible(true);}catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage());}
        });

        btnDelAluno.addActionListener(e -> {
            int r = tabelaAlunos.getSelectedRow(); if (r<0) return; String cpf = (String)alunosModel.getValueAt(r,0);
            cpf = cpf.replaceAll("\\D","");
            int conf = JOptionPane.showConfirmDialog(this, "Remover aluno?","Confirma",JOptionPane.YES_NO_OPTION);
            if (conf!=JOptionPane.YES_OPTION) return;
            try{int res = new AlunoDao().delete(new Aluno(cpf)); if (res>0) carregarDados();}catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage());}
        });

        btnEditarCurso.addActionListener(e -> {
            int r = tabelaCursos.getSelectedRow(); if (r<0) return; int id = (int)cursosModel.getValueAt(r,0);
            try{Curso c = new Curso(); c.setId(id); c = new CursoDao().findOne(c); if (c!=null) new DialogoEditarCurso(this,true,c).setVisible(true); carregarDados();}catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage());}
        });

        btnVisualizarCurso.addActionListener(e -> {
            int r = tabelaCursos.getSelectedRow(); if (r<0) return; int id = (int)cursosModel.getValueAt(r,0);
            try{Curso c = new Curso(); c.setId(id); c = new CursoDao().findOne(c); if (c!=null) new DialogoEditarCurso(this,true,c,true).setVisible(true);}catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage());}
        });

        btnDelCurso.addActionListener(e -> {
            int r = tabelaCursos.getSelectedRow(); if (r<0) return; int id = (int)cursosModel.getValueAt(r,0);
            int conf = JOptionPane.showConfirmDialog(this, "Remover curso?","Confirma",JOptionPane.YES_NO_OPTION);
            if (conf!=JOptionPane.YES_OPTION) return;
            try{Curso c = new Curso(); c.setId(id); int res = new CursoDao().delete(c); if (res>0) carregarDados();}catch(Exception ex){JOptionPane.showMessageDialog(this, ex.getMessage());}
        });
    }

    private void carregarDados(){
        alunosModel.setRowCount(0);
        cursosModel.setRowCount(0);
        try{
            AlunoDao ad = new AlunoDao();
            List<Aluno> la = ad.findAll();
            for(Aluno a: la) alunosModel.addRow(new Object[]{br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.formatCPF(a.getCpf()), a.getNome(), br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.toDisplayDate(a.getDataNascimento())});

            CursoDao cd = new CursoDao();
            List<Curso> lc = cd.findAll();
            for(Curso c: lc) cursosModel.addRow(new Object[]{c.getId(), c.getNome(), c.getDescricao(), c.getCargaHoraria(), c.getDuracao(), c.getModalidade(), c.getTurno()});
        }catch(SQLException|DAOException ex){ JOptionPane.showMessageDialog(this, "Erro: "+ex.getMessage()); }
    }

    private void filtrarAlunos(String q){
        try{AlunoDao ad = new AlunoDao(); List<Aluno> la = ad.findAll();
            List<Aluno> out = la.stream().filter(a->a.getCpf().contains(q)||a.getNome().toLowerCase().contains(q.toLowerCase())).collect(Collectors.toList());
            alunosModel.setRowCount(0); for(Aluno a: out) alunosModel.addRow(new Object[]{br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.formatCPF(a.getCpf()),a.getNome(),br.unimontes.ccet.dcc.pg1.model.dao.service.Utils.toDisplayDate(a.getDataNascimento())});
        }catch(Exception ex){ JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }

    private void filtrarCursos(String q){
        try{CursoDao cd = new CursoDao(); List<Curso> lc = cd.findAll();
            List<Curso> out = lc.stream().filter(c->String.valueOf(c.getId()).contains(q)||c.getNome().toLowerCase().contains(q.toLowerCase())).collect(Collectors.toList());
            cursosModel.setRowCount(0); for(Curso c: out) cursosModel.addRow(new Object[]{c.getId(),c.getNome(),c.getDescricao(), c.getCargaHoraria(), c.getDuracao(), c.getModalidade(), c.getTurno()});
        }catch(Exception ex){ JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }

    public static void main(String[] args){ java.awt.EventQueue.invokeLater(()-> new TelaPesquisa().setVisible(true)); }
}
