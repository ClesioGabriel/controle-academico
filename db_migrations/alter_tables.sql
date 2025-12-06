-- Migration script: add new fields to alunos and curso tables
-- Run this against the 'controle_academico' database

-- Add telefone, email, situacao to alunos
ALTER TABLE alunos
  ADD COLUMN telefone VARCHAR(50) DEFAULT NULL,
  ADD COLUMN email VARCHAR(255) DEFAULT NULL,
  ADD COLUMN situacao VARCHAR(50) DEFAULT 'ativo';

-- Update curso table: rename observacoes -> descricao (if exists), and add other fields
-- If your MySQL doesn't support RENAME COLUMN, use DROP/ADD after copying data

-- Rename column observacoes to descricao (MySQL 8+)
ALTER TABLE curso CHANGE COLUMN observacoes descricao TEXT;

-- Add other fields
ALTER TABLE curso
  ADD COLUMN carga_horaria INT DEFAULT 0,
  ADD COLUMN duracao VARCHAR(100) DEFAULT NULL,
  ADD COLUMN modalidade VARCHAR(100) DEFAULT NULL,
  ADD COLUMN turno VARCHAR(50) DEFAULT NULL;

-- Note: Review existing data and backups before running migrations.
