package com.mottu.sprint3.controller;

import com.mottu.sprint3.dto.response.*;
import com.mottu.sprint3.model.Status;
import com.mottu.sprint3.model.StatusGrupo;
import com.mottu.sprint3.model.Zona;
import com.mottu.sprint3.repository.StatusGrupoRepository;
import com.mottu.sprint3.repository.StatusRepository;
import com.mottu.sprint3.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EssenciaisRestController {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusGrupoRepository statusGrupoRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    @GetMapping("/essenciais")
    public ResponseEntity<EssenciaisResponse> getEssenciais() {
        EssenciaisResponse response = new EssenciaisResponse();

        // Modelos hardcoded (já que não existe tabela de modelos)
        List<ModeloResponse> modelos = new ArrayList<>();
        modelos.add(new ModeloResponse("1", "MOTTU-E"));
        modelos.add(new ModeloResponse("2", "POP"));
        modelos.add(new ModeloResponse("3", "SPORT-ESD"));
        response.setModelos(modelos);

        // Status
        List<Status> statusList = statusRepository.findAll();
        List<StatusResponse> statusResponses = statusList.stream()
                .map(s -> {
                    StatusGrupoResponse sgr = null;
                    if (s.getStatusGrupo() != null) {
                        sgr = new StatusGrupoResponse(
                                String.valueOf(s.getStatusGrupo().getId()),
                                s.getStatusGrupo().getNome()
                        );
                    }
                    return new StatusResponse(
                            String.valueOf(s.getId()),
                            s.getNome(),
                            sgr
                    );
                })
                .collect(Collectors.toList());
        response.setStatus(statusResponses);

        // Status Grupos
        List<StatusGrupo> statusGrupoList = statusGrupoRepository.findAll();
        List<StatusGrupoResponse> statusGrupoResponses = statusGrupoList.stream()
                .map(sg -> new StatusGrupoResponse(
                        String.valueOf(sg.getId()),
                        sg.getNome()
                ))
                .collect(Collectors.toList());
        response.setStatusGrupos(statusGrupoResponses);

        // Zonas
        List<Zona> zonaList = zonaRepository.findAll();
        List<ZonaResponse> zonaResponses = zonaList.stream()
                .map(z -> new ZonaResponse(
                        String.valueOf(z.getId()),
                        z.getNome(),
                        z.getLetra()
                ))
                .collect(Collectors.toList());
        response.setZonas(zonaResponses);

        return ResponseEntity.ok(response);
    }
}
