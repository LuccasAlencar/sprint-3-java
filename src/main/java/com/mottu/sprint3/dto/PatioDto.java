package com.mottu.sprint3.dto;

import com.mottu.sprint3.model.Patio;

public class PatioDto {
    private Long id;
    private String nome;

    public PatioDto() {}

    public PatioDto(Patio patio) {
        this.id = patio.getId();
        this.nome = patio.getNome();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}