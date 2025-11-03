package com.mottu.sprint3.dto.response;

public class TotalStatusResponse {
    private String id;
    private String nome;
    private int valor;
    private StatusGrupoResponse statusGrupo;

    public TotalStatusResponse() {
    }

    public TotalStatusResponse(String id, String nome, int valor, StatusGrupoResponse statusGrupo) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public StatusGrupoResponse getStatusGrupo() {
        return statusGrupo;
    }

    public void setStatusGrupo(StatusGrupoResponse statusGrupo) {
        this.statusGrupo = statusGrupo;
    }
}
