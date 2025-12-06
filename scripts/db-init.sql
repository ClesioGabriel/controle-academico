-- DB initialization script para Sistema Acadêmico
-- Cria o banco de dados e todas as tabelas necessárias

CREATE DATABASE IF NOT EXISTS controle_academico CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE controle_academico;

-- table alunos
CREATE TABLE IF NOT EXISTS alunos (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_nascimento VARCHAR(10) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- table curso
CREATE TABLE IF NOT EXISTS curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    observacoes TEXT,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- tabela de relacionamento aluno <-> curso
CREATE TABLE IF NOT EXISTS aluno_cursos (
    cpf_aluno VARCHAR(14) NOT NULL,
    id_curso INT NOT NULL,
    data_matricula DATE NOT NULL DEFAULT (CURRENT_DATE),
    PRIMARY KEY (cpf_aluno, id_curso),
    FOREIGN KEY (cpf_aluno) REFERENCES alunos(cpf) ON DELETE CASCADE,
    FOREIGN KEY (id_curso) REFERENCES curso(id) ON DELETE CASCADE
);

