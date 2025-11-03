package com.mottu.sprint3.dto.response;

public class StatusGrupoResponse {
    private String id;
    private String nome;

    public StatusGrupoResponse() {
    }

    public StatusGrupoResponse(String id, String nome) {
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
