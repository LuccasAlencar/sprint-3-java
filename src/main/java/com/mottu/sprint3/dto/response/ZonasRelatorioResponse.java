package com.mottu.sprint3.dto.response;

import java.util.List;

public class ZonasRelatorioResponse {
    private String id;
    private String nome;
    private String letra;
    private int totalMotos;
    private List<TotalStatusGrupoResponse> totalStatusGrupo;
    private List<TotalStatusResponse> totalStatus;

    public ZonasRelatorioResponse() {
    }

    public ZonasRelatorioResponse(String id, String nome, String letra, int totalMotos,
                                   List<TotalStatusGrupoResponse> totalStatusGrupo,
                                   List<TotalStatusResponse> totalStatus) {
        this.id = id;
        this.nome = nome;
        this.letra = letra;
        this.totalMotos = totalMotos;
        this.totalStatusGrupo = totalStatusGrupo;
        this.totalStatus = totalStatus;
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

    public int getTotalMotos() {
        return totalMotos;
    }

    public void setTotalMotos(int totalMotos) {
        this.totalMotos = totalMotos;
    }

    public List<TotalStatusGrupoResponse> getTotalStatusGrupo() {
        return totalStatusGrupo;
    }

    public void setTotalStatusGrupo(List<TotalStatusGrupoResponse> totalStatusGrupo) {
        this.totalStatusGrupo = totalStatusGrupo;
    }

    public List<TotalStatusResponse> getTotalStatus() {
        return totalStatus;
    }

    public void setTotalStatus(List<TotalStatusResponse> totalStatus) {
        this.totalStatus = totalStatus;
    }
}
