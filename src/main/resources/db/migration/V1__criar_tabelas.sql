/*
  Script SQL para criar a estrutura do banco de dados
  para o projeto de gerenciamento de motos em Oracle.
  Inclui comandos DROP para limpar as tabelas e sequências existentes.
*/

-- -----------------------------------------------------
-- Limpar o ambiente: Drop de tabelas e sequências
-- -----------------------------------------------------
-- A ordem é importante para respeitar as chaves estrangeiras.
DROP TABLE moto CASCADE CONSTRAINTS;
DROP TABLE status CASCADE CONSTRAINTS;
DROP TABLE status_grupo CASCADE CONSTRAINTS;
DROP TABLE patio CASCADE CONSTRAINTS;
DROP TABLE zona CASCADE CONSTRAINTS;
DROP TABLE usuario CASCADE CONSTRAINTS;

DROP SEQUENCE moto_seq;
DROP SEQUENCE status_seq;
DROP SEQUENCE status_grupo_seq;
DROP SEQUENCE patio_seq;
DROP SEQUENCE zona_seq;
DROP SEQUENCE usuario_seq;

-- -----------------------------------------------------
-- Tabelas
-- -----------------------------------------------------

-- Tabela `usuario`
CREATE TABLE usuario (
  id NUMBER(10,0) NOT NULL,
  usuario VARCHAR2(50) NOT NULL,
  senha VARCHAR2(255) NOT NULL,
  CONSTRAINT usuario_pk PRIMARY KEY (id),
  CONSTRAINT usuario_usuario_uk UNIQUE (usuario)
);

-- Tabela `zona`
CREATE TABLE zona (
  id NUMBER(10,0) NOT NULL,
  nome VARCHAR2(50) NOT NULL,
  letra VARCHAR2(1) NOT NULL,
  CONSTRAINT zona_pk PRIMARY KEY (id)
);

-- Tabela `patio`
CREATE TABLE patio (
  id NUMBER(10,0) NOT NULL,
  nome VARCHAR2(50) NOT NULL,
  CONSTRAINT patio_pk PRIMARY KEY (id)
);

-- Tabela `status_grupo`
CREATE TABLE status_grupo (
  id NUMBER(10,0) NOT NULL,
  nome VARCHAR2(50) NOT NULL,
  CONSTRAINT status_grupo_pk PRIMARY KEY (id)
);

-- Tabela `status`
CREATE TABLE status (
  id NUMBER(10,0) NOT NULL,
  nome VARCHAR2(50) NOT NULL,
  status_grupo_id NUMBER(10,0) NOT NULL,
  CONSTRAINT status_pk PRIMARY KEY (id),
  CONSTRAINT status_fk FOREIGN KEY (status_grupo_id) REFERENCES status_grupo(id)
);

-- Tabela `moto`
CREATE TABLE moto (
  id NUMBER(10,0) NOT NULL,
  placa VARCHAR2(10) NOT NULL,
  chassi VARCHAR2(20) NOT NULL,
  qr_code VARCHAR2(255),
  data_entrada TIMESTAMP(6) NOT NULL,
  previsao_entrega TIMESTAMP(6),
  fotos VARCHAR2(255),
  zona_id NUMBER(10,0) NOT NULL,
  patio_id NUMBER(10,0) NOT NULL,
  status_id NUMBER(10,0) NOT NULL,
  observacoes CLOB,
  CONSTRAINT moto_pk PRIMARY KEY (id),
  CONSTRAINT moto_placa_uk UNIQUE (placa),
  CONSTRAINT moto_chassi_uk UNIQUE (chassi),
  CONSTRAINT moto_zona_fk FOREIGN KEY (zona_id) REFERENCES zona(id),
  CONSTRAINT moto_patio_fk FOREIGN KEY (patio_id) REFERENCES patio(id),
  CONSTRAINT moto_status_fk FOREIGN KEY (status_id) REFERENCES status(id)
);


-- -----------------------------------------------------
-- Sequências para ID's auto-incremento
-- -----------------------------------------------------
CREATE SEQUENCE usuario_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE zona_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE patio_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE status_grupo_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE status_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE moto_seq START WITH 1 INCREMENT BY 1;


-- -----------------------------------------------------
-- Inserções (Inserts)
-- -----------------------------------------------------
-- Inserir os dados iniciais.

-- Inserir usuário fixo
INSERT INTO usuario (usuario, senha) VALUES ('admin', '$2a$10$s/CIIE7k/KUbi0dNxlAf9.DajXeFGRekeWprpfbTVgtgXuBjEneKq');

-- Inserir zonas
INSERT INTO zona (nome, letra) VALUES ('Zona Norte', 'N');
INSERT INTO zona (nome, letra) VALUES ('Zona Sul', 'S');
INSERT INTO zona (nome, letra) VALUES ('Zona Leste', 'L');
INSERT INTO zona (nome, letra) VALUES ('Zona Oeste', 'O');

-- Inserir pátios
INSERT INTO patio (nome) VALUES ('Pátio 1');
INSERT INTO patio (nome) VALUES ('Pátio 2');
INSERT INTO patio (nome) VALUES ('Pátio 3');

-- Inserir grupos de status
INSERT INTO status_grupo (nome) VALUES ('Manutenção');
INSERT INTO status_grupo (nome) VALUES ('Aguardando');
INSERT INTO status_grupo (nome) VALUES ('Indisponível');
INSERT INTO status_grupo (nome) VALUES ('Pronta');

-- Inserir status específicos
INSERT INTO status (nome, status_grupo_id) VALUES
('Específicos', (SELECT id FROM status_grupo WHERE nome = 'Manutenção'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Segurança', (SELECT id FROM status_grupo WHERE nome = 'Manutenção'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Corretiva', (SELECT id FROM status_grupo WHERE nome = 'Manutenção'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Preventiva', (SELECT id FROM status_grupo WHERE nome = 'Manutenção'));

INSERT INTO status (nome, status_grupo_id) VALUES
('Peças', (SELECT id FROM status_grupo WHERE nome = 'Aguardando'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Limpeza', (SELECT id FROM status_grupo WHERE nome = 'Aguardando'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Inspeção', (SELECT id FROM status_grupo WHERE nome = 'Aguardando'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Aprovação', (SELECT id FROM status_grupo WHERE nome = 'Aguardando'));

INSERT INTO status (nome, status_grupo_id) VALUES
('Documentação', (SELECT id FROM status_grupo WHERE nome = 'Indisponível'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Bloqueada', (SELECT id FROM status_grupo WHERE nome = 'Indisponível'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Furtada', (SELECT id FROM status_grupo WHERE nome = 'Indisponível'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Irreparável', (SELECT id FROM status_grupo WHERE nome = 'Indisponível'));

INSERT INTO status (nome, status_grupo_id) VALUES
('Pronta', (SELECT id FROM status_grupo WHERE nome = 'Pronta'));
INSERT INTO status (nome, status_grupo_id) VALUES
('Reservada', (SELECT id FROM status_grupo WHERE nome = 'Pronta'));


