package com.mottu.sprint3.service;

import com.mottu.sprint3.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MotoProcedureService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String exibirMotosJson() {
        return jdbcTemplate.execute((Connection conn) -> {
            try (CallableStatement cs = conn.prepareCall("{call PKG_MOTO.exibir_json}")) {
                boolean hasResult = cs.execute();
                StringBuilder sb = new StringBuilder();
                if (hasResult) {
                    try (ResultSet rs = cs.getResultSet()) {
                        while (rs.next()) {
                            sb.append(rs.getString(1));
                        }
                    }
                }
                return sb.toString();
            }
        });
    }

    /**
     * Chama a procedure obter_relatorio_completo que retorna dados via REF CURSOR
     * @return RelatorioResponse com dados do relatório
     */
    public RelatorioResponse obterRelatorioViaProcedure() {
        return jdbcTemplate.execute((Connection conn) -> {
            RelatorioResponse response = new RelatorioResponse();
            
            // Maps para agregação de dados
            Map<Long, Integer> statusGrupoCount = new HashMap<>();
            Map<Long, String> statusGrupoNomes = new HashMap<>();
            Map<Long, Integer> statusCount = new HashMap<>();
            Map<Long, String> statusNomes = new HashMap<>();
            Map<Long, Long> statusToGrupo = new HashMap<>();
            Map<Long, String> statusGrupoNomesById = new HashMap<>();
            
            // Maps para zonas
            Map<Long, String> zonaNomes = new HashMap<>();
            Map<Long, String> zonaLetras = new HashMap<>();
            Map<Long, Map<Long, Integer>> zonaStatusGrupoCount = new HashMap<>();
            Map<Long, Map<Long, Integer>> zonaStatusCount = new HashMap<>();
            Map<Long, Integer> zonaTotalMotos = new HashMap<>();
            
            int totalMotos = 0;

            try (CallableStatement cs = conn.prepareCall("{call obter_relatorio_completo(?)}")) {
                cs.registerOutParameter(1, Types.REF_CURSOR);
                cs.execute();
                
                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    while (rs.next()) {
                        totalMotos++;
                        
                        Long statusGrupoId = rs.getLong("status_grupo_id");
                        String statusGrupoNome = rs.getString("status_grupo_nome");
                        Long statusId = rs.getLong("status_id");
                        String statusNome = rs.getString("status_nome");
                        Long zonaId = rs.getLong("zona_id");
                        String zonaNome = rs.getString("zona_nome");
                        String zonaLetra = rs.getString("zona_letra");
                        
                        // Contadores globais
                        statusGrupoCount.put(statusGrupoId, statusGrupoCount.getOrDefault(statusGrupoId, 0) + 1);
                        statusGrupoNomes.put(statusGrupoId, statusGrupoNome);
                        statusCount.put(statusId, statusCount.getOrDefault(statusId, 0) + 1);
                        statusNomes.put(statusId, statusNome);
                        statusToGrupo.put(statusId, statusGrupoId);
                        statusGrupoNomesById.put(statusGrupoId, statusGrupoNome);
                        
                        // Dados de zona
                        zonaNomes.put(zonaId, zonaNome);
                        zonaLetras.put(zonaId, zonaLetra);
                        zonaTotalMotos.put(zonaId, zonaTotalMotos.getOrDefault(zonaId, 0) + 1);
                        
                        // Contadores por zona
                        zonaStatusGrupoCount.putIfAbsent(zonaId, new HashMap<>());
                        zonaStatusCount.putIfAbsent(zonaId, new HashMap<>());
                        
                        Map<Long, Integer> zsgCount = zonaStatusGrupoCount.get(zonaId);
                        zsgCount.put(statusGrupoId, zsgCount.getOrDefault(statusGrupoId, 0) + 1);
                        
                        Map<Long, Integer> zsCount = zonaStatusCount.get(zonaId);
                        zsCount.put(statusId, zsCount.getOrDefault(statusId, 0) + 1);
                    }
                }
            }
            
            // Montar response global
            response.setTotalMotos(totalMotos);
            response.setTotalFinalizadas(0);
            
            // Total por status grupo
            List<TotalStatusGrupoResponse> totalStatusGrupo = new ArrayList<>();
            for (Map.Entry<Long, Integer> entry : statusGrupoCount.entrySet()) {
                totalStatusGrupo.add(new TotalStatusGrupoResponse(
                    String.valueOf(entry.getKey()),
                    statusGrupoNomes.get(entry.getKey()),
                    entry.getValue()
                ));
            }
            response.setTotalStatusGrupo(totalStatusGrupo);
            
            // Total por status
            List<TotalStatusResponse> totalStatus = new ArrayList<>();
            for (Map.Entry<Long, Integer> entry : statusCount.entrySet()) {
                Long statusId = entry.getKey();
                Long grupoId = statusToGrupo.get(statusId);
                StatusGrupoResponse sgr = new StatusGrupoResponse(
                    String.valueOf(grupoId),
                    statusGrupoNomesById.get(grupoId)
                );
                totalStatus.add(new TotalStatusResponse(
                    String.valueOf(statusId),
                    statusNomes.get(statusId),
                    entry.getValue(),
                    sgr
                ));
            }
            response.setTotalStatus(totalStatus);
            
            // Relatório por zona
            List<ZonasRelatorioResponse> zonasRelatorio = new ArrayList<>();
            for (Map.Entry<Long, String> zonaEntry : zonaNomes.entrySet()) {
                Long zonaId = zonaEntry.getKey();
                
                // Total por status grupo na zona
                List<TotalStatusGrupoResponse> totalStatusGrupoZona = new ArrayList<>();
                Map<Long, Integer> zsgCount = zonaStatusGrupoCount.get(zonaId);
                if (zsgCount != null) {
                    for (Map.Entry<Long, Integer> sgEntry : zsgCount.entrySet()) {
                        totalStatusGrupoZona.add(new TotalStatusGrupoResponse(
                            String.valueOf(sgEntry.getKey()),
                            statusGrupoNomes.get(sgEntry.getKey()),
                            sgEntry.getValue()
                        ));
                    }
                }
                
                // Total por status na zona
                List<TotalStatusResponse> totalStatusZona = new ArrayList<>();
                Map<Long, Integer> zsCount = zonaStatusCount.get(zonaId);
                if (zsCount != null) {
                    for (Map.Entry<Long, Integer> sEntry : zsCount.entrySet()) {
                        Long statusId = sEntry.getKey();
                        Long grupoId = statusToGrupo.get(statusId);
                        StatusGrupoResponse sgr = new StatusGrupoResponse(
                            String.valueOf(grupoId),
                            statusGrupoNomesById.get(grupoId)
                        );
                        totalStatusZona.add(new TotalStatusResponse(
                            String.valueOf(statusId),
                            statusNomes.get(statusId),
                            sEntry.getValue(),
                            sgr
                        ));
                    }
                }
                
                zonasRelatorio.add(new ZonasRelatorioResponse(
                    String.valueOf(zonaId),
                    zonaEntry.getValue(),
                    zonaLetras.get(zonaId),
                    zonaTotalMotos.get(zonaId),
                    totalStatusGrupoZona,
                    totalStatusZona
                ));
            }
            response.setZonas(zonasRelatorio);
            
            return response;
        });
    }
}
