# Portal Motorista API

API desenvolvida para gerenciamento de motoristas e autenticação de usuários, com foco em segurança, escalabilidade e boas práticas de arquitetura.

## Funcionalidades

- Cadastro, atualização, busca e remoção de motoristas/usuários
- Autenticação JWT
- Controle de acesso baseado em roles (RBAC)
- Listagem de tipos de veículos
- Validação de dados e tratamento global de exceções
- Migrações de banco de dados com Flyway
- Documentação automática com OpenAPI/Swagger

## Tecnologias e Ferramentas Utilizadas

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway (migração de banco)
- Lombok
- OpenAPI (Swagger)
- Docker e Docker Compose
- JUnit 5 (testes)

## Como Executar

1. Clone o repositório
2. Configure as variáveis de ambiente do banco de dados no `application.yaml`
3. Execute o projeto com Maven:

   ```sh
   ./mvnw spring-boot:run
   ```

4. Acesse a documentação da API em `/swagger-ui.html`

## Estrutura do Projeto

- `controller/` — Interfaces e implementações dos endpoints REST
- `service/` — Regras de negócio e autenticação
- `model/` — DTOs, entidades e enums
- `repository/` — Integração com banco de dados
- `security/` — Configuração de autenticação e autorização
- `shared/exception/` — Tratamento global de erros
- `config/doc/` — Configuração do Swagger/OpenAPI
- `resources/db/migration/` — Scripts de migração Flyway
