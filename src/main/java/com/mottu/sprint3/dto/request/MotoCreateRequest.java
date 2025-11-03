package com.mottu.sprint3.dto.request;

import java.util.List;

public class MotoCreateRequest {
    private IdentificadorRequest identificador;
    private String dataEntrada;
    private String previsaoEntrega;
    private List<String> fotos;
    private ModeloRequest modelo;
    private ZonaRequest zona;
    private PatioRequest patio;
    private StatusRequest status;
    private List<String> observacoes;

    public MotoCreateRequest() {
    }

    public IdentificadorRequest getIdentificador() {
        return identificador;
    }

    public void setIdentificador(IdentificadorRequest identificador) {
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

    public ModeloRequest getModelo() {
        return modelo;
    }

    public void setModelo(ModeloRequest modelo) {
        this.modelo = modelo;
    }

    public ZonaRequest getZona() {
        return zona;
    }

    public void setZona(ZonaRequest zona) {
        this.zona = zona;
    }

    public PatioRequest getPatio() {
        return patio;
    }

    public void setPatio(PatioRequest patio) {
        this.patio = patio;
    }

    public StatusRequest getStatus() {
        return status;
    }

    public void setStatus(StatusRequest status) {
        this.status = status;
    }

    public List<String> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<String> observacoes) {
        this.observacoes = observacoes;
    }
}

