-- Script para criar banco e tabelas necessárias para a aplicação
-- Execute no MySQL (por exemplo, via Laragon MySQL Console)

CREATE DATABASE IF NOT EXISTS `controle_academico`
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
USE `controle_academico`;

-- Tabela alunos
CREATE TABLE IF NOT EXISTS `alunos` (
  `cpf` VARCHAR(14) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `data_nascimento` VARCHAR(10),
  PRIMARY KEY (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela curso
CREATE TABLE IF NOT EXISTS `curso` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(150) NOT NULL,
  `observacoes` TEXT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela aluno_cursos (matrículas) - data_matricula sem DEFAULT para compatibilidade
CREATE TABLE IF NOT EXISTS `aluno_cursos` (
  `cpf_aluno` VARCHAR(14) NOT NULL,
  `id_curso` INT NOT NULL,
  `data_matricula` DATE NOT NULL,
  PRIMARY KEY (`cpf_aluno`, `id_curso`),
  CONSTRAINT `fk_aluno_curso_aluno`
    FOREIGN KEY (`cpf_aluno`) REFERENCES `alunos`(`cpf`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_aluno_curso_curso`
    FOREIGN KEY (`id_curso`) REFERENCES `curso`(`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Trigger para setar data_matricula para a data atual quando não informada
DROP TRIGGER IF EXISTS trg_aluno_cursos_before_insert;
DELIMITER $$
CREATE TRIGGER trg_aluno_cursos_before_insert
BEFORE INSERT ON aluno_cursos
FOR EACH ROW
BEGIN
  IF NEW.data_matricula IS NULL OR NEW.data_matricula = '0000-00-00' THEN
    SET NEW.data_matricula = CURDATE();
  END IF;
END$$
DELIMITER ;

-- Inserção de exemplo (opcional) - descomente para testar
-- INSERT INTO alunos (cpf, nome, data_nascimento) VALUES ('000.000.000-00', 'Aluno Teste', '2000-01-01');
-- INSERT INTO curso (nome, observacoes) VALUES ('Curso Teste', 'Observações');
-- INSERT INTO aluno_cursos (cpf_aluno, id_curso) VALUES ('000.000.000-00', 1);

SELECT 'OK - script executado' AS resultado;
