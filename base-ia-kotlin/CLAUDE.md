# CLAUDE.md

> Os valores de porta/credenciais abaixo são um ponto de partida — ajuste-os assim que
> `application.yml` e `docker-compose.yml` existirem de verdade no repo.

## Projects & Ports

| Service  | Stack                | Port | Location                |
| -------- | --------------------- | ---- | ------------------------ |
| Backend  | Spring Boot (Kotlin)  | 8080 | `src/main/kotlin/`        |
| Database | PostgreSQL 18.4       | 5432 | `docker/`                 |

O backend escuta na porta **8080** (padrão do Spring Boot, configurável em
`src/main/resources/application.yml`). O banco roda na porta padrão do PostgreSQL
**5432** (usuário `postgres`, senha `123456`, database `base_ia_kotlin`).

## Install Dependencies

```
./gradlew build -x test    # baixa as dependências e compila (pula os testes no setup inicial)
```

## Run

```
# Backend (Spring Boot na :8080)
./gradlew bootRun
```
> O backend precisa do banco rodando (ver abaixo) antes de conseguir atender requisições.

## Database (Docker)

Um container PostgreSQL é definido em `docker-compose.yml` e roda na porta **5432**.
No primeiro start ele inicializa o schema a partir de `database/create.sql`.

```
docker compose up -d      # sobe o banco (detached)
docker compose down -v    # derruba o banco e remove o volume
```

## Tests

```
./gradlew test
```

## Coding Standards

> **MANDATÓRIO:** Sempre que QUALQUER código de aplicação for criado ou alterado neste
> projeto, a **skill `code-standards`** DEVE ser carregada — sem exceção. Isso vale para
> toda mudança de código: novos arquivos, novas funções, novos endpoints, novos
> controllers/services, correções de bug, refatorações, ou qualquer outra modificação em
> código de aplicação sob `src/main/kotlin/`.
> Antes de escrever ou editar código, carregue `.claude/skills/code-standards/SKILL.md`
> e siga o que está lá.
> Se você está prestes a mexer em código e ainda não carregou a skill, PARE e carregue-a
> primeiro.

Este projeto traz uma **skill `code-standards`** que define como o código é escrito e
estruturado. Sempre que você escrever, refatorar ou revisar código de aplicação — ou o
usuário pedir para limpar, simplificar ou revisar código quanto ao estilo — use a skill
em `.claude/skills/code-standards/`; ela é a referência para tamanho de método,
parâmetros, escopo de variáveis, tratamento de erros, comentários, números mágicos,
aninhamento e uso de `if`/`when`. Claude carrega automaticamente quando a tarefa toca em
código.