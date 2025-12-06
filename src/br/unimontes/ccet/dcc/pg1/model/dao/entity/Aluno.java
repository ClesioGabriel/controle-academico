package br.unimontes.ccet.dcc.pg1.model.dao.entity;

/**
 * Entity: Aluno (dados apenas, sem lógica de validação).
 * Toda validação e transformação de dados é feita no Controller.
 */
public class Aluno {
    private String cpf;
    private String nome;
    private String dataNascimento;
    private String telefone;
    private String email;
    private String situacao;

    public Aluno() {
    }

    public Aluno(String cpf) {
        this.cpf = cpf;
    }

    public Aluno(String cpf, String nome, String dataNascimento, String telefone, String email, String situacao) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.situacao = situacao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
