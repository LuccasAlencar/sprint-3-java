package com.mottu.sprint3.service;

import com.mottu.sprint3.dto.response.*;
import com.mottu.sprint3.model.*;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotoMapperService {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public MotoResponse toMotoResponse(Moto moto) {
        MotoResponse response = new MotoResponse();
        
        response.setId(String.valueOf(moto.getId()));
        
        // Converter identificador (placa, chassi ou qrCode)
        response.setIdentificador(createIdentificador(moto));
        
        // Converter datas para ISO format
        if (moto.getDataEntrada() != null) {
            response.setDataEntrada(moto.getDataEntrada().toLocalDateTime().format(ISO_FORMATTER));
        }
        
        if (moto.getPrevisaoEntrega() != null) {
            response.setPrevisaoEntrega(moto.getPrevisaoEntrega().toLocalDateTime().format(ISO_FORMATTER));
        }
        
        // Converter fotos de string para array
        response.setFotos(convertStringToList(moto.getFotos()));
        
        // Converter modelo
        response.setModelo(createModeloResponse(moto.getModelo()));
        
        // Converter zona
        if (moto.getZona() != null) {
            response.setZona(new ZonaResponse(
                String.valueOf(moto.getZona().getId()),
                moto.getZona().getNome(),
                moto.getZona().getLetra()
            ));
        }
        
        // Converter patio
        if (moto.getPatio() != null) {
            response.setPatio(new PatioResponse(
                String.valueOf(moto.getPatio().getId()),
                moto.getPatio().getNome()
            ));
        }
        
        // Converter status
        if (moto.getStatus() != null) {
            StatusGrupoResponse statusGrupoResponse = null;
            if (moto.getStatus().getStatusGrupo() != null) {
                statusGrupoResponse = new StatusGrupoResponse(
                    String.valueOf(moto.getStatus().getStatusGrupo().getId()),
                    moto.getStatus().getStatusGrupo().getNome()
                );
            }
            
            response.setStatus(new StatusResponse(
                String.valueOf(moto.getStatus().getId()),
                moto.getStatus().getNome(),
                statusGrupoResponse
            ));
        }
        
        // Converter observações de string para array
        response.setObservacoes(convertStringToList(moto.getObservacoes()));
        
        return response;
    }

    private IdentificadorResponse createIdentificador(Moto moto) {
        // Prioridade: Placa > Chassi > QR Code
        // Ignora valores AUTO gerados (que começam com "AUTO")
        String tipo;
        String valor;
        
        boolean placaReal = moto.getPlaca() != null && !moto.getPlaca().trim().isEmpty() 
                            && !moto.getPlaca().startsWith("AUTO");
        boolean chassiReal = moto.getChassi() != null && !moto.getChassi().trim().isEmpty() 
                             && !moto.getChassi().startsWith("AUTO");
        boolean qrCodeReal = moto.getQrCode() != null && !moto.getQrCode().trim().isEmpty();
        
        if (placaReal) {
            tipo = "PLACA";
            valor = moto.getPlaca();
        } else if (chassiReal) {
            tipo = "CHASSI";
            valor = moto.getChassi();
        } else if (qrCodeReal) {
            tipo = "QRCODE";
            valor = moto.getQrCode();
        } else {
            // Fallback para placa se nada for encontrado
            tipo = "PLACA";
            valor = moto.getPlaca() != null ? moto.getPlaca() : "";
        }
        
        return new IdentificadorResponse(
            String.valueOf(moto.getId()),
            tipo,
            valor
        );
    }

    private ModeloResponse createModeloResponse(String modeloNome) {
        if (modeloNome == null || modeloNome.trim().isEmpty()) {
            return new ModeloResponse("1", "MOTTU-E");
        }
        
        // Mapear nome do modelo para ID (baseado nos dados comuns)
        String id = "1";
        switch (modeloNome.toUpperCase()) {
            case "MOTTU-E":
                id = "1";
                break;
            case "POP":
                id = "2";
                break;
            case "SPORT-ESD":
                id = "3";
                break;
            default:
                id = "1";
        }
        
        return new ModeloResponse(id, modeloNome);
    }

    private List<String> convertStringToList(String str) {
        if (str == null || str.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // Se for JSON-like, tenta extrair
        if (str.startsWith("[") && str.endsWith("]")) {
            str = str.substring(1, str.length() - 1);
        }
        
        // Separa por vírgula ou quebra de linha
        return Arrays.stream(str.split("[,\n]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public List<MotoResponse> toMotoResponseList(List<Moto> motos) {
        return motos.stream()
                .map(this::toMotoResponse)
                .collect(Collectors.toList());
    }
}
