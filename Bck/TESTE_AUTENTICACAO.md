# 🧪 Guia Completo de Testes - Autenticação JWT (Postman)

## 📋 Pré-requisitos

1. **Postman instalado** (ou qualquer cliente HTTP)
2. **Backend rodando** na porta `8080` (ou porta configurada)
3. **Banco de dados PostgreSQL** configurado e rodando

---

## 🔧 Configuração Inicial do Postman

### 1. Criar uma Collection
- Abra o Postman
- Clique em "New" → "Collection"
- Nome: `FOCA+ API - Autenticação`

### 2. Configurar Variáveis de Ambiente (Opcional mas Recomendado)
- Clique em "Environments" → "Create Environment"
- Nome: `FOCA+ Local`
- Adicione variáveis:
  - `base_url`: `http://localhost:8080`
  - `access_token`: (deixar vazio inicialmente)
  - `refresh_token`: (deixar vazio inicialmente)

---

## 📝 Passo a Passo dos Testes

### **TESTE 1: Criar Usuário (POST /api/v1/users)**

#### Configuração da Requisição:
- **Método**: `POST`
- **URL**: `http://localhost:8080/api/v1/users`
- **Headers**:
  ```
  Content-Type: application/json
  ```
- **Body** (raw JSON):
  ```json
  {
    "cpf": "12345678901",
    "name": "João Silva",
    "email": "joao@example.com",
    "phone": "11987654321",
    "password": "senha123"
  }
  ```

#### Resultado Esperado:
- **Status**: `200 OK`
- **Response**:
  ```json
  {
    "name": "João Silva",
    "email": "joao@example.com",
    "phone": "11987654321"
  }
  ```

#### ⚠️ Validações:
- ✅ **Sucesso**: Usuário criado e retorna dados sem senha
- ❌ **Erro 400**: Email já existe → "Email já está em uso"
- ❌ **Erro 400**: Campos inválidos → Mensagens de validação

---

### **TESTE 2: Login (POST /api/v1/auth/login)**

#### Configuração da Requisição:
- **Método**: `POST`
- **URL**: `http://localhost:8080/api/v1/auth/login`
- **Headers**:
  ```
  Content-Type: application/json
  ```
- **Body** (raw JSON):
  ```json
  {
    "email": "joao@example.com",
    "password": "senha123"
  }
  ```

#### Resultado Esperado:
- **Status**: `200 OK`
- **Response**:
  ```json
  {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
  }
  ```

#### ⚠️ Validações:
- ✅ **Sucesso**: Retorna `accessToken` e `refreshToken`
- ❌ **Erro 400**: Email ou senha inválidos → "Email ou senha inválidos"
- ❌ **Erro 400**: Campos vazios → Mensagens de validação

#### 💾 **Salvar Tokens** (Opcional):
- Copie o `accessToken` e salve na variável `access_token`
- Copie o `refreshToken` e salve na variável `refresh_token`

---

### **TESTE 3: Acessar Rota Protegida (GET /api/v1/users)**

#### Configuração da Requisição:
- **Método**: `GET`
- **URL**: `http://localhost:8080/api/v1/users`
- **Headers**:
  ```
  Content-Type: application/json
  Authorization: Bearer {{access_token}}
  ```
  *(Substitua `{{access_token}}` pelo token real obtido no login)*

#### Resultado Esperado:
- **Status**: `200 OK` (se token válido)
- **Response**: Lista de usuários ou dados esperados

#### ⚠️ Validações:
- ✅ **Sucesso**: Retorna dados autenticados
- ❌ **Erro 401**: Sem token → "Unauthorized"
- ❌ **Erro 401**: Token inválido → "Unauthorized"
- ❌ **Erro 401**: Token expirado → "Unauthorized"

---

### **TESTE 4: Refresh Token (POST /api/v1/auth/refresh)**

#### Configuração da Requisição:
- **Método**: `POST`
- **URL**: `http://localhost:8080/api/v1/auth/refresh`
- **Headers**:
  ```
  Content-Type: application/json
  ```
- **Body** (raw JSON):
  ```json
  {
    "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
  }
  ```
  *(Use o refreshToken obtido no login)*

#### Resultado Esperado:
- **Status**: `200 OK`
- **Response**:
  ```json
  {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "660e8400-e29b-41d4-a716-446655440001"
  }
  ```

#### ⚠️ Validações:
- ✅ **Sucesso**: Retorna novos tokens (access e refresh)
- ❌ **Erro 400**: Refresh token inválido → "Refresh token inválido ou expirado"
- ❌ **Erro 400**: Refresh token expirado → "Refresh token inválido ou expirado"
- ❌ **Erro 400**: Refresh token revogado → "Refresh token inválido ou expirado"

#### 🔄 **Importante**:
- O refresh token antigo é **revogado** após o uso
- Use o **novo refresh token** para próximas renovações

---

### **TESTE 5: Tentar Acessar Rota Protegida SEM Token**

#### Configuração da Requisição:
- **Método**: `GET`
- **URL**: `http://localhost:8080/api/v1/users`
- **Headers**:
  ```
  Content-Type: application/json
  ```
  *(Sem header Authorization)*

