package com.mottu.sprint3.controller;

import com.mottu.sprint3.dto.request.MotoCreateRequest;
import com.mottu.sprint3.dto.request.MotoUpdateRequest;
import com.mottu.sprint3.dto.response.MotoResponse;
import com.mottu.sprint3.model.Moto;
import com.mottu.sprint3.repository.MotoRepository;
import com.mottu.sprint3.repository.PatioRepository;
import com.mottu.sprint3.repository.StatusRepository;
import com.mottu.sprint3.repository.ZonaRepository;
import com.mottu.sprint3.service.MotoMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/motos")
public class MotoRestController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private MotoMapperService motoMapperService;

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private StatusRepository statusRepository;

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
    private LocalDateTime parseDateTime(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            // Tenta ISO_INSTANT primeiro (com Z no final)
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e1) {
            try {
                // Tenta formato padrão
                return LocalDateTime.parse(dateStr, ISO_FORMATTER);
            } catch (Exception e2) {
                try {
                    // Tenta remover o Z e parsear novamente
                    String cleanDate = dateStr.replace("Z", "").replaceAll("\\.\\d+", "");
                    return LocalDateTime.parse(cleanDate, ISO_FORMATTER);
                } catch (Exception e3) {
                    // Log o erro e retorna null
                    System.err.println("Erro ao parsear data: " + dateStr);
                    e3.printStackTrace();
                    return null;
                }
            }
        }
    }

    // Endpoint principal usado pelo mobile para listar todas as motos
    @GetMapping("/json")
    public ResponseEntity<List<MotoResponse>> getMotosJson() {
        List<Moto> motos = motoRepository.findAll();
        List<MotoResponse> response = motoMapperService.toMotoResponseList(motos);
        return ResponseEntity.ok(response);
    }

    // Endpoint para buscar moto por ID
    @GetMapping("/{id}")
    public ResponseEntity<MotoResponse> getMotoById(@PathVariable Long id) {
        Optional<Moto> moto = motoRepository.findById(id);
        if (moto.isPresent()) {
            MotoResponse response = motoMapperService.toMotoResponse(moto.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para buscar moto por identificador (placa, chassi ou QR code)
    @GetMapping("/identificador/{valor}")
    public ResponseEntity<MotoResponse> getMotoByIdentificador(@PathVariable String valor) {
        // Busca por placa, chassi ou qrCode
        List<Moto> motos = motoRepository.findAll();
        Optional<Moto> motoOpt = motos.stream()
                .filter(m -> 
                    (m.getPlaca() != null && m.getPlaca().equalsIgnoreCase(valor)) ||
                    (m.getChassi() != null && m.getChassi().equalsIgnoreCase(valor)) ||
                    (m.getQrCode() != null && m.getQrCode().equalsIgnoreCase(valor))
                )
                .findFirst();
        
        if (motoOpt.isPresent()) {
            MotoResponse response = motoMapperService.toMotoResponse(motoOpt.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para criar nova moto
    @PostMapping
    public ResponseEntity<MotoResponse> createMoto(@RequestBody MotoCreateRequest request) {
        try {
            Moto moto = new Moto();

            // Converter identificador para placa, chassi ou qrCode
            // Campos placa e chassi são NOT NULL e UNIQUE no banco
            String timestamp = String.valueOf(System.currentTimeMillis());
            
            if (request.getIdentificador() != null) {
                String tipo = request.getIdentificador().getTipo();
                String valor = request.getIdentificador().getValor();
                
                if ("PLACA".equalsIgnoreCase(tipo)) {
                    moto.setPlaca(valor);
                    // Gerar chassi único (obrigatório no banco)
                    moto.setChassi("AUTO-" + timestamp);
                    moto.setQrCode(null); // Pode ser null
                } else if ("CHASSI".equalsIgnoreCase(tipo)) {
                    moto.setChassi(valor);
                    // Gerar placa única (obrigatório no banco)
                    moto.setPlaca("AUTO" + timestamp.substring(timestamp.length() - 7));
                    moto.setQrCode(null); // Pode ser null
                } else if ("QRCODE".equalsIgnoreCase(tipo)) {
                    moto.setQrCode(valor);
                    // Gerar placa e chassi únicos (obrigatórios no banco)
                    moto.setPlaca("AUTO" + timestamp.substring(timestamp.length() - 7));
                    moto.setChassi("AUTO-" + timestamp);
                }
            }

            // Converter datas
            if (request.getDataEntrada() != null && !request.getDataEntrada().isEmpty()) {
                LocalDateTime dataEntrada = parseDateTime(request.getDataEntrada());
                if (dataEntrada != null) {
                    moto.setDataEntrada(Timestamp.valueOf(dataEntrada));
                } else {
                    moto.setDataEntrada(Timestamp.valueOf(LocalDateTime.now()));
                }
            } else {
                moto.setDataEntrada(Timestamp.valueOf(LocalDateTime.now()));
            }

            if (request.getPrevisaoEntrega() != null && !request.getPrevisaoEntrega().isEmpty()) {
                LocalDateTime previsaoEntrega = parseDateTime(request.getPrevisaoEntrega());
                if (previsaoEntrega != null) {
                    moto.setPrevisaoEntrega(Timestamp.valueOf(previsaoEntrega));
                }
            }

            // Converter fotos de array para string
            if (request.getFotos() != null && !request.getFotos().isEmpty()) {
                moto.setFotos(String.join(",", request.getFotos()));
            }

            // Converter modelo
            if (request.getModelo() != null && request.getModelo().getId() != null) {
                String modeloId = request.getModelo().getId();
                switch (modeloId) {
                    case "1":
                        moto.setModelo("MOTTU-E");
                        break;
                    case "2":
                        moto.setModelo("POP");
                        break;
                    case "3":
                        moto.setModelo("SPORT-ESD");
                        break;
                    default:
                        moto.setModelo("MOTTU-E");
                }
            }

            // Converter relacionamentos
            if (request.getZona() != null && request.getZona().getId() != null) {
                moto.setZona(zonaRepository.findById(Long.parseLong(request.getZona().getId())).orElse(null));
            }

            if (request.getPatio() != null && request.getPatio().getId() != null) {
                moto.setPatio(patioRepository.findById(Long.parseLong(request.getPatio().getId())).orElse(null));
            }

            if (request.getStatus() != null && request.getStatus().getId() != null) {
                moto.setStatus(statusRepository.findById(Long.parseLong(request.getStatus().getId())).orElse(null));
            }

            // Converter observações de array para string
            if (request.getObservacoes() != null && !request.getObservacoes().isEmpty()) {
                moto.setObservacoes(String.join("\n", request.getObservacoes()));
            }

            // Log para debug
            System.out.println("=== SALVANDO MOTO ===");
            System.out.println("Placa: " + moto.getPlaca());
            System.out.println("Chassi: " + moto.getChassi());
            System.out.println("QR Code: " + moto.getQrCode());
            System.out.println("Modelo: " + moto.getModelo());
            System.out.println("Zona ID: " + (moto.getZona() != null ? moto.getZona().getId() : "null"));
            System.out.println("Patio ID: " + (moto.getPatio() != null ? moto.getPatio().getId() : "null"));
            System.out.println("Status ID: " + (moto.getStatus() != null ? moto.getStatus().getId() : "null"));

            Moto savedMoto = motoRepository.save(moto);
            MotoResponse response = motoMapperService.toMotoResponse(savedMoto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.err.println("=== ERRO AO SALVAR MOTO ===");
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para atualizar moto
    @PutMapping("/{id}")
    public ResponseEntity<MotoResponse> updateMoto(@PathVariable Long id, @RequestBody MotoUpdateRequest request) {
        Optional<Moto> motoOpt = motoRepository.findById(id);
        if (!motoOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Moto moto = motoOpt.get();

        // Atualizar zona
        if (request.getZona() != null && request.getZona().getId() != null) {
            moto.setZona(zonaRepository.findById(Long.parseLong(request.getZona().getId())).orElse(null));
        }

        // Atualizar status
        if (request.getStatus() != null && request.getStatus().getId() != null) {
            moto.setStatus(statusRepository.findById(Long.parseLong(request.getStatus().getId())).orElse(null));
        }

        // Atualizar observações
        if (request.getObservacoes() != null) {
            moto.setObservacoes(String.join("\n", request.getObservacoes()));
        }

        Moto updatedMoto = motoRepository.save(moto);
        MotoResponse response = motoMapperService.toMotoResponse(updatedMoto);
        return ResponseEntity.ok(response);
    }

    // Endpoint para deletar moto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoto(@PathVariable Long id) {
        if (motoRepository.existsById(id)) {
            motoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para finalizar moto (mesmo comportamento que delete por enquanto)
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarMoto(@PathVariable Long id) {
        if (motoRepository.existsById(id)) {
            motoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
