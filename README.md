# 🚛 Portal Motorista API

API REST para gerenciamento de motoristas parceiros da FreteMais, desenvolvida com Spring Boot 4.0.2 e Java 21.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Gerando Chaves RSA (OBRIGATÓRIO)](#-gerando-chaves-rsa-obrigatório)
- [Como Rodar o Projeto](#como-rodar-o-projeto)
- [Autenticação](#autenticação)
- [Endpoints da API](#endpoints-da-api)
- [Exemplos de Uso](#exemplos-de-uso)
- [Decisões Técnicas](#decisões-técnicas)
- [Estrutura do Projeto](#estrutura-do-projeto)

---

## 🎯 Sobre o Projeto

Sistema interno da FreteMais para organizar a base de motoristas parceiros, permitindo cadastro, atualização, busca avançada com filtros combináveis e garantindo acesso apenas para usuários autenticados.

### Funcionalidades Principais

- ✅ **CRUD Completo de Motoristas** (Create, Read, Update, Delete com Soft Delete)
- ✅ **Autenticação JWT** com Spring Security
- ✅ **Busca Avançada** com filtros combináveis (texto, UF, cidade, tipos de veículo)
- ✅ **Paginação e Ordenação** configuráveis
- ✅ **Documentação Swagger/OpenAPI** interativa
- ✅ **Validações** robustas com Bean Validation
- ✅ **Persistência de Array de Enums** em JSONB (PostgreSQL)
- ✅ **Migrations** com Flyway

---

## 🛠 Tecnologias Utilizadas

### Backend

- **Java 21** - Versão LTS mais recente
- **Spring Boot 4.0.2** - Framework principal
- **Spring Security** - Autenticação e autorização com JWT
- **Spring Data JPA** - Camada de persistência
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Gerenciamento de migrations
- **SpringDoc OpenAPI** - Documentação automática da API
- **Lombok** - Redução de boilerplate
- **Bean Validation** - Validações declarativas
- **Maven** - Gerenciamento de dependências

### Infraestrutura

- **Docker** - Containerização
- **HikariCP** - Pool de conexões otimizado

---

## 📦 Pré-requisitos

### Opção 1: Docker (Recomendado)

- Docker 20.10+
- Docker Compose 2.0+

### Opção 2: Local

- Java JDK 21
- PostgreSQL 14+
- Maven 3.8+

---

## 🔑 Gerando Chaves RSA (OBRIGATÓRIO)

O sistema utiliza **criptografia assimétrica RSA** para assinar tokens JWT. Você **DEVE** gerar as chaves antes de rodar o projeto, caso contrário a aplicação **NÃO INICIARÁ**.

### Linux/macOS

Execute os comandos abaixo na **raiz do projeto**:

```bash
# Gerar chave privada RSA 2048 bits
openssl genrsa -out src/main/resources/app.key 2048

# Gerar chave pública a partir da privada
openssl rsa -in src/main/resources/app.key -pubout -out src/main/resources/app.pub

# Ajustar permissões (opcional, mas recomendado)
chmod 600 src/main/resources/app.key
chmod 644 src/main/resources/app.pub
```

### Windows (PowerShell ou CMD)

Execute os comandos abaixo na **raiz do projeto**:

```cmd
REM Gerar chave privada RSA 2048 bits
openssl genrsa -out src\main\resources\app.key 2048

REM Gerar chave pública a partir da privada
openssl rsa -in src\main\resources\app.key -pubout -out src\main\resources\app.pub
```

### Verificar se as Chaves Foram Criadas

```bash
ls -la src/main/resources/app.*
```

**Saída esperada:**
```
-rw------- 1 user user 1679 Feb  4 10:00 src/main/resources/app.key
-rw-r--r-- 1 user user  451 Feb  4 10:00 src/main/resources/app.pub
```

### ⚠️ IMPORTANTE - Segurança das Chaves

**NÃO VERSIONE as chaves no Git!** Elas já devem estar no `.gitignore`:

```gitignore
# Chaves RSA (JWT)
src/main/resources/app.key
src/main/resources/app.pub
```

**Para ambientes de produção:**
- Use **gerenciamento de secrets** (AWS Secrets Manager, HashiCorp Vault, Azure Key Vault)
- **Rotacione** as chaves periodicamente
- Armazene em **variáveis de ambiente** ou **volumes seguros**

### Se Não Tiver OpenSSL Instalado

**Windows (PowerShell):**
```powershell
# Instalar via Chocolatey
choco install openssl

# Ou baixar de: https://slproweb.com/products/Win32OpenSSL.html
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt-get update
sudo apt-get install openssl
```

**macOS:**
```bash
# OpenSSL já vem pré-instalado
# Ou instalar via Homebrew:
brew install openssl
```

---

## 🚀 Como Rodar o Projeto

> **⚠️ PRÉ-REQUISITO:** Antes de continuar, você **DEVE** [gerar as chaves RSA](#-gerando-chaves-rsa-obrigatório) conforme instruções acima.

### Checklist Antes de Iniciar

Certifique-se de que os seguintes arquivos existem:

```
portal-motorista-api/
├── src/main/resources/
│   ├── app.key           ✅ Chave privada RSA (gerada por você)
│   └── app.pub           ✅ Chave pública RSA (gerada por você)
├── docker-compose.yml    ✅ Configuração Docker (já existe no projeto)
└── DockerFile            ✅ Imagem da aplicação (já existe no projeto)
```

**Se `app.key` e `app.pub` não existem, volte para [Gerando Chaves RSA](#-gerando-chaves-rsa-obrigatório).**

### Opção 1: Com Docker Compose (Recomendado)

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd portal-motorista-api
```

2. **Gere as chaves RSA** (se ainda não gerou)

Veja [Gerando Chaves RSA](#-gerando-chaves-rsa-obrigatório).

3. **(Opcional) Personalize as configurações**

O arquivo `docker-compose.yml` já existe na raiz do projeto com as configurações padrão:

```yaml
# Credenciais padrão do banco de dados
POSTGRES_DB: portal_motorista
POSTGRES_USER: admin
POSTGRES_PASSWORD: admin123
```

> **💡 Dica:** Se quiser alterar as credenciais, edite o arquivo `docker-compose.yml` antes de subir os containers.

4. **Suba os containers**

```bash
docker-compose up -d
```

5. **Acompanhe os logs**

```bash
docker-compose logs -f api
```

6. **Acesse a aplicação**

- API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/api/swagger-ui/
- Documentação OpenAPI: http://localhost:8080/api/v3/api-docs

### Opção 2: Executando Localmente

1. **Configure o PostgreSQL**

```sql
CREATE DATABASE portal_motorista;
CREATE USER admin WITH PASSWORD 'admin123';
GRANT ALL PRIVILEGES ON DATABASE portal_motorista TO admin;
```

2. **Configure as variáveis de ambiente**

```bash
export DB_URL=jdbc:postgresql://localhost:5432/portal_motorista
export DB_USERNAME=admin
export DB_PASSWORD=admin123
```

3. **Execute a aplicação**

```bash
./mvnw clean install
./mvnw spring-boot:run
```

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** com autenticação Bearer.

### Usuários de Teste (Seed Data)

Todos os usuários possuem a senha padrão: **`123456`**

#### Usuário Admin (acesso completo)

```json
{
  "email": "admin@fretemais.com",
  "senha": "123456"
}
```

#### Motoristas de Teste (acesso completo)

```json
{
  "email": "joao.silva@email.com",
  "senha": "123456"
}
```

```json
{
  "email": "maria.santos@email.com",
  "senha": "123456"
}
```

#### Usuários de Teste (apenas consulta)

```json
{
  "email": "juliana.lima@email.com",
  "senha": "123456"
}
```

```json
{
  "email": "roberto.alves@email.com",
  "senha": "123456"
}
```

> **Nota**: Existem 5 motoristas, 5 usuários e 1 admin cadastrados automaticamente via Flyway migration.

### Como Autenticar

#### 1. Obter Token JWT

**Endpoint:** `POST /api/autenticacao/login`

**Request:**
```json
{
  "email": "joao.silva@email.com",
  "senha": "123456"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "expiracaoEmMinutos": 1440
}
```

#### 2. Usar o Token nas Requisições

Adicione o header `Authorization` em todas as requisições protegidas:

```
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### 3. Usando o Swagger UI

1. Acesse: http://localhost:8080/api/swagger-ui/
2. Clique em **"Authorize"** (cadeado no topo)
3. Cole o token JWT no campo **"Value"**
4. Clique em **"Authorize"** e depois **"Close"**

Agora todos os endpoints estarão autenticados automaticamente.

---

## 📡 Endpoints da API

### Base URL: `http://localhost:8080/api`

### Autenticação

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/autenticacao/login` | Realiza login e obtém token JWT | ❌ Público |

### Motoristas

| Método | Endpoint | Descrição | Autenticação | Permissão |
|--------|----------|-----------|--------------|-----------|
| POST | `/usuarios` | Cria um novo motorista | ✅ Requerida | 🔒 MOTORISTA ou ADMIN |
| GET | `/usuarios/{id}` | Busca motorista por ID | ✅ Requerida | 👤 Qualquer autenticado |
| POST | `/usuarios/search` | Busca com filtros e paginação | ✅ Requerida | 👤 Qualquer autenticado |
| PUT | `/usuarios/{id}` | Atualiza dados do motorista | ✅ Requerida | 🔒 MOTORISTA ou ADMIN |
| DELETE | `/usuarios/{id}` | Exclui motorista (soft delete) | ✅ Requerida | 🔒 MOTORISTA ou ADMIN |

### Tipos de Veículo

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/vehicle-types` | Lista todos os tipos de veículo | ✅ Requerida |

### 🔐 Controle de Acesso

O sistema implementa **controle de acesso baseado em roles (RBAC)** utilizando `@PreAuthorize` do Spring Security.

#### Perfis de Usuário

- **👁️ USUARIO**: Pode apenas consultar motoristas (buscar por ID, listar com filtros)
- **🚛 MOTORISTA**: Tem acesso completo (criar, editar, excluir, consultar motoristas)
- **🔒 ADMIN**: Tem acesso completo (criar, editar, excluir, consultar motoristas)

#### Regras de Negócio

| Operação | USUARIO | MOTORISTA | ADMIN |
|----------|---------|-----------|-------|
| Criar motorista | ❌ Negado | ✅ Permitido | ✅ Permitido |
| Buscar motorista | ✅ Permitido | ✅ Permitido | ✅ Permitido |
| Listar/Filtrar | ✅ Permitido | ✅ Permitido | ✅ Permitido |
| Atualizar motorista | ❌ Negado | ✅ Permitido | ✅ Permitido |
| Excluir motorista | ❌ Negado | ✅ Permitido | ✅ Permitido |

#### Como Testar

**1. Login como USUARIO (apenas consulta):**
```bash
curl -X POST http://localhost:8080/api/autenticacao/login \
  -H "Content-Type: application/json" \
  -d '{"email": "juliana.lima@email.com", "senha": "123456"}'
```

**2. Tentar criar motorista como USUARIO (será NEGADO - 403 Forbidden):**
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer TOKEN_USUARIO" \
  -H "Content-Type: application/json" \
  -d '{"nome": "Teste", "email": "teste@email.com", ...}'
# Response: 403 Forbidden
```

**3. Buscar motoristas como USUARIO (será PERMITIDO - 200 OK):**
```bash
curl -X POST http://localhost:8080/api/usuarios/search \
  -H "Authorization: Bearer TOKEN_USUARIO" \
  -H "Content-Type: application/json" \
  -d '{"texto": "João"}'
# Response: 200 OK
```

**4. Login como MOTORISTA (acesso completo):**
```bash
curl -X POST http://localhost:8080/api/autenticacao/login \
  -H "Content-Type: application/json" \
  -d '{"email": "joao.silva@email.com", "senha": "123456"}'
```

**5. Criar motorista como MOTORISTA (será PERMITIDO - 201 Created):**
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer TOKEN_MOTORISTA" \
  -H "Content-Type: application/json" \
  -d '{"nome": "Teste", "email": "teste@email.com", ...}'
# Response: 201 Created
```

**6. Login como ADMIN (acesso completo):**
```bash
curl -X POST http://localhost:8080/api/autenticacao/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@fretemais.com", "senha": "123456"}'
```

**7. Criar/Atualizar/Deletar como ADMIN (será PERMITIDO):**
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer TOKEN_ADMIN" \
  -H "Content-Type: application/json" \
  -d '{"nome": "Teste Admin", "email": "teste.admin@email.com", ...}'
# Response: 201 Created
```

---

## 🧪 Exemplos de Uso

### 1. Login

```bash
curl -X POST http://localhost:8080/api/autenticacao/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao.silva@email.com",
    "senha": "123456"
  }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvLnNpbHZhQGVtYWlsLmNvbSIsImlhdCI6MTczODYxMjAwMCwiZXhwIjoxNzM4Njk4NDAwfQ...",
  "tipo": "Bearer",
  "expiracaoEmMinutos": 1440
}
```

### 2. Criar Motorista

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Ricardo Mendes",
    "email": "ricardo.mendes@email.com",
    "senha": "senha123",
    "telefone": "(11) 91234-5678",
    "cidade": "Campinas",
    "uf": "SP",
    "tiposVeiculo": ["VAN", "TRUCK"]
  }'
```

**Response:**
```json
{
  "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
  "nome": "Ricardo Mendes",
  "email": "ricardo.mendes@email.com",
  "telefone": "(11) 91234-5678",
  "cidade": "Campinas",
  "uf": "SP",
  "role": "MOTORISTA",
  "status": "ATIVO",
  "tiposVeiculo": ["VAN", "TRUCK"],
  "criadoEm": "2026-02-04T10:30:00",
  "atualizadoEm": "2026-02-04T10:30:00"
}
```

### 3. Buscar Motorista por ID

```bash
curl -X GET http://localhost:8080/api/usuarios/f47ac10b-58cc-4372-a567-0e02b2c3d479 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### 4. Listar Todos os Motoristas (com Paginação)

```bash
curl -X GET "http://localhost:8080/api/usuarios/search?page=0&size=10&sort=nome,asc" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

**Response:**
```json
{
  "content": [
    {
      "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
      "nome": "Ana Paula",
      "email": "ana.paula@email.com",
      "telefone": "(41) 95432-1098",
      "cidade": "Curitiba",
      "uf": "PR",
      "role": "MOTORISTA",
      "status": "ATIVO",
      "tiposVeiculo": ["TRUCK", "BITRUCK"],
      "criadoEm": "2026-02-04T10:00:00",
      "atualizadoEm": "2026-02-04T10:00:00"
    }
    // ... outros motoristas
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    }
  },
  "totalElements": 11,
  "totalPages": 2,
  "last": false,
  "first": true,
  "numberOfElements": 10
}
```

### 5. Buscar com Filtros Combináveis

#### Exemplo 1: Buscar por Texto (nome, email ou telefone)

```bash
curl -X GET "http://localhost:8080/api/usuarios/search?texto=João&page=0&size=10" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Exemplo 2: Buscar por UF

```bash
curl -X GET "http://localhost:8080/api/usuarios/search?uf=SP&page=0&size=10" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Exemplo 3: Buscar por Cidade

```bash
curl -X GET "http://localhost:8080/api/usuarios/search?cidade=São Paulo&page=0&size=10" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Exemplo 4: Buscar por Tipos de Veículo

```bash
curl -X GET "http://localhost:8080/api/usuarios/search?tiposVeiculo=VAN&tiposVeiculo=TRUCK&page=0&size=10" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

#### Exemplo 5: Buscar Combinando Múltiplos Filtros

```bash
curl -X GET "http://localhost:8080/api/usuarios/search?uf=SP&cidade=São Paulo&tiposVeiculo=VAN&tiposVeiculo=TRUCK&page=0&size=10&sort=nome,asc" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

**Parâmetros de Busca:**

| Parâmetro | Tipo | Obrigatório | Descrição | Exemplo |
|-----------|------|-------------|-----------|---------|
| `texto` | String | ❌ | Busca em nome, email ou telefone | `João` |
| `uf` | String | ❌ | Filtro por estado (UF) | `SP` |
| `cidade` | String | ❌ | Filtro por cidade | `São Paulo` |
| `tiposVeiculo` | Array | ❌ | Filtro por tipos de veículo (pode ser múltiplo) | `VAN`, `TRUCK` |
| `page` | Integer | ❌ | Número da página (inicia em 0) | `0` |
| `size` | Integer | ❌ | Itens por página | `10` |
| `sort` | String | ❌ | Campo e direção de ordenação | `nome,asc` |

**Valores válidos para `tiposVeiculo`:**
- `VAN`
- `TOCO`
- `BAU`
- `SIDER`
- `TRUCK`
- `BITRUCK`

### 6. Atualizar Motorista

```bash
curl -X PUT http://localhost:8080/api/usuarios/f47ac10b-58cc-4372-a567-0e02b2c3d479 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Ricardo Mendes Silva",
    "telefone": "(11) 91234-9999",
    "cidade": "São Paulo",
    "uf": "SP",
    "tiposVeiculo": ["VAN", "TRUCK", "TOCO"]
  }'
```

### 7. Excluir Motorista (Soft Delete)

```bash
curl -X DELETE http://localhost:8080/api/usuarios/f47ac10b-58cc-4372-a567-0e02b2c3d479 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

**Response:** `204 No Content`

> **Nota**: A exclusão é lógica (soft delete). O status do motorista muda para `INATIVO`, mas o registro permanece no banco.

### 8. Listar Tipos de Veículo

```bash
curl -X GET http://localhost:8080/api/vehicle-types \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

**Response:**
```json
[
  {
    "codigo": "VAN",
    "descricao": "Van"
  },
  {
    "codigo": "TOCO",
    "descricao": "Caminhão Toco"
  },
  {
    "codigo": "BAU",
    "descricao": "Caminhão Baú"
  },
  {
    "codigo": "SIDER",
    "descricao": "Caminhão Sider"
  },
  {
    "codigo": "TRUCK",
    "descricao": "Caminhão Truck"
  },
  {
    "codigo": "BITRUCK",
    "descricao": "Caminhão Bitruck"
  }
]
```

---

## 🏗 Decisões Técnicas

### 1. Por que Spring Boot 4.0.2?

- **Versão mais recente**: Aproveitamento de todas as melhorias de performance e segurança
- **Java 21**: Suporte nativo às features mais modernas do Java (Records, Pattern Matching, Virtual Threads)
- **Ecosystem maduro**: Spring Boot possui um ecossistema robusto com ampla documentação e comunidade ativa

### 2. Por que PostgreSQL com JSONB?

**Escolha do PostgreSQL:**
- Banco relacional robusto e open-source
- Excelente suporte a tipos de dados avançados (JSONB, Arrays)
- Performance superior em consultas complexas

**JSONB para Array de Enums:**
- **Flexibilidade**: Permite adicionar novos tipos de veículo sem alterar schema
- **Performance**: JSONB é indexável e otimizado para consultas
- **Simplicidade**: Um único campo armazena múltiplos valores
- **Native JSON**: Facilita integração com frontends JavaScript/React

**Alternativas consideradas:**
- ❌ **Tabela de relacionamento N:N**: Mais complexo, JOINs adicionais, overhead de queries
- ❌ **Enum Java com @ElementCollection**: Rigidez em mudanças, menos flexível

### 3. Por que JWT com RSA (Chaves Pública/Privada)?

**Vantagens:**
- **Segurança**: Chaves assimétricas são mais seguras que secrets compartilhados
- **Stateless**: Não requer armazenamento de sessão no servidor
- **Escalabilidade**: Fácil distribuição em múltiplos servidores
- **Expiração configurável**: Token expira em 24h (1440 minutos)

**Implementação:**
- Chaves RSA 2048 bits armazenadas em `app.key` (privada) e `app.pub` (pública)
- Token contém apenas identificação do usuário (email)
- Validação em cada requisição via filtro customizado (`FilterSecurity`)

**Por que RSA ao invés de HS256 (HMAC)?**
- ✅ **Mais seguro**: Chave privada nunca sai do servidor de autenticação
- ✅ **Distribuído**: Múltiplos serviços podem validar tokens com apenas a chave pública
- ✅ **Auditável**: Impossível gerar tokens sem a chave privada
- ❌ HS256: Secret compartilhado entre todos os serviços (risco de vazamento)

**⚠️ Importante:** As chaves devem ser geradas manualmente antes de rodar o projeto. Veja [Gerando Chaves RSA](#-gerando-chaves-rsa-obrigatório).

### 4. Por que Spring Data JPA Specifications ao invés de @Query?

**Vantagens das Specifications:**
- ✅ **Filtros dinâmicos**: Combinação de múltiplos critérios em tempo de execução
- ✅ **Type-safe**: Uso de Metamodel evita erros de digitação
- ✅ **Reusabilidade**: Predicates podem ser combinados e reutilizados
- ✅ **Manutenibilidade**: Código Java é mais fácil de debugar que HQL/JPQL
- ✅ **Testabilidade**: Mais simples de testar unitariamente

**Exemplo de uso:**
```java
Specification<Usuario> spec = Specification.where(null);

if (hasText(filter.texto())) {
    spec = spec.and(UsuarioSpecification.textoContains(filter.texto()));
}
if (hasText(filter.uf())) {
    spec = spec.and(UsuarioSpecification.ufEquals(filter.uf()));
}
```

**Alternativa @Query:**
```java
@Query("SELECT u FROM Usuario u WHERE " +
       "(:texto IS NULL OR u.nome LIKE %:texto% OR u.email LIKE %:texto%) AND " +
       "(:uf IS NULL OR u.uf = :uf) AND " +
       "...")
Page<Usuario> buscar(@Param("texto") String texto, @Param("uf") String uf, ...);
```
- ❌ Queries longas e difíceis de manter
- ❌ Não é type-safe
- ❌ Difícil adicionar filtros opcionais

### 5. Por que Soft Delete ao invés de Hard Delete?

**Vantagens:**
- ✅ **Auditoria**: Histórico completo de registros
- ✅ **Recuperação**: Possibilidade de restaurar dados excluídos
- ✅ **Integridade referencial**: Evita problemas com chaves estrangeiras
- ✅ **Compliance**: Atende requisitos legais de manutenção de histórico

**Implementação:**
- Campo `status` com enum `ATIVO/INATIVO`
- Filtro automático em todas as queries via Specification
- DELETE muda status para `INATIVO` ao invés de remover registro

### 6. Por que Flyway?

- ✅ **Versionamento de Schema**: Migrations rastreadas e versionadas
- ✅ **Reprodutibilidade**: Mesmo schema em todos os ambientes
- ✅ **Rollback**: Possibilidade de reverter mudanças
- ✅ **Seed Data**: Dados iniciais versionados junto com schema

### 7. Por que Separação de DTOs (Request/Response)?

**Estrutura:**
```
model/dto/
├── request/
│   ├── UsuarioRequestDto
│   └── UsuarioUpdateDto
└── response/
    └── UsuarioResponseDto
```

**Vantagens:**
- ✅ **Segurança**: Evita mass assignment vulnerabilities
- ✅ **Flexibilidade**: Request e Response podem ter campos diferentes
- ✅ **Documentação**: Swagger gera documentação mais clara
- ✅ **Validação**: Validações específicas por tipo de operação

**Exemplo:**
- `UsuarioRequestDto`: Possui campo `senha` (criação)
- `UsuarioUpdateDto`: Não possui campo `senha` (atualização)
- `UsuarioResponseDto`: Não retorna senha, inclui `id`, `criadoEm`, `atualizadoEm`

### 8. Por que Interfaces no Controller (Pattern Interface-Implementation)?

**Estrutura:**
```java
// Interface com documentação Swagger
public interface UsuarioApi {
    @Operation(summary = "Criar motorista")
    ResponseEntity<UsuarioResponseDto> criar(@Valid @RequestBody UsuarioRequestDto dto);
}

// Implementação
@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements UsuarioApi {
    @Override
    public ResponseEntity<UsuarioResponseDto> criar(UsuarioRequestDto dto) { ... }
}
```

**Vantagens:**
- ✅ **Separação de Responsabilidades**: Interface define contrato, Controller implementa lógica
- ✅ **Documentação Centralizada**: Todas as anotações Swagger em um único lugar
- ✅ **Manutenibilidade**: Controller mais limpo e focado em lógica
- ✅ **Testabilidade**: Facilita mock de contratos

### 9. Por que `@PageableDefault` ao invés de parâmetros manuais?

**Antes (Manual):**
```java
public Page<Usuario> buscar(
    @RequestParam(defaultValue = "0") Integer page,
    @RequestParam(defaultValue = "10") Integer size,
    @RequestParam(defaultValue = "nome") String sortBy,
    @RequestParam(defaultValue = "ASC") String sortDirection
) { ... }
```

**Depois (Pageable):**
```java
public Page<Usuario> buscar(
    @PageableDefault(size = 10, sort = "nome") Pageable pageable
) { ... }
```

**Vantagens:**
- ✅ **Menos código**: Spring Boot resolve automaticamente
- ✅ **Padronização**: Formato padrão do Spring Data
- ✅ **URLs REST**: Suporta `?page=0&size=10&sort=nome,asc` automaticamente
- ✅ **Flexibilidade**: Cliente pode ordenar por qualquer campo

### 10. Por que Records do Java 21?

```java
public record UsuarioFilterDto(
    String texto,
    String uf,
    String cidade,
    List<VehicleType> tiposVeiculo
) {}
```

**Vantagens:**
- ✅ **Imutabilidade**: Thread-safe por padrão
- ✅ **Menos boilerplate**: Sem getters/setters/construtores/equals/hashCode
- ✅ **Legibilidade**: Código mais conciso e expressivo
- ✅ **Performance**: Otimizações do compilador

### 11. Por que Swagger/OpenAPI?

- ✅ **Documentação interativa**: Testável diretamente no navegador
- ✅ **Geração automática**: Sincronizado com código
- ✅ **Contratos**: Permite geração de clientes em múltiplas linguagens
- ✅ **Onboarding**: Facilita integração de novos desenvolvedores

### 12. Por que Context Path `/api`?

```yaml
server:
  servlet:
    context-path: /api
```

**Vantagens:**
- ✅ **Versionamento**: Facilita adicionar `/api/v2` futuramente
- ✅ **Proxy reverso**: Facilita configuração de Nginx/Apache
- ✅ **Separação**: Distingue API de recursos estáticos
- ✅ **Convenção**: Padrão amplamente adotado na indústria

### 13. Por que RBAC (Role-Based Access Control) com @PreAuthorize?

**Implementação:**
```java
@PostMapping
@PreAuthorize("hasAnyRole('MOTORISTA', 'ADMIN')")
public ResponseEntity<UsuarioResDto> criar(@Valid @RequestBody UsuarioReqDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criar(dto));
}
```

**Vantagens:**
- ✅ **Segurança granular**: Controle de acesso por método/endpoint
- ✅ **Declarativo**: Regras visíveis no código (não em arquivos separados)
- ✅ **SpEL**: Expressões poderosas (`hasRole`, `hasAnyRole`, `hasAuthority`, condições customizadas)
- ✅ **Testável**: Fácil testar com `@WithMockUser`
- ✅ **Manutenível**: Mudanças de permissão no próprio controller

**Por que não filtrar apenas no service?**
- ❌ **Menos seguro**: Service pode ser chamado por outros lugares
- ❌ **Menos claro**: Regras de autorização espalhadas pelo código
- ❌ **Menos testável**: Difícil saber quem pode chamar cada método

**Regras implementadas:**
- 👁️ **USUARIO**: Apenas consulta (buscar/listar)
- 🚛 **MOTORISTA**: Acesso completo (criar, atualizar, deletar, consultar)
- 🔒 **ADMIN**: Acesso completo (criar, atualizar, deletar, consultar)

**Como funciona:**
1. `@EnableMethodSecurity` ativa suporte a anotações no `ConfigSecurity`
2. `@PreAuthorize("hasAnyRole('MOTORISTA', 'ADMIN')")` valida antes de executar o método
3. Se não tiver permissão → **403 Forbidden** (AccessDeniedException)
4. JWT contém o role do usuário via `RoleEnum.getAuthorities()`

---

## 📂 Estrutura do Projeto

```
portal-motorista-api/
├── src/
│   ├── main/
│   │   ├── java/com/desafio/fretemais/portal_motorista_api/
│   │   │   ├── config/
│   │   │   │   └── doc/
│   │   │   │       └── SpringDocConfig.java          # Configuração Swagger
│   │   │   ├── controller/
│   │   │   │   ├── AutenticacaoController.java       # Endpoints de autenticação
│   │   │   │   ├── UsuarioController.java            # Endpoints de motoristas
│   │   │   │   ├── VehicleTypeController.java        # Endpoints de tipos de veículo
│   │   │   │   └── impl/                             # Implementações de APIs
│   │   │   ├── model/
│   │   │   │   ├── dto/
│   │   │   │   │   ├── request/                      # DTOs de entrada
│   │   │   │   │   └── response/                     # DTOs de saída
│   │   │   │   ├── entity/
│   │   │   │   │   └── UsuarioEntity.java            # Entidade JPA
│   │   │   │   ├── enums/
│   │   │   │   │   ├── VehicleType.java              # Enum de tipos de veículo
│   │   │   │   │   ├── RoleEnum.java                 # Enum de roles
│   │   │   │   │   └── StatusEnum.java               # Enum de status
│   │   │   │   ├── mapper/
│   │   │   │   │   └── UsuarioMapper.java            # Conversão Entity ↔ DTO
│   │   │   │   ├── params/
│   │   │   │   │   └── UsuarioFilterDto.java         # Parâmetros de busca
│   │   │   │   └── converter/
│   │   │   │       └── VehicleTypeListConverter.java # Conversão JSONB ↔ List
│   │   │   ├── repository/
│   │   │   │   ├── UsuarioRepository.java            # JPA Repository
│   │   │   │   └── specification/
│   │   │   │       └── UsuarioSpecification.java     # Filtros dinâmicos
│   │   │   ├── security/
│   │   │   │   ├── ConfigSecurity.java               # Configuração Spring Security
│   │   │   │   ├── FilterSecurity.java               # Filtro JWT
│   │   │   │   └── AuthenticatedUser.java            # Detalhes do usuário
│   │   │   ├── service/
│   │   │   │   ├── AutenticacaoService.java          # Lógica de autenticação
│   │   │   │   ├── JwtService.java                   # Geração/validação JWT
│   │   │   │   ├── UsuarioService.java               # Lógica de negócio
│   │   │   │   ├── VehicleTypeService.java           # Lógica de tipos de veículo
│   │   │   │   └── impl/                             # Implementações de serviços
│   │   │   ├── shared/
│   │   │   │   ├── exception/                        # Exceções customizadas
│   │   │   │   └── audit/                            # Auditoria (JPA Auditing)
│   │   │   └── PortalMotoristaApiApplication.java    # Classe principal
│   │   └── resources/
│   │       ├── application.yaml                      # Configurações da aplicação
│   │       ├── app.key                               # Chave privada RSA (JWT)
│   │       ├── app.pub                               # Chave pública RSA (JWT)
│   │       └── db/migration/
│   │           ├── V1__create_usuario_table.sql      # Criação de tabelas
│   │           └── V2__insert_seed_data.sql          # Dados iniciais
│   └── test/
│       └── java/                                     # Testes unitários
├── DockerFile                                        # Build da imagem Docker
├── pom.xml                                           # Dependências Maven
└── README.md                                         # Este arquivo
```

---

## 🗄️ Schema do Banco de Dados

### Tabela: `usuario`

| Campo | Tipo | Restrições | Descrição |
|-------|------|------------|-----------|
| `id` | UUID | PK, NOT NULL | Identificador único |
| `nome` | VARCHAR(100) | NOT NULL | Nome completo |
| `email` | VARCHAR(100) | UNIQUE, NOT NULL | Email (login) |
| `senha` | VARCHAR(255) | NOT NULL | Senha hasheada (BCrypt) |
| `telefone` | VARCHAR(20) | NOT NULL | Telefone de contato |
| `cidade` | VARCHAR(100) | NOT NULL | Cidade de atuação |
| `uf` | VARCHAR(2) | NOT NULL | Estado (UF) |
| `role` | VARCHAR(20) | NOT NULL | Role (USUARIO/MOTORISTA/ADMIN) |
| `status` | VARCHAR(20) | NOT NULL | Status (ATIVO/INATIVO) |
| `tipos_veiculo` | JSONB | NOT NULL | Array de tipos de veículo |
| `criado_em` | TIMESTAMP | NOT NULL | Data de criação |
| `atualizado_em` | TIMESTAMP | NOT NULL | Data de atualização |

**Índices:**
- `idx_usuario_email` - Busca por email
- `idx_usuario_uf` - Filtro por UF
- `idx_usuario_cidade` - Filtro por cidade
- `idx_usuario_status` - Filtro por status
- `idx_usuario_tipos_veiculo` (GIN) - Busca em JSONB

---

## 🧪 Testando a API

### Usando cURL (Terminal)

Veja a seção [Exemplos de Uso](#-exemplos-de-uso) acima.

### Usando Swagger UI (Recomendado)

1. Acesse: http://localhost:8080/api/swagger-ui/
2. Faça login no endpoint `/autenticacao/login`
3. Copie o token retornado
4. Clique em **"Authorize"** (cadeado verde no topo)
5. Cole o token
6. Teste qualquer endpoint interativamente

### Usando Postman/Insomnia

Importe a coleção OpenAPI:
```
http://localhost:8080/api/v3/api-docs
```

---

## 🔍 Como Testar os Filtros

### Cenário 1: Buscar motoristas de São Paulo que trabalham com VAN

**Request:**
```bash
GET /api/usuarios/search?uf=SP&tiposVeiculo=VAN
```

**Resultado Esperado:** Retorna apenas motoristas de SP que possuem VAN na lista de veículos.

### Cenário 2: Buscar por nome parcial

**Request:**
```bash
GET /api/usuarios/search?texto=João
```

**Resultado Esperado:** Retorna motoristas com "João" no nome, email ou telefone.

### Cenário 3: Buscar combinando cidade e tipo de veículo

**Request:**
```bash
GET /api/usuarios/search?cidade=Curitiba&tiposVeiculo=TRUCK&tiposVeiculo=BITRUCK
```

**Resultado Esperado:** Motoristas de Curitiba que possuem TRUCK OU BITRUCK.

### Cenário 4: Paginação e ordenação

**Request:**
```bash
GET /api/usuarios/search?page=0&size=5&sort=nome,desc
```

**Resultado Esperado:** Primeira página com 5 motoristas ordenados por nome decrescente.

### Cenário 5: Buscar apenas motoristas ativos

Por padrão, apenas motoristas com `status=ATIVO` são retornados (implementado no `UsuarioSpecification`).

---

## 🐛 Troubleshooting

### Problema: Erro "RSA private key not found" ou "FileNotFoundException: app.key"

**Causa:** As chaves RSA não foram geradas.

**Solução:** Gere as chaves RSA conforme instruções na seção [Gerando Chaves RSA](#-gerando-chaves-rsa-obrigatório):

```bash
# Na raiz do projeto
openssl genrsa -out src/main/resources/app.key 2048
openssl rsa -in src/main/resources/app.key -pubout -out src/main/resources/app.pub
```

Depois reinicie a aplicação.

### Problema: Erro "Invalid JWT signature"

**Possíveis causas:**
1. Chaves RSA foram alteradas após geração do token
2. Chaves pública/privada não correspondem
3. Token foi gerado com chaves diferentes

**Solução:**
1. Verifique se as chaves estão corretas:
```bash
# A chave pública deve ser derivada da privada
openssl rsa -in src/main/resources/app.key -pubout -text -noout
```

2. Se necessário, regere as chaves e reinicie a aplicação
3. Faça login novamente para obter novo token

### Problema: Docker não encontra as chaves RSA

**Causa:** As chaves não estão sendo copiadas para a imagem Docker.

**Solução 1 - Montar como Volume (Desenvolvimento):**

Atualize o `docker-compose.yml`:

```yaml
api:
  build:
    context: .
    dockerfile: DockerFile
  volumes:
    - ./src/main/resources/app.key:/opt/app/src/main/resources/app.key:ro
    - ./src/main/resources/app.pub:/opt/app/src/main/resources/app.pub:ro
  # ...resto da configuração
```

**Solução 2 - Copiar no Dockerfile (Produção):**

Adicione no `DockerFile` antes do `ENTRYPOINT`:

```dockerfile
# Copiar chaves RSA
COPY src/main/resources/app.key /opt/app/src/main/resources/app.key
COPY src/main/resources/app.pub /opt/app/src/main/resources/app.pub

# Ajustar permissões
RUN chmod 600 /opt/app/src/main/resources/app.key
RUN chmod 644 /opt/app/src/main/resources/app.pub
```

### Problema: Erro "Connection refused" ao subir o projeto

**Solução:** Certifique-se de que o PostgreSQL está rodando:
```bash
docker-compose ps
```

Se não estiver rodando:
```bash
docker-compose up -d postgres
```

### Problema: Flyway migration falha

**Solução:** Limpe o schema e reinicie:
```bash
docker-compose down -v
docker-compose up -d
```

### Problema: Token JWT inválido ou expirado

**Solução:** Faça login novamente para obter um novo token.

### Problema: Swagger UI não carrega

**Verifique:**
1. URL correta: http://localhost:8080/api/swagger-ui/
2. Aplicação está rodando: `docker-compose logs api`
3. Porta 8080 disponível: `lsof -i :8080` (Linux/Mac) ou `netstat -ano | findstr :8080` (Windows)

### Problema: Filtros não funcionam

**Verifique:**
1. URL encoding correto para parâmetros com espaços
2. Tipos de veículo válidos (VAN, TOCO, BAU, SIDER, TRUCK, BITRUCK)
3. Token JWT válido e presente no header

---

## 📝 Notas Adicionais

### Segurança

- ✅ Senhas hasheadas com BCrypt (strength 12)
- ✅ JWT com chaves RSA 2048 bits
- ✅ CORS configurado para desenvolvimento
- ✅ Validações em todas as entradas
- ✅ SQL Injection protegido (JPA/Hibernate)

### Performance

- ✅ Pool de conexões HikariCP
- ✅ Índices otimizados no PostgreSQL
- ✅ JSONB indexado com GIN
- ✅ Paginação obrigatória em listagens
- ✅ Batch inserts habilitados (Hibernate)

### Melhorias Futuras (Não Implementadas)

- [ ] Testes automatizados (unitários e integração)
- [ ] Cache com Redis
- [ ] Rate limiting
- [ ] Logs estruturados (ELK Stack)
- [ ] Métricas (Prometheus + Grafana)
- [ ] CI/CD (GitHub Actions)
- [ ] Refresh tokens
- [ ] Upload de fotos de perfil
- [ ] Notificações por email
- [ ] Versionamento de API (v1, v2)

---

## 👨‍💻 Autor

**Rafael**
- Email: rafaelsora0@gmail.com
- Desenvolvedor Software

---

## 📄 Licença

Este projeto foi desenvolvido como desafio técnico para a FreteMais.

---

## 🙏 Agradecimentos

- Equipe FreteMais pela oportunidade
- Comunidade Spring Boot pela excelente documentação
- Stack Overflow pela resolução de dúvidas pontuais

---

**Data de Criação:** Fevereiro de 2026  
**Versão:** 1.0.0  
**Status:** Em desenvolvimento 🚧
