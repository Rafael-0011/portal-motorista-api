# 🤖 Documentação de Uso de IA

## Sobre este documento

Este arquivo documenta o uso de ferramentas de IA (GitHub Copilot/Claude) durante o desenvolvimento do projeto Portal Motorista API, conforme solicitado no desafio técnico da FreteMais.

---

## 📋 Resumo do Uso de IA

**Ferramenta utilizada:** GitHub Copilot (Claude 3.5)

**Papel da IA:** Assistente de desenvolvimento para estruturação inicial, geração de boilerplate e documentação.

**Papel do Desenvolvedor:** Validação técnica, correção de erros, ajuste de arquitetura e decisões de design.

---

## 🏗️ Estruturação do Projeto

### Prompt 1: Criação da estrutura inicial
**Solicitação:**
```
Criar estrutura Spring Boot 4.0.2 com Java 21 seguindo padrão MVC:
- Controller (com interface para Swagger)
- Service (com interface)
- Repository (JPA)
- Model (Entity, DTOs separados em request/response, Enums, Mappers)
- Security (JWT com RSA)
- Shared (Exception handlers, Audit)
```

**Gerado pela IA:**
- ✅ Estrutura de pastas completa
- ✅ Arquivos base das camadas
- ✅ Configuração do `pom.xml` com dependências

**Adaptado manualmente:**
- ✅ Ajustado versões de dependências (SpringDoc 2.7.0)
- ✅ Removido dependências desnecessárias (MinIO, Email)
- ✅ Configurado `application.yaml` com variáveis de ambiente

---

## 🗄️ Modelagem e Persistência

### Prompt 2: Entidade Usuario com JSONB
**Solicitação:**
```
Criar entidade Usuario com:
- UUID como ID
- Campos: nome, email, senha, telefone, cidade, uf
- Enum RoleEnum (ADMIN, MOTORISTA)
- Enum StatusUsuarioEnum (ATIVO, INATIVO)
- List<VehicleTypeEnum> persistido como JSONB no PostgreSQL
- Usar @Convert com converter customizado
- Herdar de classe Audit (createdAt, updatedAt)
```

**Gerado pela IA:**
- ✅ `UsuarioEntity.java` com anotações JPA
- ✅ `VehicleTypeListConverter.java` para JSONB
- ✅ Enums básicos

**Adaptado manualmente:**
- ✅ Corrigido `@Convert(converter = VehicleTypeListConverter.class)`
- ✅ Ajustado `@Column(columnDefinition = "jsonb")`
- ✅ Implementado lógica do converter usando Jackson ObjectMapper
- ✅ Adicionado tratamento de exceções no converter

**Por que corrigir:**
A IA inicialmente tentou criar lógica de paginação manual, mas o Spring Data já fornece isso via `Pageable`. Removi código desnecessário.

---

## 🔍 Filtros Combináveis com Specifications

### Prompt 3: Implementar filtros dinâmicos
**Solicitação:**
```
Implementar busca com filtros combináveis usando JPA Specifications:
- Filtro por texto (nome, email, telefone)
- Filtro por UF
- Filtro por cidade
- Filtro por tipos de veículo (JSONB contains)
- Suporte a paginação e ordenação via Pageable
```

**Gerado pela IA:**
- ✅ Classe `UsuarioSpecification` com predicates
- ✅ Método `getFilter()` combinando specifications
- ✅ Filtro de status ATIVO automático

**Adaptado manualmente:**
- ✅ **REMOVIDO:** Lógica manual de paginação (IA criou `PageRequest.of()` manualmente)
- ✅ **CORRIGIDO:** Usado `@PageableDefault` no controller
- ✅ **OTIMIZADO:** Query JSONB com `@>` operator do PostgreSQL
- ✅ Adicionado cast para `jsonb` na query nativa

**Por que corrigir:**
A IA gerou código para criar `PageRequest` manualmente quando o Spring Boot já resolve isso automaticamente via parâmetro `Pageable` no controller. Isso era código duplicado e desnecessário.

```java
// ❌ Gerado pela IA (removido)
private Pageable criarPageable(UsuarioFilterDto filter) {
    Sort sort = filter.sortDirection().equalsIgnoreCase("DESC")
        ? Sort.by(filter.sortBy()).descending()
        : Sort.by(filter.sortBy()).ascending();
    return PageRequest.of(filter.page(), filter.size(), sort);
}

// ✅ Solução correta (usando Spring)
@PostMapping("/search")
public ResponseEntity<Page<UsuarioResDto>> buscarComFiltros(
    @RequestBody UsuarioFilterReqDto filter,
    @PageableDefault(size = 10, sort = "nome") Pageable pageable
) {
    return ResponseEntity.ok(usuarioService.buscarComFiltros(filter, pageable));
}
```

