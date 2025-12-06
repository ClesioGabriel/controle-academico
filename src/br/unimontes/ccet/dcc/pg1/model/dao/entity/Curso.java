package br.unimontes.ccet.dcc.pg1.model.dao.entity;

/**
 * Entity: Curso (dados apenas, sem lógica de validação).
 * Toda validação é feita no Controller.
 */
public class Curso {
    private int id;
    private String nome;
    private String descricao;
    private int cargaHoraria;
    private String duracao;
    private String modalidade;
    private String turno;

    public Curso() {
    }

    public Curso(int id, String nome, String descricao, int cargaHoraria, String duracao, String modalidade, String turno) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.duracao = duracao;
        this.modalidade = modalidade;
        this.turno = turno;
    }

    public Curso(String nome, String descricao, int cargaHoraria, String duracao, String modalidade, String turno) {
        this.id = 0;
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.duracao = duracao;
        this.modalidade = modalidade;
        this.turno = turno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
