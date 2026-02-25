# Task Manager — Fullstack (Spring Boot + React)

A simple task management application (React frontend + Spring Boot backend) with Docker support.

## Prerequisites
- Docker & Docker Compose
- (Optional) Java 17 & Node.js 18+ if running locally without Docker

## Quick Start

Clone the repo:

```bash
git clone https://github.com/00kariim/spring-react_tskmnger.git
cd spring-react_tskmnger
```

Run with Docker Compose (recommended):

```bash
docker-compose up --build
```

- Frontend (React served by Nginx): http://localhost:3000
- Backend (Spring Boot): http://localhost:8080
- Postgres: localhost:5432

Default login (seeded by Flyway migration):
- Email: `admin@admin.com`
- Password: `123456`

> Tip: If you rebuild the backend/container, you may need to remove the Postgres volume to re-run Flyway seed data: `docker-compose down -v` then `docker-compose up --build`.

## Tech Stack

- Frontend
  - React 19 + Vite
  - Axios for HTTP
  - Nginx to serve production build
- Backend
  - Spring Boot 3.2 (Java 17)
  - Spring Data JPA / Hibernate
  - Spring Security (JWT)
  - Flyway migrations
  - PostgreSQL
- Dev / Ops
  - Docker & Docker Compose
  - Maven for build & tests

## Where things live

- frontend/ — React app (vite). Dockerfile + nginx.conf are here.
  - Source: `frontend/src`
  - Build output used by Docker: `dist` (Vite default)
- spring/ — Spring Boot backend (Maven project)
  - Application properties: `spring/src/main/resources/application.properties`
  - Dockerfile (multi-stage) sits at `spring/Dockerfile`
- docker-compose.yml — orchestrates `postgres`, `backend`, `frontend`

## API overview

- POST /api/auth/login — authenticate, returns JWT token
- Projects: `/api/projects` (list/create) and `/api/projects/{id}`
- Project tasks: `/api/projects/{id}/tasks` (list/create/complete/delete)
- Search tasks: `/api/projects/{id}/tasks/search?q=...&completed=true`

The full OpenAPI/Swagger UI is available at http://localhost:8080/swagger-ui/index.html when backend is running.

## Running the backend locally (without Docker)

1. Ensure you have JDK 17 and Maven installed.
2. Configure Postgres credentials in `spring/src/main/resources/application.properties` or ideally env vars.


```bash
mvn -f spring spring-boot:run
```

## Running frontend locally (dev)

```bash
cd frontend
npm install
npm run dev
# open http://localhost:5173 (
```



## Unit tests (JUnit + Mockito)

Tests are located under `spring/src/test/java`:

- `spring/src/test/java/com/example/taskmanager/auth` — authentication tests
- `spring/src/test/java/com/example/taskmanager/project` — project tests
- `spring/src/test/java/com/example/taskmanager/task` — task tests

Run backend tests with Maven:

```bash
mvn -f spring test
```




