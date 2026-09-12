# Project instructions

## Overview

- `Base Ia Kotlin` is a Kotlin / Spring Boot 4.1 application targeting Java 21.
- The build uses Gradle; always use the checked-in `./gradlew` wrapper.
- Persistence uses Spring Data JPA with Hibernate and PostgreSQL.
- OpenAPI documentation is generated automatically for the REST API.
- Docker Compose supplies local development services and is invoked when the application starts.

## Project structure

- Production sources live in `src/main/kotlin` and tests in `src/test/kotlin`.
- Code is organized by domain/feature.
- Application configuration lives in `src/main/resources/application.yml`.

## Development commands

Run from the repository root:

```shell
./gradlew bootRun
./gradlew test
./gradlew build
```

- Docker must be available for the Testcontainers-based test suite.

## Working conventions

- Follow adjacent Kotlin style and null-safety conventions.
- Prefer integration tests; use unit tests for focused coverage.
- Run the narrowest meaningful verification while iterating.
- Keep changes task-scoped and preserve unrelated existing changes. When a required file is already modified, integrate with those changes.
- Never commit production credentials. Keep secrets in environment variables or local, unversioned overrides.
- Add concise, verifiable repository-wide guidance discovered during a task to `AGENTS.md`; omit task details and duplicate documentation.
