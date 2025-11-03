package com.mottu.sprint3.dto.response;

import java.util.ArrayList;
import java.util.List;

public class MotoResponse {
    private String id;
    private IdentificadorResponse identificador;
    private String dataEntrada;
    private String previsaoEntrega;
    private List<String> fotos = new ArrayList<>();
    private ModeloResponse modelo;
    private ZonaResponse zona;
    private PatioResponse patio;
    private StatusResponse status;
    private List<String> observacoes = new ArrayList<>();

    public MotoResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IdentificadorResponse getIdentificador() {
        return identificador;
    }

    public void setIdentificador(IdentificadorResponse identificador) {
        this.identificador = identificador;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getPrevisaoEntrega() {
        return previsaoEntrega;
    }

    public void setPrevisaoEntrega(String previsaoEntrega) {
        this.previsaoEntrega = previsaoEntrega;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public ModeloResponse getModelo() {
        return modelo;
    }

    public void setModelo(ModeloResponse modelo) {
        this.modelo = modelo;
    }

    public ZonaResponse getZona() {
        return zona;
    }

    public void setZona(ZonaResponse zona) {
        this.zona = zona;
    }

    public PatioResponse getPatio() {
        return patio;
    }

    public void setPatio(PatioResponse patio) {
        this.patio = patio;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public List<String> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<String> observacoes) {
        this.observacoes = observacoes;
    }
}