---

## 🔐 Segurança e Autenticação

### Prompt 4: JWT com RSA e Spring Security
**Solicitação:**
```
Configurar Spring Security com:
- JWT usando chaves RSA (app.key e app.pub)
- Autenticação stateless
- Filtro customizado para validação
- Roles: ADMIN e MOTORISTA
- @PreAuthorize para controle de acesso
```

**Gerado pela IA:**
- ✅ `ConfigSecurity.java` com SecurityFilterChain
- ✅ `JwtService.java` para geração de tokens
- ✅ `AuthenticatedUser.java` implementando UserDetails
- ✅ Enum com `getAuthorities()`

**Adaptado manualmente:**
- ✅ Configurado whitelist para Swagger UI
- ✅ Ajustado endpoint de login (`/autenticacao/autenticar`)
- ✅ Adicionado `@EnableMethodSecurity` no `ConfigSecurity`
- ✅ Implementado `@PreAuthorize("hasRole('ADMIN')")` nos métodos de criação/atualização/exclusão

**Por que corrigir:**
A IA não aplicou automaticamente as restrições de role nos endpoints. Isso foi adicionado manualmente com base nas regras de negócio.

---

## 🗃️ Migrations com Flyway

### Prompt 5: Criar migrations e seed data
**Solicitação:**
```
Criar migrations Flyway:
- V1: Tabela usuario com JSONB
- V2: Seed com 10 motoristas + 1 admin
- Senha BCrypt: 123456
- Índices para otimização (email, uf, cidade, tipos_veiculo GIN)
```

**Gerado pela IA:**
- ✅ `V1__create_usuario_table.sql`
- ✅ `V2__insert_seed_data.sql`
- ✅ Índices básicos

**Adaptado manualmente:**
- ✅ **CORRIGIDO:** Hash BCrypt correto para senha "123456"
- ✅ **ADICIONADO:** Índice GIN para JSONB (`CREATE INDEX USING GIN`)
- ✅ **AJUSTADO:** Dados seed com tipos de veículo variados
- ✅ Corrigido sintaxe `'["VAN", "TRUCK"]'::jsonb`

**Por que corrigir:**
A IA gerou hash BCrypt inválido. Precisei gerar o hash correto usando:
```bash
echo -n "123456" | htpasswd -bnBC 12 "" | tr -d ':\n' | sed 's/$2y/$2a/'
```

---

## 📡 Controllers e APIs

### Prompt 6: Padrão Interface + Implementação
**Solicitação:**
```
Criar controllers seguindo padrão:
- Interface com anotações Swagger (@Operation, @ApiResponse)
- Implementação (UsuarioControllerImpl) com @RestController
- Separar DTOs de request e response
- Validações com Bean Validation
```

**Gerado pela IA:**
- ✅ Interface `UsuarioController`
- ✅ Implementação `UsuarioControllerImpl`
- ✅ Anotações Swagger completas

**Adaptado manualmente:**
- ✅ **REMOVIDO:** Mapeamentos duplicados (IA colocou `@GetMapping` na interface E na implementação)
- ✅ **CORRIGIDO:** Usado `@RequestBody` para filtros ao invés de `@ModelAttribute`
- ✅ Movido todos os `@Mapping` para a implementação, deixando interface apenas com documentação

**Por que corrigir:**
A IA colocou anotações de mapeamento tanto na interface quanto na implementação, causando conflitos. Mantive apenas na implementação.

---

## 📝 Documentação (README.md)

### Prompt 7: Criar README completo
**Solicitação:**
```
Criar README.md com:
- Como rodar o projeto (Docker e Local)
- Como autenticar (usuários seed)
- Exemplos de uso (curl com todos os endpoints)
- Como testar filtros (5 cenários)
- Decisões técnicas (por que X ao invés de Y)
- Troubleshooting
```

**Gerado pela IA:**
- ✅ Estrutura completa do README
- ✅ Exemplos de curl formatados
- ✅ Tabelas de endpoints
- ✅ Seções de decisões técnicas

**Adaptado manualmente:**
- ✅ **REMOVIDO:** Referência a arquivo `.env` (Spring Boot não usa `.env` nativamente)
- ✅ **CORRIGIDO:** Instruções para gerar chaves RSA (comandos OpenSSL)
- ✅ **ADICIONADO:** Seção de controle de acesso RBAC
- ✅ **CRIADO:** `docker-compose.yml` no projeto (IA apenas documentou, não criou)
- ✅ Corrigido URLs do Swagger (`/api/swagger-ui/` ao invés de `/swagger-ui.html`)