#### Resultado Esperado:
- **Status**: `401 Unauthorized`

#### ⚠️ Validação:
- ❌ **Deve falhar**: Rota protegida requer autenticação

---

### **TESTE 6: Tentar Acessar Rota Protegida com Token Inválido**

#### Configuração da Requisição:
- **Método**: `GET`
- **URL**: `http://localhost:8080/api/v1/users`
- **Headers**:
  ```
  Content-Type: application/json
  Authorization: Bearer token_invalido_123
  ```

#### Resultado Esperado:
- **Status**: `401 Unauthorized`

#### ⚠️ Validação:
- ❌ **Deve falhar**: Token inválido não deve passar na validação

---

### **TESTE 7: Verificar Rotas Públicas**

#### 7.1 - Login (já testado)
- ✅ Deve funcionar sem autenticação

#### 7.2 - Criar Usuário (já testado)
- ✅ Deve funcionar sem autenticação

#### 7.3 - Swagger UI
- **URL**: `http://localhost:8080/swagger-ui.html`
- ✅ Deve abrir a documentação sem autenticação

---

## 🔍 Checklist de Validação

### ✅ Funcionalidades que DEVEM funcionar:
1. ✅ Criar usuário sem autenticação
2. ✅ Login retorna tokens válidos
3. ✅ Acessar rotas protegidas com token válido
4. ✅ Refresh token gera novos tokens
5. ✅ Refresh token revoga o token antigo
6. ✅ Rotas públicas funcionam sem token

### ❌ Funcionalidades que NÃO DEVEM funcionar:
1. ❌ Acessar rotas protegidas sem token → 401
2. ❌ Acessar rotas protegidas com token inválido → 401
3. ❌ Usar refresh token expirado → 400
4. ❌ Usar refresh token revogado → 400
5. ❌ Criar usuário com email duplicado → 400

---

## 🐛 Troubleshooting

### Problema: "Email ou senha inválidos" no login
- ✅ Verifique se o usuário foi criado corretamente
- ✅ Confirme que a senha está correta
- ✅ Verifique se o email está correto

### Problema: "401 Unauthorized" em rotas protegidas
- ✅ Verifique se o header `Authorization: Bearer <token>` está presente
- ✅ Confirme que o token está completo (não cortado)
- ✅ Verifique se o token não expirou (padrão: 1 hora)
- ✅ Tente fazer login novamente para obter um novo token

### Problema: "Refresh token inválido ou expirado"
- ✅ Verifique se está usando o refresh token mais recente
- ✅ Confirme que o token não expirou (padrão: 24 horas)
- ✅ Faça login novamente para obter novos tokens

### Problema: Backend não está respondendo
- ✅ Verifique se o servidor está rodando na porta 8080
- ✅ Confirme que o banco de dados PostgreSQL está conectado
- ✅ Verifique os logs do backend para erros

---

## 📊 Exemplo de Fluxo Completo

### 1. **Criar Usuário**
```http
POST http://localhost:8080/api/v1/users
Content-Type: application/json

{
  "cpf": "12345678901",
  "name": "João Silva",
  "email": "joao@example.com",
  "phone": "11987654321",
  "password": "senha123"
}
```

### 2. **Login**
```http
POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "email": "joao@example.com",
  "password": "senha123"
}
```

**Resposta:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NTBlODQwMC1lMjliLTQxZDQtYTcxNi00NDY2NTU0NDAwMDAiLCJlbWFpbCI6ImpvYW9AZXhhbXBsZS5jb20iLCJpYXQiOjE3MDAwMDAwMDAsImV4cCI6MTcwMDAzNjAwMH0.abc123...",
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 3. **Acessar Rota Protegida**
```http
GET http://localhost:8080/api/v1/users
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NTBlODQwMC1lMjliLTQxZDQtYTcxNi00NDY2NTU0NDAwMDAiLCJlbWFpbCI6ImpvYW9AZXhhbXBsZS5jb20iLCJpYXQiOjE3MDAwMDAwMDAsImV4cCI6MTcwMDAzNjAwMH0.abc123...
```

### 4. **Refresh Token**
```http
POST http://localhost:8080/api/v1/auth/refresh
Content-Type: application/json

{
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
}
```

---

## 🎯 Próximos Passos Após Testar

Após validar que a autenticação está funcionando:
1. ✅ Prossiga para a **Fase 4 - Módulo de Cursos**
2. ✅ Teste as rotas protegidas de cursos
3. ✅ Implemente outras funcionalidades que requerem autenticação

---

## 📝 Notas Importantes

- ⏰ **Access Token**: Expira em 1 hora (padrão)
- ⏰ **Refresh Token**: Expira em 24 horas (padrão)
- 🔄 **Refresh Token**: É revogado após uso (rotação de tokens)
- 🔐 **Senhas**: São armazenadas com hash BCrypt (nunca em texto plano)
- 🛡️ **Segurança**: Rotas `/api/v1/**` requerem autenticação (exceto `/auth/**` e `/users` POST)

---

**✅ Se todos os testes passarem, a autenticação JWT está funcionando corretamente!**

