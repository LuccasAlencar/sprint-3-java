-- V2: Inserção de usuários e roles (idempotente)
-- Senhas são bcrypt hash de "password"

BEGIN
    INSERT INTO usuario (usuario, senha, role)
    SELECT 'admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN' FROM dual
    WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE usuario = 'admin');
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    INSERT INTO usuario (usuario, senha, role)
    SELECT 'operador', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'OPERADOR' FROM dual
    WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE usuario = 'operador');
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

BEGIN
    INSERT INTO usuario (usuario, senha, role)
    SELECT 'user', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'USER' FROM dual
    WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE usuario = 'user');
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
