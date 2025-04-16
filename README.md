# 📋 TaskFlow API

API RESTful para gerenciamento de projetos, tarefas, usuários, autenticação e comentários.

## 🧠 Funcionalidades

- 🔐 Autenticação com JWT (Login e Registro de Usuários)
- 👥 Gerenciamento de Usuários (CRUD parcial)
- 📁 Gerenciamento de Projetos (CRUD completo)
- ✅ Gerenciamento de Tarefas vinculadas a projetos
- 💬 Comentários em Tarefas e Projetos

---

## 🚀 Tecnologias Utilizadas

- Java / Spring Boot
- JWT para autenticação
- API RESTful com OpenAPI (Swagger)
- PostgreSQL (ou outro banco relacional)
- Docker (opcional)

---

## 📌 Endpoints Principais

### 🔐 Auth
- `POST /auth/register` — Registro de novo usuário
- `POST /auth/login` — Login e geração de token JWT

### 👤 Usuários
- `GET /user/me` — Buscar usuário autenticado
- `PUT /user` — Atualizar dados do usuário autenticado
- `DELETE /user?id={uuid}` — Deletar usuário por ID
- `GET /user/all` — Listar todos os usuários (admin)

### 📁 Projetos
- `POST /projects` — Criar novo projeto
- `PUT /projects` — Atualizar projeto existente
- `DELETE /projects?id={id}` — Deletar projeto
- `GET /projects?id={id}` — Buscar projeto por ID
- `GET /projects/all` — Listar projetos do usuário autenticado

### ✅ Tarefas
- `POST /tasks` — Criar nova tarefa
- `PUT /tasks` — Atualizar tarefa
- `DELETE /tasks?taskId={id}` — Deletar tarefa
- `GET /tasks?projectId={id}` — Listar tarefas de um projeto

### 💬 Comentários
- `POST /comments` — Criar comentário
- `DELETE /comments?commentId={id}` — Deletar comentário
- `GET /comments/task?taskId={id}` — Comentários de uma tarefa
- `GET /comments/project?projectId={id}` — Comentários de um projeto

---

## 🛠️ Executando localmente

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/taskflow-api.git

# Acesse o diretório do projeto
cd taskflow-api

# Instale as dependências e rode o projeto
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## 🧪 Documentação Interativa

Acesse a documentação interativa Swagger em:

```
http://localhost:8080/swagger-ui.html
```

---

## 📄 Licença

Este projeto pertence a Rafael Pinto Mesquita.

---
