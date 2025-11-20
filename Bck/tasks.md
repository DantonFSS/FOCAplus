# FOCA+ — Plano de Tarefas MVP (Frontend React Native)

Este documento contém tarefas extremamente pequenas, testáveis e independentes, organizadas em fases.
Cada tarefa possui:

* Início e fim claros
* Foco em apenas uma coisa
* Pode ser entregue individualmente

---

# 🧱 FASE 0 — Preparação do Projeto (Setup)

### **0.1 — Criar projeto React Native**

Criar app com Expo ou RN CLI.
**Resultado esperado:** App inicia sem erros.

### **0.2 — Criar estrutura de pastas**

Criar diretórios `src/api`, `src/components`, `src/screens`, `src/contexts`, etc.
**Resultado esperado:** Estrutura criada e app inicia normalmente.

### **0.3 — Instalar dependências básicas**

Instalar axios, navigation, async-storage, react-hook-form, etc.
**Resultado esperado:** Dependências instaladas e app compila.

### **0.4 — Criar tema global**

Criar arquivo com cores, tipografia e espaçamentos usados no app.
**Resultado esperado:** Tema exportável sem erros.

### **0.5 — Criar componente `Button`**

Botão estilizado padrão do projeto.
**Resultado esperado:** Renderiza e dispara onPress.

### **0.6 — Criar componente `InputText`**

Input com label e borda.
**Resultado esperado:** Digitação funciona com validação externa.

---

# 🔐 FASE 1 — Autenticação

## Tela de Login

### **1.1 — Criar UI da tela de Login**

Montar interface conforme mockup.
**Resultado esperado:** Layout fiel.

### **1.2 — Implementar formulário com react-hook-form**

Validação de email e senha.
**Resultado esperado:** Erros aparecem corretamente.

### **1.3 — Criar função API `login()`**

Estrutura inicial da chamada.
**Resultado esperado:** Função retorna sucesso/erro.

### **1.4 — Integrar formulário com login real**

Enviar dados e tratar erros.
**Resultado esperado:** Login válido retorna tokens.

### **1.5 — Criar AuthContext**

Gerenciar tokens e estado de autenticação.
**Resultado esperado:** Estado atualiza após login.

### **1.6 — Redirecionar para Home após login**

Navegação automática após sucesso.
**Resultado esperado:** Usuário autenticado vai para Home.

## Tela de Cadastro

### **1.7 — Criar UI da tela de Cadastro**

Layout conforme mockup.
**Resultado esperado:** Tela idêntica.

### **1.8 — Implementar validação do formulário de cadastro**

Validação nome/email/senha.
**Resultado esperado:** Campos inválidos mostram erro.

### **1.9 — Criar função API `register()`**

Chamada inicial ao endpoint.
**Resultado esperado:** Cadastro retorna dados.

### **1.10 — Integrar cadastro real**

Salvar tokens e redirecionar.
**Resultado esperado:** Cadastro → Home.

---

# 🏠 FASE 2 — Home Inicial

### **2.1 — Criar layout da Home (Começar)**

Tela estática conforme mockup.
**Resultado esperado:** Layout igual ao design.

### **2.2 — Criar Drawer Navigation**

Adicionar itens do menu lateral.
**Resultado esperado:** Drawer abre e navega.

### **2.3 — Exibir avatar e nome do usuário no Drawer**

Consumir endpoint "me".
**Resultado esperado:** Nome e foto aparecem.

### **2.4 — Listar cursos do usuário**

Chamar GET `/courses`.
**Resultado esperado:** Lista vazia mostra mensagem.

### **2.5 — Botão "Criar novo curso" navega para fluxo**

Navegar para NewCourseScreen.
**Resultado esperado:** Ação navega.

---

# 🎓 FASE 3 — Novo Curso

### **3.1 — Criar UI da tela "Novo Curso"**

Reproduzir layout.
**Resultado esperado:** Campos exibidos corretamente.

### **3.2 — Aplicar validações no formulário**

