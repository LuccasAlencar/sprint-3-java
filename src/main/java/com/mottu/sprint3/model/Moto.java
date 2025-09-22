package com.mottu.sprint3.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "moto")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moto_seq")
    @SequenceGenerator(name = "moto_seq", sequenceName = "moto_seq", allocationSize = 1)
    private Long id;

    private String placa;
    private String chassi;
    private String qrCode;
    private Timestamp dataEntrada;
    private Timestamp previsaoEntrega;
    private String fotos;
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "zona_id")
    private Zona zona;

    @ManyToOne
    @JoinColumn(name = "patio_id")
    private Patio patio;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

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

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public Patio getPatio() {
		return patio;
	}

	public void setPatio(Patio patio) {
		this.patio = patio;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

    
}