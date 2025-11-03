package com.mottu.sprint3.dto.response;

public class ZonaResponse {
    private String id;
    private String nome;
    private String letra;

    public ZonaResponse() {
    }

    public ZonaResponse(String id, String nome, String letra) {
        this.id = id;
        this.nome = nome;
        this.letra = letra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }
}
