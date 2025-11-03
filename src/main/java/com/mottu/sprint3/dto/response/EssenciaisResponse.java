package com.mottu.sprint3.dto.response;

import java.util.List;

public class EssenciaisResponse {
    private List<ModeloResponse> modelos;
    private List<StatusResponse> status;
    private List<StatusGrupoResponse> statusGrupos;
    private List<ZonaResponse> zonas;

    public EssenciaisResponse() {
    }

    public List<ModeloResponse> getModelos() {
        return modelos;
    }

    public void setModelos(List<ModeloResponse> modelos) {
        this.modelos = modelos;
    }

    public List<StatusResponse> getStatus() {
        return status;
    }

    public void setStatus(List<StatusResponse> status) {
        this.status = status;
    }

    public List<StatusGrupoResponse> getStatusGrupos() {
        return statusGrupos;
    }

    public void setStatusGrupos(List<StatusGrupoResponse> statusGrupos) {
        this.statusGrupos = statusGrupos;
    }

    public List<ZonaResponse> getZonas() {
        return zonas;
    }

    public void setZonas(List<ZonaResponse> zonas) {
        this.zonas = zonas;
    }
}
