package com.mottu.sprint3.dto.response;

public class StatusResponse {
    private String id;
    private String nome;
    private StatusGrupoResponse statusGrupo;

    public StatusResponse() {
    }

    public StatusResponse(String id, String nome, StatusGrupoResponse statusGrupo) {
        this.id = id;
        this.nome = nome;
        this.statusGrupo = statusGrupo;
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

    public StatusGrupoResponse getStatusGrupo() {
        return statusGrupo;
    }

    public void setStatusGrupo(StatusGrupoResponse statusGrupo) {
        this.statusGrupo = statusGrupo;
    }
}
