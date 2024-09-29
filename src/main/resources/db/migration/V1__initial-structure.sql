-- Migration V1 - Criação de tabelas e inserção de dados

-- Criação da tabela tb_categorias
CREATE TABLE tb_categorias (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  nome VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Inserção de dados na tabela tb_categorias
INSERT INTO tb_categorias (id, user_id, nome, created_at) VALUES
(1, 8, 'Teste', '2024-08-31 19:36:49'),
(2, 8, 'teste 2', '2024-08-31 19:36:57');

-- Criação da tabela tb_chats
CREATE TABLE tb_chats (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Inserção de dados na tabela tb_chats
INSERT INTO tb_chats (id, user_id, title, created_at) VALUES
(1, 8, 'Teste', '2024-08-30 01:34:53'),
(2, 8, 'Teste 2', '2024-08-30 01:42:07'),
(3, 8, 'Duvida', '2024-09-18 16:57:54');

-- Criação da tabela tb_clientes
CREATE TABLE tb_clientes (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  cpf VARCHAR(14) NOT NULL UNIQUE,
  telefone VARCHAR(15),
  data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  senha VARCHAR(255) NOT NULL
);

-- Inserção de dados na tabela tb_clientes
INSERT INTO tb_clientes (id, nome, email, cpf, telefone, data_cadastro, senha) VALUES
(8, 'Ewerton Gonçalves Pereira', 'tonpublic97@gmail.com', '38773308862', '11959383753', '2024-08-29 02:09:29', '$2y$10$Tk1Spg68wGIqaePEY2zcpe5j/j7afNkUpu/AhacKARhdBjo8daYzy');

-- Criação da tabela tb_empresa
CREATE TABLE tb_empresa (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_cliente INT NOT NULL,
  nome_empresa VARCHAR(255) NOT NULL,
  cnpj VARCHAR(20) NOT NULL UNIQUE,
  data_constituicao DATE NOT NULL,
  nire VARCHAR(20),
  inscricao_estadual VARCHAR(20),
  inscricao_municipal VARCHAR(20),
  telefone VARCHAR(20),
  site VARCHAR(255),
  endereco VARCHAR(255),
  cidade VARCHAR(100),
  estado VARCHAR(2),
  cep VARCHAR(10),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Criação da tabela tb_filial
CREATE TABLE tb_filial (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_empresa INT NOT NULL,
  cnpj VARCHAR(20) NOT NULL UNIQUE,
  inscricao_estadual VARCHAR(20),
  inscricao_municipal VARCHAR(20),
  telefone VARCHAR(20),
  endereco VARCHAR(255),
  cidade VARCHAR(100),
  estado VARCHAR(2),
  cep VARCHAR(10),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT tb_filial_ibfk_1 FOREIGN KEY (id_empresa) REFERENCES tb_empresa (id) ON DELETE CASCADE
);

-- Criação da tabela tb_gastos
CREATE TABLE tb_gastos (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  valor DECIMAL(10,2) NOT NULL,
  tipo_pagamento ENUM('recorrente','parcelado') NOT NULL,
  quantidade_parcelas INT,
  categoria_id INT,
  data_inicio DATE NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT tb_gastos_ibfk_1 FOREIGN KEY (categoria_id) REFERENCES tb_categorias (id)
);

-- Inserção de dados na tabela tb_gastos
INSERT INTO tb_gastos (id, user_id, descricao, valor, tipo_pagamento, quantidade_parcelas, categoria_id, data_inicio, created_at) VALUES
(1, 8, 'Teste', 5000.00, 'recorrente', 0, 2, '2024-08-24', '2024-08-31 19:48:46');

-- Criação da tabela tb_lancamentos
CREATE TABLE tb_lancamentos (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  identificador VARCHAR(255) NOT NULL,
  valor DECIMAL(10,2) NOT NULL,
  data DATE NOT NULL,
  tipo ENUM('entrada','saida') NOT NULL,
  criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT tb_lancamentos_ibfk_1 FOREIGN KEY (id_usuario) REFERENCES tb_clientes (id) ON DELETE CASCADE
);

-- Inserção de dados na tabela tb_lancamentos
INSERT INTO tb_lancamentos (id, id_usuario, identificador, valor, data, tipo, criado_em, atualizado_em) VALUES
(2, 8, 'Pagamento funcionario', 2000.00, '2024-08-29', 'saida', '2024-08-30 00:33:13', '2024-08-30 00:33:13'),
(5, 8, 'Venda de Produto A', 1500.00, '2024-08-01', 'entrada', '2024-08-01 11:00:00', '2024-08-01 11:00:00');

-- Criação da tabela tb_messages
CREATE TABLE tb_messages (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  chat_id INT NOT NULL,
  user_id INT,
  role ENUM('user','assistant') NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT tb_messages_ibfk_1 FOREIGN KEY (chat_id) REFERENCES tb_chats (id)
);

-- Inserção de dados na tabela tb_messages
INSERT INTO tb_messages (id, chat_id, user_id, role, content, created_at) VALUES
(1, 1, 8, 'user', 'Quantos Graus está em SP?', '2024-08-30 01:37:45');

-- Criação da tabela tb_metas
CREATE TABLE tb_metas (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  meta_faturamento DECIMAL(10,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Inserção de dados na tabela tb_metas
INSERT INTO tb_metas (id, user_id, meta_faturamento, created_at) VALUES
(2, 8, 45000.00, '2024-08-31 22:37:19');
