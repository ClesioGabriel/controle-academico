-- Script de Inicialização Completa do Banco de Dados
-- Execute este script para criar/recriar o banco de dados com todas as tabelas

-- DROP DATABASE IF EXISTS controle_academico;
-- Descomente a linha acima se quiser apagar o banco anterior

CREATE DATABASE IF NOT EXISTS controle_academico CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE controle_academico;

-- Tabela de alunos
CREATE TABLE IF NOT EXISTS alunos (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_nascimento VARCHAR(10) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de cursos
CREATE TABLE IF NOT EXISTS curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    observacoes TEXT,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de relacionamento aluno <-> curso (matrícula)
CREATE TABLE IF NOT EXISTS aluno_cursos (
    cpf_aluno VARCHAR(14) NOT NULL,
    id_curso INT NOT NULL,
    data_matricula DATE NOT NULL DEFAULT (CURRENT_DATE),
    PRIMARY KEY (cpf_aluno, id_curso),
    FOREIGN KEY (cpf_aluno) REFERENCES alunos(cpf) ON DELETE CASCADE,
    FOREIGN KEY (id_curso) REFERENCES curso(id) ON DELETE CASCADE
);
-- Mensagens de sucesso
SELECT 'Database controle_academico created successfully!' AS message;
SELECT 'Tables: alunos, cursos, aluno_cursos' AS tables_created;
