# 🚀 Guia de Execução - Backend FOCA+

## 📋 Pré-requisitos

1. **Java 17** instalado
2. **Maven** instalado (ou usar Maven Wrapper `mvnw`)
3. **Docker** e **Docker Compose** instalados
4. **PostgreSQL** (via Docker ou local)

---

## 🗄️ Opção 1: Executar PostgreSQL via Docker (Recomendado)

### Passo 1: Verificar se o Docker está rodando
```bash
docker --version
docker-compose --version
```

### Passo 2: Executar apenas o PostgreSQL
```bash
# Na raiz do projeto
docker-compose up db -d
```

Este comando:
- ✅ Cria e inicia o container do PostgreSQL
- ✅ Configura o banco `db_foca`
- ✅ Expõe a porta `5432` (ou a porta configurada no `.env`)
- ✅ Usa as credenciais do arquivo `.env`

### Passo 3: Verificar se o PostgreSQL está rodando
```bash
# Verificar logs do container
docker logs database

# Verificar se o container está rodando
docker ps
```

Você deve ver algo como:
```
CONTAINER ID   IMAGE      COMMAND                  STATUS         PORTS
xxxxxxxxxxxx   postgres   "docker-entrypoint.s…"   Up X seconds   0.0.0.0:5432->5432/tcp
```

### Passo 4: Testar conexão com o banco (Opcional)
```bash
# Conectar ao PostgreSQL via Docker
docker exec -it database psql -U postgres -d db_foca
```

---

## 🖥️ Opção 2: Executar Backend Localmente

### Passo 1: Verificar variáveis de ambiente

Certifique-se de que o arquivo `.env` existe na raiz do projeto com:
```env
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=db_foca
DATABASE_PORT=5432
POSTGRES_URL=jdbc:postgresql://localhost:5432/db_foca
```

**Importante:** Se o PostgreSQL estiver rodando via Docker, use:
- `POSTGRES_URL=jdbc:postgresql://localhost:5432/db_foca` (para conexão local)

### Passo 2: Executar o backend

#### Opção A: Usando Maven Wrapper (Windows)
```bash
# Na raiz do projeto
.\mvnw.cmd spring-boot:run
```

#### Opção B: Usando Maven instalado
```bash
# Na raiz do projeto
mvn spring-boot:run
```

#### Opção C: Gerar JAR e executar
```bash
# Compilar o projeto
mvn clean package -DskipTests

# Executar o JAR
java -jar target/foca-0.0.1-SNAPSHOT.jar
```

### Passo 3: Verificar se o backend está rodando

Abra o navegador e acesse:
- **Swagger UI**: `http://localhost:8080/foca.html`
- **API Docs**: `http://localhost:8080/foca`
- **Health Check**: `http://localhost:8080/api/v1/users` (deve retornar algo ou erro 401)

---

## 🐳 Opção 3: Executar Tudo via Docker Compose

### Passo 1: Executar todos os serviços
```bash
# Na raiz do projeto
docker-compose up -d
```

Este comando:
- ✅ Inicia o PostgreSQL
- ✅ Constrói e inicia o backend (se Dockerfile existir)
- ✅ Constrói e inicia o frontend (se Dockerfile existir)

### Passo 2: Verificar logs
```bash
# Ver logs de todos os serviços
docker-compose logs -f

# Ver logs apenas do backend
docker-compose logs -f backend

# Ver logs apenas do banco
docker-compose logs -f db
```

### Passo 3: Parar os serviços
```bash
# Parar todos os serviços
docker-compose down

# Parar e remover volumes (⚠️ apaga dados do banco)
docker-compose down -v
```

---

## 🔧 Configuração de Desenvolvimento

### Arquivo `.env` (Raiz do Projeto)

```env
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=db_foca
DATABASE_PORT=5432
POSTGRES_URL=jdbc:postgresql://localhost:5432/db_foca
```

### Arquivo `application.yml` (Configurado)

```yaml
spring:
  application:
    name: foca
  
  datasource:
    url: ${POSTGRES_URL:jdbc:postgresql://localhost:5432/db_foca}
    username: ${POSTGRES_USER:postgres}
    password: ${POSTGRES_PASSWORD:postgres}
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

springdoc:
  swagger-ui:
    path: /foca.html
  api-docs:
    path: /foca
  packages-to-scan: com.focados.foca.modules

jwt:
  secret: ${JWT_SECRET:defaultSecretKeyForDevelopmentOnlyChangeInProduction}
  expiration: ${JWT_EXPIRATION:3600000}
  refresh:
    expiration: ${JWT_REFRESH_EXPIRATION:86400000}

server:
  port: ${WEBAPI_PORT:8080}
```

---

## 🐛 Troubleshooting

### Problema: "Cannot connect to database"

**Solução:**
1. Verifique se o PostgreSQL está rodando:
   ```bash
   docker ps | grep database
   ```

2. Verifique as credenciais no `.env`

3. Verifique a URL de conexão:
   - Docker: `jdbc:postgresql://localhost:5432/db_foca`
   - Docker Compose: `jdbc:postgresql://db:5432/db_foca`

### Problema: "Port 8080 already in use"

**Solução:**
1. Altere a porta no `application.yml`:
   ```yaml
   server:
     port: 8081
   ```

2. Ou pare o processo que está usando a porta:
   ```bash
   # Windows
   netstat -ano | findstr :8080
   taskkill /PID <PID> /F
   ```

### Problema: "Container database not found"

**Solução:**
```bash
# Criar e iniciar o container do banco
docker-compose up db -d
```

### Problema: "Maven not found"

**Solução:**
Use o Maven Wrapper:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

---

## 📝 Checklist de Execução

### Antes de executar:
- [ ] Java 17 instalado
- [ ] Docker instalado e rodando
- [ ] Arquivo `.env` configurado
- [ ] Arquivo `application.yml` atualizado

### Executar:
- [ ] PostgreSQL rodando (via Docker ou local)
- [ ] Backend compilando sem erros
- [ ] Backend rodando na porta 8080
- [ ] Swagger UI acessível em `http://localhost:8080/foca.html`

### Testar:
- [ ] Criar usuário via POST `/api/v1/users`
- [ ] Login via POST `/api/v1/auth/login`
- [ ] Acessar rotas protegidas com token

---

## 🎯 Próximos Passos

Após o backend estar rodando:
1. ✅ Acesse o Swagger UI: `http://localhost:8080/foca.html`
2. ✅ Teste a autenticação conforme o guia `TESTE_AUTENTICACAO.md`
3. ✅ Prossiga para a Fase 4 - Módulo de Cursos

---

## 📚 Comandos Úteis

```bash
# Ver logs do backend
docker-compose logs -f backend

# Restart do backend
docker-compose restart backend

# Parar tudo
docker-compose down

# Limpar e recriar containers
docker-compose down -v
docker-compose up -d

# Verificar status dos containers
docker-compose ps
```

---

**✅ Se tudo estiver configurado corretamente, o backend deve iniciar sem erros!**

