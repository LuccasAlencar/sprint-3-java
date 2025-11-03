-- =============================================
-- PROCEDURE: obter_relatorio_completo
-- =============================================

CREATE OR REPLACE PROCEDURE obter_relatorio_completo(
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT 
            m.id,
            m.placa,
            m.chassi,
            m.qr_code,
            TO_CHAR(m.data_entrada, 'YYYY-MM-DD"T"HH24:MI:SS') as data_entrada,
            TO_CHAR(m.previsao_entrega, 'YYYY-MM-DD"T"HH24:MI:SS') as previsao_entrega,
            m.fotos,
            m.observacoes,
            m.valor_servico,
            m.modelo,
            s.id as status_id,
            s.nome as status_nome,
            s.cor as status_cor,
            sg.id as status_grupo_id,
            sg.nome as status_grupo_nome,
            sg.descricao as status_grupo_descricao,
            z.id as zona_id,
            z.nome as zona_nome,
            z.letra as zona_letra,
            p.id as patio_id,
            p.nome as patio_nome,
            p.capacidade as patio_capacidade
        FROM moto m
        JOIN status s ON m.status_id = s.id
        JOIN status_grupo sg ON s.status_grupo_id = sg.id
        JOIN zona z ON m.zona_id = z.id
        JOIN patio p ON m.patio_id = p.id
        ORDER BY m.id;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Nenhuma moto encontrada');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20002, 'Erro ao gerar relatório: ' || SQLERRM);
END obter_relatorio_completo;
/
