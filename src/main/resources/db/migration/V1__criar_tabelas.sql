/* V1__criar_tabelas.sql
   Estrutura inicial do banco - Sprint 3
   Versão idempotente (pode ser executada múltiplas vezes)
*/

-- Bloco anônimo para limpar objetos existentes de forma segura
BEGIN
    -- Remove tabelas se existirem (em ordem reversa por causa das FKs)
    FOR t IN (SELECT table_name FROM user_tables WHERE table_name IN ('MOTO', 'STATUS', 'STATUS_GRUPO', 'PATIO', 'ZONA', 'USUARIO')) LOOP
        EXECUTE IMMEDIATE 'DROP TABLE ' || t.table_name || ' CASCADE CONSTRAINTS';
    END LOOP;

    -- Remove sequences se existirem
    FOR s IN (SELECT sequence_name FROM user_sequences WHERE sequence_name IN ('USUARIO_SEQ', 'ZONA_SEQ', 'PATIO_SEQ', 'STATUS_GRUPO_SEQ', 'STATUS_SEQ', 'MOTO_SEQ')) LOOP
        EXECUTE IMMEDIATE 'DROP SEQUENCE ' || s.sequence_name;
    END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        NULL; -- Ignora erros se objetos não existirem
END;
/

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

-- Tabela `moto` - CAMPOS PLACA, CHASSI E QR_CODE SÃO OPCIONAIS
CREATE TABLE moto (
  id NUMBER(10,0) NOT NULL,
  placa VARCHAR2(10) NULL,
  chassi VARCHAR2(20) NULL,
  qr_code VARCHAR2(255) NULL,
  data_entrada TIMESTAMP(6) NOT NULL,
  previsao_entrega TIMESTAMP(6),
  fotos VARCHAR2(255),
  zona_id NUMBER(10,0) NOT NULL,
  patio_id NUMBER(10,0) NOT NULL,
  status_id NUMBER(10,0) NOT NULL,
  observacoes CLOB,
  CONSTRAINT moto_pk PRIMARY KEY (id),
  CONSTRAINT moto_zona_fk FOREIGN KEY (zona_id) REFERENCES zona(id),
  CONSTRAINT moto_patio_fk FOREIGN KEY (patio_id) REFERENCES patio(id),
  CONSTRAINT moto_status_fk FOREIGN KEY (status_id) REFERENCES status(id)
);

-- Constraint para garantir que pelo menos um dos campos (placa, chassi, qr_code) esteja preenchido
ALTER TABLE moto ADD CONSTRAINT moto_at_least_one_field_check 
  CHECK (placa IS NOT NULL OR chassi IS NOT NULL OR qr_code IS NOT NULL);

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
-- Inserções usando IDs fixos em ordem
-- -----------------------------------------------------

-- Inserir usuário fixo (bcrypt) - senha: senha123
INSERT INTO usuario (id, usuario, senha) VALUES (1, 'admin', '$2a$10$CNphorZ/LzTqAHb62IDDrO1S7pTJ086qLWe5DaUJXdHEmAulSdp5K');

-- Inserir zonas
INSERT INTO zona (id, nome, letra) VALUES (1, 'Zona Norte', 'N');
INSERT INTO zona (id, nome, letra) VALUES (2, 'Zona Sul', 'S');
INSERT INTO zona (id, nome, letra) VALUES (3, 'Zona Leste', 'L');
INSERT INTO zona (id, nome, letra) VALUES (4, 'Zona Oeste', 'O');

-- Inserir pátios
INSERT INTO patio (id, nome) VALUES (1, 'Pátio 1');
INSERT INTO patio (id, nome) VALUES (2, 'Pátio 2');
INSERT INTO patio (id, nome) VALUES (3, 'Pátio 3');

-- Inserir grupos de status
INSERT INTO status_grupo (id, nome) VALUES (1, 'Manutenção');
INSERT INTO status_grupo (id, nome) VALUES (2, 'Aguardando');
INSERT INTO status_grupo (id, nome) VALUES (3, 'Indisponível');
INSERT INTO status_grupo (id, nome) VALUES (4, 'Pronta');

-- Inserir status específicos usando IDs fixos
INSERT INTO status (id, nome, status_grupo_id) VALUES (1, 'Específicos', 1);
INSERT INTO status (id, nome, status_grupo_id) VALUES (2, 'Segurança', 1);
INSERT INTO status (id, nome, status_grupo_id) VALUES (3, 'Corretiva', 1);
INSERT INTO status (id, nome, status_grupo_id) VALUES (4, 'Preventiva', 1);

INSERT INTO status (id, nome, status_grupo_id) VALUES (5, 'Peças', 2);
INSERT INTO status (id, nome, status_grupo_id) VALUES (6, 'Limpeza', 2);
INSERT INTO status (id, nome, status_grupo_id) VALUES (7, 'Inspeção', 2);
INSERT INTO status (id, nome, status_grupo_id) VALUES (8, 'Aprovação', 2);

INSERT INTO status (id, nome, status_grupo_id) VALUES (9, 'Documentação', 3);
INSERT INTO status (id, nome, status_grupo_id) VALUES (10, 'Bloqueada', 3);
INSERT INTO status (id, nome, status_grupo_id) VALUES (11, 'Furtada', 3);
INSERT INTO status (id, nome, status_grupo_id) VALUES (12, 'Irreparável', 3);

INSERT INTO status (id, nome, status_grupo_id) VALUES (13, 'Pronta', 4);
INSERT INTO status (id, nome, status_grupo_id) VALUES (14, 'Reservada', 4);

-- Exemplo de motos para testar com campos opcionais
INSERT INTO moto (id, placa, chassi, qr_code, data_entrada, previsao_entrega, fotos, zona_id, patio_id, status_id, observacoes) 
VALUES (1, 'ABC-1234', NULL, NULL, SYSTIMESTAMP, NULL, NULL, 1, 1, 13, 'Moto de exemplo apenas com placa');

INSERT INTO moto (id, placa, chassi, qr_code, data_entrada, previsao_entrega, fotos, zona_id, patio_id, status_id, observacoes) 
VALUES (2, NULL, '1HGCM82633A123456', NULL, SYSTIMESTAMP, NULL, NULL, 2, 2, 5, 'Moto de exemplo apenas com chassi');

INSERT INTO moto (id, placa, chassi, qr_code, data_entrada, previsao_entrega, fotos, zona_id, patio_id, status_id, observacoes) 
VALUES (3, NULL, NULL, 'QR123456789', SYSTIMESTAMP, NULL, NULL, 3, 3, 1, 'Moto de exemplo apenas com QR Code');

-- Atualizar as sequências para começar após os registros inseridos
DROP SEQUENCE usuario_seq;
DROP SEQUENCE zona_seq;
DROP SEQUENCE patio_seq;
DROP SEQUENCE status_grupo_seq;
DROP SEQUENCE status_seq;
DROP SEQUENCE moto_seq;

-- Recriar as sequências com os valores corretos
CREATE SEQUENCE usuario_seq START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE zona_seq START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE patio_seq START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE status_grupo_seq START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE status_seq START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE moto_seq START WITH 100 INCREMENT BY 1;

COMMIT;