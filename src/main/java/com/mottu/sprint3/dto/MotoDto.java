package com.mottu.sprint3.dto;

import com.mottu.sprint3.model.Moto;
import com.mottu.sprint3.model.Patio;
import com.mottu.sprint3.model.Status;
import com.mottu.sprint3.model.Zona;

import java.sql.Timestamp;

public class MotoDto {

    private Long id;
    private String placa;
    private String chassi;
    private String qrCode;
    private Timestamp dataEntrada;
    private Timestamp previsaoEntrega;
    private String fotos;
    private String observacoes;
    private Long zonaId;
    private Long patioId;
    private Long statusId;

    // Construtor para facilitar a conversão de Model para DTO (útil para o formulário de edição)
    public MotoDto(Moto moto) {
        this.id = moto.getId();
        this.placa = moto.getPlaca();
        this.chassi = moto.getChassi();
        this.qrCode = moto.getQrCode();
        this.dataEntrada = moto.getDataEntrada();
        this.previsaoEntrega = moto.getPrevisaoEntrega();
        this.fotos = moto.getFotos();
        this.observacoes = moto.getObservacoes();
        if (moto.getZona() != null) {
            this.zonaId = moto.getZona().getId();
        }
        if (moto.getPatio() != null) {
            this.patioId = moto.getPatio().getId();
        }
        if (moto.getStatus() != null) {
            this.statusId = moto.getStatus().getId();
        }
    }

    public MotoDto() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Timestamp getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Timestamp dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Timestamp getPrevisaoEntrega() {
        return previsaoEntrega;
    }

    public void setPrevisaoEntrega(Timestamp previsaoEntrega) {
        this.previsaoEntrega = previsaoEntrega;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getZonaId() {
        return zonaId;
    }

    public void setZonaId(Long zonaId) {
        this.zonaId = zonaId;
    }

    public Long getPatioId() {
        return patioId;
    }

    public void setPatioId(Long patioId) {
        this.patioId = patioId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}