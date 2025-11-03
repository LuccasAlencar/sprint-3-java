package com.mottu.sprint3.controller;

import com.mottu.sprint3.dto.response.RelatorioResponse;
import com.mottu.sprint3.service.MotoProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RelatorioRestController {

    @Autowired
    private MotoProcedureService motoProcedureService;

    /**
     * Endpoint que retorna relatório completo usando procedure SQL
     * A procedure obter_relatorio_completo é chamada via REF CURSOR
     */
    @GetMapping("/relatorio")
    public ResponseEntity<RelatorioResponse> getRelatorio() {
        // Chama a procedure SQL obter_relatorio_completo via MotoProcedureService
        RelatorioResponse response = motoProcedureService.obterRelatorioViaProcedure();
        return ResponseEntity.ok(response);
    }
}
