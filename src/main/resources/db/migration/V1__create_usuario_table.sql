-- Criação da tabela de usuários/motoristas
CREATE TABLE IF NOT EXISTS usuario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(50) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ATIVO',
    tipos_veiculo JSONB,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Criar índices para melhorar performance nas buscas
CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_usuario_uf ON usuario(uf);
CREATE INDEX idx_usuario_cidade ON usuario(cidade);
CREATE INDEX idx_usuario_status ON usuario(status);
CREATE INDEX idx_usuario_tipos_veiculo ON usuario USING GIN (tipos_veiculo);

-- Comentários para documentação
COMMENT ON TABLE usuario IS 'Tabela de cadastro de usuários/motoristas';
COMMENT ON COLUMN usuario.tipos_veiculo IS 'Array de tipos de veículo armazenado como JSONB';
COMMENT ON COLUMN usuario.status IS 'Status para soft delete (ATIVO, INATIVO, BLOQUEADO)';
