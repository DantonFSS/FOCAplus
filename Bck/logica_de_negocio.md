# 🧠 **1) Lógica Gamificada da Aplicação**

Uma plataforma educacional gamificada que funciona assim:

### 🎓 **Usuário**

Tabela: `users`

Base de identidade.

---

### 📘 **Course Template**

Tabela: `course_templates`

Um “modelo” de curso, como:

* Engenharia de Software
* Ensino Médio
* Curso Livre

Esse modelo define:

* Nome
* Instituição
* Data de início/fim
* Tipo de divisão (semestre, trimestre…)
* Quantidade de divisões
* Emails/telefones extras

Ele é **compartilhável**.

---

### 🧱 **Period Template**

Tabela: `period_templates`

Esses são **semestres, anos, períodos**, etc.

Cada curso tem:

* 6 semestres
* 8 trimestres
* 1 ano

Depende do `division_type`.

---

### 📚 **Discipline Template**

Tabela: `discipline_templates`

Disciplinas fixas (ex: Matematica I, Cálculo, História, etc.)

* Pertencem a um período
* Podem ter avaliações padrão

---

### 👨‍🏫 Teachers

Você pode adicionar professores fixos a disciplinas
(`discipline_teachers`).

---

### 🕒 Schedules (horários)

`discipline_template_schedules`

Horários padrão das aulas.

—

💡 Até aqui: tudo é **modelo**. Não é de ninguém ainda.

---

### 👥 **UserCourse**

Tabela: `user_courses`

É aqui que entra o **usuário**.

Quando ele “adota” um curso:

* Vira dono ou membro
* Pode personalizar datas
* Pode entrar via **share_code**
* Pode participar junto com amigos

Isso é um vínculo: user → curso.

---

### 🔁 **Period Instance**

Tabela: `period_instances`

Agora vira individual, por usuário.

O aluno pode:

* adiar um período
* renomear
* mudar datas

---

### 🧩 **Discipline Instance**

Tabela: `discipline_instances`

Representa **a versão pessoal** de uma disciplina.

Armazena:

* Situação (a_iniciar, em_andamento, finalizada)
* Nota final do aluno
* Datas personalizadas

Cada aluno terá sua nota individual.

---

### 📌 Tasks

Tarefas dentro da disciplina:

* Trabalhos
* Atividades
* Exercícios

Podem gerar **pontos gamificados**.

---

### 🧪 Assessments

Provas, avaliações, testes.

Também podem gerar nota e pontos.

---

### 🧠 Study Sessions

Sessões de estudo (pomodoro, resumo, etc).

Geram gamificação:

* Duração
* Pontos
* Ciclos pomodoro

Depois você faz uma **auto-avaliação** (`self_eval_answers`)
para medir qualidade.

---

### ⭐ Score Records

Histórico **gamificado** de pontuação.

Sempre que algo dá pontos, registra:

* O que deu pontos?
* Quanto?
* Quando?
* Relacionado a que disciplina?

Serve para dashboards e ranking.

---

### 🤝 Friendships

Amigos podem ver:

* Pontos
* Status
* Progresso

Isso cria competição/cooperação gamificada.

---

# ✅ **Resumo Gamificado**

O aluno:

* entra em um curso compartilhado
* tem cópias personalizadas de períodos/disciplinas
* conclui tarefas, provas, estudos
* ganha pontos (`score_records`)
* compete com amigos
* mostra progresso
* personaliza horários e metas

É basicamente um **Duolingo para qualquer curso offline**.

---

# 🧬 **2) Por que Template vs Instance?**

### Template

É **imutável** e compartilhável.
Serve para padronizar:

* Disciplinas
* Semestres
* Horários
* Professores
* Nome do curso

**Todos** que entrarem no curso compartilham esse template.

---

### Instance

É **individual** para cada usuário.

Exemplos:

* sua nota é diferente da minha
* você pode atrasar uma disciplina
* você pode personalizar horários
* você pode setar status diferentes

---

### Isso evita 2 problemas graves:

✅ Não duplicamos estrutura fixa para cada aluno
✅ Não misturamos lógica mutável com compartilhada

---

# 🎉 Faz sentido? Faz TOTAL sentido

É exatamente o padrão usado por:

* Moodle
* Canvas
* Google Classroom
* Notion templates
* RPG Online (missões compartilhadas com instâncias individuais)

---

# 🧩 **3) Estrutura da Lógica do Curso (passo a passo)**

Quando alguém cria um curso:

### (1) Cria o **Course Template**

```
POST /api/v1/courses
```

* name
* level
* division_type
* divisions_count
* institution
* dates
* address
* phones/emails

---

### (2) O sistema cria automaticamente `division_type` períodos

Exemplo:

* division_type = semestre
* divisions_count = 6

Gera:

* Semestre 1
* Semestre 2
* Semestre 3...

Você pode usar um service:
`GeneratePeriodsService`

---

### (3) Depois cria disciplina templates no período

```
POST /api/v1/periods/{id}/disciplines
```

Com:

* nome
* professor
* avaliações padrão
* horários

---

### (4) O criador vira `owner` em `user_courses`

Isso cria o vínculo.

---

### (5) Ele recebe um `share_code`

Outros usuários podem entrar.

---

### (6) Ao aceitar convite

O aluno ganha:

* period_instances
* discipline_instances
* schedule_instances (opcional)

---

### (7) O aluno começa:

✅ criar tasks
✅ marcar completed
✅ estudar
✅ fazer auto-avaliação
✅ gerar score_records

---

### (8) Ao finalizar disciplina

* atualiza status
* calcula grade
* registra pontos

---

### (9) Dashboards podem usar score_records + study_sessions

---

# 👀 Para o seu Controller

Você não precisa separar CreateCourseController, GetCoursesController, etc.

Pode ter **um**:

`CourseController`

com endpoints:

```
POST /api/v1/courses            (cria template + owner)
GET /api/v1/courses             (lista meus cursos)
GET /api/v1/courses/{id}        (detalhes)
POST /api/v1/courses/{id}/join  (entrar via código)
POST /api/v1/courses/{id}/periods
POST /api/v1/periods/{id}/disciplines
...
```

---

# 🔐 Autenticação

Não receba `userId` no header manualmente.

Faça via token:

```java
UUID userId = AuthUtil.getAuthenticatedUserId();
```