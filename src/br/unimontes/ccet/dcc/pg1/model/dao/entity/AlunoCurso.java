package br.unimontes.ccet.dcc.pg1.model.dao.entity;

/**
 * Entity: AlunoCurso (Matrícula) - dados apenas.
 */
public class AlunoCurso {
    private String cpfAluno;
    private int idCurso;
    private String dataMatricula;

    public AlunoCurso(String cpfAluno, int idCurso) {
        this.cpfAluno = cpfAluno;
        this.idCurso = idCurso;
        this.dataMatricula = null;
    }

    public AlunoCurso(String cpfAluno, int idCurso, String dataMatricula) {
        this.cpfAluno = cpfAluno;
        this.idCurso = idCurso;
        this.dataMatricula = dataMatricula;
    }

    public String getCpfAluno() {
        return cpfAluno;
    }

    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }
}
