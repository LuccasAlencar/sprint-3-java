package com.mottu.sprint3.dto.response;

import java.util.List;

public class RelatorioResponse {
    private int totalMotos;
    private int totalFinalizadas;
    private List<TotalStatusGrupoResponse> totalStatusGrupo;
    private List<TotalStatusResponse> totalStatus;
    private List<ZonasRelatorioResponse> zonas;

    public RelatorioResponse() {
    }

    public int getTotalMotos() {
        return totalMotos;
    }

    public void setTotalMotos(int totalMotos) {
        this.totalMotos = totalMotos;
    }

    public int getTotalFinalizadas() {
        return totalFinalizadas;
    }

    public void setTotalFinalizadas(int totalFinalizadas) {
        this.totalFinalizadas = totalFinalizadas;
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

    public List<ZonasRelatorioResponse> getZonas() {
        return zonas;
    }

    public void setZonas(List<ZonasRelatorioResponse> zonas) {
        this.zonas = zonas;
    }
}
