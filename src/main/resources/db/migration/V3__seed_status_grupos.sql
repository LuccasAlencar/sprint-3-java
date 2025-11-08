-- V3: Inserção de grupos de status (idempotente)
BEGIN
    INSERT INTO status_grupo (nome) 
    SELECT 'Entrada' FROM dual 
    WHERE NOT EXISTS (SELECT 1 FROM status_grupo WHERE nome = 'Entrada');
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    INSERT INTO status_grupo (nome) 
    SELECT 'Processamento' FROM dual 
    WHERE NOT EXISTS (SELECT 1 FROM status_grupo WHERE nome = 'Processamento');
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    INSERT INTO status_grupo (nome) 
    SELECT 'Saída' FROM dual 
    WHERE NOT EXISTS (SELECT 1 FROM status_grupo WHERE nome = 'Saída');
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    INSERT INTO status_grupo (nome) 
    SELECT 'Manutenção' FROM dual 
    WHERE NOT EXISTS (SELECT 1 FROM status_grupo WHERE nome = 'Manutenção');
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    INSERT INTO status_grupo (nome) 
    SELECT 'Aguardando' FROM dual 
    WHERE NOT EXISTS (SELECT 1 FROM status_grupo WHERE nome = 'Aguardando');
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
