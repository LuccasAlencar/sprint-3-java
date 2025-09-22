package com.mottu.sprint3.dto;

import com.mottu.sprint3.model.Zona;

public class ZonaDto {
    private Long id;
    private String nome;
    private String letra;

    public ZonaDto() {}

    public ZonaDto(Zona zona) {
        this.id = zona.getId();
        this.nome = zona.getNome();
        this.letra = zona.getLetra();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLetra() { return letra; }
    public void setLetra(String letra) { this.letra = letra; }
}