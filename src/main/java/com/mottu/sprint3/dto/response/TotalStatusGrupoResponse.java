package com.mottu.sprint3.dto.response;

public class TotalStatusGrupoResponse {
    private String id;
    private String nome;
    private int valor;

    public TotalStatusGrupoResponse() {
    }

    public TotalStatusGrupoResponse(String id, String nome, int valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
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
}
