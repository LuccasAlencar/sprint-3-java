-- V4: Inserção de status e dados iniciais (idempotente)

-- Status para cada grupo
BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Recebida', 1 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Recebida'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Registrada', 1 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Registrada'); EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Em Inspeção', 2 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Em Inspeção'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Em Avaliação', 2 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Em Avaliação'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Documentação Pendente', 2 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Documentação Pendente'); EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Pronta para Entrega', 3 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Pronta para Entrega'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Entregue', 3 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Entregue'); EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Necessita Reparo', 4 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Necessita Reparo'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Em Reparo', 4 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Em Reparo'); EXCEPTION WHEN OTHERS THEN NULL; END;
/

BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Aguardando Cliente', 5 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Aguardando Cliente'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO status (nome, status_grupo_id) SELECT 'Aguardando Documentos', 5 FROM dual WHERE NOT EXISTS (SELECT 1 FROM status WHERE nome = 'Aguardando Documentos'); EXCEPTION WHEN OTHERS THEN NULL; END;
/

-- Zonas iniciais
BEGIN INSERT INTO zona (nome, letra) SELECT 'Zona A - Entrada', 'A' FROM dual WHERE NOT EXISTS (SELECT 1 FROM zona WHERE nome = 'Zona A - Entrada'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO zona (nome, letra) SELECT 'Zona B - Processamento', 'B' FROM dual WHERE NOT EXISTS (SELECT 1 FROM zona WHERE nome = 'Zona B - Processamento'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO zona (nome, letra) SELECT 'Zona C - Saída', 'C' FROM dual WHERE NOT EXISTS (SELECT 1 FROM zona WHERE nome = 'Zona C - Saída'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO zona (nome, letra) SELECT 'Zona D - Manutenção', 'D' FROM dual WHERE NOT EXISTS (SELECT 1 FROM zona WHERE nome = 'Zona D - Manutenção'); EXCEPTION WHEN OTHERS THEN NULL; END;
/

-- Pátios iniciais
BEGIN INSERT INTO patio (nome) SELECT 'Pátio Principal' FROM dual WHERE NOT EXISTS (SELECT 1 FROM patio WHERE nome = 'Pátio Principal'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO patio (nome) SELECT 'Pátio Secundário' FROM dual WHERE NOT EXISTS (SELECT 1 FROM patio WHERE nome = 'Pátio Secundário'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO patio (nome) SELECT 'Pátio de Manutenção' FROM dual WHERE NOT EXISTS (SELECT 1 FROM patio WHERE nome = 'Pátio de Manutenção'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN INSERT INTO patio (nome) SELECT 'Pátio de Entrada' FROM dual WHERE NOT EXISTS (SELECT 1 FROM patio WHERE nome = 'Pátio de Entrada'); EXCEPTION WHEN OTHERS THEN NULL; END;
/