Campos obrigatórios e limites.
**Resultado esperado:** Erros detectados.

### **3.3 — Integrar criação de curso (POST)**

Chamar `/api/v1/courses`.
**Resultado esperado:** ID do curso é retornado.

### **3.4 — Navegar para Seleção de Período**

Enviar dados via navigation params.
**Resultado esperado:** Tela de seleção abre.

---

# 🧩 FASE 4 — Períodos

### **4.1 — Criar tela "Selecionar Período" (UI)**

Botões 1 a 6.
**Resultado esperado:** UI correta.

### **4.2 — Criar instância de período (POST)**

Enviar seleção ao backend.
**Resultado esperado:** Período criado.

### **4.3 — Criar tela Detalhes do Período (UI)**

Mostrar estado vazio.
**Resultado esperado:** Mensagem padrão exibida.

### **4.4 — Criar formulário para adicionar disciplinas**

Inputs dinâmicos.
**Resultado esperado:** Usuário adiciona N disciplinas.

### **4.5 — Integrar criação de disciplinas (POST)**

Enviar todas ao backend.
**Resultado esperado:** Lista populada.

---

# 📚 FASE 5 — Disciplinas

### **5.1 — Criar UI "DisciplinaInfo"**

Com docentes, horários, avaliações, tarefas.
**Resultado esperado:** Layout montado.

### **5.2 — Integrar GET disciplina**

Buscar `/discipline-instances/{id}`.
**Resultado esperado:** Dados reais carregam.

### **5.3 — Adicionar horários da disciplina**

Integrar CRUD de horários.
**Resultado esperado:** Horário salvo.

### **5.4 — Adicionar docentes**

Integrar CRUD de docentes.
**Resultado esperado:** Docente aparece na lista.

### **5.5 — Avaliações (CRUD)**

Criar, editar e listar.
**Resultado esperado:** Avaliação criada.

### **5.6 — Tarefas (CRUD)**

Criar, completar e colaborar.
**Resultado esperado:** Tarefa aparece e muda status.

---

# 🧭 FASE 6 — Sessões de Estudo

### **6.1 — Criar botão "Iniciar Estudo"**

UI simples.
**Resultado esperado:** Botão clicável.

### **6.2 — Criar tela da sessão com temporizador**

Cronômetro simples.
**Resultado esperado:** Contagem funcionando.

### **6.3 — Integrar criação de sessão de estudo (POST)**

Salvar quando finalizada.
**Resultado esperado:** Sessão aparece no backend.

---

# 📈 FASE 7 — Pontuação e Amizades

### **7.1 — Exibir XP da disciplina**

Usar `/score-records/by-discipline/{id}`.
**Resultado esperado:** XP exibido.

### **7.2 — Exibir amigos/colaboradores**

Usar `/friendships/accepted`.
**Resultado esperado:** Lista de amigos real.

---

# 🎁 FASE 8 — Refinamentos do MVP

### **8.1 — Implementar Refresh Token**

Chamar `/auth/refresh` quando necessário.
**Resultado esperado:** Sessão prolongada automaticamente.

### **8.2 — Criar loading global**

Mostrar durante chamadas.
**Resultado esperado:** Nenhuma tela fica "travada".

### **8.3 — Criar tratamento global de erros**

Erros amigáveis.
**Resultado esperado:** Mensagens padronizadas.

### **8.4 — Splash Screen com logo**

Tela inicial.
**Resultado esperado:** Logo exibe ao abrir app.

### **8.5 — Revisar navegação completa**

Fluxos funcionando.
**Resultado esperado:** MVP navegável de ponta a ponta.

---

# 🎉 FASE 9 — Entrega do MVP

O MVP está pronto quando:

* Autenticação completa funciona
* Curso, períodos e disciplinas podem ser criados
* Avaliações e tarefas funcionam
* Sessões de estudo são registradas
* Drawer funciona
* Experiência está estável e navegável

---

**Fim do documento.**