**Por que corrigir:**
A IA sugeriu criar arquivo `.env` como no Node.js, mas Spring Boot usa `application.yaml` ou variáveis de ambiente do sistema. Documentei a forma correta.

---

## 🐳 Docker

### Prompt 8: Docker Compose para desenvolvimento
**Solicitação:**
```
Criar docker-compose.yml com:
- PostgreSQL 16 Alpine
- API Spring Boot
- Healthcheck no banco
- Variáveis de ambiente configuradas
```

**Gerado pela IA:**
- ✅ Estrutura básica do docker-compose
- ✅ Configuração de networks e volumes

**Adaptado manualmente:**
- ✅ **ADICIONADO:** `healthcheck` no PostgreSQL
- ✅ **ADICIONADO:** `depends_on` com `condition: service_healthy`
- ✅ **ADICIONADO:** `restart: unless-stopped`
- ✅ **REMOVIDO:** Variável `TZ` (não necessária)

---

## 🎯 Padrões e Boas Práticas

### Decisões que a IA NÃO tomou (implementadas manualmente):

1. ✅ **Specifications ao invés de @Query**
   - IA sugeriu `@Query` com HQL
   - Escolhi Specifications para filtros dinâmicos

2. ✅ **Records ao invés de Classes com Lombok**
   - IA usou classes com `@Data`
   - Migrei DTOs para Records (Java 21)

3. ✅ **Soft Delete com enum STATUS**
   - IA implementou campo `deleted` boolean
   - Refatorei para `StatusUsuarioEnum` (ATIVO/INATIVO/BLOQUEADO)

4. ✅ **JSONB ao invés de tabela N:N**
   - IA sugeriu `@ManyToMany` para tipos de veículo
   - Escolhi JSONB para flexibilidade e performance

5. ✅ **Context Path `/api`**
   - IA não configurou
   - Adicionei para padronização REST

---

## 📊 Estatísticas de Uso

| Categoria | Gerado pela IA | Adaptado Manualmente | % Manual |
|-----------|----------------|----------------------|----------|
| Estrutura de Pastas | 100% | 0% | 0% |
| Entidades JPA | 80% | 20% | 20% |
| Controllers | 70% | 30% | 30% |
| Services | 90% | 10% | 10% |
| Security | 60% | 40% | 40% |
| Specifications | 50% | 50% | 50% |
| README.md | 70% | 30% | 30% |
| Docker | 80% | 20% | 20% |
| Migrations | 60% | 40% | 40% |

**Média geral:** ~70% IA / ~30% Desenvolvimento Manual

---

## 🧠 Lições Aprendidas

### O que a IA faz bem:
- ✅ Criar estrutura de pastas e boilerplate
- ✅ Gerar código repetitivo (DTOs, Mappers)
- ✅ Documentação inicial (Swagger, README)
- ✅ Configurações básicas (application.yaml, pom.xml)

### O que precisa de validação/correção:
- ⚠️ Decisões arquiteturais (Specifications vs @Query)
- ⚠️ Otimizações (remover código duplicado/desnecessário)
- ⚠️ Segurança (RBAC, validações, hashing)
- ⚠️ Detalhes de infraestrutura (Docker healthchecks, índices DB)
- ⚠️ Compatibilidade de versões (Spring Boot 4.x é recente)

### Erros comuns da IA corrigidos:
1. ❌ Criar paginação manual quando `Pageable` já existe
2. ❌ Duplicar anotações entre interface e implementação
3. ❌ Usar `.env` em Spring Boot
4. ❌ Gerar hash BCrypt inválido
5. ❌ Esquecer de habilitar `@EnableMethodSecurity`
6. ❌ Não aplicar `@PreAuthorize` automaticamente

---

## 🎓 Conclusão

O uso de IA foi **essencial para acelerar o desenvolvimento**, economizando tempo em tarefas repetitivas. No entanto, **a validação técnica e decisões arquiteturais** foram responsabilidade do desenvolvedor.

**Proporção ideal identificada:** 70% IA + 30% Humano = Código de qualidade produzido rapidamente.

**Habilidade demonstrada:** Capacidade de usar IA de forma crítica, validando e corrigindo saídas, ao invés de aceitar cegamente o código gerado.

---

**Data de criação:** 05/02/2026  
**Desenvolvedor:** Rafael  
**Ferramenta de IA:** GitHub Copilot (Claude 3.5 Sonnet)
