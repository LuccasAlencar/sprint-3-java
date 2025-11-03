package com.mottu.sprint3.dto.request;

import java.util.List;

public class MotoUpdateRequest {
    private ZonaRequestUpdate zona;
    private StatusRequestUpdate status;
    private List<String> observacoes;

    public MotoUpdateRequest() {
    }

    public ZonaRequestUpdate getZona() {
        return zona;
    }

    public void setZona(ZonaRequestUpdate zona) {
        this.zona = zona;
    }

    public StatusRequestUpdate getStatus() {
        return status;
    }

    public void setStatus(StatusRequestUpdate status) {
        this.status = status;
    }

    public List<String> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<String> observacoes) {
        this.observacoes = observacoes;
    }
}

