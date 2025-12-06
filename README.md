# Sistema Acadêmico (Controle de Alunos e Cursos)

Este projeto é uma aplicação desktop Java Swing para gerenciar alunos, cursos e matrículas.

## Passos rápidos

1. Copie `config.properties.example` para `config.properties` na raiz do projeto e ajuste `db.url`, `db.user`, `db.password` conforme seu MySQL.
2. No DBeaver (ou terminal), execute `scripts/db-init.sql` para criar as tabelas necessárias.
3. Abra o projeto no NetBeans ou VS Code e rode a `TelaPrincipal`.

## Funcionalidades adicionadas

- Scripts SQL para criar as tabelas (`scripts/db-init.sql`).
- `config.properties.example` para facilitar configuração do banco.
- `Theme` para estilo consistente das telas.
- `Utils` para formatação/validação de CPF e datas.
- Melhoria nas telas: `DialogoNovoAluno`, `DialogoEditarAluno`, `DialogoNovoCurso`, `DialogoEditarCurso` com campos adicionais e validações.
- `TelaPesquisa` e gerenciadores (`GerenciadorAlunos`, `GerenciadorCursos`, `GerenciadorMatriculas`) para operações CRUD.

## Próximos passos recomendados

- Implementar `AlunoController`/`CursoController` para separação completa de responsabilidades.
- Adicionar testes unitários e de integração para DAOs e utilitários.
- Implementar logging e migração formal do banco (Flyway/Liquibase) para facilitar deploys.

Se quiser, eu posso preparar um README mais detalhado com exemplos de execução e instruções de deploy.