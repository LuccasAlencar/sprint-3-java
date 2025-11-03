package com.mottu.sprint3.dto.response;

public class PatioResponse {
    private String id;
    private String nome;

    public PatioResponse() {
    }

    public PatioResponse(String id, String nome) {
        this.id = id;
        this.nome = nome;
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
}
