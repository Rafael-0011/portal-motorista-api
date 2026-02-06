-- Inserção de dados de exemplo para testes
-- Senha padrão para todos: 123456 (hash BCrypt)

-- Motoristas (podem criar, editar, excluir)
INSERT INTO usuario (id, nome, email, senha, telefone, cidade, uf, role, status, tipos_veiculo, criado_em, atualizado_em) VALUES
(gen_random_uuid(), 'João da Silva', 'joao.silva@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(11) 98765-4321', 'São Paulo', 'SP', 'MOTORISTA', 'ATIVO', '["VAN", "TRUCK"]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Maria Santos', 'maria.santos@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(21) 97654-3210', 'Rio de Janeiro', 'RJ', 'MOTORISTA', 'ATIVO', '["TOCO", "BITRUCK"]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Carlos Oliveira', 'carlos.oliveira@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(31) 96543-2109', 'Belo Horizonte', 'MG', 'MOTORISTA', 'ATIVO', '["BAU", "SIDER"]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Ana Paula', 'ana.paula@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(41) 95432-1098', 'Curitiba', 'PR', 'MOTORISTA', 'ATIVO', '["TRUCK", "BITRUCK"]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Pedro Costa', 'pedro.costa@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(51) 94321-0987', 'Porto Alegre', 'RS', 'MOTORISTA', 'ATIVO', '["VAN", "TOCO"]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Usuários (apenas consulta)
INSERT INTO usuario (id, nome, email, senha, telefone, cidade, uf, role, status, tipos_veiculo, criado_em, atualizado_em) VALUES
(gen_random_uuid(), 'Juliana Lima', 'juliana.lima@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(85) 93210-9876', 'Fortaleza', 'CE', 'USUARIO', 'ATIVO', '[]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Roberto Alves', 'roberto.alves@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(71) 92109-8765', 'Salvador', 'BA', 'USUARIO', 'ATIVO', '[]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Fernanda Rocha', 'fernanda.rocha@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(81) 91098-7654', 'Recife', 'PE', 'USUARIO', 'ATIVO', '[]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Lucas Martins', 'lucas.martins@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(61) 90987-6543', 'Brasília', 'DF', 'USUARIO', 'ATIVO', '[]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Camila Souza', 'camila.souza@email.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(62) 89876-5432', 'Goiânia', 'GO', 'USUARIO', 'ATIVO', '[]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Admin (acesso total)
INSERT INTO usuario (id, nome, email, senha, telefone, cidade, uf, role, status, tipos_veiculo, criado_em, atualizado_em) VALUES
(gen_random_uuid(), 'Admin Sistema', 'admin@fretemais.com', '$2a$12$g8i8M5Vx9SA3nD/j2ufZ0usMSyufZmJe/jBK57ypbRQi939xJHLQG', '(11) 99999-9999', 'São Paulo', 'SP', 'ADMIN', 'ATIVO', '[]'::jsonb, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);






