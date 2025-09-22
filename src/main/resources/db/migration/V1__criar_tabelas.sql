/* V1__criar_tabelas.sql
   Estrutura inicial do banco - Sprint 3
   Observação: não faz DROP de objetos. Usa sequences e NEXTVAL nos inserts.
*/

-- -----------------------------------------------------
-- Tabelas (criação na ordem correta)
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
-- Inserções (usando SEQUENCE.NEXTVAL para id)
-- -----------------------------------------------------

-- Inserir usuário fixo (bcrypt)
INSERT INTO usuario (id, usuario, senha) VALUES (usuario_seq.NEXTVAL, 'admin', '$2a$10$s/CIIE7k/KUbi0dNxlAf9.DajXeFGRekeWprpfbTVgtgXuBjEneKq');

-- Inserir zonas (com id)
INSERT INTO zona (id, nome, letra) VALUES (zona_seq.NEXTVAL, 'Zona Norte', 'N');
INSERT INTO zona (id, nome, letra) VALUES (zona_seq.NEXTVAL, 'Zona Sul', 'S');
INSERT INTO zona (id, nome, letra) VALUES (zona_seq.NEXTVAL, 'Zona Leste', 'L');
INSERT INTO zona (id, nome, letra) VALUES (zona_seq.NEXTVAL, 'Zona Oeste', 'O');

-- Inserir pátios
INSERT INTO patio (id, nome) VALUES (patio_seq.NEXTVAL, 'Pátio 1');
INSERT INTO patio (id, nome) VALUES (patio_seq.NEXTVAL, 'Pátio 2');
INSERT INTO patio (id, nome) VALUES (patio_seq.NEXTVAL, 'Pátio 3');

-- Inserir grupos de status (com id)
INSERT INTO status_grupo (id, nome) VALUES (status_grupo_seq.NEXTVAL, 'Manutenção');
INSERT INTO status_grupo (id, nome) VALUES (status_grupo_seq.NEXTVAL, 'Aguardando');
INSERT INTO status_grupo (id, nome) VALUES (status_grupo_seq.NEXTVAL, 'Indisponível');
INSERT INTO status_grupo (id, nome) VALUES (status_grupo_seq.NEXTVAL, 'Pronta');

-- Inserir status específicos (usando SELECT para obter status_grupo.id)
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Específicos', (SELECT id FROM status_grupo WHERE nome = 'Manutenção'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Segurança', (SELECT id FROM status_grupo WHERE nome = 'Manutenção'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Corretiva', (SELECT id FROM status_grupo WHERE nome = 'Manutenção'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Preventiva', (SELECT id FROM status_grupo WHERE nome = 'Manutenção'));

INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Peças', (SELECT id FROM status_grupo WHERE nome = 'Aguardando'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Limpeza', (SELECT id FROM status_grupo WHERE nome = 'Aguardando'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Inspeção', (SELECT id FROM status_grupo WHERE nome = 'Aguardando'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Aprovação', (SELECT id FROM status_grupo WHERE nome = 'Aguardando'));

INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Documentação', (SELECT id FROM status_grupo WHERE nome = 'Indisponível'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Bloqueada', (SELECT id FROM status_grupo WHERE nome = 'Indisponível'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Furtada', (SELECT id FROM status_grupo WHERE nome = 'Indisponível'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Irreparável', (SELECT id FROM status_grupo WHERE nome = 'Indisponível'));

INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Pronta', (SELECT id FROM status_grupo WHERE nome = 'Pronta'));
INSERT INTO status (id, nome, status_grupo_id) VALUES (status_seq.NEXTVAL, 'Reservada', (SELECT id FROM status_grupo WHERE nome = 'Pronta'));
